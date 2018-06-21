package uk.ac.ulster.mur.diamonitor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class AddInsulin extends AppCompatActivity {
    private MyDBHandler dbHandler;
    private EditText InsulinReadingET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_insulin);
        InsulinReadingET = findViewById(R.id.InsulinReadingEditText);
        dbHandler = new MyDBHandler(this, null, null, 1);

    }

        public void addButtonClicked(View view){
            //Create new Insulin Object
            Insulin insulin = new Insulin();
            //Set Insulin Object Values
            insulin.setUnits(Integer.valueOf(InsulinReadingET.getText().toString()));
            insulin.setTime(System.currentTimeMillis());
            //Pass Insulin object to DBHandler
            dbHandler.addInsulin(insulin);
            //Clear Insulin ET Fields for next entry
            InsulinReadingET.setText("");
            //Inform User of addition
            Toast.makeText(AddInsulin.this,
                    "Insulin added to Diary " + dbHandler.StringEpochToStringDate(String.valueOf(insulin.getTime())), Toast.LENGTH_LONG).show();


            //Hides the keyboard after entry to ensure it does not interfere with the next action.
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
