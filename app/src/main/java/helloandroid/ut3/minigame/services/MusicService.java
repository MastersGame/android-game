package helloandroid.ut3.minigame.services;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicService {

    private static final float DEFAULT_REPLAY_SPEED = 1.0f;
    private static final float STEP = 0.1f;
    private static volatile MusicService instance;
    private MediaPlayer mediaPlayer;
    private float replaySpeed = DEFAULT_REPLAY_SPEED;


    private MusicService() {

    }

    public static MusicService getInstance() {
        return instance;
    }


    public static void instanciate() {
        if (instance == null) {
            synchronized (MusicService.class) {
                if (instance == null) {
                    instance = new MusicService();
                }
            }
        }
    }


    public void changeSound(Context context, int i) {
        if (mediaPlayer != null) {
            stop();
            replaySpeed = DEFAULT_REPLAY_SPEED;
        }
        mediaPlayer = MediaPlayer.create(context, i);
        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(replaySpeed));
    }

    public void play() {
        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(replaySpeed));
        mediaPlayer.start();
    }

    public void playonLoop() {
        mediaPlayer.setPlaybackParams(mediaPlayer.getPlaybackParams().setSpeed(replaySpeed));
        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.start(); // Start playing
    }


    public void stop() {
        mediaPlayer.stop();
    }


    public void speedUp() {
        if (replaySpeed < 2) {
            replaySpeed += STEP;
        }
    }

}
