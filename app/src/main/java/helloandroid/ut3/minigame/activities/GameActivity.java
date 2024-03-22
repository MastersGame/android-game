package helloandroid.ut3.minigame.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import helloandroid.ut3.minigame.R;
import helloandroid.ut3.minigame.services.GameService;
import helloandroid.ut3.minigame.services.GyroscopeService;
import helloandroid.ut3.minigame.services.MusicService;
import helloandroid.ut3.minigame.services.VibratorService;

public class GameActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    GameService gameService;
    private TextView timerTextView;
    private MusicService musicService;
    private long startTime;
    private final Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            // Calculer le temps écoulé
            long elapsedTime = SystemClock.elapsedRealtime() - startTime;
            int seconds = (int) (elapsedTime / 1000);

            // Mettre à jour le texte du TextView
            timerTextView.setText("Time: " + seconds + "s");

            gameService.setTimer(seconds);

            // Planifier la prochaine mise à jour dans 1 seconde
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (VibratorService.getInstance() == null) {
            VibratorService.instanciate(this);
        }

        if (GyroscopeService.getInstance() == null) {
            GyroscopeService.instanciate(this);
        }


        if (MusicService.getInstance() == null) {
            MusicService.instanciate();
        }
        musicService = MusicService.getInstance();
        musicService.changeSound(this, R.raw.maxwell);
        musicService.playonLoop();


        gameService = GameService.getInstance();
        gameService.setup();

        setContentView(R.layout.game);
        timerTextView = findViewById(R.id.timerTextView);
        timerTextView.setTextColor(Color.RED);
        startTime = SystemClock.elapsedRealtime();

        // Lancer la mise à jour du chronomètre
        handler.post(updateTimerRunnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Arrêter la mise à jour du chronomètre pour éviter les fuites de mémoire
        musicService.stop();
        handler.removeCallbacks(updateTimerRunnable);

    }
}
