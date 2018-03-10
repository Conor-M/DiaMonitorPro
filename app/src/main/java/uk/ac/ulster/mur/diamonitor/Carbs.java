package uk.ac.ulster.mur.diamonitor;


public class Carbs {

    public static final String TAG = Carbs.class.getSimpleName();
    public static final String TABLE = "Carbs";


    // Labels Table Columns names
    public static final String KEY_BloodID = "BloodId";
    public static final String KEY_Amount = "Amount";
    public static final String KEY_time = "time";

    private String ID ;
    private String amount;
    private String time;

    public String getStudentId() {
        return ID;
    }

    public void setStudentId(String ID) {
        this.ID = ID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

