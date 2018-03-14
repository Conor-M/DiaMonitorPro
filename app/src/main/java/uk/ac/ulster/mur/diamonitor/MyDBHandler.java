package uk.ac.ulster.mur.diamonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "diamonitor.db";


    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createCarbsTable());
        db.execSQL(createBloodsTable());
        db.execSQL(createInsulinTable());
        db.execSQL(createUserTable());
    }

    public static String createUserTable(){
        return "CREATE TABLE " + User.TABLE  + "("
                + User.KEY_CarbRatio + " INTEGER,"
                + User.KEY_CorrectionRatio + " INTEGER, "
                + User.KEY_MaxRange + " FLOAT, "
                + User.KEY_MinRange + " FLOAT, "
                + User.KEY_FirstRun + " INTEGER )";
    }

    public void editUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(User.KEY_CarbRatio, user.getCarbRatio());
        values.put(User.KEY_CorrectionRatio, user.getCorrectionRatio());
        values.put(User.KEY_MinRange, user.getMinRange());
        values.put(User.KEY_MaxRange, user.getMaxRange());
        values.put(User.KEY_FirstRun, 0);

        // Inserting Row
        db.insert(User.TABLE, null, values);
        db.close();
    }






    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // DROP TABLE FOR UPGRADE
        db.execSQL("DROP TABLE IF EXISTS " + Carbs.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Blood.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Insulin.TABLE);
        onCreate(db);
    }

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    public static String createInsulinTable(){
        return "CREATE TABLE " + Insulin.TABLE  + "("
                + Insulin.KEY_InsulinId + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Insulin.KEY_Units + " INTEGER, "
                + Insulin.KEY_TIME + " LONG )";
    }
    public void addInsulin(Insulin insulin) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Insulin.KEY_InsulinId, insulin.getInsulinId());
        values.put(Insulin.KEY_Units, insulin.getUnits());
        values.put(Insulin.KEY_TIME, insulin.getTime());

        // Inserting Row
        db.insert(Insulin.TABLE, null, values);
        db.close();
    }

    public void deleteInsulin( ) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Insulin.TABLE, null,null);
        db.close();
    }

    public String InsulinDatabaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + Insulin.TABLE + " WHERE 1";// SELECT ALL

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex("Units")) != null) {
                dbString += "||";
                dbString += recordSet.getString(recordSet.getColumnIndex("InsulinId"));
                dbString += "||";
                dbString += recordSet.getString(recordSet.getColumnIndex("Units"));
                dbString += "||";
                dbString += StingEpochToStringDate(recordSet.getString(recordSet.getColumnIndex("Time")));
                dbString += "||\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

    public static String createCarbsTable(){
        return "CREATE TABLE " + Carbs.TABLE  + "("
                + Carbs.KEY_CarbsID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Carbs.KEY_Amount + " INTEGER, "
                + Carbs.KEY_Time + " LONG )";
    }

    public void addCarbs(Carbs carbs) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Carbs.KEY_Amount, carbs.getAmount());
        values.put(Carbs.KEY_Time, carbs.getTime());
        // Inserting Row
        db.insert(Carbs.TABLE, null, values);
        db.close();
    }

    public void deleteCarbs( ) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Carbs.TABLE, null,null);
        db.close();
    }

    public String CarbsDatabaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
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
                dbString += StingEpochToStringDate(recordSet.getString(recordSet.getColumnIndex("Time")));
                dbString += "||\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }

    public static String createBloodsTable(){
        return "CREATE TABLE " + Blood.TABLE  + "("
                + Blood.KEY_BloodID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + Blood.KEY_Reading + " FLOAT, "
                + Blood.KEY_TIME + " LONG )";
    }

    public void addBlood(Blood blood) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Blood.KEY_Reading, blood.getReading());
        values.put(Blood.KEY_TIME, blood.getTime());

        // Inserting Row
        db.insert(Blood.TABLE, null, values);
        db.close();

    }

    public void deleteBlood() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Blood.TABLE, null,null);
        db.close();
    }

    public String bloodDatabaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
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
                dbString += StingEpochToStringDate(recordSet.getString(recordSet.getColumnIndex("Time")));
                dbString += "||\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
    }



    public String StingEpochToStringDate(String epoch){
        String stringDate = "";
        long epochSec = Long.parseLong(epoch);

        Date date = new Date(epochSec);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");
        return format.format(date);
    }
}
