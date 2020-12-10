package com.example.piskvorky;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayItemActivity extends AppCompatActivity {

    private DBHelper mydb;
    TextView hrac1;
    TextView hrac2;
    TextView body;
    TextView vyhral;
    Button buttonDelete;
    TextView lokace;
    int idToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleactivity);

        hrac1 = findViewById(R.id.hrac1);
        hrac2 = findViewById(R.id.hrac2);
        body = findViewById(R.id.body);
        vyhral = findViewById(R.id.vyherce);
        buttonDelete = findViewById(R.id.buttonDelete);
        lokace = findViewById(R.id.locationEditText);



        mydb = new DBHelper(this);
        Intent i = getIntent();
        if(i !=null)
        {
            int value = i.getIntExtra("id", 0);
            idToUpdate = value;
            if (idToUpdate > 0)
            {
                Cursor rs = mydb.getData(idToUpdate);
                rs.moveToFirst();
                String p1DB = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_P1));
                String p2DB = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_P2));
                String bodyDB = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_BODY));
                String vyherceDB = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_VYHERCE));
                String lokaceDB = rs.getString(rs.getColumnIndex(DBHelper.ITEM_COLUMN_LOKACE));
                if (!rs.isClosed()) { rs.close(); }
                hrac1.setText(p1DB);
                hrac1.setEnabled(false);
                hrac1.setFocusable(false);
                hrac1.setClickable(false);
                hrac2.setText(p2DB);
                hrac2.setEnabled(false);
                hrac2.setFocusable(false);
                hrac2.setClickable(false);
                body.setText(bodyDB);
                body.setEnabled(false);
                body.setFocusable(false);
                body.setClickable(false);
                vyhral.setText(vyherceDB);
                vyhral.setEnabled(false);
                vyhral.setFocusable(false);
                vyhral.setClickable(false);
                lokace.setText(lokaceDB);
                lokace.setFocusable(false);
                lokace.setClickable(false);
            }
        }

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydb.deleteItem(idToUpdate);
                finish();
                Intent i = new Intent(getApplicationContext(), Score.class);
                startActivity(i);
            }
        });
    }




    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(getApplicationContext(), Score.class);
        startActivity(i);
    }
}