package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class GameRanking extends AppCompatActivity {


    static DBHelper mHelper;
    static SQLiteDatabase db;
    static Cursor cursor;
    static MyCursorAdapter2 myAdapter;

    final static String querySelectAll = String.format("SELECT * FROM RANKTB");

    static ListView ranklv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ranking);

        mHelper = new DBHelper(this);
        db = mHelper.getWritableDatabase();
        cursor = db.rawQuery(querySelectAll, null);
        myAdapter = new MyCursorAdapter2(this, cursor);

        ranklv = findViewById(R.id.ranklv);
            selectRANK();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void selectRANK(){
        String top = String.format("SELECT * FROM RANKTB ORDER BY COUNT DESC limit 5");
        cursor = db.rawQuery(top, null);

        try {
            cursor.moveToFirst();

            int count = cursor.getCount();
            System.out.println("count : " + count);
            if(cursor.getCount() > 0){
                startManagingCursor(cursor);
                MyCursorAdapter2 myadapter = new MyCursorAdapter2(this, cursor);
                ranklv.setAdapter(myadapter);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "아무 내용이 없습니다!", Toast.LENGTH_SHORT).show();
        }
    }
}
