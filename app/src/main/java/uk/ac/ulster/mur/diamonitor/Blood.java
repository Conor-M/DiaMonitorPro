package uk.ac.ulster.mur.diamonitor;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Model for the blood table in the database
 *
 *
 * @author  Conor Murphy
 * @version 1.0
 * @since   2018-1-20
 *
 */
public class Blood {

    //TABLE NAME
    public static final String TABLE = "Blood";

    // Labels Table Columns names
    public static final String KEY_BLOODID = "BloodId";
    public static final String KEY_READING = "Reading";
    public static final String KEY_TIME = "Time";
    public static final String DEFAULTMAXRANGE = "10.0";
    public static final String DEFAULTMINRANGE = "4.0";


    // Class Variables for storing DB Columns
    private int ID ;
    private float reading;
    private long time;

    //GETTERS AND SETTERS
    public int getBloodId(){return ID;}
    public void setBloodId(int ID){this.ID = ID;}
    public float getReading(){return reading;}
    public void setReading(float reading){this.reading = reading;}
    public long getTime(){return time;}
    public void setTime(long time){this.time = time;}

    //To String Method for standard implementation of arrayAdapter
    @Override
    public String toString() {
        Date date = new Date(this.time);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd-MM-yy");
        String stringDate = format.format(date);
        String objectString = "Blood Reading was " + reading + " at " + stringDate + " ";
        return objectString;
    }
}

