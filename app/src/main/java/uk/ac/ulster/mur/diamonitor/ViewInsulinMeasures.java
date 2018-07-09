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

public class ViewInsulinMeasures extends AppCompatActivity {
    private MyDBHandler myDBHandler;
    private ListView insulinListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("View Insulin Records");

        setContentView(R.layout.activity_view_insulin_measures);
        myDBHandler = new MyDBHandler(this, null, null, 1);
        //Reference Insulin List
        insulinListView = findViewById(R.id.listInsulin);
        //Get All Insulin records
        ArrayList<Insulin> insulinList = myDBHandler.getAllInsulin();
        //Reverse list so that items are in reverse chronological order
        Collections.reverse(insulinList);

        Collections.sort(insulinList, new InsulinComparator());

        //apply ArrayList of Insulin to ArrayAdapter to display in ListView
        ArrayAdapter<Insulin> adapter = new ArrayAdapter<Insulin>(this,android.R.layout.simple_list_item_1,insulinList);
        this.insulinListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                Insulin selectedItem = (Insulin) parent.getItemAtPosition(position);

                AlertDialog.Builder builderHigh = new AlertDialog.Builder(ViewInsulinMeasures.this);
                builderHigh.setTitle("Delete Insulin Record");
                builderHigh.setMessage("Are your sure you want to delete the record at " + myDBHandler.StringEpochToStringDate(String.valueOf(selectedItem.getTime())) + " which was " + selectedItem.getUnits() + " units.");
                builderHigh.setCancelable(true);
                builderHigh.setIcon(R.drawable.dialogicon);
                builderHigh.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myDBHandler.deleteInsulin(selectedItem.getID());
                                dialog.cancel();
                                Intent i = new Intent(ViewInsulinMeasures.this, ViewInsulinMeasures.class);
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
        insulinListView.setAdapter(adapter);
    }

    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
