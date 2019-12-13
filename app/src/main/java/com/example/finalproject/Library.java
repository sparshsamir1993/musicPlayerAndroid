package com.example.finalproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Library extends AppCompatActivity {

    ListView library;
    ArrayAdapter libraryDetails

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        library = findViewById(R.id.musicLibrary);

    }

}
