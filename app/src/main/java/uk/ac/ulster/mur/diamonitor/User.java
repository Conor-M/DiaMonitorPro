package uk.ac.ulster.mur.diamonitor;

public class User {
    public static final String TAG = User.class.getSimpleName();
    public static final String TABLE = "User";

    // Labels Table Columns names
    public static final String KEY_CarbRatio = "CarbRatio";
    public static final String KEY_CorrectionRatio = "CorrectionRatio";
    public static final String KEY_MaxRange = "MaxRange";
    public static final String KEY_MinRange = "MinRange";
    public static final String KEY_FirstRun = "FirstRun";

    // Class Variables for storing DB Columns
    private int carbRatio ;
    private int correctionRatio;
    private float minRange;
    private float maxRange;
    private int firstRun;

    public int getCarbRatio() {return carbRatio;}

    public void setCarbRatio(int carbRatio) {this.carbRatio = carbRatio;}

    public int getCorrectionRatio() {return correctionRatio;}

    public void setCorrectionRatio(int correctionRatio) {this.correctionRatio = correctionRatio;}

    public float getMinRange() {return minRange;}

    public void setMinRange(float minRange) {this.minRange = minRange;}

    public float getMaxRange() {return maxRange;}

    public void setMaxRange(float maxRange) {this.maxRange = maxRange;}

    public int getFirstRun() {return firstRun;}

    public void setFirstRun(int firstRun) {this.firstRun = firstRun;}

}

