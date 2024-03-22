package helloandroid.ut3.minigame.services;

import android.graphics.Bitmap;

import java.util.Random;

import helloandroid.ut3.minigame.data.Position;

public class GameService {
    private static final int DEFAULT_RADIUS = 10;
    private static final int DEFAULT_STEP = 1;

    private static final int DEFAULT_BORDER_SIZE = 10;
    private static volatile GameService instance;
    private final int radius;
    private final int victoryRadius;
    private final ImageService imageService = ImageService.getInstance();
    private final GyroscopeService gyroscopeService = GyroscopeService.getInstance();
    private final VibratorService vibratorService = VibratorService.getInstance();
    private int nbTentatives;
    private int timer = 0;
    private Bitmap map;
    private Position currentPosition;
    private Position victoryPosition;
    private Position startingPosition;

    private GameService() {
        radius = DEFAULT_RADIUS;
        victoryRadius = DEFAULT_RADIUS;
    }

    public static GameService getInstance() {
        if (instance == null) {
            synchronized (ImageService.class) {
                if (instance == null) {
                    instance = new GameService();
                }
            }
        }
        return instance;
    }

    public int getNbTentatives() {
        return nbTentatives;
    }

    public void setNbTentatives(int nbTentatives) {
        this.nbTentatives = nbTentatives;
    }

    public int getRadius() {
        return radius;
    }

    public int getVictoryRadius() {
        return victoryRadius;
    }

    public Bitmap getMap() {
        return map;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Position getVictoryPosition() {
        return victoryPosition;
    }

    public void tick() {
        Integer nextX = null;
        Integer nextY = null;
        switch (gyroscopeService.getDirection()) {
            case UP:
                nextX = currentPosition.getX();
                nextY = ((currentPosition.getY() - DEFAULT_STEP) + map.getHeight()) % map.getHeight();
                break;
            case DOWN:
                nextX = currentPosition.getX();
                nextY = ((currentPosition.getY() + DEFAULT_STEP) + map.getHeight()) % map.getHeight();
                break;
            case LEFT:
                nextX = ((currentPosition.getX() - DEFAULT_STEP) + map.getWidth()) % map.getWidth();
                nextY = currentPosition.getY();
                break;
            case RIGHT:
                nextX = ((currentPosition.getX() + DEFAULT_STEP) + map.getWidth()) % map.getWidth();
                nextY = currentPosition.getY();
                break;
            case CENTER:
                nextX = currentPosition.getX();
                nextY = currentPosition.getY();
                break;
        }
        //Compute next position
        Position nextPosition = new Position(nextX, nextY);

        //Check collision
        int nextPositionPixel = map.getPixel(nextPosition.getX(), nextPosition.getY());
        if (nextPositionPixel == ImageService.BLACK_PIXEL_COLOR) {
            MusicService.getInstance().speedUp();
            MusicService.getInstance().playonLoop();

            nbTentatives++;
            if (vibratorService.hasVibrator()) {
                vibratorService.vibrate();
            }
            nextPosition = startingPosition;
        }

        //Set the new currentPosition
        currentPosition = nextPosition;
    }

    public boolean isVictory() {
        return victoryPosition.getY() == currentPosition.getY() && victoryPosition.getX() == currentPosition.getX();
    }

    public void setup() {
        map = imageService.getThresholdedBitmap();
        Random r = new Random();
        nbTentatives = 1;

        //Iterate till start is not on a with pixel
        do {
            startingPosition = new Position(r.nextInt(map.getWidth() - 2 * DEFAULT_BORDER_SIZE) + DEFAULT_BORDER_SIZE, r.nextInt(map.getHeight() - 2 * DEFAULT_BORDER_SIZE) + DEFAULT_BORDER_SIZE);
        }
        while (map.getPixel(startingPosition.getX(), startingPosition.getY()) == ImageService.BLACK_PIXEL_COLOR);

        //Iterate till victory is not on a with pixel
        do {
            victoryPosition = new Position(r.nextInt(map.getWidth() - 2 * DEFAULT_BORDER_SIZE) + DEFAULT_BORDER_SIZE, r.nextInt(map.getHeight() - 2 * DEFAULT_BORDER_SIZE) + DEFAULT_BORDER_SIZE);
        }
        while (map.getPixel(victoryPosition.getX(), victoryPosition.getY()) == ImageService.BLACK_PIXEL_COLOR);

        //Set the starting position
        currentPosition = startingPosition;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }


}
