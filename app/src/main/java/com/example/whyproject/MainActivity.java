package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
                // 다이얼로그 띄워서 or 액티비티 하나 띄워서
                // DB에 비밀번호를 업데이트 할 코드 (NULL -> 비밀번호, 기존 비밀번호 -> NULL)
            case R.id.unlock:
           //     String update_q = String.format("UPDATE PWDTB SET SET_VALUE = '0' WHERE SET_VALUE = '1'");
           //     db.execSQL(update_q);
           //     Toast.makeText(getApplicationContext(), "암호가 해제되었습니다.", Toast.LENGTH_SHORT).show();
                // DB값 null로 업데이트 할 코드
                // 다이얼로그로 확인 메시지 팝업
        }
        return false;
    }
}
