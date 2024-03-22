package helloandroid.ut3.minigame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button launch = findViewById(R.id.launch);
        launch.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, PhotoActivity.class);
            context.startActivity(intent);
        });
    }
}