package com.example.piskvorky;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class afterwin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afterwin);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
         int width = dm.widthPixels;
         int height = dm.heightPixels;

         getWindow().setLayout((int)(width*0.7),(int)(height*0.5));

         Button buttonBack = findViewById(R.id.buttonBack);
        Button buttonAgain = findViewById(R.id.buttonAgain);
        EditText winnerField = findViewById(R.id.vyherceWin);

        Intent i = getIntent();
        String winner = i.getStringExtra("vitez");
        winnerField.setText(winner);
        winnerField.setEnabled(false);
        winnerField.setFocusable(false);
        winnerField.setClickable(false);


        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        buttonAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
