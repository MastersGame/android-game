package helloandroid.ut3.minigame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import helloandroid.ut3.minigame.services.GameService;
import helloandroid.ut3.minigame.services.VibratorService;
import helloandroid.ut3.minigame.views.GameView;

public class GameActivity extends AppCompatActivity {

    GameService gameService = GameService.getInstance();
    Bitmap flattenedVersion;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SharedPreferences sharedPref =
                this.getPreferences(Context.MODE_PRIVATE);

        gameService.setup();

        int valeur_y = sharedPref.getInt("valeur_y", 0);
        valeur_y = (valeur_y + 100) % 400;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("valeur_y", valeur_y);
        editor.apply();
        Bitmap map = gameService.getMap();
        VibratorService.instanciate(this);

        setContentView(new GameView(this));
    }
}
