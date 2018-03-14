package uk.ac.ulster.mur.diamonitor;



public class Insulin{
    public static final String TAG = Insulin.class.getSimpleName();
    public static final String TABLE = "Insulin";
    // Labels Table Columns names
    public static final String KEY_InsulinId = "InsulinId";
    public static final String KEY_Units = "Units";
    public static final String KEY_TIME = "Time";

    // Class Variables for storing DB Columns
    private int insulinId;
    private int units;
    private long time;

    public int getUnits(){return units;}
    public void setUnits(int units){this.units=units;}
    public int getInsulinId(){return insulinId;}
    public void setInsulinId(int insulinId){this.insulinId = insulinId;}
    public long getTime(){return time;}
    public void setTime(long time){this.time = time;}
}
