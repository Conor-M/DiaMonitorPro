package uk.ac.ulster.mur.diamonitor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Blood {

    //TABLE NAME AND LOGCAT DEBUG TAG
    public static final String TAG = Blood.class.getSimpleName();
    public static final String TABLE = "Blood";

    // Labels Table Columns names
    public static final String KEY_BLOODID = "BloodId";
    public static final String KEY_READING = "Reading";
    public static final String KEY_TIME = "Time";


    // Class Variables for storing DB Columns
    private int ID ;
    private double reading;
    private long time;

    //GETTERS AND SETTERS
    public int getBloodId(){return ID;}
    public void setBloodId(int ID){this.ID = ID;}
    public double getReading(){return reading;}
    public void setReading(double reading){this.reading = reading;}
    public long getTime(){return time;}
    public void setTime(long time){this.time = time;}

    @Override
    public String toString() {
        Date date = new Date(this.time);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd-MM-yy");
        String stringDate = format.format(date);
        String objectString = "|Blood Reading was " + reading + " at " + stringDate + "|";
        return objectString;
    }
}

