package uk.ac.ulster.mur.diamonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class ViewInsulinMeasures extends AppCompatActivity {
    private MyDBHandler myDBHandler;
    private ListView myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_insulin_measures);

        myDBHandler = new MyDBHandler(this, null, null, 1);
        //Reference Insulin List
        myList = findViewById(R.id.listInsulin);
        //Get All Insulin records
        ArrayList<Insulin> insulinList = myDBHandler.getAllInsulin();
        //Reverse list so that items are in reverse chronological order
        Collections.reverse(insulinList);
        //apply ArrayList of Insulin to ArrayAdapter to display in ListView
        ArrayAdapter<Insulin> adapter = new ArrayAdapter<Insulin>(this,android.R.layout.simple_list_item_1,insulinList);
        myList.setAdapter(adapter);
    }

    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
