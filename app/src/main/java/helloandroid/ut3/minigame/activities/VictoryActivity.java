package helloandroid.ut3.minigame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Date;

import helloandroid.ut3.minigame.R;
import helloandroid.ut3.minigame.data.Score;
import helloandroid.ut3.minigame.services.GameService;
import helloandroid.ut3.minigame.services.MusicService;
import helloandroid.ut3.minigame.services.ScoreService;

public class VictoryActivity extends AppCompatActivity {

    private MusicService musicService;
    private GameService gameService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        musicService = MusicService.getInstance();
        musicService.changeSound(this, R.raw.victory);
        musicService.play();

        gameService = GameService.getInstance();

        setContentView(R.layout.victory);


        TextView textView = findViewById(R.id.WinTextView);

        Score newScore = new Score(new Date(), gameService.getTimer(), gameService.getNbTentatives());
        if (ScoreService.getInstance() == null) {
            ScoreService.instanciate(this);
        }
        ScoreService.getInstance().addScore(newScore);
        textView.setText("Vous avez gagné, votre score était de " + newScore.getScore() + " en " + newScore.getNbTentatives() + " tentatives.");

        Button button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click here
                // You can navigate back to MainActivity using an Intent
                musicService.stop();
                Intent intent = new Intent(VictoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        Glide.with(this).load(R.drawable.maxwell).into(imageView);
        musicService.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicService.stop();
    }
}
