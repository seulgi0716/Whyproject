package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Punch extends AppCompatActivity {

    TextView timertv, countview2;
    MyTimer myTimer;
    Button start, resetbtn;
    Vibrator mVibe;
    ImageButton target, ranking;
    EditText target_name;
    int touchcount=0;
    Intent intent;
    String targetn;

    static DBHelper mHelper;
    static SQLiteDatabase db;
    static Cursor cursor;
    static MyCursorAdapter2 myAdapter;
    final static String querySelectAll = String.format("SELECT * FROM RANKTB");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch);
        setTitle("풀자! 스트레스!");
        mVibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        timertv = findViewById(R.id.timertv);
        ranking = findViewById(R.id.ranking);
        start = findViewById(R.id.startbtn);
        resetbtn = findViewById(R.id.resetbtn);
        target = findViewById(R.id.target);
        target_name = findViewById(R.id.target_name);
        countview2 = findViewById(R.id.countview2);

      //  target_name.addTextChangedListener(watcher2);
        countview2.addTextChangedListener(watcher);
        countview2.setVisibility(View.INVISIBLE);
        myTimer = new MyTimer(30000, 1000);

        target.setEnabled(false);

        mHelper = new DBHelper(this);
        db = mHelper.getWritableDatabase();
        cursor = db.rawQuery(querySelectAll, null);
        myAdapter = new MyCursorAdapter2(this, cursor);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (target_name.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "타겟이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    targetn = target_name.getText().toString();
                    myTimer.start();
                    mVibe.vibrate(150);
                }
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myTimer.cancel();
                target_name.setText("");
                timertv.setText("30 초");
                touchcount = 0;
                target.setEnabled(false);
                target.setBackgroundResource(R.drawable.building);
                timertv.setTextColor(Color.BLACK);
            }
        });

        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), GameRanking.class);
                startActivity(intent);
            }
        });
    }

    public class MyTimer extends CountDownTimer {

        public MyTimer(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            String remain = millisUntilFinished/1000 + " 초";
            timertv.setText(remain);

            if(remain.equals("10 초"))
                timertv.setTextColor(Color.RED);

            target.setEnabled(true);
            target.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    touchcount++;
                    System.out.println(touchcount);
                    countview2.setText(String.valueOf(touchcount));
                }
            });
        }

        @Override
        public void onFinish() {
            timertv.setText("타임 오버~!");
            timertv.setTextColor(Color.RED);
            target.setEnabled(false);
            // db 저장
            System.out.println("targetn : " + targetn + ", touchcount : " + touchcount);
            String insertresult = String.format("INSERT INTO RANKTB VALUES(null, '%s', '%d');", targetn, touchcount);
            db.execSQL(insertresult);
            cursor = db.rawQuery(querySelectAll, null);
            myAdapter.changeCursor(cursor);
        }
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void afterTextChanged(Editable edit) {
            // Text가 바뀌고 동작할 코드
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Text가 바뀌기 전 동작할 코드
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            touchcount = Integer.parseInt(s.toString());
            if(touchcount >= 31 && touchcount <= 60) {
                target.setBackgroundResource(R.drawable.fire);
            } else if(touchcount >= 61 && touchcount <= 100) {
                target.setBackgroundResource(R.drawable.broke4);
            } else if(touchcount >= 101 && touchcount <= 130) {
                target.setBackgroundResource(R.drawable.broke5);
            } else if(touchcount >= 131 && touchcount <= 170) {
                target.setBackgroundResource(R.drawable.broke6);
            } else if(touchcount >= 171) {
                target.setBackgroundResource(R.drawable.broke7);
            }
        }
    };

    @Override
    public void onBackPressed() {
        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
