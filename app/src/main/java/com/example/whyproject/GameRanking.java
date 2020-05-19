package com.example.whyproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
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
        setTitle("랭킹");

        mHelper = new DBHelper(this);
        db = mHelper.getWritableDatabase();
        cursor = db.rawQuery(querySelectAll, null);
        myAdapter = new MyCursorAdapter2(this, cursor);

        ranklv = findViewById(R.id.ranklv);
            selectRANK();

        ranklv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String qq = String.format("SELECT * FROM RANKTB ORDER BY COUNT DESC limit 5");
                cursor = db.rawQuery(qq, null);
                myAdapter.changeCursor(cursor);
                cursor.moveToFirst();
                cursor.moveToPosition(i);
                final String tn = cursor.getString(cursor.getColumnIndex("TARGET_NAME"));
                final int c = cursor.getInt(cursor.getColumnIndex("COUNT"));

                System.out.println("tn : " + tn);
                System.out.println("c : " + c);

                AlertDialog.Builder build = new AlertDialog.Builder(GameRanking.this);

                build.setTitle("항목 삭제").setMessage("해당 항목을 삭제하시겠습니까?");
                build.setIcon(R.drawable.soso);
                build.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            System.out.println("tn : " + tn);
                            String query = String.format("DELETE FROM RANKTB WHERE TARGET_NAME = '%s' and COUNT = '%d';", tn, c);
                            db.execSQL(query);
                            selectRANK();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "aaaa", Toast.LENGTH_SHORT).show();
                        }
                        int count = cursor.getCount();
                        System.out.println("delete : " + count);

                        Toast.makeText(getApplicationContext(), "삭제되었습니다!", Toast.LENGTH_SHORT).show();
                    }
                });

                build.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog alertDialog = build.create();
                alertDialog.show();

                return true;
            }
        });

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
