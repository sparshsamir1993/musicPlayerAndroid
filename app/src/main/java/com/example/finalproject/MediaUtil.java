package com.example.finalproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class MediaUtil {

    MediaPlayer mediaPlayer;
    public MediaUtil(){
        mediaPlayer = new MediaPlayer();
    }

    public MediaUtil(Context context, Uri songPath){
        mediaPlayer = MediaPlayer.create(context, songPath);
    }

    public boolean isMediaPlaying(){
        return mediaPlayer.isPlaying();
    }

    public boolean stopMediaPlayer(){
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();

        return true;
    }

    public boolean startPlaying(){
        mediaPlayer.start();
        return true;
    }
}
