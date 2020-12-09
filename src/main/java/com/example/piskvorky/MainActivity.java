package com.example.piskvorky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.SensorEvent;

import com.tomer.fadingtextview.FadingTextView;

import java.util.Objects;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    Button buton3_3;
    Button buton4_4;
    Button butonSettings;
    Button butonScore;
    Button nastavHrace;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hudba na pozadi
        checkMusic();


        final TextView shakerTV = findViewById(R.id.shakerTV);
        shakerTV.setVisibility(View.INVISIBLE);

        CountDownTimer timer = new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                shakerTV.setVisibility(View.VISIBLE);
            }
        }.start();




        buton3_3 = findViewById(R.id.button3_3);
        buton4_4 = findViewById(R.id.button4_4);
        butonSettings = findViewById(R.id.buttonSettings);
        butonScore = findViewById(R.id.buttonScore);
        nastavHrace = findViewById(R.id.nastavHrace);

        butonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), settings.class);
                startActivity(i);
            }
        });

        buton4_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), four.class);
                startActivity(i);
            }
        });
        buton3_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), three.class);
                startActivity(i);
            }
        });

        nastavHrace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), popup.class);
                startActivity(i);
            }
        });



//sensor shaker
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }



    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > 25) {
                shakerOn();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    @Override
    protected void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }



    @Override
    public void onClick(View v) {

    }

    public void shakerOn() {
            buton3_3.setBackgroundResource(R.drawable.custom_button_shake);
            buton4_4.setBackgroundResource(R.drawable.custom_button_shake);
            butonSettings.setBackgroundResource(R.drawable.custom_button_shake);
            butonScore.setBackgroundResource(R.drawable.custom_button_shake);
              TextView shakerTV = findViewById(R.id.shakerTV);
              shakerTV.setVisibility(View.INVISIBLE);
              nastavHrace.setBackgroundResource(R.drawable.custom_button_shake);



    }

    public void checkMusic() {
        SharedPreferences mPrefs = getSharedPreferences("settings", 0);
        Integer mString = mPrefs.getInt("music", 1);
        Intent intent = new Intent(MainActivity.this, BackgroundMusic.class);

        if (mString==1) {
        startService(intent);
        }
        else {
            stopService(intent);
        }
    }


}