package com.example.mlmmusic;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ywl5320.libmusic.WlMusic;
import com.ywl5320.listener.OnPreparedListener;

import java.io.IOException;

//import com.example.mlmmusic.music.libmusic.WlMusic;
//import com.example.mlmmusic.music.listener.OnPreparedListener;


public class MusicService extends Service {

    private WlMusic wlMusic;
    private String url;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wlMusic = WlMusic.getInstance();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        url = intent.getStringExtra("url");
//        wlMusic = WlMusic.getInstance();
//        wlMusic.setSource(url);
//        wlMusic.setPlayCircle(true);
//        wlMusic.setVolume(100);
//        wlMusic.setOnPreparedListener(new OnPreparedListener() {
//
//            @Override
//            public void onPrepared() {
//                wlMusic.start();
//
//
//            }
//        });
//        wlMusic.prePared();



//        MediaPlayer mediaPlayer = new MediaPlayer();
//        try {
//            mediaPlayer.setDataSource(url);
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//            mediaPlayer.prepareAsync();
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mediaPlayer.start();
//                }
//            });
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return super.onStartCommand(intent, flags, startId);
    }
}
