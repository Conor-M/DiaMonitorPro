package uk.ac.ulster.mur.diamonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserSettingsSP extends AppCompatActivity {

    EditText etCorrectionRatio, etCarbRatio, etMaxRange, etMinRange;
    MyDBHandler dbHandler;
    //Default Values set for each
    private final int DEFAULTCORRRATIO = 2;
    private final int DEFAULTCARBRATIO = 5;
    private final String DEFAULTMAXRANGE = "10.0";
    private final String DEFAULTMINRANGE = "4.0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        etCorrectionRatio = (EditText) findViewById(R.id.etCorrectionRatio);
        etCarbRatio = (EditText) findViewById(R.id.etCarbRatio);
        etMaxRange = (EditText) findViewById(R.id.etMaxRange);
        etMinRange = (EditText) findViewById(R.id.etMinRange);
    }

    public void HomeButtonClicked(View view){
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        //set userSet to value held or zero as default value meaning userset hasnt been set
        int userSet = sharedPref.getInt("UserSet", 0);
        if(userSet == 1) {
        Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }else{
            Toast.makeText(UserSettingsSP.this,
                    "You must Set all usersettings before leaving this page" , Toast.LENGTH_LONG).show();
        }


    }

    public void saveInfo(View view){
        //create reference to Shared Preferences File
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        //Open for Editing
        SharedPreferences.Editor editor = sharedPref.edit();

        //Place Values in the shared preferences file
        editor.putInt("carbRatio" ,Integer.valueOf(etCarbRatio.getText().toString()));
        editor.putInt("corrRatio" ,Integer.valueOf(etCorrectionRatio.getText().toString()));
        editor.putString("maxRange", etMaxRange.getText().toString());
        editor.putString("minRange", etMinRange.getText().toString());
        //Set UserSet to 1 meaning user settings have been set by used
        editor.putInt("UserSet", 1);
        //Save the file
        editor.apply();
        //Inform User that settings have been changed
        Toast.makeText(UserSettingsSP.this,
                "User setting changed" , Toast.LENGTH_LONG).show();

    }

    public void showData(View view){
        //Display Current user setting as hints in editText box

        //open Reference to shared preferences
        SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
        //set userSet to value held or zero as default value meaning userset hasnt been set
        int userSet = sharedPref.getInt("UserSet", 0);
        if(userSet == 1) {
            int carbRatio = sharedPref.getInt("carbRatio", DEFAULTCARBRATIO);
            int corrRatio = sharedPref.getInt("corrRatio", DEFAULTCORRRATIO);
            double minRange = Double.parseDouble(sharedPref.getString("minRange", DEFAULTMINRANGE));
            double maxRange = Double.parseDouble(sharedPref.getString("maxRange", DEFAULTMAXRANGE));
            etCarbRatio.setHint(Integer.toString(carbRatio));
            etCorrectionRatio.setHint(Integer.toString(corrRatio));
            etMaxRange.setHint(Double.toString(maxRange));
            etMinRange.setHint(Double.toString(minRange));
        }
        else{
            Toast.makeText(UserSettingsSP.this,
                    "Please Set User Settings" , Toast.LENGTH_LONG).show();
        }

    }
}
