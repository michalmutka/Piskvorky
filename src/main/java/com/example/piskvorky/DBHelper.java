package com.example.piskvorky;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DBTAMZpisk.db";
    public static final String ITEM_COLUMN_P1 = "p1name";
    public static final String ITEM_COLUMN_P2 = "p2name";
    public static final String ITEM_COLUMN_BODY = "points";
    public static final String ITEM_COLUMN_VYHERCE = "winner";
    public static final String ITEM_COLUMN_LOKACE = "location";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE score " + "(id INTEGER PRIMARY KEY, p1name TEXT,p2name TEXT, points TEXT, winner TEXT, location TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS score");
        onCreate(db);
    }

    public boolean insertItem(String p1name, String p2name, String points, String winner,String location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("p1name", p1name);
        contentValues.put("p2name", p2name);
        contentValues.put("points", points);
        contentValues.put("winner", winner);
        contentValues.put("location", location);

        long insertedId = db.insert("score", null, contentValues);
        if (insertedId == -1) return false;
        return true;
    }

    public boolean deleteItem (int id)
    {
        SQLiteDatabase database = this.getReadableDatabase();
        database.delete("score", "id=" + id, null);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from score where id=" + id + "", null);
        return res;
    }



    public ArrayList<String> getItemList()
    {
        ArrayList<String> arrayList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from score", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String p1nameVypis = res.getString(res.getColumnIndex(ITEM_COLUMN_P1));
            int id = res.getInt(0);
            String p2nameVypis = res.getString(res.getColumnIndex(ITEM_COLUMN_P2));
            String bodyVypis = res.getString(res.getColumnIndex(ITEM_COLUMN_BODY));
            String winnerVypis = res.getString(res.getColumnIndex(ITEM_COLUMN_VYHERCE));


            arrayList.add(id+": "+p1nameVypis+ " VS. " + p2nameVypis + " hráli do " + bodyVypis + " bodů a vyhrál: " + winnerVypis);
            res.moveToNext();
        }

        return arrayList;
    }




}

