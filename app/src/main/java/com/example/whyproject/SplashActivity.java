package com.example.whyproject;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class SplashActivity extends Activity {

    static DBHelper dhelper;
    static SQLiteDatabase db;
    static Cursor cursor2;

    final static String querySelectAll = "SELECT * FROM PWDTB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 디비 접근 및 검색 코드 작성
        dhelper = new DBHelper(this);
        db = dhelper.getWritableDatabase();

        cursor2 = db.rawQuery(querySelectAll, null);

        String qq2 = String.format("SELECT SET_VALUE FROM PWDTB");
        cursor2 = db.rawQuery(qq2, null);
        cursor2.moveToFirst();
        int set_value = 2;

        try {
            set_value = cursor2.getInt(0);
            System.out.println("set_v1 : " + set_value);
        } catch (Exception e) {
                String insert_value = String.format("INSERT INTO PWDTB VALUES(null, '0', null);");
                db.execSQL(insert_value);
                cursor2 = db.rawQuery(querySelectAll, null);

                String qq3 = String.format("SELECT SET_VALUE FROM PWDTB");
                cursor2 = db.rawQuery(qq3, null);
                cursor2.moveToFirst();
                set_value = cursor2.getInt(0);
                System.out.println("set_v2 : " + set_value);

        }

        try {
            Thread.sleep(3000);

            if(set_value == 0) { // 암호가 설정되어 있지 않을 경우 메인 화면으로 바로
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else if(set_value == 1){ // 암호가 설정되어 있을 경우 암호 입력 창으로
                startActivity(new Intent(this, PasswordActivity.class));
                finish();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
