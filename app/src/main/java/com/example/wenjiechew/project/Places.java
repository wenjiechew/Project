package com.example.wenjiechew.project;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by WenJieChew on 9/6/2016.
 */
public class Places {
    private int _id;
    private String _placeName;
    private String  _date;

    public Places(){}

    public Places(String placeName, String date){
        this._placeName = placeName;
        this._date = date;

    }

    public void set_id(int _id){
        this._id = _id;
    }

    public void set_placeName(String _placeName){
        this._placeName = _placeName;
    }

    public void set_date(String _date){ this._date = _date; }



    public int get_id(){ return _id; }

    public String get_placeName(){
        return _placeName;
    }

    public String get_date() { return _date; }


    public List<Places> getAllPlaces(DBAccess instance){
        List<Places> list = new ArrayList<>();
        Cursor c = instance.SelectAll(instance.TABLENAME_PLACE);
        while (c.moveToNext()){
            String placeName = c.getString(c.getColumnIndex(instance.COL_PLACENAME));
            String dateText = c.getString(c.getColumnIndex(instance.COL_LISTDATE));
            Places places = new Places(placeName,dateText);
            list.add(places);
        }

        return list;
    }
}
