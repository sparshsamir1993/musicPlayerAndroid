package com.example.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "MusicApp.db";
    public static final String TABLE_USERS = "tbl_Users";
    public static final String MyPREFERENCES = "MusicAppPrefs" ;


    public static final String ID = "ID";
    public static final String EMAIL = "EMAIL";
    public static final String PASSWORD = "PASSWORD";
    SharedPreferences sharedpreferences;
    DBHandler(Context context)
    {
        super(context,DB_NAME,null,1);
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        //  db= this.getWritableDatabase();
//        createBodyParts(db);
//        createEmployee(db);

        String CREATE_TABLE = ("Create table " + TABLE_USERS + "(ID INTEGER PRIMARY KEY AUTOINCREMENT ,DATE TEXT," +
                "NAME TEXT," +
                "EMAIL TEXT,"+
                "PASSWORD TEXT)");

        db.execSQL(CREATE_TABLE);

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
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("currentUserId", userId );
            editor.putString("currentUserEmail", emailInDb );
            editor.commit();
            return true;
        }
        return false;
    }


}
