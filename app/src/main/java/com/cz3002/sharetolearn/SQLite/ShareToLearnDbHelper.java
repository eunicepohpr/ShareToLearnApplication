package com.cz3002.sharetolearn.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;

public class ShareToLearnDbHelper extends SQLiteOpenHelper { // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ShareToLearn.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ShareToLearn.TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ShareToLearn.COURSES + " TEXT NOT NULL," +
                    ShareToLearn.COURSEREVIEWS + " TEXT NOT NULL," +
                    ShareToLearn.DISCUSSIONS + " TEXT NOT NULL," +
                    ShareToLearn.DISCUSSIONRESPONSES + " TEXT NOT NULL," +
                    ShareToLearn.PYPS + " TEXT NOT NULL," +
                    ShareToLearn.PYPRESPONSES + " TEXT NOT NULL," +
                    ShareToLearn.USERS + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ShareToLearn.COURSES + ShareToLearn.COURSEREVIEWS +
                    ShareToLearn.USERS + ShareToLearn.DISCUSSIONS + ShareToLearn.PYPS +
            ShareToLearn.DISCUSSIONRESPONSES + ShareToLearn.PYPRESPONSES;


    public ShareToLearnDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
