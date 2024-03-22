package helloandroid.ut3.minigame.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import helloandroid.ut3.minigame.threads.GameThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final GameThread thread;
    private final int y;
    private final Bitmap photo;
    private int x = 0;

    public GameView(Context context, int aY, Bitmap photo) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);
        y = aY;
        this.photo = photo;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas != null) {
            // Obtenir les dimensions de la surface
            int canvasWidth = canvas.getWidth();
            int canvasHeight = canvas.getHeight();

            // Dessiner le bitmap en étirant pour remplir toute la surface
            if (photo != null) {
                // Calculer les facteurs d'échelle pour l'ajustement
                float scaleX = (float) canvasWidth / photo.getWidth();
                float scaleY = (float) canvasHeight / photo.getHeight();

                // Créer une matrice de transformation
                android.graphics.Matrix matrix = new android.graphics.Matrix();
                matrix.setScale(scaleX, scaleY);

                // Dessiner le bitmap avec la matrice de transformation
                canvas.drawBitmap(photo, matrix, null);
            }

            // Dessiner le rectangle rouge
            Paint paint = new Paint();
            paint.setColor(Color.rgb(250, 0, 0));
            canvas.drawRect(x, y, x + 100, y + 200, paint);
        }
    }

    public void update() {
        x = (x + 1) % 300;
    }


}
