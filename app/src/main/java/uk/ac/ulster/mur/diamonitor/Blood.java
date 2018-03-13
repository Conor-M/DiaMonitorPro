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
    private String reading;
    private int time;


    public int getBloodId(){return ID;}
    public void setBloodId(int ID){this.ID = ID;}
    public String getReading(){return reading;}
    public void setReading(String reading){this.reading = reading;}
    public int getTime(){return time;}
    public void setTime(int time){this.time = time;}


}

