package uk.ac.ulster.mur.diamonitor;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Carbs {

    //TABLE NAME AND LOGCAT DEBUG TAG
    public static final String TAG = Carbs.class.getSimpleName();
    public static final String TABLE = "Carbs";

    // Labels Table Columns names
    public static final String KEY_CARBSID = "CarbsId";
    public static final String KEY_AMOUNT = "Amount";
    public static final String KEY_TIME = "Time";

    // Class Variables for storing DB Columns
    private int ID ;
    private int amount;
    private long time;

    //GETTERS AND SETTERS
    public int getCarbsId(){return ID;}
    public void setCarbsId(int ID){this.ID = ID;}
    public int getAmount(){return amount;}
    public void setAmount(int amount){this.amount = amount;}
    public long getTime(){return time;}
    public void setTime(long time){this.time = time;}

    @Override
    public String toString() {
        Date date = new Date(this.time);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");
        String stringDate = format.format(date);
        String objectString = ID + " || " + amount + " || " + stringDate;
        return objectString;
    }
}

