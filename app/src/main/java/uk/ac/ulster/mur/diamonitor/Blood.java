package uk.ac.ulster.mur.diamonitor;

public class Blood {
    public static final String TAG = Blood.class.getSimpleName();
    public static final String TABLE = "Blood";

    // Labels Table Columns names
    public static final String KEY_BloodID = "BloodId";
    public static final String KEY_Reading = "Reading";
    public static final String KEY_TIME = "Time";


    // Class Variables for storing DB Columns
    private int ID ;
    private float reading;
    private long time;


    public int getBloodId(){return ID;}
    public void setBloodId(int ID){this.ID = ID;}
    public float getReading(){return reading;}
    public void setReading(float reading){this.reading = reading;}
    public long getTime(){return time;}
    public void setTime(long time){this.time = time;}


}

