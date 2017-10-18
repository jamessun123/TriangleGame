package hehexd.trianglegame;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.WindowManager;


public class SensorActivity extends Activity implements SensorEventListener{

    SensorManager mSensorManager;
    Sensor mRotator;
    WriterView writing; // the view, draws everything

    static float[] tilt = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSensorManager = (SensorManager) this.getSystemService(this.SENSOR_SERVICE);
        mRotator = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        mSensorManager.registerListener(this, mRotator, SensorManager.SENSOR_DELAY_GAME);



        writing = new WriterView(this);
        setContentView(writing);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //now, if you tilted your phone...

        float[] rotationalVector = new float[16];
        SensorManager.getRotationMatrixFromVector(rotationalVector, event.values);
        SensorManager.getOrientation(rotationalVector, tilt);
        //calculated the orientation, mostly used for the roll
        if(!writing.isEnded()) { //  if the game isn't over, move the triangle
            writing.moveGame(); // move the position
            // also, move the enemies closer
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing, please don't screw me sensor
    }

    @Override
    public void onStop(){
        super.onPause();
        // if you close the app, then it will turn off the sensor so you don't drain battery
        mSensorManager.unregisterListener(this, mRotator);
    }

    @Override
    public void onStart(){
        //when you open the app, it will register the listener
        super.onResume();
        mSensorManager.registerListener(this, mRotator, SensorManager.SENSOR_DELAY_NORMAL);
        //set the initial vectors to the ones when you put the app back in focus
    }
}
