package helloandroid.ut3.minigame.services;

import android.graphics.Bitmap;

import helloandroid.ut3.minigame.data.Position;

public class GameService {
    private static final int DEFAULT_RADIUS = 10;
    private static final Position DEFAULT_STARTING_POSITION = new Position(5 + DEFAULT_RADIUS, 5 + DEFAULT_RADIUS);
    private static volatile GameService instance;
    private final int radius;
    private final ImageService imageService = ImageService.getInstance();
    private Bitmap map;
    private Position currentPosition;

    private GameService() {
        radius = DEFAULT_RADIUS;
        currentPosition = DEFAULT_STARTING_POSITION;
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

    public int getRadius() {
        return radius;
    }

    public Bitmap getMap() {
        return map;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void tick() {
        //Compute next position
        Position nextPosition = new Position((currentPosition.getX() + 10) % 300, currentPosition.getY());

        //Check collision

        //Set the new currentPosition
        currentPosition = nextPosition;
    }

    public void setup() {
        map = imageService.getThresholdedBitmap();
    }
}
