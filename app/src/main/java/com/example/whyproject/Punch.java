package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Punch extends AppCompatActivity {

    TextView timertv;
    MyTimer myTimer;
    Button ranking, start, resetbtn;
    Vibrator mVibe;
    ImageButton target;
    EditText target_name;
    int touchcount = 0;

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

        myTimer = new MyTimer(30000, 1000);

        target.setEnabled(false);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  target.setEnabled(false);
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
            timertv.setText(millisUntilFinished/1000 + " 초");
            target.setEnabled(true);
            target.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    touchcount++;
                    System.out.println(touchcount);
                }
            });
        }

        @Override
        public void onFinish() {
            timertv.setText("0 초");
            target.setEnabled(false);
        }
    }

}
