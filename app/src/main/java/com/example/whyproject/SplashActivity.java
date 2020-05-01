package com.example.whyproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 디비 접근 및 검색 코드 작성

        try {
            Thread.sleep(3000);

            // 디비에서 현재 암호 설정 여부 값 변수에 넣는 코드 작성
            int set = 1;
            if(set == 1) { // 암호가 설정되어 있을 경우 암호 입력 창으로
                startActivity(new Intent(this,MainActivity.class));
                finish();
            } else { // 암호가 설정되어 있지 않을 경우 메인 화면으로 바로
                startActivity(new Intent(this,Trashcan.class));
                finish();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
