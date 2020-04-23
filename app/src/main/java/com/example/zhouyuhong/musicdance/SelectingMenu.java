package com.example.zhouyuhong.musicdance;

import android.animation.ArgbEvaluator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SelectingMenu extends AppCompatActivity {
    ViewPager viewPager;
    SlideCardAdapter adapter;
    List<SlideCardModel> models;
    Integer[] colors=null;
    ArgbEvaluator argbEvaluator=new ArgbEvaluator();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_menu);
        models = new ArrayList<>();
        models.add(new SlideCardModel(0,R.drawable.coin,"coin","zoe",getResources().getColor(R.color.black50)));

        models.add(new SlideCardModel(1,R.drawable.character,"character","zoe",getResources().getColor(R.color.black50)));

        models.add(new SlideCardModel(2,R.drawable.littleblack,"littleblack","zoe",getResources().getColor(R.color.black50)));

        models.add(new SlideCardModel(3,R.drawable.coin,"coin","zoe",getResources().getColor(R.color.black50)));

        models.add(new SlideCardModel(4,R.drawable.character,"character","zoe",getResources().getColor(R.color.black50)));

        models.add(new SlideCardModel(5,R.drawable.littleblack,"littleblack","zoe",getResources().getColor(R.color.black50)));


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

}
