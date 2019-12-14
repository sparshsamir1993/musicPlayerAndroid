package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewPlayList extends AppCompatActivity {

    Button addPListButton;
    EditText plName;
    DBHandler db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newplaylist);

        db = new DBHandler(getApplicationContext(), getPackageName());

        plName = findViewById(R.id.plName);
        addPListButton = findViewById(R.id.addPlaylist);
        addPListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent toNewPList = new Intent(NewPlayList.this, NewPlayList.class);
                String name = plName.getText().toString();
                boolean check =  db.savePlaylist(name);
                if(check){
                    Toast.makeText(getApplicationContext(), "Playlist Created.", Toast.LENGTH_SHORT).show();
                    Intent toList = new Intent(NewPlayList.this, PlaylistList.class);
                    startActivity(toList);
                }else{
                    Toast.makeText(getApplicationContext(), "Error in Playlist Creation.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
