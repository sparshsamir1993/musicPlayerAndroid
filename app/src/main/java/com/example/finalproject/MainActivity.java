package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHandler db;
    Button signIn, signUp;
    SharedPreferencesUtil shrdUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHandler(this, getPackageName());
        shrdUtil = new SharedPreferencesUtil(this);
        ContentValues vals = shrdUtil.getUserInSession();
        if(vals.get("userId") != null && vals.get("userId").toString().length() >0){
            Intent toLibrary = new Intent(MainActivity.this, Library.class);
            startActivity(toLibrary);
        }
        signIn = findViewById(R.id.signInButton);
        signUp = findViewById(R.id.signUpButton);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(MainActivity.this, SignIn.class);
                startActivity(signInIntent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(MainActivity.this, SignUp.class);
                startActivity(signUpIntent);
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        MenuItem item = menu.findItem(R.id.sessionManage);
        shrdUtil = new SharedPreferencesUtil(this);
        ContentValues vals = shrdUtil.getUserInSession();
        if(vals.get("userId") != null && vals.get("userId").toString().length() >0){
            item.setTitle("Logout");
            return true;
        }else{
            item.setTitle("Login-Register");
            return true;
        }

    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.library:
                Intent libraryIntent = new Intent(MainActivity.this,Library.class);
                startActivity(libraryIntent);
                return  true;

            case R.id.newPlayList:
                Intent viewIntent = new Intent(MainActivity.this,PlaylistList.class);
                startActivity(viewIntent);
                return true;

            case R.id.sessionManage:
                shrdUtil = new SharedPreferencesUtil(this);
                ContentValues vals = shrdUtil.getUserInSession();
                if(vals.get("userId") != null && vals.get("userId").toString().length() >0){

                    if(shrdUtil.saveDataInPrefs(null, null)){
                        Toast.makeText(this, "You've been logged out", Toast.LENGTH_SHORT).show();
                        Intent sessionIntent = new Intent(MainActivity.this,SignIn.class);
                        startActivity(sessionIntent);

                    }else{
                        Toast.makeText(this, "Error in loggin out", Toast.LENGTH_SHORT).show();
                    }

                }
                return true;



            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
