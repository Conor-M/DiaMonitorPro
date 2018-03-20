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

    public void addButtonClicked(View view){

        EditText et3 = findViewById(R.id.displayEditText);
        Carbs carbs = new Carbs();
        carbs.setAmount(Integer.valueOf(CarbReadingET.getText().toString()));
        carbs.setTime(System.currentTimeMillis());
        dbHandler.addCarbs(carbs);
        CarbReadingET.setText("");
        et3.setText(dbHandler.CarbsDatabaseToString());
        Toast.makeText(AddCarbs.this,
                "Carbohydrates added to Diary " + dbHandler.StingEpochToStringDate(String.valueOf(carbs.getTime())), Toast.LENGTH_LONG).show();
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