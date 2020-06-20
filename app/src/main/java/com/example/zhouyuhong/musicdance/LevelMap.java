package com.example.zhouyuhong.musicdance;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class LevelMap extends AppCompatActivity implements CustomAdapt {

    ViewPager viewPager;
    LevelMapAdapter adapter;
    List<LevelMapModel> models;

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 900;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_map);

        Intent intent=getIntent();
        final String section=intent.getStringExtra("section");
        String resId=intent.getStringExtra("resId");


        models = new ArrayList<>();
        //int id,String level
        models.add(new LevelMapModel(0));

        models.add(new LevelMapModel(1));

        models.add(new LevelMapModel(2));

        //List<LevelMapModel> models, Context context, String section, int resId, Dialog dialog
        adapter=new LevelMapAdapter(models,this,section,Integer.parseInt(resId));
        viewPager=findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(0,0,0,0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
}
