package com.example.zhouyuhong.musicdance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LearningEnd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
