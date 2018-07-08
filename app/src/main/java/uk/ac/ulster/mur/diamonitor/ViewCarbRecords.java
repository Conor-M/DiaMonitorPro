package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class ViewCarbRecords extends AppCompatActivity {
    private MyDBHandler myDBHandler;
    private ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_carb_records);
        // Create connection to SQLite Database
        myDBHandler = new MyDBHandler(this, null, null, 1);
        //Reference Carbs List
        myList = findViewById(R.id.listCarbs);
        //Get All Blood Sugars records
        ArrayList<Carbs> carbsList = myDBHandler.getAllCarbs();
        //Reverse array to put newest records at top of the list
        Collections.reverse(carbsList);

        Collections.sort(carbsList, new CarbsComparator());

        //apply ArrayList of carbs to ArrayAdapter to display in ListView
        ArrayAdapter<Carbs> adapter = new ArrayAdapter<Carbs>(this,android.R.layout.simple_list_item_1,carbsList);
        myList.setAdapter(adapter);
    }

    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
