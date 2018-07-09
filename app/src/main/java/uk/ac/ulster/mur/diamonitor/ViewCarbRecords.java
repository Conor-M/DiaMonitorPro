package uk.ac.ulster.mur.diamonitor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

public class ViewCarbRecords extends AppCompatActivity {
    private MyDBHandler myDBHandler;
    private ListView carbsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("View Carb Records");

        setContentView(R.layout.activity_view_carb_records);
        // Create connection to SQLite Database
        myDBHandler = new MyDBHandler(this, null, null, 1);
        //Reference Carbs List
        carbsListView = findViewById(R.id.listCarbs);
        //Get All Blood Sugars records
        ArrayList<Carbs> carbsList = myDBHandler.getAllCarbs();
        //Reverse array to put newest records at top of the list
        Collections.reverse(carbsList);

        Collections.sort(carbsList, new CarbsComparator());

        //apply ArrayList of carbs to ArrayAdapter to display in ListView
        ArrayAdapter<Carbs> adapter = new ArrayAdapter<Carbs>(this,android.R.layout.simple_list_item_1,carbsList);
        this.carbsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                Carbs selectedItem = (Carbs) parent.getItemAtPosition(position);

                AlertDialog.Builder builderHigh = new AlertDialog.Builder(ViewCarbRecords.this);
                builderHigh.setTitle("Delete Carb Record");
                builderHigh.setMessage("Are your sure you want to delete the record at " + myDBHandler.StringEpochToStringDate(String.valueOf(selectedItem.getTime())) + " which was " + selectedItem.getAmount() + " grams.");
                builderHigh.setCancelable(true);
                builderHigh.setIcon(R.drawable.dialogicon);
                builderHigh.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myDBHandler.deleteCarbs(selectedItem.getCarbsId());
                                dialog.cancel();
                                Intent i = new Intent(ViewCarbRecords.this, ViewCarbRecords.class);
                                startActivity(i);
                            }
                        });
                builderHigh.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builderHigh.create();
                alert11.show();
            }

        });
        carbsListView.setAdapter(adapter);
    }

    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
