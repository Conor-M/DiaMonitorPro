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

public class UserSettings extends AppCompatActivity {

    EditText etCorrectionRatio, etCarbRatio, etMaxRange, etMinRange;
    TextView tvTest;
    //Default Values set for each
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        etCorrectionRatio = (EditText) findViewById(R.id.etCorrectionRatio);
        etCarbRatio = (EditText) findViewById(R.id.etCarbRatio);
        etMaxRange = (EditText) findViewById(R.id.etMaxRange);
        etMinRange = (EditText) findViewById(R.id.etMinRange);
        tvTest = (TextView) findViewById(R.id.tvTest);
        InformUser();
        showData();
    }

    public void InformUser(){
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        //set userSet to value held or zero as default value meaning userset hasnt been set
        int userSet = sharedPref.getInt("userSet", 0);
        if(userSet == 1) {
            AlertDialog alertLowDialog = new AlertDialog.Builder(
                    this).create();
            // Setting Dialog Title
            alertLowDialog.setTitle("Important");
            // Setting Dialog Message
            alertLowDialog.setMessage("Your blood sugar was  which is below your optimal range. You should treat this hypo with 30 grams of carbohydrates!");
            // Setting Icon to Dialog
            alertLowDialog.setIcon(R.drawable.warningicon);
            // Showing Alert Message
            alertLowDialog.show();
        }
    }

    public void HomeButtonClicked(View view){
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        //set userSet to value held or zero as default value meaning userset hasnt been set
        int userSet = sharedPref.getInt("userSet", 0);
        if(userSet == 1) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(UserSettings.this,
                    "You must Set all user settings before leaving this page" , Toast.LENGTH_LONG).show();
        }


    }

    public void saveInfo(View view){
        int validationError;
        //create reference to Shared Preferences File
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        //Open for Editing
        SharedPreferences.Editor editor = sharedPref.edit();

        if (("".equals(etMaxRange.getText().toString().trim()) || "".equals(etMaxRange.getText().toString().trim()) || "".equals(etCorrectionRatio.getText().toString().trim()) || "".equals(etCarbRatio.getText().toString().trim()) )){
            Toast.makeText(this, "You must fill in all values", Toast.LENGTH_LONG).show();
        }
        else{
        //Place Values in the shared preferences file
            editor.putInt("carbRatio" ,Integer.valueOf(etCarbRatio.getText().toString()));
            editor.putInt("corrRatio" ,Integer.valueOf(etCorrectionRatio.getText().toString()));
            editor.putString("maxRange", etMaxRange.getText().toString());
            editor.putString("minRange", etMinRange.getText().toString());
            //Set UserSet to 1 show initialized settings
            editor.putInt("userSet", 1);
            //Save the file
            editor.apply();
            //Inform User that settings have been changed
            Toast.makeText(UserSettings.this,
                    "User setting changed" , Toast.LENGTH_LONG).show();
            showData();
        }
    }




    public void showData(){
        //Display Current user setting as hints in editText box

        //open Reference to shared preferences
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        //set userSet to value held or zero as default value meaning userset hasnt been set
        int userSet = sharedPref.getInt("userSet", 0);
        if(userSet == 1) {
            int carbRatio = sharedPref.getInt("carbRatio", Insulin.DEFAULTCARBRATIO);
            int corrRatio = sharedPref.getInt("corrRatio", Insulin.DEFAULTCORRRATIO);
            //double minRange = Double.parseDouble(sharedPref.getString("minRange", DEFAULTMINRANGE));
            //double maxRange = Double.parseDouble(sharedPref.getString("maxRange", DEFAULTMAXRANGE));
            String minRange =sharedPref.getString("minRange", Blood.DEFAULTMINRANGE);
            String maxRange = sharedPref.getString("maxRange", Blood.DEFAULTMAXRANGE);
            /*etCarbRatio.setHint(Integer.toString(carbRatio));
            etCorrectionRatio.setHint(Integer.toString(corrRatio));
            etMaxRange.setHint(Double.toString(maxRange));
            etMinRange.setHint(Double.toString(minRange));*/
            tvTest.setText("Carb: 1:" + carbRatio + " Corr: 1:" + corrRatio + " Min Range: " + minRange + " Max Range: " + maxRange);
        }
        else{
            Toast.makeText(UserSettings.this,
                    "Please Set User Settings" , Toast.LENGTH_LONG).show();
        }
        long time = System.currentTimeMillis();

    }
}
