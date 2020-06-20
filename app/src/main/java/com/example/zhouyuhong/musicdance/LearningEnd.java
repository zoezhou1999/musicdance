package com.example.zhouyuhong.musicdance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.jessyan.autosize.internal.CustomAdapt;

public class LearningEnd extends AppCompatActivity implements CustomAdapt {
    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 900;
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        setContentView(R.layout.activity_learning_end);
    }
    public void learnAgain(View view){

        Intent intent=new Intent(this,MainMenu.class);
        startActivity(intent);
    }
    public void taskChallenge(View view){

        Intent intent=new Intent(this,MainMenu.class);
        startActivity(intent);
    }
    public void returnMainMenu(View view){

        Intent intent=new Intent(this,MainMenu.class);
        startActivity(intent);
    }
}
