package uk.ac.ulster.mur.diamonitor;


public class Carbs {

    public static final String TAG = Carbs.class.getSimpleName();
    public static final String TABLE = "Carbs";

    // Labels Table Columns names
    public static final String KEY_CarbsID = "CarbsId";
    public static final String KEY_Amount = "Amount";
    public static final String KEY_Time = "Time";

    // Class Variables for storing DB Columns
    private int ID ;
    private int amount;
    private long time;

    public int getCarbsId(){return ID;}
    public void setCarbsId(int ID){this.ID = ID;}
    public int getAmount(){return amount;}
    public void setAmount(int amount){this.amount = amount;}
    public long getTime(){return time;}
    public void setTime(long time){this.time = time;}
}

