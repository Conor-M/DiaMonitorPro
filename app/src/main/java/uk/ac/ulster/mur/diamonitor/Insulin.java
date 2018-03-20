package uk.ac.ulster.mur.diamonitor;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Insulin{

    //TABLE NAME AND LOGCAT DEBUG TAG
    public static final String TAG = Insulin.class.getSimpleName();
    public static final String TABLE = "Insulin";
    // Labels Table Columns names
    public static final String KEY_INSULINID = "InsulinId";
    public static final String KEY_UNITS = "Units";
    public static final String KEY_TIME = "Time";

    // Class Variables for storing DB Columns
    private int ID;
    private int units;
    private long time;

    //GETTERS AND SETTERS
    public int getUnits(){return units;}
    public void setUnits(int units){this.units=units;}
    public int getID(){return ID;}
    public void setID(int ID){this.ID = ID;}
    public long getTime(){return time;}
    public void setTime(long time){this.time = time;}

    @Override
    public String toString() {
        Date date = new Date(this.time);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");
        String stringDate = format.format(date);
        String objectString = ID + " || " + units + " || " + stringDate;
        return objectString;
    }
}
