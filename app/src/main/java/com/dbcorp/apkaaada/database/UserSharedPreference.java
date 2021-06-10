package com.dbcorp.apkaaada.database;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pc on 3/8/2018.
 */

public class UserSharedPreference {

    private final static String PREFS_NAME = "Inform";
    Context context;
    SharedPreferences pre;
    SharedPreferences.Editor editor;
    public static String CITY_ID="cityId";
    public static String CITY_NAME="cityName";


    public static String CurrentLatitude="latitude";
    public static String CurrentLongitude="longitude";
    public static String CurrentAddress="address";
    private int PRIVATE_MODE = 0;
    public UserSharedPreference(Context context) {
        this.context = context;
        pre = context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = pre.edit();
    }



    public void setCity(String cityId,String cityName){
        editor.putString(CITY_ID,cityId);
        editor.putString(CITY_NAME,cityName);
        editor.commit();
    }


    public void setAddress(String currentAddress,String currentLongitude,String currentLatitude){

        Log.e("datata",currentAddress);
        editor.putString(CurrentAddress,currentAddress);
        editor.putString(CurrentLongitude,currentLongitude);
        editor.putString(CurrentLatitude,currentLatitude);
        editor.commit();
        editor.apply();
    }

    public HashMap<String,String> getAddress(){
        HashMap<String,String> userdata=new HashMap<>();


        userdata.put(CurrentAddress,pre.getString(CurrentAddress,null));
        userdata.put(CurrentLongitude,pre.getString(CurrentLongitude,null));
        userdata.put(CurrentLatitude,pre.getString(CurrentLatitude,null));

        return  userdata;
    }


}

