package uk.ac.ulster.mur.diamonitor;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddBlood extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private SimpleDateFormat mFormatter = new SimpleDateFormat("dd MM yyyy hh:mm aa");
    private Button mButton;
    private Date recordingDate = null;
    private long recordingTime = 0;
    private MyDBHandler dbHandler;
    private EditText bloodReadingET;
    Button btnPick;
    TextView tvResult;
    int day,month,year,hour,minute,timeSet; // current time/date for initialising calendar
    int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;// set time/date
    private final int DEFAULTCORRRATIO = 2;
    private final String DEFAULTMAXRANGE = "10.0";
    private final String DEFAULTMINRANGE = "4.0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood);
        dbHandler = new MyDBHandler(this, null, null, 2);
        bloodReadingET = findViewById(R.id.BloodReadingEditText);

        btnPick = (Button) findViewById(R.id.btnPick);
        btnPick.setOnClickListener((View view) -> {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddBlood.this, AddBlood.this, year, month, day);
            datePickerDialog.show();

        });
    }

    public void addButtonClicked(View view){
        float reading = Float.valueOf(bloodReadingET.getText().toString());
        if ("".equals(bloodReadingET.getText().toString().trim())){
            Toast.makeText(this, "You must enter your blood sugar reading", Toast.LENGTH_LONG).show();
        }else {
            //Create new Blood Object to store record
            Blood blood = new Blood();
            //Set values of blood record fields
            NumberFormat format = new DecimalFormat("##.#");
            blood.setReading(Float.valueOf(format.format(reading)));
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
            dbHandler.addBlood(blood);
            //Clear Text Field for next entry
            bloodReadingET.setText("");

            //Inform user of Addition made
            Toast.makeText(AddBlood.this,
                    "Blood Reading added to Diary " + dbHandler.StringEpochToStringDate(String.valueOf(blood.getTime())), Toast.LENGTH_LONG).show();
            //Hides Keyboard after addition of record
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        DialogBoxes(reading);
    }
    public void DialogBoxes(float reading){

        //Load user preferences
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        float minRange = Float.parseFloat(sharedPref.getString("minRange", DEFAULTMINRANGE));
        float maxRange = Float.parseFloat(sharedPref.getString("maxRange", DEFAULTMAXRANGE));
        int corrRatio = sharedPref.getInt("corrRatio", DEFAULTCORRRATIO);

        //IF BS is High
        if(reading>maxRange) {
            int correctionUnits;
            correctionUnits = Math.round(reading-maxRange);//rounding reading to nearest full number to find how far above desired ideal range
            correctionUnits -= (correctionUnits%corrRatio);//rounding reading to nearest unit
            correctionUnits = correctionUnits/corrRatio; //finding units
            AlertDialog.Builder builderHigh = new AlertDialog.Builder(AddBlood.this);
            builderHigh.setMessage("Your Blood Sugar was high. According to your correction ratio you should inject " + correctionUnits +
                    " correction units. Do you want to record your correction insulin units");
            builderHigh.setCancelable(true);
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
            builderHigh.setIcon(R.drawable.dialogicon);
            builderHigh.setTitle("Correction Units");
            AlertDialog alert11 = builderHigh.create();
            alert11.show();
        }

        //IF BS is Low
        if(reading<minRange) {
            AlertDialog alertLowDialog = new AlertDialog.Builder(
                    AddBlood.this).create();
            // Setting Dialog Title
            alertLowDialog.setTitle("Low Blood Sugar");
            // Setting Dialog Message
            alertLowDialog.setMessage("Your blood sugar was " + reading + " which is below your optimal range. You should treat this hypo with 30 grams of carbohydrates!");
            // Setting Icon to Dialog
            alertLowDialog.setIcon(R.drawable.warningicon);
            // Showing Alert Message
            alertLowDialog.show();
        }

    }

    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        timePicker.setIs24HourView(true);
        hourFinal = i;
        minuteFinal = i1;
        timeSet = 1;

    }
}
