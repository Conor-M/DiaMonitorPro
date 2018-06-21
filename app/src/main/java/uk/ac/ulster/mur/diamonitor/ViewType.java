package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ViewType extends AppCompatActivity implements View.OnClickListener{
    //Declare Buttons
    Button btnViewBloodReadings, btnViewInsulin, btnViewCarbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_type);
        //Set onClickListeners
        btnViewBloodReadings = findViewById(R.id.btnViewBloodReadings);
        btnViewBloodReadings.setOnClickListener(this);
        btnViewInsulin = findViewById(R.id.btnViewInsulin);
        btnViewInsulin.setOnClickListener(this);
        btnViewCarbs = findViewById(R.id.btnViewCarbs);
        btnViewCarbs.setOnClickListener(this);
    }

    public void onClick(View view) {
        //onClickListener actions for each button
        if (view ==findViewById(R.id.btnViewBloodReadings)){
            Intent i = new Intent(this, ViewBloodReadings.class);
            startActivity(i);
        }else if (view ==findViewById(R.id.btnViewCarbs)){
            Intent i = new Intent(this, ViewCarbRecords.class);
            startActivity(i);
        }else if (view ==findViewById(R.id.btnViewInsulin)){
            Intent i = new Intent(this, ViewInsulinMeasures.class);
            startActivity(i);
        }
    }

    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
