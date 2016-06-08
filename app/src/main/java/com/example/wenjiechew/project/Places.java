package com.example.wenjiechew.project;

/**
 * Created by WenJieChew on 9/6/2016.
 */
public class Places {
    private int _id;
    private String _placeName;

    public Places(){}

    public Places(String placeName){
        this._placeName = placeName;
    }

    public void set_id(int _id){
        this._id = _id;
    }

    public void set_placeName(String _placeName){
        this._placeName = _placeName;
    }

    public int get_id(){
        return _id;
    }

    public String get_placeName(){
        return _placeName;
    }
}
