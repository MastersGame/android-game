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
    private float x;
    private float y;
    private float z;

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
        float xt = 0;
        float yt = 0;
        float zt = 0;
        synchronized (this) {
            if (sensor == Sensor.TYPE_ACCELEROMETER) {
                xt = values[0];
                yt = values[1];
                zt = values[2];
            }
        }
        this.x = xt;
        this.y = yt;
        this.z = zt;

        Log.d("PERSO", x + " " + y + " " + z);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public float[] getData() {
        return new float[]{x, y, z};
    }
}
