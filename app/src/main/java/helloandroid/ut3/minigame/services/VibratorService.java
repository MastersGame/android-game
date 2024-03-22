package helloandroid.ut3.minigame.services;

import android.content.Context;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class VibratorService {
    private static volatile VibratorService instance;
    private final Vibrator vibrator;

    private VibratorService(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public static VibratorService getInstance() {
        if (instance == null)
            throw new NullPointerException("This service was not instanciate");
        return instance;
    }

    public static void instanciate(Context context) {
        if (instance == null) {
            synchronized (VibratorService.class) {
                if (instance == null) {
                    instance = new VibratorService(context);
                }
            }
        }
    }

    public boolean hasVibrator() {
        return vibrator.hasVibrator();
    }

    public void vibrate() {
        vibrator.vibrate(VibrationEffect.createOneShot(
                1000,
                VibrationEffect.DEFAULT_AMPLITUDE
        ));
    }
}
