package helloandroid.ut3.minigame.services;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import java.net.URI;

public class MusicService {

    private static volatile MusicService instance;
    private MediaPlayer mediaPlayer;

    private boolean running = false;
    private float replaySpeed = 1.0f;
    private float step = 0.1f;


    public MusicService(Context context, int i){
        mediaPlayer = MediaPlayer.create(context, i);
        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(replaySpeed));
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

        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(replaySpeed));
        mediaPlayer.start();
        running = true;
    }

    public void playonLoop(){

        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(replaySpeed));
        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.start(); // Start playing
        running = true;
    }

    public void pause(){
        mediaPlayer.pause();
    }
    public void stop(){
        mediaPlayer.stop();
        running = false;
    }


    public void speedUp(){
        if(replaySpeed < 2){
            replaySpeed += step;
        }
    }
    public void slowDown(){
        replaySpeed -= step;

    }

}
