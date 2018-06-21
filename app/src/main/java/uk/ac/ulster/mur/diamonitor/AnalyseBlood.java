package uk.ac.ulster.mur.diamonitor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class AnalyseBlood extends AppCompatActivity {

    private MyDBHandler myDBHandler;
    ArrayList<Blood> bloodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse_blood);
        myDBHandler = new MyDBHandler(this, null, null, 1);
        //Get All Blood Sugars records
        bloodList = myDBHandler.getAllBlood();
        //Reverse array to put newest records at top of the list
        Collections.reverse(bloodList);
    }

    public void analyseBloods(){
        int count = 0;
        double averageTotal = 0;
        double maxValue = 0.1;
        double minValue = 100.1;
        for(Blood bloodReading: bloodList){
                //1209600seconds = 2weeks
            long timeCurr = System.currentTimeMillis();
            long prev2WeeksTime = timeCurr - 1209600;
            double br = bloodReading.getReading();
            long time = bloodReading.getTime();
            if(time<prev2WeeksTime){
                break;
            }
            averageTotal += br;
            count++;

            //Sets the maximum and minimum recorded blood sugar in the 2 week period.
            if(br>maxValue)
                maxValue=br;
            if(br<minValue)
                minValue=br;



            myDBHandler.StringEpochToTime(time);

        }
        //bloodList
    }
}
