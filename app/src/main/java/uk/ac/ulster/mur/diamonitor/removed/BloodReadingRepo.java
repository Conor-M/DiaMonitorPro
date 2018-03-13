package uk.ac.ulster.mur.diamonitor.removed;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import uk.ac.ulster.mur.diamonitor.DatabaseManager;

public class BloodReadingRepo {


    public BloodReadingRepo(){

    }

    private BloodReading bloodReading;

    public void insert(BloodReading bloodReading){
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(BloodReading.KEY_READINGID, bloodReading.getReadingId());
        values.put(BloodReading.KEY_BLOODID, bloodReading.getBloodId());
        values.put(BloodReading.KEY_INSULINID, bloodReading.getInsulinId());
        values.put(BloodReading.KEY_BLOODCOMM, bloodReading.getBloodComment());
        values.put(BloodReading.KEY_TIME, bloodReading.getTime());

        db.insert(BloodReading.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }

    public void delete() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(BloodReading.TABLE, null, null);
        DatabaseManager.getInstance().closeDatabase();
    }


    public void getBloodReadingList(){

    }


}
