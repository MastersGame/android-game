package helloandroid.ut3.minigame.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import helloandroid.ut3.minigame.data.Position;
import helloandroid.ut3.minigame.services.GameService;
import helloandroid.ut3.minigame.threads.GameThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final GameThread thread;
    private final GameService gameService = GameService.getInstance();
    private final int POINT_COLOR = Color.rgb(250, 0, 0);


    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);
    }

    public GameView(Context context, AttributeSet set) {
        super(context, set);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);
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

            final Bitmap map = gameService.getMap();
            // Calculer les facteurs d'échelle pour l'ajustement
            float scaleX = (float) canvasWidth / map.getWidth();
            float scaleY = (float) canvasHeight / map.getHeight();
            // Dessiner le bitmap en étirant pour remplir toute la surface
            if (map != null) {
                // Créer une matrice de transformation
                android.graphics.Matrix matrix = new android.graphics.Matrix();
                matrix.setScale(scaleX, scaleY);

                // Dessiner le bitmap avec la matrice de transformation
                canvas.drawBitmap(map, matrix, null);
            }

            final Position currentPosition = gameService.getCurrentPosition();

            // Dessiner le rectangle rouge
            Paint paint = new Paint();
            paint.setColor(POINT_COLOR);
            canvas.drawCircle(currentPosition.getX() * scaleX, currentPosition.getY() * scaleY, gameService.getRadius(), paint);
        }
    }
}
