package com.example.finalproject;

import android.content.ContentValues;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Library extends AppCompatActivity {

    ListView library;
    ArrayAdapter libraryDetails;
    ArrayList songList, songNameList;
    DBHandler db;
    MediaPlayer mediaPlayer;
    SharedPreferencesUtil shrdUtil;
    MediaUtil mUtil ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        db = new DBHandler(getApplicationContext(), getPackageName());
        mUtil = new MediaUtil();
        library = findViewById(R.id.musicLibrary);
        mediaPlayer = new MediaPlayer();
        songList = db.getSongList();
        songNameList = new ArrayList();
        for(int i=0;i<songList.size();i++){
            String songName = songList.get(i).toString().split("\\${4}")[0];
            songNameList.add(songName);
        }
        libraryDetails = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,songNameList);
        library.setAdapter(libraryDetails);
        library.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    String song = songList.get(position).toString();
                    String[] s = song.split("\\${4}");
                    Uri songPath = Uri.parse(s[1]);
                    Log.i("error",  s[1]);
                    if(mUtil != null && mUtil.isMediaPlaying()){
                        mUtil.stopMediaPlayer();
                    }
                    mUtil = new MediaUtil(getApplicationContext(), songPath);
                    mUtil.startPlaying();

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
                Intent libraryIntent = new Intent(Library.this,Library.class);
                startActivity(libraryIntent);
                return  true;

            case R.id.newPlayList:
                Intent viewIntent = new Intent(Library.this,PlaylistList.class);
                startActivity(viewIntent);
                return true;

            case R.id.sessionManage:
                shrdUtil = new SharedPreferencesUtil(this);
                ContentValues vals = shrdUtil.getUserInSession();
                if(vals.get("userId") != null && vals.get("userId").toString().length() >0){

                    if(shrdUtil.saveDataInPrefs(null, null)){
                        Toast.makeText(this, "You've been logged out", Toast.LENGTH_SHORT).show();
                        Intent sessionIntent = new Intent(Library.this,SignIn.class);
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
