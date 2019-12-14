package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PlaylistList extends AppCompatActivity {


    ListView playlistList;
    Button addPlaylist;
    ArrayAdapter plAdapter;
    ArrayList plArrList;
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlistlist);

        db = new DBHandler(getApplicationContext(), getPackageName());
        plArrList = db.getPlayListList();
        addPlaylist = findViewById(R.id.addPlaylist);
        playlistList = findViewById(R.id.playlistList);
        plAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item, plArrList);
        playlistList.setAdapter(plAdapter);

        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toNewPList = new Intent(PlaylistList.this, NewPlayList.class);
                startActivity(toNewPList);
            }
        });
    }
}
