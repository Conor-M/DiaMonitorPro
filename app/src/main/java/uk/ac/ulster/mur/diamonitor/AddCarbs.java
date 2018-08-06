package uk.ac.ulster.mur.diamonitor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
/**
 * Activity to add carbs records to the Carbs table in the database
 *
 *
 * @author  Conor Murphy
 * @version 1.0
 * @since   2018-1-20
 *
 */
public class AddCarbs extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private MyDBHandler dbHandler;
    private EditText carbReadingET;
    private int day,month,year,hour,minute,timeSet; // current time/date for initialising calendar
    private int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;// set time/date

    /**
     * Creates the view of the activity when the activity is first started
     *
     * @param savedInstanceState Required as is an implementation of the onClick defined in xml for this activit
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Carb Record");
        setContentView(R.layout.activity_add_carbs);
        carbReadingET = findViewById(R.id.carbReadingEditText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        carbReadingET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
     * Brings the user to the food search activity
     *
     * @param view
     */
    public void searchButtonClicked(View view){
        Intent i = new Intent(this, Search.class);
        startActivity(i);
    }
    /**
     * Called when add button is clicked and create a new record in the carb records
     * Performs validation to ensure field is not empty and date is not set in the future
     *
     * @param view Required as is an implementation of the onClick defined in xml for this activity
     * @return void
     */
    public void addButtonClicked(View view){
        if ("".equals(carbReadingET.getText().toString().trim())){
            Toast.makeText(this, "You must enter the amount of carbs", Toast.LENGTH_LONG).show();
        }else {
            //Create new Carb Object
            Carbs carbs = new Carbs();
            //Set values of carb object to values of Edit text
            carbs.setAmount(Integer.valueOf(carbReadingET.getText().toString()));
            if(timeSet==1){
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, yearFinal);
                c.set(Calendar.MONTH, monthFinal);
                c.set(Calendar.DAY_OF_MONTH, dayFinal);
                c.set(Calendar.HOUR_OF_DAY, hourFinal);
                c.set(Calendar.MINUTE, minuteFinal);
                carbs.setTime(c.getTimeInMillis());
            }else{
                carbs.setTime(System.currentTimeMillis());
            }
            //pass Carb object DbHandler to put values in database
            // checks if time is set in future +1, one is added to the time as the time can be set to now
            if(System.currentTimeMillis()+1>carbs.getTime()) {
                dbHandler.addCarbs(carbs);


                //Inform User of addition
                Toast.makeText(AddCarbs.this,
                        "Carbohydrates added to Diary " +
                                dbHandler.StringEpochToStringDate(String.valueOf(carbs.getTime())), Toast.LENGTH_LONG).show();
                ShowDialog(Integer.valueOf(carbReadingET.getText().toString()));
                //Clear Values from edittext for next entry
                carbReadingET.setText("");
            }else
                Toast.makeText(this, "Your record cannot be set in the future", Toast.LENGTH_LONG).show();




        }

    }
    /**
     * Shows the user how many units of insulin should be injected for the amount carb consumed
     *
     * @param carbs Passes the carbs amount that is to be displayed in the dialog box
     * @return void
     */
    public void ShowDialog(int carbs){
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        int carbRatio = sharedPref.getInt("carbRatio", Insulin.DEFAULTCARBRATIO);
        int carbUnits = carbs;
        carbUnits -= (carbUnits%carbRatio);//finding the nearest carb amount divisible bt the carb ratio
        carbUnits = carbUnits/carbRatio;
        if(carbUnits>0) {
            AlertDialog.Builder builderCarbs = new AlertDialog.Builder(AddCarbs.this);
            builderCarbs.setMessage("You have eaten " + carbs + " grams of carbohydrates and according to you " +
                    "carb ratio you should inject " + carbUnits + " Units for this if your blood sugars are in the optimal range. " +
                    "Do you want to record your correction insulin units");
            builderCarbs.setCancelable(true);
            builderCarbs.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent i = new Intent(AddCarbs.this, AddInsulin.class);
                            startActivity(i);
                            dialog.cancel();
                        }
                    });
            builderCarbs.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builderCarbs.setIcon(R.drawable.dialogicon);
            builderCarbs.setTitle("Carbohydrate Units");
            AlertDialog alert11 = builderCarbs.create();
            alert11.show();
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddCarbs.this, AddCarbs.this, year, month, day);
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddCarbs.this,
                AddCarbs.this, hour, minute, DateFormat.is24HourFormat(this));
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