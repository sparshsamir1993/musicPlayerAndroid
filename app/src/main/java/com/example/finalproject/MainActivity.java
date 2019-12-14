package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

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
}
