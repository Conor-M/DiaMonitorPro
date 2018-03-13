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
    private String units;
    private int time;

    public String getUnits(){return units;}
    public void setUnits(String units){this.units=units;}
    public void setName(String units) {this.units = units;}
    public int getInsulinId(){return insulinId;}
    public void setInsulinId(int insulinId){this.insulinId = insulinId;}
    public int getTime(){return time;}
    public void setTime(int time){this.time = time;}
}
