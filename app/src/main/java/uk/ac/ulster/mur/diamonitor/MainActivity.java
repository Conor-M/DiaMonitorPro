package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
/**
 * Main activity used to navigate to other activities in the app
 *
 *
 * @author  Conor Murphy
 * @version 1.0
 * @since   2018-1-20
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // DECLARE BUTTONS
    private Button btnBloodReading,btnCarb,btnInsulin,btnUserSet,btnViewReading,btnAnalyseBloodReadings;
    private MyDBHandler dbHandler;
    @Override
    /**
     * Creates the view of the activity when the activity is first started
     * sets title of the activity to be displayed
     * sets onClickListeners for all of the buttons in the activity
     *
     * @param savedInstanceState Required as is an implementation of the onClick defined in xml for this activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Base_AppTheme);
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
        ImageView ivLogo = (ImageView) findViewById(R.id.ivlogo);
        ivLogo.setImageResource(R.drawable.logo);
    }
    /**
     * onClick method for all the buttons in the activity which takes the button as a view and then
     * a switch decides where to direct the user based on the button clicked
     *
     * @param view Passes the button that is calling the onClick method as a view
     */
    public void onClick(View view){
        //onclicklistener actions for each button to bring user to specified intent

        switch (view.getId()) {
            case R.id.btnBloodReading:
                startActivity(new Intent(this, AddBlood.class));
                break;
            case R.id.btnCarb:
                startActivity(new Intent(this, AddCarbs.class));
                break;
            case R.id.btnInsulin:
                startActivity(new Intent(this, AddInsulin.class));
                break;
            case R.id.btnUserSet:
                startActivity(new Intent(this, UserSettings.class));
                break;
            case R.id.btnViewReadings:
                startActivity(new Intent(this, ViewType.class));
                break;
            case R.id.btnAnalyseBloodReadings:
                startActivity(new Intent(this, AnalyseBlood.class));
                break;
        }
    }

}
