package com.example.zhouyuhong.musicdance;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.autosize.internal.CustomAdapt;

public class SelectingMenu extends AppCompatActivity implements CustomAdapt {
    ViewPager viewPager;
    SlideCardAdapter adapter;
    List<SlideCardModel> models;
    Integer[] colors=null;

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
        setContentView(R.layout.activity_selecting_menu);
        models = new ArrayList<>();
        models.add(new SlideCardModel(0,R.drawable.fairy_tail,"童话","光良",getResources().getColor(R.color.black50)));

        models.add(new SlideCardModel(1,R.drawable.meet,"遇见","孙燕姿",getResources().getColor(R.color.black50)));

        models.add(new SlideCardModel(2,R.drawable.sky_city,"伴随着你","久石让",getResources().getColor(R.color.black50)));

        models.add(new SlideCardModel(3,R.drawable.big_fish,"大鱼","尹约/钱雷",getResources().getColor(R.color.black50)));

        models.add(new SlideCardModel(4,R.drawable.if_i_stay,"夜空中最亮的星","逃跑计划",getResources().getColor(R.color.black50)));

        models.add(new SlideCardModel(5,R.drawable.spirted_away,"千与千寻","久石让",getResources().getColor(R.color.black50)));


        String section=getIntent().getStringExtra("section");
        adapter=new SlideCardAdapter(models,this,section);
        viewPager=findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,20,130,20);

        final Integer[] colors_temp= {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
        };

        colors=colors_temp;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
//                if (i<(adapter.getCount()-1) && i <(colors.length-1)){
//                    viewPager.setBackgroundColor(
//                            (Integer) argbEvaluator.evaluate(i,colors[i],colors[i+1])
//                    );
//                }else{
//                    viewPager.setBackgroundColor(colors[colors.length-1]);
//                }
            }

            @Override
            public void onPageSelected(int i) {
//                SlideCardAdapter viewPagerAdapter = viewPager.getAdapter();
//
//                models=viewPagerAdapter.getModels();
//                models[i].
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    public void returnMainMenu(View view){

        Intent intent=new Intent(this,MainMenu.class);
        startActivity(intent);

    }

}
