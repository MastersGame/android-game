package helloandroid.ut3.minigame.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import helloandroid.ut3.minigame.R;
import helloandroid.ut3.minigame.threads.GameThread;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private final GameThread thread;
    private final int y;
    private int x = 0;

    public GameView(Context context, int aY) {
        super(context);
        getHolder().addCallback(this);
        thread = new GameThread(getHolder(), this);
        setFocusable(true);
        y = aY;
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
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.md_theme_error, null));
            canvas.drawRect(x, y, x + 100, y + 200, paint);
        }
    }

    public void update() {
        x = (x + 1) % 300;
    }


}
