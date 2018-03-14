package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
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
    MyDBHandler dbHandler;
    EditText bloodReadingET;





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
        dbHandler = new MyDBHandler(this, null, null, 1);
        setContentView(R.layout.activity_add_blood);
        bloodReadingET = (EditText) findViewById(R.id.BloodReadingEditText);
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

        EditText et3 = (EditText) findViewById(R.id.editText3);
        Blood blood = new Blood();
        blood.setReading(Float.valueOf(bloodReadingET.getText().toString()));
        blood.setTime(System.currentTimeMillis());
        dbHandler.addBlood(blood);
        bloodReadingET.setText("");
        et3.setText(dbHandler.bloodDatabaseToString());
        Toast.makeText(AddBlood.this,
                "This is DB ADDED" + dbHandler.StingEpochToStringDate(String.valueOf(blood.getTime())), Toast.LENGTH_LONG).show();
    }

    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
