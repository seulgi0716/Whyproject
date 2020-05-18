package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class RandomPicker extends AppCompatActivity {

    private ImageButton roll;
    String moc[] = {
            "푹 잔다", "술자리", "운동", "친구와의 대화",
            "맛있는 음식", "목욕", "산책", "행복한 상상",
            "TV보기", "영화보기", "유투브 재밌는 영상보기",
            "눈감고 크게 심호흡", "음악 듣기", "노래방 가기",
            "청소하기", "쇼핑가기", "스트레칭 하기", "일기에 적어놓기", "게임하기",
            "아무도 안보는 곳에서 크게 욕하기"
    };
    ArrayList<String> methodOfcancel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_picker);
        setTitle("알려줘요! 해소 방법");

        roll = findViewById(R.id.roll);



        SoundManager.getInstance();
        SoundManager.initSounds(this);
        SoundManager.loadSounds();

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SoundManager.playSound(1,1);

            }
        });


    }
    public void onDestory() {
        super.onDestroy();
        SoundManager.cleanup();
    }
}
