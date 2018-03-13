package uk.ac.ulster.mur.diamonitor;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CarbsRepo {
    private Carbs carbs;

    public CarbsRepo(){carbs = new Carbs();}

    public static String createTable(){
        return "CREATE TABLE " + Carbs.TABLE  + "("
                + Carbs.KEY_BloodID + " TEXT  PRIMARY KEY AUTOINCREMENT, "
                + Carbs.KEY_Amount + " TEXT, "
                + Carbs.KEY_Time + "INT )";
    }

    public int insert(Blood blood) {
        int bloodId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Carbs.KEY_BloodID, blood.getBloodId());
        values.put(Carbs.KEY_Amount, carbs.getAmount());
        values.put(Carbs.KEY_Time, carbs.getTime());

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

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String query = "SELECT * FROM " + Carbs.TABLE + " WHERE 1";// SELECT ALL

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Amount")) != null) {
                dbString += "||";
                dbString += recordSet.getString(recordSet.getColumnIndex("CarbsId"));
                dbString += "||";
                dbString += recordSet.getString(recordSet.getColumnIndex("Amount"));
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
