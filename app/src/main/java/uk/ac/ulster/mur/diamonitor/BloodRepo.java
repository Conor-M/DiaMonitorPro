package uk.ac.ulster.mur.diamonitor;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class BloodRepo {

    private Blood blood;

    public BloodRepo(){blood = new Blood();}

    public static String createTable(){
        return "CREATE TABLE " + Blood.TABLE  + "("
                + Blood.KEY_BloodID + " TEXT  PRIMARY KEY AUTOINCREMENT,  "
                + Blood.KEY_Reading + " TEXT, "
                + Blood.KEY_TIME + "INT )";
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

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + Blood.TABLE + " WHERE 1";// SELECT ALL

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Reading")) != null) {
                dbString += "||";
                dbString += recordSet.getString(recordSet.getColumnIndex("BloodId"));
                dbString += "||";
                dbString += recordSet.getString(recordSet.getColumnIndex("Reading"));
                dbString += "||";
                recordSet.getString(recordSet.getColumnIndex("Time"));
                dbString += "||\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }
}
