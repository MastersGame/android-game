package helloandroid.ut3.minigame.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

import helloandroid.ut3.minigame.R;
import helloandroid.ut3.minigame.services.ImageService;

public class ThresholdActivity extends AppCompatActivity {

    ImageService imageService;
    private ImageView imageView;
    private Slider slider;
    private Bitmap originalBitmap;
    private Bitmap thresholdedBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threshold);
        imageService = ImageService.getInstance();

        imageView = findViewById(R.id.imageView);
        slider = findViewById(R.id.slider);
        slider.setValue(128);

        originalBitmap = imageService.getPhoto();
        thresholdedBitmap = ImageService.flattenedVersion(originalBitmap, 128);

        imageView.setImageBitmap(thresholdedBitmap);

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float v, boolean b) {
                updateThresholdedImage((int) v);
            }
        });
    }

    public void validate(View view) {
        imageService.setThresholdedBitmap(thresholdedBitmap);
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void updateThresholdedImage(int threshold) {
        thresholdedBitmap = ImageService.flattenedVersion(originalBitmap, threshold);
        imageView.setImageBitmap(thresholdedBitmap);
    }
}

