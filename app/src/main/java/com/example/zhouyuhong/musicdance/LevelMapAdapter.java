package com.example.zhouyuhong.musicdance;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class LevelMapAdapter extends PagerAdapter {
    private List<LevelMapModel> models;

    private LayoutInflater layoutInflater;
    private Context context;
    private String section;
    private int resId;


    public LevelMapAdapter(List<LevelMapModel> models, Context context, String section, int resId) {
        this.models = models;
        this.context = context;
        this.section=section;
        this.resId=resId;

    }

    @Override
    public int getCount() {
        return models.size();
    }

    public List<LevelMapModel> getModels(){ return models; }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=LayoutInflater.from(context);
        final View view=layoutInflater.inflate(R.layout.level_map_item,container,false);
        TextView level,text1,text2;
        ImageView close;

        text1=view.findViewById(R.id.text1);
        text2=view.findViewById(R.id.text2);
        level=view.findViewById(R.id.level);
        close=view.findViewById(R.id.close);

        final int levelId=models.get(position).getId();
        switch(levelId){
            case 0:
                level.setText("初级");
                break;
            case 1:
                level.setText("中级");
                break;
            case 2:
                level.setText("高级");
                break;
        }
        //音符
        text1.setText("音符");
        text1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //0
                Intent intent=new Intent(context,LearnNotes.class);

                intent.putExtra("section", section);//String
                intent.putExtra("resId", String.valueOf(resId)); //int
                intent.putExtra("level", String.valueOf(levelId)); //int
                intent.putExtra("learningPart", "0"); //int
                context.startActivity(intent);

            }
        });

        //节奏
        text2.setText("节奏");
        text2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //1
                Intent intent=new Intent(context,LearnNotes.class);

                intent.putExtra("section", section);//String
                intent.putExtra("resId", String.valueOf(resId)); //int
                intent.putExtra("level", String.valueOf(levelId)); //int
                intent.putExtra("learningPart", "1"); //int
                context.startActivity(intent);

            }
        });



        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SelectingMenu.class);
                intent.putExtra("section",section);
                v.getContext().startActivity(intent);

            }
        });

        container.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View)object);
    }

}
