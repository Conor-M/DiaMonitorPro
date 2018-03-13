package uk.ac.ulster.mur.diamonitor.removed;


public class CarbInsulin {
    public static final String TAG = CarbInsulin.class.getSimpleName();
    public static final String TABLE = "BloodInsulin";

    // Labels Table Columns names
    public static final String KEY_BLOODID = "BloodId";
    public static final String KEY_INSULINID = "InsulinId";
    public static final String KEY_READINGID = "ReadingId";
    public static final String KEY_BLOODCOMM = "BloodComm";

    private String ID;
    private String bloodId;
    private String insulinId;

    public String getBloodComment() {
        return bloodComment;
    }

    public void setBloodComment(String bloodComment) {
        this.bloodComment = bloodComment;
    }

    private String bloodComment;

    public String getReadingId() {
        return ID;
    }

    public void setReadingId(String ID) {
        this.ID = ID;
    }

    public String getBloodId() {
        return bloodId;
    }

    public void setBloodId(String bloodId) {
        this.bloodId = bloodId;
    }

    public String getInsulinId() {
        return insulinId;
    }

    public void setInsulinId(String InsulinId) {
        this.insulinId = InsulinId;
    }


}
