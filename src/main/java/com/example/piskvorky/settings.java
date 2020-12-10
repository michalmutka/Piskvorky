package com.example.piskvorky;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;


public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        final CheckBox sound = findViewById(R.id.checkBox);
        final CheckBox music = findViewById(R.id.checkBox1);
        final Intent intent = new Intent(this, BackgroundMusic.class);
        final SharedPreferences myFile = getSharedPreferences("settings", 0);
        Integer soundString = myFile.getInt("sound", 1);
        Integer musicString = myFile.getInt("music", 1);

        if(musicString==1) {
            music.setChecked(true);
        }
        else music.setChecked(false);

        if(soundString==1) {
            sound.setChecked(true);
        }
        else sound.setChecked(false);

        sound.setOnTouchListener(new View.OnTouchListener() {
                                     @Override
                                     public boolean onTouch(View v, MotionEvent event) {
                                         if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                             if (sound.isChecked() == true) {
                                                 SharedPreferences.Editor editor = myFile.edit();
                                                 editor.putInt("sound", 0).commit();
                                             } else {
                                                 SharedPreferences.Editor editor = myFile.edit();
                                                 editor.putInt("sound", 1).commit();
                                             }

                                         }
                                         return false;
                                     }
                                 });

        music.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (music.isChecked() == true) {
                        SharedPreferences.Editor editor = myFile.edit();
                        editor.putInt("music", 0).commit();
                        stopService(intent);

                    } else {
                        SharedPreferences.Editor editor = myFile.edit();
                        editor.putInt("music", 1).commit();
                        startService(intent);

                    }

                }
                return false;
            }
        });

    }
}

