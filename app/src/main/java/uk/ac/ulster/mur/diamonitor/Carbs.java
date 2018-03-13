package uk.ac.ulster.mur.diamonitor;


public class Carbs {

    public static final String TAG = Carbs.class.getSimpleName();
    public static final String TABLE = "Carbs";

    // Labels Table Columns names
    public static final String KEY_BloodID = "CarbsId";
    public static final String KEY_Amount = "Amount";
    public static final String KEY_Time = "Time";

    // Class Variables for storing DB Columns
    private int ID ;
    private String amount;
    private int time;

    public int getCarbsId(){return ID;}
    public void setCarbsId(int ID){this.ID = ID;}
    public String getAmount(){return amount;}
    public void setAmount(String amount){this.amount = amount;}
    public int getTime(){return time;}
    public void setTime(int time){this.time = time;}
}

