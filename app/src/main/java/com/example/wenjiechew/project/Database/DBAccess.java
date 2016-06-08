package com.example.wenjiechew.project.Database;

/**
 * Created by WenJieChew on 9/6/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAccess {
    private String TAG = "DBAccess";

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DBAccess instance;

    public static final String TABLENAME_PLACE = "place";

    private static final String SELECTALL_CMD = "SELECT * FROM ";
    private static final String DELECTFROM_CMD = "DELETE FROM ";

    public static final String COL_ID = "_id";
    public static final String COL_PLACENAME = "placeName";

    public static final String[] PLACE_LIST_COL_TO_DISPLAY = {COL_PLACENAME};





    /**
     * private Constructor to avoid object creation outside of class
     * @param context
     */
    private DBAccess(Context context) { this.openHelper = new DBHandler(context); }

    /**
     *
     * @param context
     * @return instance of the DBAccess
     */
    public static DBAccess getInstance(Context context) {
        if(instance == null) { instance = new DBAccess(context);}
        return instance;
    }

    public void openWrite(){ this.database = openHelper.getWritableDatabase(); }
    public void openRead(){ this.database = openHelper.getReadableDatabase(); }

    public void close() {
        if(database != null) { this.database.close(); }
    }


    public Cursor SelectAll(String tableName) {
        return database.rawQuery(SELECTALL_CMD + tableName, null);
    }

    public void Insert(String tableName, Places hotspot){
        Log.d(TAG, "In insert function");

        if(tableName.equals(TABLENAME_PLACE)) {
            Log.d(TAG, "insert " + hotspot.get_placeName() + " into " + TABLENAME_PLACE);

            ContentValues values = new ContentValues();
            values.put(COL_PLACENAME, hotspot.get_placeName());

            database.insert(TABLENAME_PLACE, null, values);
        }
    }

    public void Delete(String tableName, String placeName){
        Log.d(TAG, "In delete Function");

        if(tableName.equals(TABLENAME_PLACE)){
            Log.d(TAG, "delete " + placeName + " FROM " + TABLENAME_PLACE);
            database.execSQL(DELECTFROM_CMD + TABLENAME_PLACE + " WHERE " + COL_PLACENAME + " ");
        }


    }






}