package uk.ac.ulster.mur.diamonitor;

import android.app.Application;
import android.content.Context;


public class App extends Application {
    private static Context context;
    private static DBHelpr dbHelpr;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelpr = new DBHelpr();
        DatabaseManager.initializeInstance(dbHelpr);

    }


    public static Context getContext(){
        return context;
    }

}

