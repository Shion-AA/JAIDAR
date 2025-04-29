package ph.edu.usc.jaidar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;


    private static final String DATABASE_NAME = "jaidarDB";
    private static final int DATABASE_VERSION = 1;

    // USERS Table
    public static final String TABLE_USERS = "users";
    public static final String USERS_ID = "user_id";
    public static final String USERS_NAME = "name";
    public static final String USERS_EMAIL = "email";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    USERS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USERS_NAME + " VARCHAR(255), " +
                    USERS_EMAIL + " VARCHAR(255));";

    // JOBS table
    public static final String TABLE_JOBS = "jobs";
    public static final String JOB_ID = "job_id";
    public static final String JOB_TITLE = "title";
    public static final String JOB_DESCRIPTION = "description";

    private static final String CREATE_TABLE_JOBS =
            "CREATE TABLE " + TABLE_JOBS + " (" +
                    JOB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    JOB_TITLE + " TEXT, " +
                    JOB_DESCRIPTION + " TEXT);";

    // Table 3: Applications (sample code for foreign keys)
//    public static final String TABLE_APPLICATIONS = "Applications";
//    public static final String APP_ID = "AppID";
//    public static final String APP_USER_ID = "UserID";
//    public static final String APP_JOB_ID = "JobID";
//
//    private static final String CREATE_TABLE_APPLICATIONS =
//            "CREATE TABLE " + TABLE_APPLICATIONS + " (" +
//                    APP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                    APP_USER_ID + " INTEGER, " +
//                    APP_JOB_ID + " INTEGER, " +
//                    "FOREIGN KEY(" + APP_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + USERS_ID + ") ON DELETE CASCADE, " +
//                    "FOREIGN KEY(" + APP_JOB_ID + ") REFERENCES " + TABLE_JOBS + "(" + JOB_ID + ") ON DELETE CASCADE);";
//
//    public void onConfigure(SQLiteDatabase db) {
//        db.setForeignKeyConstraintsEnabled(true); // Enable FK constraints
//    }

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE_JOBS);
            db.execSQL(CREATE_TABLE_USERS);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
            onCreate(db);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
