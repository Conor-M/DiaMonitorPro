package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
/**
 * Activity to allow the user to navigate to the different record view activities
 *
 *
 * @author  Conor Murphy
 * @version 1.0
 * @since   2018-1-20
 *
 */
public class ViewType extends AppCompatActivity implements View.OnClickListener{
    //Declare Buttons
    Button btnViewBloodReadings, btnViewInsulin, btnViewCarbs;
    /**
     * Creates the view of the activity when the activity is first started
     *
     * @param savedInstanceState Required as is an implementation of the onClick defined in xml for this activit
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("View Records");
        setContentView(R.layout.activity_view_type);
        //Set onClickListeners
        btnViewBloodReadings = findViewById(R.id.btnViewBloodReadings);
        btnViewBloodReadings.setOnClickListener(this);
        btnViewInsulin = findViewById(R.id.btnViewInsulin);
        btnViewInsulin.setOnClickListener(this);
        btnViewCarbs = findViewById(R.id.btnViewCarbs);
        btnViewCarbs.setOnClickListener(this);
    }
    /**
     * onClick method for all the buttons in the activity which takes the button as a view and then
     * a switch decides where to direct the user based on the button clicked
     *
     * @param view Passes the button that is calling the onClick method as a view
     */
    public void onClick(View view) {
        //onClickListener actions for each button
        /*if (view ==findViewById(R.id.btnViewBloodReadings)){
            Intent i = new Intent(this, ViewBloodReadings.class);
            startActivity(i);
        }else if (view ==findViewById(R.id.btnViewCarbs)){
            Intent i = new Intent(this, ViewCarbRecords.class);
            startActivity(i);
        }else if (view ==findViewById(R.id.btnViewInsulin)){
            Intent i = new Intent(this, ViewInsulinMeasures.class);
            startActivity(i);
        }*/
        switch (view.getId()) {
            case R.id.btnViewBloodReadings:
                startActivity(new Intent(this, ViewBloodReadings.class));
                break;
            case R.id.btnViewCarbs:
                startActivity(new Intent(this, ViewCarbRecords.class));
                break;
            case R.id.btnViewInsulin:
                startActivity(new Intent(this, ViewInsulinMeasures.class));
                break;
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
}
