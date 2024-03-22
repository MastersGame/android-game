package helloandroid.ut3.minigame.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import helloandroid.ut3.minigame.R;
import helloandroid.ut3.minigame.services.ImageService;

public class PhotoActivity extends AppCompatActivity {

    ImageService imageService;
    ImageView photo;
    private ActivityResultLauncher<Intent> takePictureLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo);
        imageService = ImageService.getInstance();
        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        imageService.setPhoto(imageBitmap);
                        Intent intent = new Intent(this, ThresholdActivity.class);
                        this.startActivity(intent);
                    }
                });
        findViewById(R.id.take_photo).setOnClickListener(v -> dispatchTakePictureIntent());
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureLauncher.launch(takePictureIntent);
        }
    }
}
