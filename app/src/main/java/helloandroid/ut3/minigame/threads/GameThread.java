package helloandroid.ut3.minigame.threads;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import helloandroid.ut3.minigame.views.GameView;

public class GameThread extends Thread {
    private static final int TIMEOUT_RUN = 160;
    private final SurfaceHolder surfaceHolder;
    private final GameView gameView;
    private boolean running = false;

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        while (running) {
            try {
                sleep(TIMEOUT_RUN);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Canvas canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            } catch (Exception e) {
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}
