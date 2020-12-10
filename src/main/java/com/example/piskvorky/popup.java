package com.example.piskvorky;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class popup extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);

        Button buttonDone;
        buttonDone = findViewById(R.id.buttonHotovo);
        final SharedPreferences myFile = getSharedPreferences("settings", 0);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> staticAdapter1 = ArrayAdapter.createFromResource(this, R.array.barvy, android.R.layout.simple_spinner_item);
        staticAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(staticAdapter1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String p1color=(String) parent.getItemAtPosition(position).toString();
                SharedPreferences.Editor editor = myFile.edit();
                editor.putString("p1color", p1color).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> staticAdapter2 = ArrayAdapter.createFromResource(this, R.array.barvy, android.R.layout.simple_spinner_item);
        staticAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(staticAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String p2color=(String) parent.getItemAtPosition(position).toString();
                SharedPreferences.Editor editor = myFile.edit();
                editor.putString("p2color", p2color).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText p1name=findViewById(R.id.hrac1jmeno);
                String name_1=p1name.getText().toString();
                EditText p2name=findViewById(R.id.hrac2jmeno);
                String name_2=p2name.getText().toString();
                EditText pocetBodu=findViewById(R.id.pocetBodu);

                String value= pocetBodu.getText().toString();
                int body=Integer.parseInt(value);

                SharedPreferences.Editor editor = myFile.edit();
                editor.putString("p1name", name_1).commit();
                editor.putString("p2name", name_2).commit();
                editor.putInt("pocetBodu", body).commit();
                Toast.makeText(getApplicationContext(),"pts: "+body,Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
               startActivity(i);
            }
        });




    }
}

