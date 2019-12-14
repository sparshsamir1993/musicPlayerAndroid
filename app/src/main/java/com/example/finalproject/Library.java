package com.example.finalproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Library extends AppCompatActivity {

    ListView library;
    ArrayAdapter libraryDetails;
    ArrayList songList;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        db = new DBHandler(getApplicationContext(), getPackageName());
        library = findViewById(R.id.musicLibrary);

        songList = db.getSongList();
        libraryDetails = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,songList);
        library.setAdapter(libraryDetails);

    }

}
