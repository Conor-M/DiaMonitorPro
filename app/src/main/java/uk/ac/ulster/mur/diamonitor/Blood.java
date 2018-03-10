package uk.ac.ulster.mur.diamonitor;


public class Blood {

    public static final String TAG = BloodReading.class.getSimpleName();
    public static final String TABLE = "Blood";

    // Labels Table Columns names
    public static final String KEY_BloodID = "BloodId";
    public static final String KEY_Reading = "Reading";
    public static final String KEY_TIME = "Time";

    private String ID ;
    private String Reading;
    private String Time;


    public String getBloodId() {
        return ID;
    }

    public void setBloodId(String ID) {
        this.ID = ID;
    }

    public String getReading() {
        return Reading;
    }

    public void setReading(String Reading) {
        this.Reading = Reading;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }


}

