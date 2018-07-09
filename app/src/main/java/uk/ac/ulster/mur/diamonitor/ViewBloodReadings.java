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


public class ViewBloodReadings extends AppCompatActivity {

    private MyDBHandler myDBHandler;
    private ListView bloodListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blood_readings);
        // Create connection to SQLite Database
        myDBHandler = new MyDBHandler(this, null, null, 1);

        //Reference ListView
        bloodListView = findViewById(R.id.listBloodReading);
        //Get All Blood Sugars records
        ArrayList<Blood> bloodList = myDBHandler.getAllBlood();
        //Reverse array to put newest records at top of the list
        Collections.reverse(bloodList);
        Collections.sort(bloodList, new BloodComparator());


        //Create and set array adapter to put Blood records in the list
        ArrayAdapter<Blood> adapter = new ArrayAdapter<Blood>(this,android.R.layout.simple_list_item_1,bloodList);
        this.bloodListView.setAdapter(adapter);


        // Populate ListView with items from ArrayAdapter
        // Set an item click listener for ListView
                this.bloodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                Blood selectedItem = (Blood) parent.getItemAtPosition(position);

                AlertDialog.Builder builderHigh = new AlertDialog.Builder(ViewBloodReadings.this);
                builderHigh.setTitle("Delete blood reading");
                builderHigh.setMessage("Are your sure you want to delete the record at " + myDBHandler.StringEpochToStringDate(String.valueOf(selectedItem.getTime())) + " which was " + selectedItem.getReading() + " mmol/l");
                builderHigh.setCancelable(true);
                builderHigh.setIcon(R.drawable.dialogicon);
                builderHigh.setPositiveButton(
                        "Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myDBHandler.deleteBlood(selectedItem.getBloodId());
                                dialog.cancel();
                                Intent i = new Intent(ViewBloodReadings.this, ViewBloodReadings.class);
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
        this.bloodListView.setAdapter(adapter);



    }
    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
