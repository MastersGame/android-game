package helloandroid.ut3.minigame.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import helloandroid.ut3.minigame.R;
import helloandroid.ut3.minigame.services.GameService;
import helloandroid.ut3.minigame.services.MusicService;

public class VictoryActivity extends AppCompatActivity {

    private MusicService musicService;
    private GameService gameService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        MusicService.instanciate(this,R.raw.victory);
        musicService = MusicService.getInstance();

        gameService = GameService.getInstance();


        setContentView(R.layout.victory);


        TextView textView = findViewById(R.id.WinTextView);

        int score = gameService.getTimer();

        textView.setText("Vous avez gagné, votre score était de " + score);

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
