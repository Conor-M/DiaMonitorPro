package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserSettings extends AppCompatActivity {

    EditText etCorrectionRatio, etCarbRatio, etMaxRange, etMinRange;
    MyDBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        etCorrectionRatio = (EditText) findViewById(R.id.etCorrectionRatio);
        etCarbRatio = (EditText) findViewById(R.id.etCarbRatio);
        etMaxRange = (EditText) findViewById(R.id.etMaxRange);
        etMinRange = (EditText) findViewById(R.id.etMinRange);
    }
    public void ChangeUser(View view){
        User user = new User();
        user.setCarbRatio(Integer.valueOf(etCarbRatio.getText().toString()));
        user.setCorrectionRatio(Integer.valueOf(etCorrectionRatio.getText().toString()));
        user.setMaxRange(Float.valueOf(etMaxRange.getText().toString()));
        user.setMinRange(Float.valueOf(etMinRange.getText().toString()));
        dbHandler.editUser(user);
        Toast.makeText(UserSettings.this,
                "User setting changed" , Toast.LENGTH_LONG).show();
    }
    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
