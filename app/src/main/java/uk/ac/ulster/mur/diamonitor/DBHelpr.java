package uk.ac.ulster.mur.diamonitor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelpr extends SQLiteOpenHelper {
    //VERSION NUMBER INCREMENT ON CHANGES
    private static final int DATABASE_VERSION =8;

    //NAME DATABASE WILL BE STORED IN
    private static final String DATABASE_NAME = "diamonitor.db";
    private static final String TAG = DBHelpr.class.getSimpleName().toString();

    public DBHelpr() {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE TABLES
        db.execSQL(CarbsRepo.createTable());
        db.execSQL(BloodRepo.createTable());
        db.execSQL(InsulinRepo.createTable());
        db.execSQL(UserRepo.createTable());
        //db.execSQL(Bloodreading.createTable());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        // DROP TABLE FOR UPGRADE
        db.execSQL("DROP TABLE IF EXISTS " + Carbs.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Blood.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Insulin.TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + BloodReading.TABLE);
        onCreate(db);
    }



}