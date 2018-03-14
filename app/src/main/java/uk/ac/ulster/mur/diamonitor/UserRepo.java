/*package uk.ac.ulster.mur.diamonitor;

import android.content.ContentValues;
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
        SQLiteDatabase db =
        ContentValues values = new ContentValues();
        values.put(User.KEY_CarbRatio, user.getCarbRatio());
        values.put(User.KEY_CorrectionRatio, user.getCorrectionRatio());
        values.put(User.KEY_FirstRun, user.getFirstRun());
        values.put(User.KEY_MaxRange, user.getMaxRange());
        values.put(User.KEY_MinRange, user.getMinRange());

        // Inserting Row
        userId=(int)db.insert(User.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return userId;
    }

    public void delete( ) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(User.TABLE, null,null);
        DatabaseManager.getInstance().closeDatabase();
    }
}
*/