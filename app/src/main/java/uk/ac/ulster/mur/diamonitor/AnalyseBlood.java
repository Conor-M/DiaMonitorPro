package uk.ac.ulster.mur.diamonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
/**
 * Activity to Analyse the blood records in the Blood Database
 *
 *
 * @author  Conor Murphy
 * @version 1.0
 * @since   2018-1-20
 *
 */
public class AnalyseBlood extends AppCompatActivity {

    private MyDBHandler dbHandler;
    private ArrayList<Blood> bloodList;
    private TextView tvAnalysisResult;
    long timeCurr = System.currentTimeMillis();

    /**
     * Creates the view of the activity when the activity is first started
     * sets title of the activity to be displayed
     *
     * @param savedInstanceState Required as is an implementation of the onClick defined in xml for this activit
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Analyse Blood Readings");
        setContentView(R.layout.activity_analyse_blood);
        dbHandler = new MyDBHandler(this, null, null, 1);
        //Get All Blood Sugars records
        //Reverse array to put newest records at top of the list
        analyseBloods();
        DrawGraph();
    }


    /**
     * Analyses the Blood sugar readings for the last 2 weeks
     * Finds the maximum/minimum blood sugar within this time period
     * Finds the number of Low and High blood sugar readings at morning, evening and night within this time period
     * Finds the Average reading of all the blood sugars within the time period
     *
     * Then outputs this information in a textview for the user to read
     */
    public void analyseBloods(){
        int count = 0;
        float averageTotal = 0f;
        float maxValue = 0.00f; //set low so first value takes maximum value
        float minValue = 100.00f; // set high so first value takes minimum value
        int highNight = 0, highEvening = 0, highMorning = 0, lowNight = 0, lowEvening = 0, lowMorning = 0;
        bloodList = dbHandler.getAllBlood();
        Collections.reverse(bloodList);
        for(Blood bloodReading: bloodList){
                //1209600 seconds = 2weeks
            long prev2WeeksTime = timeCurr - TimeUnit.DAYS.toMillis(14);;
            float br = bloodReading.getReading();
            long time = bloodReading.getTime();
            int bsHour = dbHandler.StringEpochToHour(time); //Extract the hour digit out of the time for period of the day
            SharedPreferences sharedPref = getSharedPreferences("UserSettings", Context.MODE_PRIVATE);
            float minRange = Float.parseFloat(sharedPref.getString("minRange", Blood.DEFAULTMINRANGE));
            float maxRange = Float.parseFloat(sharedPref.getString("maxRange", Blood.DEFAULTMAXRANGE));


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
                "During the last 2 weeks your average blood sugar reading has been " + average + " mmol/l\n"
                +"You had a maximum reading of " + maxValue + " mmol/l\n"
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

    /**
     * Draws a graph of the average blood sugar for each of the last 7 days
     */
    public void DrawGraph(){
        bloodList = dbHandler.getAllBlood();
        //Collections.reverse(bloodList);
        //sorts blood readings by date
        Collections.sort(bloodList, new BloodComparator());

        int countDays = 0;
        int count = 0;
        float averageTotal = 0.0f;
        float[] averages = new float[7];

        for(Blood bloodReading: bloodList){
            //Stops the for loop after the 7th day
            if(countDays==7)
                break;
            long time = bloodReading.getTime();

            //finds the amount of time in millis of the amount of days averaged
            long start = TimeUnit.DAYS.toMillis(countDays);
            long startTime = timeCurr - start;


            long end = TimeUnit.DAYS.toMillis(countDays+1);
            long endTime = timeCurr - end;

            //checks the reading was between the time of the last 2 days
            if(time<startTime && time>endTime){
                //gets the reading
                float reading = bloodReading.getReading();
                //adds the reading to average
                averageTotal += reading;
                count++;
                //finds the average of the readings
                averages[countDays] = averageTotal/count;



            }else {
                //In the case that the next reading is the next day this code
                //will execute to add the reading to the next days average and then increase the daycount


                count = 1;
                averageTotal = 0;
                float reading = bloodReading.getReading();

                averageTotal += reading;
                //if the reading is on the 7th day this prevents  array out of bounds
                if(countDays<6) {
                    averages[countDays + 1] = averageTotal / count;
                }

                //if(countMin)
                countDays++;

            }
        }

        // This is the code that plots the line for the graph
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, averages[6]),
                new DataPoint(2, averages[5]),
                new DataPoint(3, averages[4]),
                new DataPoint(4, averages[3]),
                new DataPoint(5, averages[2]),
                new DataPoint(6, averages[1]),
                new DataPoint(7, averages[0])
        });
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(8);
        series.setThickness(6);


        // custom paint to make a dotted line and format the graph
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 10}, 1));
        series.setCustomPaint(paint);
        graph.addSeries(series);
        graph.getViewport().setXAxisBoundsManual(false);
        graph.setTitle("Daily Average Blood Sugar Reading");
        graph.setTitleColor(Color.BLACK);
        graph.setBackgroundColor(Color.argb(50, 13, 205, 255));
        graph.getViewport().setMinX(1);
        graph.getViewport().setMaxX(8);
        graph.getViewport().setXAxisBoundsManual(true);
        // custom label formatter to show currency "EUR"
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return "Day " + super.formatLabel(value, isValueX);

                } else {
                    // show mmol/l for y values
                    return super.formatLabel(value, isValueX) + " mmol/l";
                }
            }
        });
    }
    /**
     * Brings the user back to the home activity on click of the button
     * @param view Required as is an implementation of the onClick defined in xml for this activity
     */
    public void HomeButtonClicked(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
