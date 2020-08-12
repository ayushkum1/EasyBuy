package com.example.vineet.easybuy;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager extends Application {
    public String username = "";
    public static String userid = "";
    private Context context;
    public SessionManager(Context context){
        this.context  = context;
    }
    public void setPreferences( String key, String value) {

        if (context!=null){
            SharedPreferences.Editor editor = context.getSharedPreferences("DataPreference", Context.MODE_PRIVATE).edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public String getPreferences( String key) {

        if (context!=null){
            SharedPreferences prefs = context.getSharedPreferences("DataPreference",	Context.MODE_PRIVATE);
            username = prefs.getString(key, "");

        }
        return username;
    }

}
