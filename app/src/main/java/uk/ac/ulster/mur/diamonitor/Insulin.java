package uk.ac.ulster.mur.diamonitor;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Model for the insulin table in the database
 *
 *
 * @author  Conor Murphy
 * @version 1.0
 * @since   2018-1-20
 *
 */
public class Insulin{

    //Table name
    public static final String TABLE = "Insulin";
    // Labels Table Columns names
    public static final String KEY_INSULINID = "InsulinId";
    public static final String KEY_UNITS = "Units";
    public static final String KEY_TIME = "Time";
    public static final int DEFAULTCORRRATIO = 2;
    public static final int DEFAULTCARBRATIO = 5;

    // Class Variables for storing DB Columns
    private int ID;
    private int units;
    private long time;

    //Get and Set methods
    public int getUnits(){return units;}
    public void setUnits(int units){this.units=units;}
    public int getID(){return ID;}
    public void setID(int ID){this.ID = ID;}
    public long getTime(){return time;}
    public void setTime(long time){this.time = time;}


    //To String Method for standard implementation of arrayAdapter
    @Override
    public String toString() {
        Date date = new Date(this.time);
        SimpleDateFormat format = new SimpleDateFormat("HH:mm dd-MM-yy");
        String stringDate = format.format(date);
        return " You injected " + units + " units of insulin on " + stringDate + " ";

    }
}
