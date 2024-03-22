package helloandroid.ut3.minigame.gyro;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class Gyroscope implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor gyroscope;
    private float x;
    private float y;
    private float z;

    public Gyroscope(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this,gyroscope,SensorManager.SENSOR_DELAY_GAME);
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
        float [] values = event.values;
        float xt=0;
        float yt=0;
        float zt=0;
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
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    public float[] getData() {
        return new float[]{x, y, z};
    }
}
