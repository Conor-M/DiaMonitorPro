package uk.ac.ulster.mur.diamonitor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
/**
 * Activity to add Insulin records to the insulin table in the database
 *
 *
 * @author  Conor Murphy
 * @version 1.0
 * @since   2018-1-20
 *
 */
public class AddInsulin extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private MyDBHandler dbHandler;
    private EditText InsulinUnitsET;
    Button btnPick;
    int day,month,year,hour,minute,timeSet; // current time/date for initialising calendar
    int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;// set time/date
    /**
     * Creates the view of the activity when the activity is first started
     * sets title of the activity to be displayed
     *
     * @param savedInstanceState Required as is an implementation of the onClick defined in xml for this activit
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Insulin Record");
        setContentView(R.layout.activity_add_insulin);
        InsulinUnitsET = findViewById(R.id.InsulinReadingEditText);
        dbHandler = new MyDBHandler(this, null, null, 1);


        InsulinUnitsET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
    }
    /**
     * When an area of the screen is clicked this minimises the keyboard so that it doesnt interupt the workflow of the
     * application
     *
     * @param view Required as is an implementation of onClick
     * @return void
     */
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     * Called when add button is clicked and create a new record in the insulin records
     * Performs validation to ensure field is not empty and date is not set in the future
     *
     * @param view Required as is an implementation of the onClick defined in xml for this activity
     * @return void
     */
    public void addButtonClicked(View view){
        if ("".equals(InsulinUnitsET.getText().toString().trim())){
            Toast.makeText(this, "You must enter a unit value for insulin", Toast.LENGTH_LONG).show();
        }else {
            //Create new Insulin Object
            Insulin insulin = new Insulin();
            //Set Insulin Object Values
            insulin.setUnits(Integer.valueOf(InsulinUnitsET.getText().toString()));
            if(timeSet==1){
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, yearFinal);
                c.set(Calendar.MONTH, monthFinal);
                c.set(Calendar.DAY_OF_MONTH, dayFinal);
                c.set(Calendar.HOUR_OF_DAY, hourFinal);
                c.set(Calendar.MINUTE, minuteFinal);
                insulin.setTime(c.getTimeInMillis());
            }else{
                insulin.setTime(System.currentTimeMillis());
            }
            //Pass Insulin object to DBHandler
            //Add blood Object values to database fields
            if(System.currentTimeMillis()+1>insulin.getTime()) { // checks if time is set in future +1 as the time can be set to now
                dbHandler.addInsulin(insulin);
                //Clear Insulin ET Fields for next entry
                InsulinUnitsET.setText("");
                //Inform User of addition
                Toast.makeText(AddInsulin.this,
                        "Insulin added to Diary " + dbHandler.StringEpochToStringDate(String.valueOf(insulin.getTime())), Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(this, "Your record cannot be set in the future", Toast.LENGTH_LONG).show();
            }

        }
    }
    /**
     * Brings the user back to the home activity on click of the button
     * @param view Required as is an implementation of the onClick defined in xml for this activity
     */
    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    /**
     * Called when btnPick is clicked which brings up the datepicker dialog
     *
     * @param view Required as is an implementation of the onClick defined in xml for this activity
     * @return void
     */
    public void PickDateTimeClicked(View view){
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddInsulin.this, AddInsulin.this, year, month, day);
        datePickerDialog.show();
    }
    /**
     * Method handles the dates chosen by the datepicker dialog that allows the user to specify the
     * date on which the record was taken. The datepicker dialog then brings up timepicker dialog when ok is clicked
     * @param datePicker datepicker object that is used by the dialog
     * @param i Year chosen by the datepicker object
     * @param i1 Month chosen by the datepicker object
     * @param i2 Day choosen by the datepicker object
     */
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal= i1;
        dayFinal = i2;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddInsulin.this, AddInsulin.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }
    /**
     * Method handles the dates chosen by the timepicker dialog
     * Method on completion sets timeset variable to 1 showing that date and time were both set
     * @param timePicker Required as is an implementation of the onClick defined in xml for this activity
     * @param i Year chosen by the datepicker object
     * @param i1 Month chosen by the datepicker object
     */
   @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        timePicker.setIs24HourView(true);
        hourFinal = i;
        minuteFinal = i1;
        timeSet = 1;
    }
}
