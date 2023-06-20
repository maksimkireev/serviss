package com.example.serviss;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    final String CHECK_STEP_KEY = "steps_key";

    private double magLast = 0;
    static int stepsCheck = -1;
    private TextView steps;

    final String TAG = "myLog";
    public MyService() {
    }


    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        Log.d(TAG, "onStartCommand ");

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        stepsCheck = sPref.getInt(CHECK_STEP_KEY, 0);

        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                if (sensorEvent != null) {
                    double dx = sensorEvent.values[0];
                    double dy = sensorEvent.values[1];
                    double dz = sensorEvent.values[2];

                    double mag = Math.sqrt(dx * dx + dy * dy + dz * dz);

                    if (mag - magLast > 4) {
                        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                        SharedPreferences.Editor myEditor = sPref.edit();

                        myEditor.putInt(CHECK_STEP_KEY, stepsCheck);
                        myEditor.commit();
                        stepsCheck++;
                        Log.d(TAG, String.valueOf(stepsCheck));

                        myEditor.putInt(CHECK_STEP_KEY, stepsCheck);
                    }
                    magLast = mag;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(stepDetector,sensor,sensorManager.SENSOR_DELAY_NORMAL);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}