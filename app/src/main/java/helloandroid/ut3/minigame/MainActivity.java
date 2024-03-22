package helloandroid.ut3.minigame;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        SharedPreferences sharedPref =
                this.getPreferences(Context.MODE_PRIVATE);
        int valeur_y = sharedPref.getInt("valeur_y", 0);
        valeur_y = (valeur_y + 100) % 400;
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("valeur_y", valeur_y);
        editor.apply();*/

        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        //Intent intent = new Intent(this, GameActivity.class);
        //startActivity(intent);
    }

    public void showScores(View view) {
        //Intent intent = new Intent(this, ScoresActivity.class);
        //startActivity(intent);
    }
}