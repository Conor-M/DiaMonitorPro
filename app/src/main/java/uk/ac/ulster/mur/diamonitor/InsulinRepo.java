package uk.ac.ulster.mur.diamonitor;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;


public class InsulinRepo {
    private Insulin insulin;

    public InsulinRepo(){

        insulin = new Insulin();

    }

    public static String createTable(){
        return "CREATE TABLE " + Insulin.TABLE  + "("
                + Insulin.KEY_InsulinId + " TEXT  PRIMARY KEY AUTOINCREMENT "
                + Insulin.KEY_Units + " TEXT, "
                + Insulin.KEY_TIME + "INT )";
    }

    public int insert(Insulin insulin) {
        int insulinId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Insulin.KEY_InsulinId, insulin.getInsulinId());
        values.put(Insulin.KEY_Units, insulin.getUnits());
        values.put(Insulin.KEY_TIME, insulin.getTime());

        // Inserting Row
        insulinId=(int)db.insert(Insulin.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return insulinId;
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(Insulin.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
