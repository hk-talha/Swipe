package com.example.jnetbackup.swipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Haseeb on 7/15/2016.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TEMPERATURE + TEXT_TYPE + COMMA_SEP  +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_SOMKE + TEXT_TYPE +COMMA_SEP  +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_HUMIDITY + TEXT_TYPE +COMMA_SEP+
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TIME + TEXT_TYPE +COMMA_SEP+
                    FeedReaderContract.FeedEntry.COLUMN_NAME_INSERT_TIME + TEXT_TYPE + COMMA_SEP +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_Branch + TEXT_TYPE +
     // Any other options for the CREATE command
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 10;
    public static final String DATABASE_NAME = "FeedReader.db";
    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    void  insert(int id,String title,String content)
    {

    }
}
