package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


public class RandomPicker extends AppCompatActivity {

    private Button reset;
    private ImageButton slot;
    private TextView randomtv;

    String moc[] = {
            "아무 생각 없이 푹 잠에 들기", "부어라~ 마셔라~ 알코올 넣기", "땀 쭉쭉 운동", "하하호호 친구들과 수다떨기",
            "제일 좋아하는 음식 먹기", "목욕", "가볍게 산책", "즐거운 상상",
            "좋아하는 TV 예능 보기", "영화보기", "유튜브 재밌는 영상보기",
            "눈감고 크게 심호흡", "음악 듣기", "나는 가수다! 노래방 가기",
            "쓱싹쓱싹 청소하기", "즐거운 쇼핑하기", "팔다리 쭉쭉 스트레칭 하기", "평소에 즐겨하는 게임",
            "아무도 없는 곳에서 크게 욕하기", "땀 뻘뻘! 매운 음식 먹기"
    };
    ArrayList<String> methodOfcancel = new ArrayList<>();
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_picker);
        setTitle("알려줘요! 해소 방법");

        slot = findViewById(R.id.slot);
        randomtv = findViewById(R.id.randomtv);
        reset = findViewById(R.id.reset);

        reset.setVisibility(View.INVISIBLE);

        SoundManager.getInstance();
        SoundManager.initSounds(this);
        SoundManager.loadSounds();

        slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundManager.playSound(1,1);
                slot.setBackgroundDrawable(ContextCompat.getDrawable(RandomPicker.this, R.drawable.downslot));

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate);
                randomtv.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener(){
                    public void onAnimationEnd(Animation animation){
                        int ran = random.nextInt(moc.length);
                        System.out.println(moc[ran]);
                        randomtv.setText(moc[ran]);
                        System.out.println("ran : " + ran );
                        reset.setVisibility(View.VISIBLE);

                        reset.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                slot.setBackgroundDrawable(ContextCompat.getDrawable(RandomPicker.this, R.drawable.upslot));
                                randomtv.setText("'?'");
                                slot.setEnabled(true);
                            }
                        });
                    }
                    public void onAnimationStart(Animation animation){;}
                    public void onAnimationRepeat(Animation animation){;}
                });

                slot.setEnabled(false);


            }
        });
    }

    public void onDestory() {
        super.onDestroy();
        SoundManager.cleanup();
    }
}
