package helloandroid.ut3.minigame.services;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class GyroscopeService implements SensorEventListener {
    private static volatile GyroscopeService instance;
    private final SensorManager sensorManager;
    private final Sensor gyroscope;
    private Direction direction;
    private static final float UPPER_THRESHOLD = 1;

    private static final float LOWER_THRESHOLD = -1;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        CENTER
    }

    public GyroscopeService(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_GAME);
    }

    public static GyroscopeService getInstance() {
        if (instance == null)
            throw new NullPointerException("This service was not instanciate");
        return instance;
    }

    public static void instanciate(Context context) {
        if (instance == null) {
            synchronized (GyroscopeService.class) {
                if (instance == null) {
                    instance = new GyroscopeService(context);
                }
            }
        }
    }

    public void register() {
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregister() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensor = event.sensor.getType();
        float[] values = event.values;

        synchronized (this) {
            if (sensor == Sensor.TYPE_ACCELEROMETER) {
                this.direction = computeDirection(event.values[0],event.values[1]);
                Log.d("aaaaa"," "+this.direction);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    public  Direction computeDirection(float x,float y) {
        float x_abs = Math.abs(x);
        float y_abs = Math.abs(y);



        if((x_abs<UPPER_THRESHOLD && x_abs>LOWER_THRESHOLD) && (y_abs<UPPER_THRESHOLD && y_abs>LOWER_THRESHOLD)){
            return Direction.CENTER;
        }


        if( y_abs > x_abs){
            if (y < 0){
                return Direction.UP;
            }
            else {
                return Direction.DOWN;
            }
        }
        else {
            if(x < 0){
                return Direction.RIGHT;
            }
            else{
                return Direction.LEFT;
            }
        }
    }


    public Direction getDirection(){
        return this.direction;
    }
}
