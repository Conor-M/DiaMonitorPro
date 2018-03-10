package uk.ac.ulster.mur.diamonitor;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class CarbsRepo {
    private Carbs carbs;

    public CarbsRepo(){
        carbs = new Carbs();
    }

    public static String createTable(){
        return "CREATE TABLE " + Carbs.TABLE  + "("
                + Carbs.KEY_BloodID + " TEXT  PRIMARY KEY, "
                + Carbs.KEY_Amount + " TEXT, "
                + Carbs.KEY_time + "TEXT )";
    }

    public int insert(Blood blood) {
        int bloodId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Carbs.KEY_BloodID, blood.getBloodId());
        values.put(Carbs.KEY_Amount, carbs.getAmount());
        values.put(Carbs.KEY_time, carbs.getTime());

        // Inserting Row
        bloodId=(int)db.insert(Carbs.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return bloodId;
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Carbs.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
