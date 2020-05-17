package com.meterstoinches.moviedirectory.Util;

import android.app.Activity;
import android.content.SharedPreferences;

//a class that will save the last search item when you open the app again
public class Prefs {

    SharedPreferences sharedPreferences;

    public Prefs(Activity activity) {
        sharedPreferences = activity.getPreferences(activity.MODE_PRIVATE);
    }

    //whenever user search anything and press submit
    //we will save that search in our shared preferences
    public void setSearch(String search){
        sharedPreferences.edit().putString("search", search).commit();
    }

    public String getSearch(){
        return sharedPreferences.getString("search", "Batman");
    }
}
