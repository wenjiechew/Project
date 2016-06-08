package com.example.wenjiechew.project.Database;

/**
 * Created by WenJieChew on 8/6/2016.
 */

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DBCreator extends SQLiteOpenHelper{

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Hotspot.db";
    public static final String TABLENAME_PLACE = "place";
    public static final String COL_ID = "_id";
    public static final String COL_PLACENAME = "placeName";

    //Constructor
    public DBCreator(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLENAME_PLACE + "(" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PLACENAME + " TEXT" +
                ");";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLENAME_PLACE);
        onCreate(db);
    }

    @Override
    public synchronized void close() { super.close(); }
}