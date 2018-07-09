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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddCarbs extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private MyDBHandler dbHandler;
    private EditText carbReadingET;
    Button btnPick;
    int day,month,year,hour,minute,timeSet; // current time/date for initialising calendar
    int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;// set time/date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Carb Record");
        setContentView(R.layout.activity_add_carbs);
        carbReadingET = findViewById(R.id.carbReadingEditText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        btnPick = (Button) findViewById(R.id.btnPick);
        btnPick.setOnClickListener(view -> {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddCarbs.this, AddCarbs.this, year, month, day);
            datePickerDialog.show();

        });
        carbReadingET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void ShowDialog(int carbs){
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        int carbRatio = sharedPref.getInt("carbRatio", Insulin.DEFAULTCARBRATIO);
        int carbUnits = carbs;
        carbUnits -= (carbUnits%carbRatio);//rounding reading to nearest unit
        carbUnits = carbUnits/carbRatio;
        AlertDialog.Builder builderCarbs = new AlertDialog.Builder(AddCarbs.this);
        builderCarbs.setMessage("You have eaten " + carbs + " grams of carbohydrates and according to you carb ratio you should inject " + carbUnits + " Units for this if your blood sugars are in the optimal range. Do you want to record your correction insulin units");
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

    public void searchButtonClicked(View view){
        Intent i = new Intent(this, Search.class);
        startActivity(i);
    }

    public void addButtonClicked(View view){
        int carbsET = Integer.valueOf(carbReadingET.getText().toString());
        if ("".equals(carbReadingET.getText().toString().trim())){
            Toast.makeText(this, "You must enter the amount of carbs", Toast.LENGTH_LONG).show();
        }else {
            //Create new Carb Object
            Carbs carbs = new Carbs();
            //Set values of carb object to values of Edittext
            carbs.setAmount(carbsET);
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
            //Add blood Object values to database fields
            if(System.currentTimeMillis()>carbs.getTime()) {
                dbHandler.addCarbs(carbs);
            }else
                Toast.makeText(this, "Your record cannot be set in the future", Toast.LENGTH_LONG).show();

            //Clear Values from edittext for next entry
            carbReadingET.setText("");
            //Inform User of addition
            Toast.makeText(AddCarbs.this,
                    "Carbohydrates added to Diary " + dbHandler.StringEpochToStringDate(String.valueOf(carbs.getTime())), Toast.LENGTH_LONG).show();


        }
        ShowDialog(carbsET);
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddCarbs.this, AddCarbs.this, hour, minute, DateFormat.is24HourFormat(this));
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