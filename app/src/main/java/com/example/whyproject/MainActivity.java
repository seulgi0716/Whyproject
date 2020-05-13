package com.example.whyproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button button[] = new Button[4];
    private int btnid[] = {R.id.who, R.id.note, R.id.randompicker, R.id.punch};
    ImageView today_emo;

    /* DB 부분 */
    static DBHelper dhelper;
    static SQLiteDatabase db;
    static Cursor cursor;
    static MyCursorAdapter2 myCursorAdapter;

    final static String querySelectAll = "SELECT * FROM PWDTB";

    String today;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("나한테 왜그래?!");

        // 디비 접근 및 검색 코드 작성
        dhelper = new DBHelper(this);
        db = dhelper.getWritableDatabase();

        cursor = db.rawQuery(querySelectAll, null);
        myCursorAdapter = new MyCursorAdapter2(this, cursor);


        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        today = sdf.format(date);
        Toast.makeText(getApplicationContext(), today, Toast.LENGTH_SHORT).show();

        today_emo = (ImageView)findViewById(R.id.today_emo);
        // if문으로 db값에 있는 스트레스 지수로 바꿈

        for (int i = 0; i < button.length; i++) {
            int index = i;
            button[i] = findViewById(btnid[i]);
        }

        button[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WhoteaseActivity.class);
                startActivity(intent);
            }
        });

        button[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Trashcan.class);
                startActivity(intent);
            }
        });

        button[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RandomPicker.class);
                startActivity(intent);
            }
        });

        button[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Punch.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.lock:
                Intent intent = new Intent(getApplicationContext(), CreatePassword.class);
                startActivity(intent);
                finish();
                break;

            case R.id.unlock:
                try {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("비밀번호 해제");
                    builder.setIcon(R.drawable.openic);
                    builder.setMessage("비밀번호를 해제하시겠습니까?");

                    builder.setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String upquery = String.format("UPDATE PWDTB SET SET_VALUE = '0', PASSWORD = '0';");
                                    db.execSQL(upquery);
                                    cursor = db.rawQuery(querySelectAll, null);
                                    myCursorAdapter.changeCursor(cursor);
                                    Toast.makeText(getApplicationContext(), "암호가 해제되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();



                    // 다시한번 비밀번호를 확인하고 맞으면 해제
                    break;

                } catch (Exception e) {}

        }
        return false;
    }
}
