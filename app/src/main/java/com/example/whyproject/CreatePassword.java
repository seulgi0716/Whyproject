package com.example.whyproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CreatePassword extends AppCompatActivity {

    private Button btn[] = new Button[11];
    private int btnid[] = {R.id.one_btn, R.id.two_btn, R.id.three_btn, R.id.four_btn, R.id.five_btn, R.id.six_btn, R.id.seven_btn, R.id.eight_btn, R.id.nine_btn, R.id.zero_btn, R.id.del_btn};
    private EditText pwdview;
    private ImageView first, second, third, forth;

    static DBHelper dhelper;
    static SQLiteDatabase db;
    static Cursor cursor;

    int b;


    final static String querySelectAll = "SELECT * FROM PWDTB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);
        setTitle("비밀번호 설정/변경");

        pwdview = findViewById(R.id.pwdview);
        first = findViewById(R.id.first_pwd);
        second = findViewById(R.id.second_pwd);
        third = findViewById(R.id.third_pwd);
        forth = findViewById(R.id.forth_pwd);

        // 디비 접근 및 검색 코드 작성
        dhelper = new DBHelper(this);
        db = dhelper.getWritableDatabase();
        cursor = db.rawQuery(querySelectAll, null);

        for (int i = 0; i < btn.length; i++) {
            btn[i] = findViewById(btnid[i]);
        }
        pwdview.addTextChangedListener(watcher);
        pwdview.setVisibility(View.INVISIBLE);


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
        public void onTextChanged(CharSequence s, final int start, int before, int count) {

            btn[10].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pwdview.getText().toString().equals("")) {

                    } else {
                        String subpw = (pwdview.getText().toString()).substring(0,b-1);
                        System.out.println("subpw : " + subpw);
                        pwdview.setText(subpw);
                        System.out.println("b : " + b);
                    }
                }
            });


            int len = s.length();
            switch (len) {
                case 0:
                    first.setImageResource(R.drawable.blue_dot);
                    second.setImageResource(R.drawable.blue_dot);
                    third.setImageResource(R.drawable.blue_dot);
                    forth.setImageResource(R.drawable.blue_dot);
                    break;
                case 1:
                    first.setImageResource(R.drawable.green_dot);
                    second.setImageResource(R.drawable.blue_dot);
                    third.setImageResource(R.drawable.blue_dot);
                    forth.setImageResource(R.drawable.blue_dot);
                    b = 1;
                    break;
                case 2:
                    first.setImageResource(R.drawable.green_dot);
                    second.setImageResource(R.drawable.green_dot);
                    third.setImageResource(R.drawable.blue_dot);
                    forth.setImageResource(R.drawable.blue_dot);
                    b = 2;
                    break;
                case 3:
                    first.setImageResource(R.drawable.green_dot);
                    second.setImageResource(R.drawable.green_dot);
                    third.setImageResource(R.drawable.green_dot);
                    forth.setImageResource(R.drawable.blue_dot);
                    b = 3;
                    break;
                case 4:
                    first.setImageResource(R.drawable.green_dot);
                    second.setImageResource(R.drawable.green_dot);
                    third.setImageResource(R.drawable.green_dot);
                    forth.setImageResource(R.drawable.green_dot);
                    b = 4;

                    final int pw = Integer.parseInt(pwdview.getText().toString());
                    System.out.println("비밀번호 확인 : " + pw);

                    AlertDialog.Builder builder = new AlertDialog.Builder(CreatePassword.this);
                    builder.setIcon(R.drawable.lockic);
                    String valueq = String.format("SELECT SET_VALUE FROM PWDTB");
                    cursor = db.rawQuery(valueq, null);
                    cursor.moveToFirst();
                    int searchvalue = cursor.getInt(0);
                    if(searchvalue == 1) {
                        builder.setTitle("비밀번호 변경");
                        builder.setMessage("비밀번호를 변경하시겠습니까?");
                    } else if(searchvalue == 0) {
                        builder.setTitle("비밀번호 설정");
                        builder.setMessage("비밀번호를 설정하시겠습니까?");
                    }

                    builder.setPositiveButton("예",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    System.out.println("비밀번호 확인2 : " + pw);

                                    String setpw = String.format("UPDATE PWDTB SET SET_VALUE = '1', PASSWORD = '%d';", pw);
                                    db.execSQL(setpw);
                                    cursor = db.rawQuery(querySelectAll, null);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Toast.makeText(getApplicationContext(), "비밀번호가 설정되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.setNegativeButton("아니오",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    pwdview.setText("");
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                    builder.show();

                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}