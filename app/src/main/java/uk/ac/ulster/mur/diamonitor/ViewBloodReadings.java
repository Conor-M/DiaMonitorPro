package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;


public class ViewBloodReadings extends AppCompatActivity {

    private MyDBHandler myDBHandler;
    private ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blood_readings);
        // Create connection to SQLite Database
        myDBHandler = new MyDBHandler(this, null, null, 1);

        //Reference ListView
        myList = findViewById(R.id.listBloodReading);
        //Get All Blood Sugars records
        ArrayList<Blood> bloodList = myDBHandler.getAllBlood();
        //Reverse array to put newest records at top of the list
        Collections.reverse(bloodList);
        Collections.sort(bloodList, new BloodComparator());


        //Create and set array adapter to put Blood records in the list
        ArrayAdapter<Blood> adapter = new ArrayAdapter<Blood>(this,android.R.layout.simple_list_item_1,bloodList);
        myList.setAdapter(adapter);

    }
    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
