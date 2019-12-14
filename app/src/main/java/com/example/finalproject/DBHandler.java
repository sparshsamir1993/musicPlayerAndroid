package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "MusicApp.db";
    public static final String TABLE_USERS = "tbl_Users";
    public static final String TABLE_SONGS = "tbl_Songs";
    public static final String MyPREFERENCES = "MusicAppPrefs" ;


    public static final String ID = "ID";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    public static final String SONG_NAME = "NAME";
    public static final String SONG_PATH = "PATH";
    String packageName = "";

//    SharedPreferences sharedpreferences;
    SharedPreferencesUtil shrdutil;
    DBHandler(Context context, String pName)
    {
        super(context,DB_NAME,null,1);
         shrdutil = new SharedPreferencesUtil(context);
         packageName = pName;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //  db= this.getWritableDatabase();
//        createBodyParts(db);
        createSongs(db);

        String CREATE_TABLE = ("Create table " + TABLE_USERS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "NAME TEXT," +
                "EMAIL TEXT,"+
                "PASSWORD TEXT)");

        db.execSQL(CREATE_TABLE);

    }

    private void createSongs(SQLiteDatabase db) {
        String CREATE_TABLE = ("Create table " + TABLE_SONGS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "NAME TEXT," +
                "PATH TEXT)");

        db.execSQL(CREATE_TABLE);
        songRecord(db);
    }
    public void songRecord(SQLiteDatabase mydb) {
        try{
            JSONObject song1 = new JSONObject();
            JSONObject song2 = new JSONObject();
            JSONArray arr = new JSONArray();
            song1.put("name", "test 1");
            Uri location1 = Uri.parse("android.resource://"+packageName+"/"+R.raw.magicbruno);
            song1.put("path", location1.toString());

            song2.put("name", "sunrise");
            Uri location2 = Uri.parse("android.resource://"+packageName+"/"+R.raw.sunrisecoldplay);
            song2.put("path", location2.toString());
            arr.put(song1);
            arr.put(song2);


            // mydb = this.getWritableDatabase();
            ContentValues myvalues = new ContentValues();

            for (int i = 0; i < arr.length(); i++) {
                myvalues.put(SONG_NAME, arr.getJSONObject(i).getString("name"));
                myvalues.put(SONG_PATH, arr.getJSONObject(i).getString("path"));
                mydb.insert(TABLE_SONGS, null, myvalues);
            }
        }catch(Exception e){

        }
    }
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_Songs");
        onCreate(sqLiteDatabase);
    }



    public boolean registerUser(String email, String password){
        SQLiteDatabase mydb = this.getWritableDatabase();
        ContentValues myvalues = new ContentValues();
        myvalues.put(EMAIL, email);
        myvalues.put(PASSWORD, password);
        long result = mydb.insert(TABLE_USERS,null,myvalues);
        return (result != -1) ;
    }

    public boolean loginUser(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {ID,EMAIL,PASSWORD};

//        Cursor cr = mydb.rawQuery("select * from "+TABLE_USERS+" where ")

        Cursor cursor = db.query(TABLE_USERS,columns,"EMAIL = ?",new String[]{email},null,null,null);
        String passwordInDb="" ;
        String emailInDb="" ;
        String userId ="";
        while(cursor.moveToNext()){
            userId = cursor.getString(0);
            emailInDb = cursor.getString(1);
            passwordInDb = cursor.getString(2);
        }
        if(passwordInDb.equals(password)){

            return shrdutil.saveDataInPrefs(emailInDb, userId);
        }
        return false;
    }


    public ArrayList getSongList() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList list = new ArrayList();
        Cursor cursor = db.rawQuery("select  * FROM " + TABLE_SONGS, null);
        while(cursor.moveToNext()){
            String joined = cursor.getString(1) + "$$$$" + cursor.getString(2);
            list.add(joined);
        }
        return list;
    }
}
