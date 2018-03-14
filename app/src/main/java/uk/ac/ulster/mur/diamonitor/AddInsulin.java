package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class AddInsulin extends AppCompatActivity {
    private Button mButton;
    private Date recordingDate = null;
    private long recordingTime = 0;
    MyDBHandler dbHandler;
    EditText InsulinReadingET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InsulinReadingET = (EditText) findViewById(R.id.InsulinReadingEditText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_insulin);
    }

        public void addButtonClicked(View view){

            EditText et3 = (EditText) findViewById(R.id.editText3);
            Insulin insulin = new Insulin();
            insulin.setUnits(Integer.valueOf(InsulinReadingET.getText().toString()));
            insulin.setTime(System.currentTimeMillis());
            dbHandler.addInsulin(insulin);
            InsulinReadingET.setText("");
            et3.setText(dbHandler.bloodDatabaseToString());
            Toast.makeText(AddInsulin.this,
                    "This is DB ADDED" + dbHandler.StingEpochToStringDate(String.valueOf(insulin.getTime())), Toast.LENGTH_LONG).show();
        }

    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
