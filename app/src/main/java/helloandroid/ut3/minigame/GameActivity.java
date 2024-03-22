package helloandroid.ut3.minigame;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import helloandroid.ut3.minigame.services.GyroscopeService;
import helloandroid.ut3.minigame.services.ImageService;
import helloandroid.ut3.minigame.services.VibratorService;
import helloandroid.ut3.minigame.views.GameView;

public class GameActivity extends AppCompatActivity {

    ImageService imageService;
    Bitmap flattenedVersion;
    Bitmap photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageService = ImageService.getInstance();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        photo = imageService.getPhoto();
        flattenedVersion = ImageService.flattenedVersion(photo, 128);
        SharedPreferences sharedPref =
                this.getPreferences(Context.MODE_PRIVATE);
        int valeur_y = sharedPref.getInt("valeur_y", 0);
        valeur_y = (valeur_y + 100) % 400;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("valeur_y", valeur_y);
        editor.apply();

        VibratorService.instanciate(this);

        GyroscopeService.instanciate(this);

        setContentView(new GameView(this, valeur_y, photo));
    }
}
