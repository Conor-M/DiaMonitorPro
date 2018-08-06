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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

/**
 * Activity to add blood records to the Bloods table in the database
 *
 *
 * @author  Conor Murphy
 * @version 1.0
 * @since   2018-1-20
 *
 */
public class AddBlood extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{


    private MyDBHandler dbHandler;
    private EditText bloodReadingET;
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
        setTitle("Add Blood Reading");
        setContentView(R.layout.activity_add_blood);
        dbHandler = new MyDBHandler(this, null, null, 2);
        bloodReadingET = findViewById(R.id.BloodReadingEditText);
        bloodReadingET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
     * Called when add button is clicked and create a new record in the blood records
     * Performs validation to ensure field is not empty and date is not set in the future
     *
     * @param view Required as is an implementation of the onClick defined in xml for this activity
     * @return void
     */
    public void addButtonClicked(View view){
        if ("".equals(bloodReadingET.getText().toString().trim())){
            Toast.makeText(this, "You must enter your blood sugar reading", Toast.LENGTH_LONG).show();
        }else {
            //Create new Blood Object to store record
            Blood blood = new Blood();
            //Set values of blood record fields
            NumberFormat format = new DecimalFormat("##.#");
            blood.setReading(Float.valueOf(format.format(Float.valueOf(bloodReadingET.getText().toString()))));
            //If User has set the time and date using datepicker set blood reading to that time
            //else set to current time
            if(timeSet==1){
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, yearFinal);
                c.set(Calendar.MONTH, monthFinal);
                c.set(Calendar.DAY_OF_MONTH, dayFinal);
                c.set(Calendar.HOUR_OF_DAY, hourFinal);
                c.set(Calendar.MINUTE, minuteFinal);
                blood.setTime(c.getTimeInMillis());
            }else{
                blood.setTime(System.currentTimeMillis());
            }
            //Add blood Object values to database fields
            if(System.currentTimeMillis()+1>(blood.getTime())) { // checks if time is set in future +1 as the time can be set to now
                dbHandler.addBlood(blood);
                //Inform user of Addition made
                Toast.makeText(AddBlood.this,
                        "Blood Reading added to Diary " + dbHandler.StringEpochToStringDate(String.valueOf(blood.getTime())), Toast.LENGTH_LONG).show();
                DialogBoxes(Float.valueOf(bloodReadingET.getText().toString()));
                //Clear Text Field for next entry
                bloodReadingET.setText("");
            }else {
                Toast.makeText(this, "Your record cannot be set in the future", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Checks if the reading is within the optimal ranges defined by the user if this is not the
     * case then the application will bring up a dialog box suggesting what the user may want to do in this case
     *
     * @param reading Passes the reading that is to be displayed in the dialog box
     * @return void
     */
    public void DialogBoxes(float reading){
        //Load user preferences
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        float minRange = Float.parseFloat(sharedPref.getString("minRange", Blood.DEFAULTMINRANGE));
        float maxRange = Float.parseFloat(sharedPref.getString("maxRange", Blood.DEFAULTMAXRANGE));
        int corrRatio = sharedPref.getInt("corrRatio", Insulin.DEFAULTCORRRATIO);

        //IF BS is High
        if(reading>maxRange) {
            int correctionUnits;
            //rounding reading to nearest full number to find how far above desired ideal range
            correctionUnits = Math.round(reading-maxRange);
            //rounding reading to nearest unit
            correctionUnits -= (correctionUnits%corrRatio);
            //calculating units to be injected
            correctionUnits = correctionUnits/corrRatio;
            if(correctionUnits>0) {
                AlertDialog.Builder builderHigh = new AlertDialog.Builder(AddBlood.this);
                builderHigh.setMessage("Your Blood Sugar was high. According to your correction ratio you should inject "
                        + correctionUnits +
                        " correction units. Do you want to record your correction insulin units");
                builderHigh.setCancelable(true);
                builderHigh.setIcon(R.drawable.dialogicon);
                builderHigh.setTitle("Correction Units");
                builderHigh.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(AddBlood.this, AddInsulin.class);
                                startActivity(i);
                                dialog.cancel();
                            }
                        });
                builderHigh.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builderHigh.create();
                alert11.show();
            }
        }

        //IF BS is Low
        if(reading<minRange) {
            AlertDialog alertLowDialog = new AlertDialog.Builder(
                    AddBlood.this).create();
            // Setting Dialog Title
            alertLowDialog.setTitle("Low Blood Sugar");
            // Setting Dialog Message
            alertLowDialog.setMessage("Your blood sugar was " + reading
                    + " which is below your optimal range. You should treat this hypo with 30 grams of carbohydrates!");
            // Setting Icon to Dialog
            alertLowDialog.setIcon(R.drawable.warningicon);
            // Showing Alert Message
            alertLowDialog.show();
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
     */
    public void PickDateTimeClicked(View view){
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddBlood.this, AddBlood.this, year, month, day);
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddBlood.this, AddBlood.this, hour, minute, DateFormat.is24HourFormat(this));
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
