package uk.ac.ulster.mur.diamonitor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
/**
 * Activity to set the user setting for the app
 *
 *
 * @author  Conor Murphy
 * @version 1.0
 * @since   2018-1-20
 *
 */
public class UserSettings extends AppCompatActivity {

    EditText etCorrectionRatio, etCarbRatio, etMaxRange, etMinRange;
    TextView tvShowSettings;
    /**
     * Creates the view of the activity when the activity is first started
     * sets title of the activity to be displayed
     *
     * @param savedInstanceState Required as is an implementation of the onClick defined in xml for this activit
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("User Settings");

        setContentView(R.layout.activity_user_settings);
        etCorrectionRatio = (EditText) findViewById(R.id.etCorrectionRatio);
        etCarbRatio = (EditText) findViewById(R.id.etCarbRatio);
        etMaxRange = (EditText) findViewById(R.id.etMaxRange);
        etMinRange = (EditText) findViewById(R.id.etMinRange);
        tvShowSettings = (TextView) findViewById(R.id.tvShowSettings);
        InformUser();
        showData();
    }

    /**
     * Informs the user in a dialog box on first time viewing the app that this app is not
     * medical advice and that they should only treat their diabetes in a manor consistent with
     * there physician or GP advice
     *
     */
    public void InformUser(){
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        //set userSet to value held or zero as default value meaning userset hasnt been set
        int userSet = sharedPref.getInt("userSet", 0);
        if(userSet == 0) {
            AlertDialog alertLowDialog = new AlertDialog.Builder(
                    this).create();
            // Setting Dialog Title
            alertLowDialog.setTitle("Important");
            // Setting Dialog Message
            alertLowDialog.setMessage("The information given in this app is not to be confused with medical advice " +
                    "and its function is to assist the user with recording diabetes treatment." +
                    " Users must use caution to only use treatment that is consistent" +
                    " with their own medical advice given by their physician or GP");
            // Setting Icon to Dialog
            alertLowDialog.setIcon(R.drawable.warningicon);
            // Showing Alert Message
            alertLowDialog.show();
        }
    }
    /**
     * Brings the user back to the home activity on click of the button unless the user has not set the
     * personal diabetes correction rates in which case the user is prompted to fill the data out before leaving
     * @param view Required as is an implementation of the onClick defined in xml for this activity
     */
    public void HomeButtonClicked(View view){
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        //set userSet to value held or zero as default value meaning userset hasnt been set
        int userSet = sharedPref.getInt("userSet", 0);
        if(userSet == 1) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(UserSettings.this,
                    "You must set all user settings before leaving this page" , Toast.LENGTH_LONG).show();
        }


    }

    /**
     * When save button is clicked the information provided by the user is saved in the UserSettings SharedPreference File
     * if an edittext field is left empty the user is prompted to provide setting for all values
     *
     * @param view
     */
    public void saveInfo(View view){
        int validationError;
        //create reference to Shared Preferences File
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        //Open for Editing
        SharedPreferences.Editor editor = sharedPref.edit();
        //only updates the information if all fields have been filled otherwise prompt appears in Toast()
        if (("".equals(etMaxRange.getText().toString().trim()) || "".equals(etMinRange.getText().toString().trim()) || "".equals(etCorrectionRatio.getText().toString().trim()) || "".equals(etCarbRatio.getText().toString().trim()) )){
            Toast.makeText(this, "You must fill in all values", Toast.LENGTH_LONG).show();
        }
        else
        //Place Values in the shared preferences file
            if(Float.valueOf(etMinRange.getText().toString())<Float.valueOf(etMaxRange.getText().toString())) {
                if(!Integer.valueOf(etCarbRatio.getText().toString()).equals(0) && !Integer.valueOf(etCorrectionRatio.getText().toString()).equals(0)) {
                    editor.putInt("carbRatio", Integer.valueOf(etCarbRatio.getText().toString()));
                    editor.putInt("corrRatio", Integer.valueOf(etCorrectionRatio.getText().toString()));
                    editor.putString("maxRange", etMaxRange.getText().toString());
                    editor.putString("minRange", etMinRange.getText().toString());
                    //Set UserSet to 1 show initialized settings
                    editor.putInt("userSet", 1);
                    //Save the file
                    editor.apply();
                    //Inform User that settings have been changed
                    Toast.makeText(UserSettings.this,
                            "User setting changed", Toast.LENGTH_LONG).show();
                    showData();
                }else
                    Toast.makeText(UserSettings.this,
                            "Correction Ratio or Carb Ratio Cannot be set to zero", Toast.LENGTH_LONG).show();

            }else
                Toast.makeText(UserSettings.this,
                    "Minimum Range must be set to a value lower than Maximum Range", Toast.LENGTH_LONG).show();

    }


    /**
     * Shows the currently set values for the carb ratio,correction ratio, minimum range and maximum range
     * if these are not set the user is prompted to set these values
     *
     */
    public void showData(){
        //Display Current user setting as hints in editText box

        //open Reference to shared preferences
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        //set userSet to value held or zero as default value meaning userset hasnt been set
        int userSet = sharedPref.getInt("userSet", 0);
        if(userSet == 1) {
            int carbRatio = sharedPref.getInt("carbRatio", Insulin.DEFAULTCARBRATIO);
            int corrRatio = sharedPref.getInt("corrRatio", Insulin.DEFAULTCORRRATIO);
            String minRange =sharedPref.getString("minRange", Blood.DEFAULTMINRANGE);
            String maxRange = sharedPref.getString("maxRange", Blood.DEFAULTMAXRANGE);

            tvShowSettings.setText("Carb: 1:" + carbRatio + " Corr: 1:" + corrRatio + " Min Range: " + minRange + " Max Range: " + maxRange);
        }
        else{
            Toast.makeText(UserSettings.this,
                    "Please Set User Settings" , Toast.LENGTH_LONG).show();
        }

    }
}
