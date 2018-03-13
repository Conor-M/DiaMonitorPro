package uk.ac.ulster.mur.diamonitor;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class UserRepo {

    private User user;

    public UserRepo(){user = new User();}

    public static String createTable(){
        return "CREATE TABLE " + User.TABLE  + "("
                + User.KEY_CarbRatio + " INT,  "
                + User.KEY_CorrectionRatio + " INT, "
                + User.KEY_MaxRange + " FLOAT, "
                + User.KEY_MaxRange + " FLOAT, "
                + User.KEY_FirstRun + "INT )";
    }

    public int insert(User user) {
        int userId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_CarbRatio, user.getCarbRatio());
        values.put(User.KEY_CorrectionRatio, user.getCorrectionRatio());
        values.put(User.KEY_FirstRun, user.getFirstRun());
        values.put(User.KEY_MaxRange, user.getMaxRange());
        values.put(User.KEY_MinRange, user.getMinRange());

        // Inserting Row
        userId=(int)db.insert(Blood.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return userId;
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
