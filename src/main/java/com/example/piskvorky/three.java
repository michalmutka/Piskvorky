package com.example.piskvorky;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class three extends AppCompatActivity implements View.OnClickListener {
    private Button[][] poleTlacitek = new Button[3][3];
    private boolean jeTahHrace1 = true;
    private int pocetTahu;
    private int bodyP1;
    private int bodyP2;
    private TextView bodyP1TV;
    private TextView bodyP2TV;
    private String player1name;
    private String player2name;
    private String player1color;
    private String player2color;
    private String p1rgb;
    private String p2rgb;
    SharedPreferences myFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.three);

        myFile = getSharedPreferences("settings", 0);
        player1name = myFile.getString("p1name", "Hráč 1");
        player2name = myFile.getString("p2name", "Hráč 2");
        player1color = myFile.getString("p1color", "barva1");
        player2color = myFile.getString("p2color", "barva2");
        bodyP1TV = findViewById(R.id.skorep1);
        bodyP2TV = findViewById(R.id.skorep2);
        bodyP1TV.setText(player1name+" : 0");
        bodyP2TV.setText(player2name+" : 0");

        if (player1color.equals("Červená")) {p1rgb="#F51010";}
        else if (player1color.equals("Modrá")) {p1rgb="#0830F7";}
        else if (player1color.equals("Zelená")) {p1rgb="#5FAF36";}
        else if (player1color.equals("Žlutá")) {p1rgb="#ECEC1C";}
        else if (player1color.equals("Černá")) {p1rgb="#000000";}


        if (player2color.equals("Červená")) {p2rgb="#F51010";}
        else if (player2color.equals("Modrá")) {p2rgb="#0830F7";}
        else if (player2color.equals("Zelená")) {p2rgb="#5FAF36";}
        else if (player2color.equals("Žlutá")) {p2rgb="#ECEC1C";}
        else if (player2color.equals("Černá")) {p2rgb="#000000";}

        bodyP1TV.setTextColor(Color.parseColor(p1rgb));
        bodyP2TV.setTextColor(Color.parseColor(p2rgb));

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++) {
                String buttonID = "button_r"+i+"c"+j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                poleTlacitek[i][j]=findViewById(resID);
                poleTlacitek[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int ID = item.getItemId();
        if(ID == R.id.restartMenuList) {
            novaHra();
        }
        if(ID == R.id.settingsMenuList) {
            Intent intent = new Intent(this, settings.class);
            startActivity(intent);
        }

        if(ID == R.id.scoreMenuList) {


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (!((Button)v).getText().toString().equals("")) {
            return;
        }
        if (jeTahHrace1) {
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor(p1rgb));


        }
        else {
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor(p2rgb));
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
        vykresliBody();
        vykresliDesku();
        winningSound();

    }
    private void vyhraP2() {
        bodyP2++;
        vykresliBody();
        vykresliDesku();
        winningSound();
    }
    private void winningSound() {
        SharedPreferences mPrefs = getSharedPreferences("settings", 0);
        Integer soundString = mPrefs.getInt("sound", 1);
        if (soundString==1){
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.win);
            mp.start();
        }
    }

    private void remiza() {
        vykresliDesku();
        tieSound();
    }

    private void tieSound () {
        SharedPreferences mPrefs = getSharedPreferences("settings", 0);
        Integer soundString = mPrefs.getInt("sound", 1);
        if (soundString==1){
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.tie);
            mp.start();
        }
    }

    private void vykresliBody() {
        bodyP1TV.setText(player1name+" : "+bodyP1);
        bodyP2TV.setText(player2name+" : "+bodyP2);
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