package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

public class SharedPreferencesUtil {
    SharedPreferences sharedpreferences;
    private static String PREF_NAME = "prefs";

    SharedPreferencesUtil(Context context){
        sharedpreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean saveDataInPrefs(String email, String userId){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("currentUserId", userId );
        editor.putString("currentUserEmail", email );
        editor.commit();
        return true;
    }

    public ContentValues getUserInSession(){
        ContentValues obj = new ContentValues();
//        SharedPreferences.Editor editor = sharedpreferences.edit();
        String userId = sharedpreferences.getString("currentUserId", null);
        String userEmail = sharedpreferences.getString("currentUserEmail", null);
        obj.put("userId", userId);
        obj.put("userEmail", userEmail);
        return obj;
    }
}
