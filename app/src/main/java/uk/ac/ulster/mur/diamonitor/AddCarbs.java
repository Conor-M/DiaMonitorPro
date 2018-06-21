package uk.ac.ulster.mur.diamonitor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class AddCarbs extends AppCompatActivity {
    private MyDBHandler dbHandler;
    private EditText CarbReadingET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_carbs);
        CarbReadingET = findViewById(R.id.carbReadingEditText);
        dbHandler = new MyDBHandler(this, null, null, 1);
    }

    public void searchButtonClicked(View view){
        Intent i = new Intent(this, Search.class);
        startActivity(i);
    }

    public void addButtonClicked(View view){
        //Create new Carb Object
        Carbs carbs = new Carbs();
        //Set values of carb object to values of Edittext
        carbs.setAmount(Integer.valueOf(CarbReadingET.getText().toString()));
        carbs.setTime(System.currentTimeMillis());
        //pass Carb object DbHandler to put values in database
        dbHandler.addCarbs(carbs);
        //Clear Values from edittext for next entry
        CarbReadingET.setText("");
        //Inform User of addition
        Toast.makeText(AddCarbs.this,
                "Carbohydrates added to Diary " + dbHandler.StringEpochToStringDate(String.valueOf(carbs.getTime())), Toast.LENGTH_LONG).show();

        //Hide Keyboard to ensure can access navigation buttons
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