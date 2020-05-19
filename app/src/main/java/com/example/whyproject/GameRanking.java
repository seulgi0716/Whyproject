package com.example.whyproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class GameRanking extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_ranking);
        setTitle("명예의 전당 TOP5!");






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
