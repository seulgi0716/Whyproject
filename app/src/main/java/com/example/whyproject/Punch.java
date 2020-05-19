package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
    int touchcount = 0;
    Intent intent;

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

        countview2.addTextChangedListener(watcher);
        countview2.setVisibility(View.INVISIBLE);
        myTimer = new MyTimer(30000, 1000);

        target.setEnabled(false);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myTimer.start();
                mVibe.vibrate(150);
            }
        });

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myTimer.cancel();
                timertv.setText("30 초");
                touchcount = 0;
                target.setEnabled(false);
                target.setBackgroundResource(R.drawable.building);
                timertv.setTextColor(Color.RED);
            }
        });

        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), GameRanking.class);
                startActivity(intent);
                finish();
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
            timertv.setText("0 초");
            target.setEnabled(false);
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
            } else if(touchcount >= 61 && touchcount <= 120) {
                target.setBackgroundResource(R.drawable.broke1);
            } else if(touchcount >= 121 && touchcount <= 150) {
                target.setBackgroundResource(R.drawable.broke2);
            } else if(touchcount >= 151){
                target.setBackgroundResource(R.drawable.broke3);
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
