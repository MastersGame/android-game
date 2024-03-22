package helloandroid.ut3.minigame.activities;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import helloandroid.ut3.minigame.services.GameService;
import helloandroid.ut3.minigame.services.GyroscopeService;
import helloandroid.ut3.minigame.services.VibratorService;
import helloandroid.ut3.minigame.views.GameView;

public class GameActivity extends AppCompatActivity {

    GameService gameService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        VibratorService.instanciate(this);
        GyroscopeService.instanciate(this);

        gameService = GameService.getInstance();

        gameService.setup();

        setContentView(new GameView(this));
    }
}
