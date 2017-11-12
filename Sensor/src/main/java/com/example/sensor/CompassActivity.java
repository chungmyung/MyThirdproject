package com.example.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager ;
    private Sensor mSensor;

    private ImageView mCompassImage ;
    private TextView mDegreeTextView ;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);

        mCompassImage = (ImageView) findViewById(R.id.compass);
        mDegreeTextView = (TextView) findViewById(R.id.degree);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 각도
            float degree = Math.round(event.values[0]);
            mDegreeTextView.setText("" + degree);

//            이미지 돌려 주는 코드
            mCompassImage.setRotation(degree);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }



}
