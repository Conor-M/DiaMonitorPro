package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnBloodReading,btnCarb,btnInsulin,btnUserSet,btnViewReading,btnAnalyseBloodReadings; // DECLARE BUTTONS
    MyDBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SET BUTTON SETONCLICKLISTENERS
        btnBloodReading= findViewById(R.id.btnBloodReading);
        btnBloodReading.setOnClickListener(this);
        btnCarb = findViewById(R.id.btnCarb);
        btnCarb.setOnClickListener(this);
        btnInsulin = findViewById(R.id.btnInsulin);
        btnInsulin.setOnClickListener(this);
        btnAnalyseBloodReadings = findViewById(R.id.btnAnalyseBloodReadings);
        btnAnalyseBloodReadings.setOnClickListener(this);
        btnUserSet = findViewById(R.id.btnUserSet);
        btnUserSet.setOnClickListener(this);
        btnViewReading = findViewById(R.id.btnViewReadings);
        btnViewReading.setOnClickListener(this);
        dbHandler = new MyDBHandler(this, null, null, 1);
        //CHECKS IF USER TABLE HAS ANY RECORDS THEN

        //
        // checkUser();

    }

    public void onClick(View view) {
        //ONCLICKLISTENERS ACTIONS FOR EACH BUTTON
        if (view ==findViewById(R.id.btnBloodReading)){
            Intent i = new Intent(this, AddBlood.class);
            startActivity(i);
        }else if (view ==findViewById(R.id.btnCarb)){
            Intent i = new Intent(this, AddCarbs.class);
            startActivity(i);
        }else if (view ==findViewById(R.id.btnInsulin)){
            Intent i = new Intent(this, AddInsulin.class);
            startActivity(i);
        }else if (view ==findViewById(R.id.btnUserSet)){
            Intent i = new Intent(this, UserSettings.class);
            startActivity(i);
        }else if (view ==findViewById(R.id.btnViewReadings)) {
            Intent i = new Intent(this, ViewType.class);
            startActivity(i);
        }else if (view ==findViewById(R.id.btnAnalyseBloodReadings)) {
            Intent i = new Intent(this, AddBlood.class);
            startActivity(i);
        }
    }

    private void checkUser(){
        if(dbHandler.isUserTableEmpty()){
            Toast.makeText(MainActivity.this,
                    "Default User Setting Loaded" , Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this,
                    "Default User Setting Loaded" , Toast.LENGTH_LONG).show();}
    }
}
