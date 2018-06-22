package uk.ac.ulster.mur.diamonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class AnalyseBlood extends AppCompatActivity {

    private MyDBHandler myDBHandler;
    private ArrayList<Blood> bloodList;
    private final int DEFAULTCORRRATIO = 2;
    private final int DEFAULTCARBRATIO = 5;
    private final String DEFAULTMAXRANGE = "10.0";
    private final String DEFAULTMINRANGE = "4.0";
    private TextView tvAnalysisResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyse_blood);
        myDBHandler = new MyDBHandler(this, null, null, 1);
        //Get All Blood Sugars records
        //Reverse array to put newest records at top of the list
        analyseBloods();
    }
    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void analyseBloods(){
        int count = 0;
        float averageTotal = 0f;
        float maxValue = 0.00f; //set low so first value takes maximum value
        float minValue = 100.00f; // set high so first value takes minimum value
        int highNight = 0, highEvening = 0, highMorning = 0, lowNight = 0, lowEvening = 0, lowMorning = 0;
        bloodList = myDBHandler.getAllBlood();
        Collections.reverse(bloodList);
        for(Blood bloodReading: bloodList){
                //1209600 seconds = 2weeks
            long timeCurr = System.currentTimeMillis();
            long prev2WeeksTime = timeCurr - 1209600000;
            float br = bloodReading.getReading();
            long time = bloodReading.getTime();
            int bsHour = myDBHandler.StringEpochToHour(time); //Extract the hour digit out of the time for period of the day
            SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
            float minRange = Float.parseFloat(sharedPref.getString("minRange", DEFAULTMINRANGE));
            float maxRange = Float.parseFloat(sharedPref.getString("maxRange", DEFAULTMAXRANGE));


            if(time<prev2WeeksTime)//if the time of recording the blood sugar is greater than 2 weeks old
                break; //break out of for loop as out of range of analysis

            averageTotal += br; //create a running total of the blood sugars
            count++; // count number of blood sugars as the divisor

            //Sets the maximum and minimum recorded blood sugar in the 2 week period.
            if(br>maxValue){ // if blood reading is higher than current maxvalue
                maxValue=br;// set blood reading to max value
            }else if(br<minValue) { //
                minValue = br;
            }

            if(br>maxRange){
                if(bsHour>5 && bsHour <13){
                    //Add one to morning time high
                    highMorning++;
                }else if(bsHour>12 && bsHour<20){
                    //Add one to evening time high
                    highEvening++;
                }else if(bsHour>19 && bsHour<6){
                    //Add one to night time high
                    highNight++;
                }
            }else if(br<minRange){
                if(bsHour>5 && bsHour <13){
                    //Add one to morning time low
                    lowMorning++;
                }else if(bsHour>12 && bsHour<21){
                    //Add one to evening time low
                    lowEvening++;
                }else if(bsHour>20 && bsHour<6){
                    //Add one to night time low
                    lowNight++;
                }
            }
        }// For loop End
        double average = averageTotal/count;
        average = Math.round(average*10)/10.0d;
        tvAnalysisResult = findViewById(R.id.tvAnalysisResult);
        String analysisResults =
                "During the last 2 weeks your Average Blood Sugars has been " + average + " mmol/l\n"
                +"You had  a maximum reading of " + maxValue + " mmol/l\n"
                +"You had a minimum reading of " + minValue + " mmol/l\n\n";

        if(highMorning>2){
            analysisResults += "You have had " + highMorning + " high readings in the morning time\n";
        }
        if(highEvening>2){
            analysisResults += "You have had " + highEvening + " high readings in the evening time\n";
        }
        if(highNight>2){
            analysisResults += "You have had " + highNight + " high readings in the night time\n";
        }
        if(lowMorning>2){
            analysisResults += "You have had " + lowMorning + " low readings in the morning time\n";
        }
        if(lowEvening>2){
            analysisResults += "You have had " + lowEvening + " low readings in the evening time\n";
        }
        if(lowNight>2){
            analysisResults += "You have had " + lowNight + " low readings in the night time\n";
        }
        tvAnalysisResult.setText(analysisResults);



    }

}
