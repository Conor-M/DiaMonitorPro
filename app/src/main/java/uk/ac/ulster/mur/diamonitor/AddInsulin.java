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

public class AddInsulin extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private MyDBHandler dbHandler;
    private EditText InsulinUnitsET;
    Button btnPick;
    int day,month,year,hour,minute,timeSet; // current time/date for initialising calendar
    int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;// set time/date

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Add Insulin Record");
        setContentView(R.layout.activity_add_insulin);
        InsulinUnitsET = findViewById(R.id.InsulinReadingEditText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        btnPick = (Button) findViewById(R.id.btnPick);
        btnPick.setOnClickListener(view -> {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddInsulin.this, AddInsulin.this, year, month, day);
            datePickerDialog.show();

        });

        InsulinUnitsET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
            if(System.currentTimeMillis()<insulin.getTime()) {
                dbHandler.addInsulin(insulin);
            }else {
                Toast.makeText(this, "Your record cannot be set in the future", Toast.LENGTH_LONG).show();
            }
                //Clear Insulin ET Fields for next entry
            InsulinUnitsET.setText("");
            //Inform User of addition
            Toast.makeText(AddInsulin.this,
                    "Insulin added to Diary " + dbHandler.StringEpochToStringDate(String.valueOf(insulin.getTime())), Toast.LENGTH_LONG).show();

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
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddInsulin.this, AddInsulin.this, hour, minute, DateFormat.is24HourFormat(this));
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
