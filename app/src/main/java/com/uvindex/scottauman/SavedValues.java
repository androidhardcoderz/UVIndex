package com.uvindex.scottauman;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Scott on 5/8/2016.
 */
public class SavedValues {

    public final static String UVINDEX = "index";
    public final static String LAST_UPDATE = "updated";

    public static void storeUVIndex(Context context,String index){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(UVINDEX,index).apply();
    }

    public static String getUVIndex(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(UVINDEX,"0");
    }


    public static void storeUPDATE(Context context,String date){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(LAST_UPDATE,date)
                .apply();
    }

    public static String getUPDATE(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(LAST_UPDATE,"");
    }



}
