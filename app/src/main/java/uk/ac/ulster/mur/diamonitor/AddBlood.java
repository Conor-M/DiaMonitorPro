package uk.ac.ulster.mur.diamonitor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AddBlood extends FragmentActivity {
    private SimpleDateFormat mFormatter = new SimpleDateFormat("dd MM yyyy hh:mm aa");
    private Button mButton;
    private Date recordingDate = null;
    private long recordingTime = 0;
    private MyDBHandler dbHandler;
    private EditText bloodReadingET;





    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            //recordingDate = date;
            //recordingTime = date.getTime();

            Toast.makeText(AddBlood.this,
                    mFormatter.format(date), Toast.LENGTH_SHORT).show();
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(AddBlood.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blood);
        dbHandler = new MyDBHandler(this, null, null, 1);
        bloodReadingET = findViewById(R.id.BloodReadingEditText);
        /*mButton = (Button) findViewById(R.id);
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(new Date())
                        //.setMinDate(minDate)
                        //.setMaxDate(maxDate)
                        //.setIs24HourTime(true)
                        //.setTheme(SlideDateTimePicker.HOLO_DARK)
                        //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
            }
        });*/


    }

    public void addButtonClicked(View view){

        Blood blood = new Blood();
        blood.setReading(Float.valueOf(bloodReadingET.getText().toString()));
        blood.setTime(System.currentTimeMillis());
        dbHandler.addBlood(blood);
        bloodReadingET.setText("");
        Toast.makeText(AddBlood.this,
                "Blood Reading added to Diary " + dbHandler.StingEpochToStringDate(String.valueOf(blood.getTime())), Toast.LENGTH_LONG).show();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
