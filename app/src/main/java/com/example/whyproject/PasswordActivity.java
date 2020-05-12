package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PasswordActivity extends AppCompatActivity {

    private Button btn[] = new Button[10];
    private int btnid[] = {R.id.one_btn, R.id.two_btn, R.id.three_btn, R.id.four_btn, R.id.five_btn, R.id.six_btn, R.id.seven_btn, R.id.eight_btn, R.id.nine_btn, R.id.zero_btn };
    private EditText pwdview;
    private ImageView first, second, third, forth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        setTitle("암호 설정");

        pwdview = findViewById(R.id.pwdview);
        first = findViewById(R.id.first_pwd);
        second = findViewById(R.id.second_pwd);
        third = findViewById(R.id.third_pwd);
        forth = findViewById(R.id.forth_pwd);

        for (int i = 0; i < btn.length; i++) {
            int index = i;
            btn[i] = findViewById(btnid[i]);
        }

        pwdview.addTextChangedListener(watcher);


        btn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdview.setText(pwdview.getText().toString()+1);
            }
        });

        btn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdview.setText(pwdview.getText().toString()+2);
            }
        });

        btn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdview.setText(pwdview.getText().toString()+3);
            }
        });

        btn[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdview.setText(pwdview.getText().toString()+4);
            }
        });

        btn[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdview.setText(pwdview.getText().toString()+5);
            }
        });

        btn[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdview.setText(pwdview.getText().toString()+6);
            }
        });

        btn[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdview.setText(pwdview.getText().toString()+7);
            }
        });

        btn[7].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdview.setText(pwdview.getText().toString()+8);
            }
        });

        btn[8].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdview.setText(pwdview.getText().toString()+9);
            }
        });

        btn[9].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwdview.setText(pwdview.getText().toString()+0);
            }
        });
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
            int len = s.length();
            Toast.makeText(getApplicationContext(), "길이: " + len, Toast.LENGTH_SHORT).show();


            switch (len) {
                case 0:
                    first.setImageResource(R.drawable.blue_dot);
                    second.setImageResource(R.drawable.blue_dot);
                    third.setImageResource(R.drawable.blue_dot);
                    forth.setImageResource(R.drawable.blue_dot);
                    break;
                case 1:
                    first.setImageResource(R.drawable.green_dot);
                    break;
                case 2:
                    first.setImageResource(R.drawable.green_dot);
                    second.setImageResource(R.drawable.green_dot);
                    break;
                case 3:
                    first.setImageResource(R.drawable.green_dot);
                    second.setImageResource(R.drawable.green_dot);
                    third.setImageResource(R.drawable.green_dot);
                    break;
                case 4:
                    first.setImageResource(R.drawable.green_dot);
                    second.setImageResource(R.drawable.green_dot);
                    third.setImageResource(R.drawable.green_dot);
                    forth.setImageResource(R.drawable.green_dot);
                    Toast.makeText(getApplicationContext(), "4자리가 다 눌렸음!", Toast.LENGTH_SHORT).show();
                    // 이 암호로 하시겠습니까? 다이얼로그
                    break;
            }

        }
    };

}
