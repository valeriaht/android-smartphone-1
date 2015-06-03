package com.example.valeria.sensorsloggin;

import android.hardware.SensorEventListener;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements SensorEventListener{

    private float xAnterior, yAnterior, zAnterior;

    private boolean incializacionmanager;
    //Manager necessary for handling the sensor
    private SensorManager smng;
    //accelerometer
    private Sensor acelerometro;
    //light
    private Sensor luz;


    private final float filtro = (float) 2.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        incializacionmanager = false;
        smng = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        acelerometro = smng.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        luz = smng.getDefaultSensor(Sensor.TYPE_LIGHT);

        smng.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);


    }


    protected void onResume() {
        super.onResume();
        smng.registerListener(this, luz, SensorManager.SENSOR_DELAY_NORMAL);
        smng.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();

        //Turning off the sensors
        smng.unregisterListener(this, luz);
        smng.unregisterListener(this);
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        //Light implementation deleted from here, was not working

        TextView xequis= (TextView)findViewById(R.id.valorx);
        TextView yye= (TextView)findViewById(R.id.valory);
        TextView zzeta= (TextView)findViewById(R.id.valorz);

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if (!incializacionmanager) {

            xAnterior = x;
            yAnterior = y;
            zAnterior = z;

            incializacionmanager = true;


        } else {

            float deltaX = Math.abs(xAnterior - x);
            float deltaY = Math.abs(yAnterior - y);
            float deltaZ = Math.abs(zAnterior - z);

            if (deltaX < filtro)
                deltaX = (float)0.0;
            if (deltaY < filtro)
                deltaY = (float)0.0;
            if (deltaZ < filtro)
                deltaZ = (float)0.0;


            xAnterior = x;
            yAnterior = y;
            zAnterior = z;


            xequis.setText(Float.toString(deltaX));
            yye.setText(Float.toString(deltaY));
            zzeta.setText(Float.toString(deltaZ));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
