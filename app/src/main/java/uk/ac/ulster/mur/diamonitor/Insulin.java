package uk.ac.ulster.mur.diamonitor;



public class Insulin{
    public static final String TAG = Insulin.class.getSimpleName();
    public static final String TABLE = "Insulin";
    // Labels Table Columns names
    public static final String KEY_InsulinId = "InsulinId";
    public static final String KEY_Units = "Units";
    public static final String KEY_TIME = "Time";

    private String insulinId;
    private String units;
    private String time;

    public String getUnits() {
        return units;
    }

    public void setName(String units) {
        this.units = units;
    }

    public String getInsulinId() {
        return insulinId;
    }

    public void setInsulinId(String insulinId) {
        this.insulinId = insulinId;
    }

    public String getTime() {
        return insulinId;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
