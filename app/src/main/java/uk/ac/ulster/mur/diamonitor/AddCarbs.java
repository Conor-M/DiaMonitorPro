package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class AddCarbs extends AppCompatActivity {


    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_carbs);
        CarbReadingET = (EditText) findViewById(R.id.carbReadingEditText);
        dbHandler = new MyDBHandler(this, null, null, 1);
    }
    private Button mButton;
    private Date recordingDate = null;
    private long recordingTime = 0;
    MyDBHandler dbHandler;
    EditText CarbReadingET;



    public void addButtonClicked(View view){

        EditText et3 = (EditText) findViewById(R.id.displayEditText);
        Carbs carbs = new Carbs();
        carbs.setAmount(Integer.valueOf(CarbReadingET.getText().toString()));
        carbs.setTime(System.currentTimeMillis());
        dbHandler.addCarbs(carbs);
        CarbReadingET.setText("");
        et3.setText(dbHandler.bloodDatabaseToString());
        Toast.makeText(AddCarbs.this,
                "This is DB ADDED" + dbHandler.StingEpochToStringDate(String.valueOf(carbs.getTime())), Toast.LENGTH_LONG).show();
    }
}