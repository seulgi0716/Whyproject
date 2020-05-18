package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class RandomPicker extends AppCompatActivity {

    private ImageButton slot;
    private ImageView uphandle, downhandle;
    private TextView randomtv;

    String moc[] = {
            "아무 생각 없이 푹 잠에 들기", "부어라~ 마셔라~ 알코올 털어넣기", "땀 쭉쭉 운동", "하하호호 친구들과 수다떨기",
            "제일 좋아하는 음식 먹기", "목욕", "가볍게 산책", "즐거운 상상",
            "좋아하는 TV 예능 보기", "영화보기", "유튜브 재밌는 영상보기",
            "눈감고 크게 심호흡", "음악 듣기", "나는 가수다! 노래방 가기",
            "쓱싹쓱싹 청소하기", "즐거운 쇼핑하기", "팔다리 쭉쭉 스트레칭 하기", "평소에 즐겨하는 게임",
            "아무도 안보는 곳에서 크게 욕하기", "땀 뻘뻘! 매운 음식 먹기"
    };
    ArrayList<String> methodOfcancel = new ArrayList<>();
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_picker);
        setTitle("알려줘요! 해소 방법");

        slot = findViewById(R.id.slot);
        uphandle = findViewById(R.id.uphandle);
        downhandle = findViewById(R.id.downhandle);
        randomtv = findViewById(R.id.randomtv);

        downhandle.setVisibility(View.INVISIBLE);

        int ran = random.nextInt(moc.length)+1;


        SoundManager.getInstance();
        SoundManager.initSounds(this);
        SoundManager.loadSounds();

        slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SoundManager.playSound(1,1);
                downhandle.setVisibility(View.VISIBLE);
                uphandle.setVisibility(View.INVISIBLE);




            }
        });


    }
    public void onDestory() {
        super.onDestroy();
        SoundManager.cleanup();
    }
}
