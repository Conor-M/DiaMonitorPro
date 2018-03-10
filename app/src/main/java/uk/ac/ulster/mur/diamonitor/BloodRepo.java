package uk.ac.ulster.mur.diamonitor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;


public class BloodRepo {

    private Blood blood;

    public BloodRepo(){

        blood = new Blood();

    }

    public static String createTable(){
        return "CREATE TABLE " + Blood.TABLE  + "("
                + Blood.KEY_BloodID + " TEXT  PRIMARY KEY, "
                + Blood.KEY_Reading + " TEXT, "
                + Blood.KEY_TIME + "TEXT )";
    }

    public int insert(Blood blood) {
        int bloodId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Blood.KEY_BloodID, blood.getBloodId());
        values.put(Blood.KEY_Reading, blood.getReading());
        values.put(Blood.KEY_TIME, blood.getTime());

        // Inserting Row
        bloodId=(int)db.insert(Blood.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return bloodId;
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Blood.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
