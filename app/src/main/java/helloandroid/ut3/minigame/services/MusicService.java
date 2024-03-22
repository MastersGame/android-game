package helloandroid.ut3.minigame.services;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.net.URI;

public class MusicService {

    private static volatile MusicService instance;
    private MediaPlayer mediaPlayer;

    public MusicService(Context context, int i){
        mediaPlayer = MediaPlayer.create(context, i);

    }

    public static MusicService getInstance() {
        if (instance == null)
            throw new NullPointerException("This service was not instanciate");
        return instance;
    }


    public static void instanciate(Context context, int i ) {
        if (instance == null) {
            synchronized (MusicService.class) {
                if (instance == null) {
                    instance = new MusicService(context,i);
                }
            }
        }
    }




    public void changeSound(Context context, int i){
        mediaPlayer = MediaPlayer.create(context,i);
    }

    public void play(){
        mediaPlayer.start();
    }

    public void playonLoop(){
        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.start(); // Start playing
    }

    public void pause(){
        mediaPlayer.pause();
    }
    public void stop(){
        mediaPlayer.stop();
    }



}
