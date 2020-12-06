package com.example.piskvorky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class three extends AppCompatActivity implements View.OnClickListener {
    private Button[][] poleTlacitek = new Button[3][3];
    private boolean jeTahHrace1 = true;
    private int pocetTahu;
    private int bodyP1;
    private int bodyP2;
    private TextView bodyP1TV;
    private TextView bodyP2TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three);

        bodyP1TV = findViewById(R.id.skorep1);
        bodyP2TV = findViewById(R.id.skorep2);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++) {
                String buttonID = "button_r"+i+"c"+j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                poleTlacitek [i][j]=findViewById(resID);
                poleTlacitek[i][j].setOnClickListener(this);
            }
        }
        Button tlacitkoReset = findViewById(R.id.button_reset);
        tlacitkoReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novaHra();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals("")) {
            return;
        }
        if (jeTahHrace1) {
            ((Button)v).setText("1");
        }
        else {
            ((Button)v).setText("0");
        }
        pocetTahu++;
        if (vyhralNekdo()) {
            if (jeTahHrace1) {
                vyhraP1();
            }
            else {
                vyhraP2();
            }
        }
        else if (pocetTahu==9) {
            remiza();
        }
        else {
            jeTahHrace1 = !jeTahHrace1;
        }

    }

    private boolean vyhralNekdo() {
        String [][] podminky =  new String [3][3];
        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                podminky [i][j] =  poleTlacitek [i][j].getText().toString();
            }
        }
        for(int i=0;i<3;i++) {
            if (podminky[i][0].equals (podminky [i][1]) && podminky[i][0].equals (podminky [i][2]) && !podminky[i][0].equals("") ) {  //rows
                return true;
            }
        }
        for(int i=0;i<3;i++) {
            if (podminky[0][i].equals (podminky [1][i]) && podminky[0][i].equals (podminky [2][i]) && !podminky[0][i].equals("") ) {  //cols
                return true;
            }
        }
        for(int i=0;i<3;i++) {
            if (podminky[0][0].equals (podminky [1][1]) && podminky[0][0].equals (podminky [2][2]) && !podminky[0][0].equals("") ) {  //diag
                return true;
            }
        }
        for(int i=0;i<3;i++) {
            if (podminky[0][2].equals (podminky [1][1]) && podminky[0][2].equals (podminky [2][0]) && !podminky[0][2].equals("") ) {  //diag
                return true;
            }
        }
        return false;
    }

    private void vyhraP1() {
        bodyP1++;
        Toast.makeText(this, "Hráč 1 vyhrál!", Toast.LENGTH_LONG).show();
        vykresliBody();
        vykresliDesku();

    }
    private void vyhraP2() {
        bodyP2++;
        Toast.makeText(this, "Hráč 2 vyhrál!", Toast.LENGTH_LONG).show();
        vykresliBody();
        vykresliDesku();
    }
    private void remiza() {
        Toast.makeText(this, "Remiza!", Toast.LENGTH_LONG).show();
        vykresliDesku();
    }

    private void vykresliBody() {
        bodyP1TV.setText("Hráč 1 : "+bodyP1);
        bodyP2TV.setText("Hráč 2 : "+bodyP2);
    }
    private void vykresliDesku() {
        for (int i=0;i<3;i++) {
            for(int j =0;j<3;j++) {
                poleTlacitek[i][j].setText("");
            }
        }
        pocetTahu=0;
        jeTahHrace1=true;
    }
    private void novaHra() {
        bodyP1=0;
        bodyP2=0;
        vykresliBody();
        vykresliDesku();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("pocetTahu", pocetTahu);
        outState.putInt("bodyP1", bodyP1);
        outState.putInt("bodyP2", bodyP2);
        outState.putBoolean("jeTahHrace1", jeTahHrace1);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pocetTahu = savedInstanceState.getInt("pocetTahu");
        bodyP1 = savedInstanceState.getInt("bodyP1");
        bodyP2 = savedInstanceState.getInt("bodyP2");
        jeTahHrace1 = savedInstanceState.getBoolean("jeTahHrace1");
    }
}