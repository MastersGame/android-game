package helloandroid.ut3.minigame.threads;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import helloandroid.ut3.minigame.services.GameService;
import helloandroid.ut3.minigame.views.GameView;

public class GameThread extends Thread {
    private static final int TIMEOUT_RUN = 160;
    private final SurfaceHolder surfaceHolder;
    private final GameView gameView;
    private boolean running = false;

    private final GameService gameService = GameService.getInstance();

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
                    this.gameView.draw(canvas);
                    gameService.tick();
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
