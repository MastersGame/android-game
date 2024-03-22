package helloandroid.ut3.minigame;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

import helloandroid.ut3.minigame.models.Score;
import helloandroid.ut3.minigame.services.ScoreService;

public class ScoreActivity extends AppCompatActivity {

    private ScoreService scoreService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ScoreService.instanciate(this);
        scoreService = ScoreService.getInstance();
        scoreService.addScore(new Score(new Date(), "1m25s"));
        Log.i("Score Activity", "onStart: " + scoreService.getScores().toString());
    }



}