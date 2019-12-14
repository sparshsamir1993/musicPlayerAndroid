package com.example.finalproject;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Library extends AppCompatActivity {

    ListView library;
    ArrayAdapter libraryDetails;
    ArrayList songList;
    DBHandler db;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        db = new DBHandler(getApplicationContext(), getPackageName());
        library = findViewById(R.id.musicLibrary);
        mediaPlayer = new MediaPlayer();
        songList = db.getSongList();
        libraryDetails = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,songList);
        library.setAdapter(libraryDetails);
        library.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{

                    String song = libraryDetails.getItem(position).toString();
                    String[] s = song.split("\\${4}");
                    Uri songPath = Uri.parse(s[1]);
                    Log.i("error",  s[1]);

                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();

                    }

                    mediaPlayer = MediaPlayer.create(getApplicationContext(), songPath);


                    mediaPlayer.start();


                }catch (Exception e){
                    Log.i("error",  e.getMessage());
                }

            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.library:
                Intent libraryIntent = new Intent(Library.this,Library.class);
                startActivity(libraryIntent);
                return  true;

            case R.id.newPlayList:
                Intent viewIntent = new Intent(Library.this,PlaylistList.class);
                startActivity(viewIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
