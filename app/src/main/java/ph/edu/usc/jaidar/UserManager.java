package ph.edu.usc.jaidar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserManager {

    private final MyDatabaseHelper dbHelper;

    public UserManager(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    public long insertUser(String name, String email) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.USERS_NAME, name);
        values.put(MyDatabaseHelper.USERS_EMAIL, email);
        return db.insert(MyDatabaseHelper.TABLE_USERS, null, values);
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(MyDatabaseHelper.TABLE_USERS, null, null, null, null, null, null);
    }

    public int updateUser(int userId, String newName, String newEmail) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDatabaseHelper.USERS_NAME, newName);
        values.put(MyDatabaseHelper.USERS_EMAIL, newEmail);
        return db.update(MyDatabaseHelper.TABLE_USERS, values,
                MyDatabaseHelper.USERS_ID + " = ?", new String[]{String.valueOf(userId)});
    }

    public int deleteUser(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(MyDatabaseHelper.TABLE_USERS,
                MyDatabaseHelper.USERS_ID + " = ?", new String[]{String.valueOf(userId)});
    }


//    USAGE EXAMPLE:
//    UserManager userManager = new UserManager(context);
//    userManager.insertUser("Alex", "alex@example.com");


}