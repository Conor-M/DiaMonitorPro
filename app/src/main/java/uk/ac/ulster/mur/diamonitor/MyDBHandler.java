package uk.ac.ulster.mur.diamonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "diamonitor1.db";


    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createCarbsTable());
        db.execSQL(createBloodsTable());
        db.execSQL(createInsulinTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // DROP TABLE FOR UPGRADE
        db.execSQL("DROP TABLE IF EXISTS " + Carbs.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Blood.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Insulin.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + User.TABLE);
        onCreate(db);
    }

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //CREATE TABLES

    public static String createInsulinTable(){
        return "CREATE TABLE " + Insulin.TABLE  + "("
                + Insulin.KEY_INSULINID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Insulin.KEY_UNITS + " INTEGER, "
                + Insulin.KEY_TIME + " LONG )";
    }
    public static String createBloodsTable(){
        return "CREATE TABLE " + Blood.TABLE  + "("
                + Blood.KEY_BLOODID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "
                + Blood.KEY_READING + " DECIMAL(4,1), "
                + Blood.KEY_TIME + " LONG )";
    }
    public static String createCarbsTable(){
        return "CREATE TABLE " + Carbs.TABLE  + "("
                + Carbs.KEY_CARBSID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Carbs.KEY_AMOUNT + " INTEGER, "
                + Carbs.KEY_TIME + " LONG )";
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    //INSERT into tables methods
    public void addCarbs(Carbs carbs) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Carbs.KEY_AMOUNT, carbs.getAmount());
        values.put(Carbs.KEY_TIME, carbs.getTime());
        // Inserting Row
        db.insert(Carbs.TABLE, null, values);
        db.close();
    }
    public void addInsulin(Insulin insulin) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Insulin.KEY_UNITS, insulin.getUnits());
        values.put(Insulin.KEY_TIME, insulin.getTime());
        // Inserting Row
        db.insert(Insulin.TABLE, null, values);
        db.close();
    }

    public void deleteInsulin(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+Insulin.TABLE+" where "+Insulin.KEY_INSULINID+"='"+id+"'");
        db.close();
    }

    public void deleteCarbs(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+Carbs.TABLE+" where "+Carbs.KEY_CARBSID+"='"+id+"'");
        db.close();
    }

    public void deleteBlood(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+Blood.TABLE+" where "+Blood.KEY_BLOODID+"='"+id+"'");
        db.close();
    }

    public void addBlood(Blood blood) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Blood.KEY_READING, blood.getReading());
        values.put(Blood.KEY_TIME, blood.getTime());

        // Inserting Row
        db.insert(Blood.TABLE, null, values);
        db.close();

    }

    //RETURN all records in the database table
    public ArrayList<Blood> getAllBlood(){


        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + Blood.TABLE + " WHERE 1";// SELECT ALL
        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        ArrayList<Blood> bloodList = new ArrayList<Blood>();
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex(Blood.KEY_READING)) != null) {
                Blood blood = new Blood();
                blood.setBloodId(recordSet.getInt(recordSet.getColumnIndex(Blood.KEY_BLOODID)));
                blood.setReading(recordSet.getFloat(recordSet.getColumnIndex(Blood.KEY_READING)));
                blood.setTime(recordSet.getLong(recordSet.getColumnIndex(Blood.KEY_TIME)));
                bloodList.add(blood);
            }
            recordSet.moveToNext();
        }
        return bloodList;
    }

    public ArrayList<Insulin> getAllInsulin(){
        ArrayList<Insulin> insulinList = new ArrayList<Insulin>();
        SQLiteDatabase db = getWritableDatabase();
        //Select all fields from table insulin select all records
        String query = "SELECT * FROM " + Insulin.TABLE + " WHERE 1";// SELECT ALL
        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex(Insulin.KEY_UNITS)) != null) {
                Insulin insulin = new Insulin();
                insulin.setID(recordSet.getInt(recordSet.getColumnIndex(Insulin.KEY_INSULINID)));
                insulin.setUnits(recordSet.getInt(recordSet.getColumnIndex(Insulin.KEY_UNITS)));
                insulin.setTime(recordSet.getLong(recordSet.getColumnIndex(Insulin.KEY_TIME)));
                insulinList.add(insulin);
            }
            recordSet.moveToNext();
        }
        return insulinList;
    }

    public ArrayList<Carbs> getAllCarbs(){
        ArrayList<Carbs> carbsList = new ArrayList<Carbs>();
        SQLiteDatabase db = getWritableDatabase();
        //Select all fields from table carbs select all records
        String query = "SELECT * FROM " + Carbs.TABLE + " WHERE 1";// SELECT ALL
        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex(Carbs.KEY_AMOUNT)) != null) {
                Carbs carbs = new Carbs();
                carbs.setCarbsId(recordSet.getInt(recordSet.getColumnIndex(Carbs.KEY_CARBSID)));
                carbs.setAmount(recordSet.getInt(recordSet.getColumnIndex(Carbs.KEY_AMOUNT)));
                carbs.setTime(recordSet.getLong(recordSet.getColumnIndex(Carbs.KEY_TIME)));
                carbsList.add(carbs);
            }
            recordSet.moveToNext();
        }
        return carbsList;
    }

    public String StringEpochToStringDate(String epoch){
        long epochSec = Long.parseLong(epoch);
        Date date = new Date(epochSec);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm");
        return format.format(date);
    }
    public int StringEpochToHour(long epoch) {
        String hour;
        Date date = new Date(epoch);
        SimpleDateFormat format = new SimpleDateFormat("HH");
        hour = format.format(date);
        return Integer.parseInt(hour);
    }
    /* DATABASE TABLE TO STRING DEBUGGING FUNCTIONS
    public String bloodDatabaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        //Select all fields from table blood select all records
        String query = "SELECT * FROM " + Blood.TABLE + " WHERE 1";// SELECT ALL

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();

        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {
            // null could happen if we used our empty constructor
            if (recordSet.getString(recordSet.getColumnIndex(Blood.KEY_READING)) != null) {
                dbString += "||";
                dbString += recordSet.getString(recordSet.getColumnIndex(Blood.KEY_BLOODID));
                dbString += "||";
                dbString += recordSet.getString(recordSet.getColumnIndex(Blood.KEY_READING));
                dbString += "||";
                dbString += StringEpochToStringDate(recordSet.getString(recordSet.getColumnIndex(Blood.KEY_TIME)));
                dbString += "||\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
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
            if (recordSet.getString(recordSet.getColumnIndex(Carbs.KEY_AMOUNT)) != null) {
                dbString += "||";
                dbString += recordSet.getString(recordSet.getColumnIndex(Carbs.KEY_CARBSID));
                dbString += "||";
                dbString += recordSet.getString(recordSet.getColumnIndex(Carbs.KEY_AMOUNT));
                dbString += "||";
                dbString += StringEpochToStringDate(recordSet.getString(recordSet.getColumnIndex(Carbs.KEY_TIME)));
                dbString += "||\n";
            }
            recordSet.moveToNext();
        }
        db.close();
        return dbString;
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
            if (recordSet.getString(recordSet.getColumnIndex(Insulin.KEY_UNITS)) != null) {
                dbString += "||";
                dbString += recordSet.getString(recordSet.getColumnIndex(Insulin.KEY_INSULINID));
                dbString += "||";
                dbString += recordSet.getString(recordSet.getColumnIndex(Insulin.KEY_UNITS));
                dbString += "||";
                dbString += StringEpochToStringDate(recordSet.getString(recordSet.getColumnIndex(Insulin.KEY_TIME)));
                dbString += "||\n";
            }
            recordSet.moveToNext();

        }

        db.close();
        return dbString;
    }*/
}