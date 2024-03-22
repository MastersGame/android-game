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
import helloandroid.ut3.minigame.services.VibratorService;
import helloandroid.ut3.minigame.views.GameView;

public class GameActivity extends AppCompatActivity {

    GameService gameService;
    private final Handler handler = new Handler();
    private TextView timerTextView;
    private long startTime;
    private final Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            // Calculer le temps écoulé
            long elapsedTime = SystemClock.elapsedRealtime() - startTime;
            int seconds = (int) (elapsedTime / 1000);

            // Mettre à jour le texte du TextView
            timerTextView.setText("Time: " + seconds + "s");

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
        setContentView(R.layout.game);
        timerTextView = findViewById(R.id.timerTextView);
        timerTextView.setTextColor(Color.RED);
        startTime = SystemClock.elapsedRealtime();
        // Lancer la mise à jour du chronomètre
        handler.post(updateTimerRunnable);

        VibratorService.instanciate(this);
        GyroscopeService.instanciate(this);

        gameService = GameService.getInstance();

        gameService.setup();
    }
}
