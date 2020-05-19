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


    Date date = Calendar.getInstance().getTime();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    String today = sdf.format(date);

    private long pressedTime = 0;


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


        System.out.println("today : " + today);

        today_emo = findViewById(R.id.today_emo);
        System.out.println("aa");

        cursor = db.rawQuery(querySelectAll, null);
        String selectq = String.format("SELECT SUM(S_VALUE) as SUM FROM STRESSTB WHERE S_DATE = '%s';", today);
        System.out.println("today : " + today);
        cursor = db.rawQuery(selectq, null);
        cursor.moveToFirst();
        int sum_value = cursor.getInt(cursor.getColumnIndex("SUM"));
        System.out.println("sum_value : " + sum_value);

        if(sum_value <= 10){
            today_emo.setImageResource(R.drawable.happy);
        }
        if(sum_value > 10 && sum_value <= 150) {
            today_emo.setImageResource(R.drawable.soso);
        }
        if(sum_value > 150) {
            today_emo.setImageResource(R.drawable.unhappy);
        }

        for (int i = 0; i < button.length; i++) {
            int index = i;
            button[i] = findViewById(btnid[i]);
        }

        button[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WhoteaseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Trashcan.class);
                startActivity(intent);
                finish();
            }
        });

        button[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RandomPicker.class);
                startActivity(intent);
                finish();
            }
        });

        button[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Punch.class);
                startActivity(intent);
                finish();
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


                                    String valueq = String.format("SELECT SET_VALUE FROM PWDTB");
                                    cursor = db.rawQuery(valueq, null);
                                    cursor.moveToFirst();
                                    int searchvalue2 = cursor.getInt(0);
                                    if(searchvalue2 == 0) {
                                        Toast.makeText(getApplicationContext(), "설정되어 있는 비밀번호가 없습니다!", Toast.LENGTH_SHORT).show();
                                    } else if(searchvalue2 == 1) {
                                        String upquery = String.format("UPDATE PWDTB SET SET_VALUE = '0', PASSWORD = '0';");
                                        db.execSQL(upquery);
                                        cursor = db.rawQuery(querySelectAll, null);
                                        myCursorAdapter.changeCursor(cursor);
                                        Toast.makeText(getApplicationContext(), "비밀번호가 해제되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
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

    @Override
    public void onBackPressed() {

        if ( pressedTime == 0 ) {
            Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if (seconds > 2000) {
                Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                pressedTime = 0;
            } else {
                finish();
            }
        }
    }
}
