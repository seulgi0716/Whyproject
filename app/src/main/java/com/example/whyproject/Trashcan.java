package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Trashcan extends AppCompatActivity {

    private EditText writetrash;
    private TextView papertv;
    private ImageButton throwbtn;
    private ImageView trashcan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trashcan);
        setTitle("괜찮아 다 말해봐!");

        writetrash = findViewById(R.id.writetrash);
        papertv = findViewById(R.id.papertv);
        throwbtn = findViewById(R.id.throwbtn);
        trashcan = findViewById(R.id.trashcan);
        papertv.setVisibility(View.INVISIBLE);

        writetrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trashcan.setImageResource(R.drawable.emptycan);
            }
        });

        throwbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(writetrash.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "버릴 것이 없습니다!!", Toast.LENGTH_SHORT).show();
                } else {
                    papertv.setVisibility(View.VISIBLE);

                    String writepaper = writetrash.getText().toString();
                    papertv.setText(writepaper);

                    writetrash.setText("");

                    papertv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.scale);
                            papertv.startAnimation(animation);
                            animation.setAnimationListener(new Animation.AnimationListener(){
                                public void onAnimationEnd(Animation animation){
                                    trashcan.setImageResource(R.drawable.can);
                                    papertv.setVisibility(View.INVISIBLE);
                                }
                                public void onAnimationStart(Animation animation){;}
                                public void onAnimationRepeat(Animation animation){;}
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}

