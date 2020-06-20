package com.example.zhouyuhong.musicdance;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import me.jessyan.autosize.internal.CustomAdapt;
import java.util.List;

public class SlideCardAdapter extends PagerAdapter implements CustomAdapt{
    private List<SlideCardModel> models;

    private LayoutInflater layoutInflater;
    private Context context;
    private String section;

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 900;
    }

    public SlideCardAdapter(List<SlideCardModel> models, Context context,String section) {
        this.models = models;
        this.context = context;
        this.section=section;

    }

    @Override
    public int getCount() {
        return models.size();
    }

    public List<SlideCardModel> getModels(){ return models; }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=LayoutInflater.from(context);
        final View view=layoutInflater.inflate(R.layout.item,container,false);
        ImageView imageView;
        TextView title,author,id;
        Integer color;
        CardView cardView;

        imageView=view.findViewById(R.id.image);
        title=view.findViewById(R.id.title);
        author=view.findViewById(R.id.author);
        cardView=view.findViewById(R.id.cardView);
        id=view.findViewById(R.id.id);
        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
        author.setText(models.get(position).getAuthor());
        id.setText(String.valueOf(models.get(position).getId()));
        cardView.setCardBackgroundColor(models.get(position).getColor());
        final String resId=String.valueOf(models.get(position).getId());

        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String[] levelItems={"简单","中等","困难"};
                final String[] items1 = { "音符","节奏","和弦"};

                System.out.print("section "+section);
                if(section.equals("learning")){
                    //************************************************************//
                    //new version

                    Intent intent=new Intent(context,LevelMap.class);
                    intent.putExtra("section",section);
                    intent.putExtra("resId",resId);
                    context.startActivity(intent);

                    //************************************************************//
                    //old version
/*
                    final int[] learingPart = new int[1];
                    learingPart[0]=0;
                    // Get the layout inflater
                    // Inflate and set the layout for the dialog
                    // Pass null as the parent view because its going in the dialog layout
                    View view=inflater.inflate(R.layout.alert_learning, null);

                    RadioGroup rgroup1=(RadioGroup)view.findViewById(R.id.radiogroup1);

                    //通过setOnCheckedChangeListener( )来响应按钮的事
                    rgroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                        @Override
                        public void onCheckedChanged(RadioGroup rg,int checkedId)
                        {
                            switch(checkedId){
                                case R.id.rb1:learingPart[0]=0;break;
                                case R.id.rb2:learingPart[0]=1;break;
                                case R.id.rb3:learingPart[0]=2;break;
                            }
                        }
                    });

                    RadioGroup rgroup2=(RadioGroup)view.findViewById(R.id.radiogroup2);

                    //通过setOnCheckedChangeListener( )来响应按钮的事
                    rgroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                        @Override
                        public void onCheckedChanged(RadioGroup rg,int checkedId)
                        {
                            switch(checkedId){
                                case R.id.easy:level[0]=0;break;
                                case R.id.medium:level[0]=1;break;
                                case R.id.hard:level[0]=2;break;
                            }
                        }
                    });

                    builder.setView(view);

                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }});


                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            if(learingPart[0]!=-1 && level[0]!=-1){

                                Intent intent=new Intent(context,LearnNotes.class);

                                intent.putExtra("section", section);//String
                                intent.putExtra("resId", resId); //int
                                intent.putExtra("learningPart", String.valueOf(learingPart[0])); //int
                                intent.putExtra("level", String.valueOf(level[0])); //int
                                context.startActivity(intent);
                            }
                        }
                    });
*/
                }else{

                    //old one, Might Change
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("选择细分模块");
                    builder.setCancelable(true);
                    LayoutInflater inflater = LayoutInflater.from(context);

                    final int[] level = new int[1];
                    level[0]=0;

                    View view=inflater.inflate(R.layout.alert_playingcomposing, null);

                    RadioGroup rgroup2=(RadioGroup)view.findViewById(R.id.radiogroup2);

                    //通过setOnCheckedChangeListener( )来响应按钮的事
                    rgroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
                        @Override
                        public void onCheckedChanged(RadioGroup rg,int checkedId)
                        {
                            switch(checkedId){
                                case R.id.easy:level[0]=0;break;
                                case R.id.medium:level[0]=1;break;
                                case R.id.hard:level[0]=2;break;
                            }
                        }
                    });

                    builder.setView(view);


                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                        }});

//                    builder.setSingleChoiceItems(levelItems, 0, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            level[0] = which;
//                        }
//                    });

                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            if(level[0]!=-1){

                                Intent intent=new Intent(context,LearnNotes.class);

                                intent.putExtra("section", section);//String
                                intent.putExtra("resId", resId); //int
                                intent.putExtra("level", String.valueOf(level[0])); //int
                                intent.putExtra("learingPart", "None"); //int
                                context.startActivity(intent);

                            }
                        }
                    });

                    AlertDialog alert= builder.create();
                    alert.show();

                }

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

    @Override
    public float getPageWidth(int position){
        return (0.3f);
    }
}
