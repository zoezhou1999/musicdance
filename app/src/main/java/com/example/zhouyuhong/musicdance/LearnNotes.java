package com.example.zhouyuhong.musicdance;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LearnNotes extends AppCompatActivity {
    private ConstraintLayout constraintLayout;

    private int width;
    private int height;

    private int scaledWhiteWidth;
    private int scaledWhiteHeight;
    private int scaledBrownWidth;

    private int scaledBrownHeight;

    private int scaledRemainWhiteMargin;
    private int scaledBrownWidthOffset;
    private int scaledWhiteWidthOffset;
    private SoundPool musicSoundPool;

    private HashMap<String,Integer> dict=new HashMap<>();
    private HashMap<Integer,Integer> soundID=new HashMap<>();

    private List<Integer> buttons; //id
    private int[] notes={
            R.drawable.ic_music_note_black_400dp,
            R.drawable.ic_music_note_blue_400dp,
            R.drawable.ic_music_note_green_400dp,
            R.drawable.ic_music_note_orange_400dp,
            R.drawable.ic_music_note_pink_400dp,
            R.drawable.ic_music_note_purple_400dp,
            R.drawable.ic_music_note_white_400dp
    };
    private int[] texts={1,2,3,4,5,6,7,8,9};

    private List<Integer> poleAnchors=new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_notes);


        WindowManager windowManager=(WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width=displayMetrics.widthPixels;
        height=displayMetrics.heightPixels;

        int [] poles={0,0,1,0,1,0,0,1,0,1,0,1,0,0,1};

        ImageView pole1=findViewById(R.id.pole1); //0
        ImageView pole2=findViewById(R.id.pole2); //1
        ImageView pole3=findViewById(R.id.pole3); //0
        ImageView pole4=findViewById(R.id.pole4); //0

        int whiteWidth=pole1.getWidth(); ///image
        int whiteHeight=pole1.getHeight(); //image
        int brownWidth=pole1.getWidth(); //image
        int brownHeight=pole1.getHeight(); //iamge
        System.out.print("whiteWidth "+Integer.toString(whiteWidth));
        System.out.print("whiteHeight "+Integer.toString(whiteHeight));
        System.out.print("brownWidth "+Integer.toString(brownWidth));
        System.out.print("brownHeight "+Integer.toString(brownHeight));
        loadMusic();

        ConstraintLayout.LayoutParams pole4LeftParams = (ConstraintLayout.LayoutParams) pole4.getLayoutParams();
        int pole4leftMargin=pole4LeftParams.leftMargin;
        ConstraintLayout.LayoutParams pole3LeftParams = (ConstraintLayout.LayoutParams) pole3.getLayoutParams();
        int pole3leftMargin=pole3LeftParams.leftMargin;
        int whiteWidthOffset=pole4leftMargin-pole3leftMargin; //Real width

        ConstraintLayout.LayoutParams pole2LeftParams = (ConstraintLayout.LayoutParams) pole2.getLayoutParams();
        int pole2leftMargin=pole2LeftParams.leftMargin;
        ConstraintLayout.LayoutParams pole1LeftParams = (ConstraintLayout.LayoutParams) pole1.getLayoutParams();
        int pole1leftMargin=pole1LeftParams.leftMargin;
        int remainWhiteMargin=pole1leftMargin-whiteWidthOffset;

        int brownWidthOffset=pole3leftMargin-pole2leftMargin; //real width

        int totalWidth=0;
        for(int i=0;i<poles.length;i++){
            if(poles[i]==0){
                totalWidth+=whiteWidth;
            }else{
                totalWidth+=brownWidth;
            }
        }
        float scale;
        if(totalWidth>0){
            scale=width/totalWidth;
        }else{
            scale=1;
        }


        scaledWhiteWidth= (int) (whiteWidth*scale);
        scaledWhiteHeight=(int)(whiteHeight*scale);
        scaledBrownWidth=(int)(brownWidth*scale);

        scaledBrownHeight=(int)(brownHeight*scale);

        scaledRemainWhiteMargin=(int)(remainWhiteMargin*scale);
        scaledBrownWidthOffset=(int)(brownWidthOffset*scale);//real height
        scaledWhiteWidthOffset=(int)(whiteWidthOffset*scale);//Real width
//        int scaledRemainWhiteMargin=(int)(remainWhiteMargin*scale);

        int finalTotalWidth=0; //real width

        for(int i=0;i<poles.length;i++){
            if(poles[i]==0){
                finalTotalWidth+=scaledWhiteWidthOffset;
            }else{
                finalTotalWidth+=scaledBrownWidthOffset;
            }
        }

        int remainWidth=width-finalTotalWidth;

        constraintLayout = (ConstraintLayout) findViewById(R.id.root);


        int finalLeftMargin=(int)(remainWidth/2)-scaledRemainWhiteMargin;


        //Create Buttons
        buttons=new ArrayList<>();
        int idIndex=0;
        int tempI=0;

        for(int i=0;i<poles.length;i++){

            if(poles[i]==0){
                poleAnchors.add(finalLeftMargin);
                //createButton(idIndex,tempI,0,finalLeftMargin);

                dict.put("btn"+Integer.toString(i),idIndex);
                finalLeftMargin+=scaledWhiteWidthOffset;
                idIndex+=1;
            }else{
                poleAnchors.add(finalLeftMargin);
                //createButton(idIndex,tempI,1,finalLeftMargin);

                dict.put("btn"+Integer.toString(i),idIndex);
                finalLeftMargin+=scaledBrownWidthOffset;
                idIndex+=1;
            }
            tempI+=1;
        }

        //Create Texts
        tempI=0;
        for(int i=0;i<poles.length;i++){
            if(poles[i]==1){
                createText(idIndex,tempI,poleAnchors.get(i));
                dict.put("text"+Integer.toString(i),idIndex);
                idIndex+=1;
                tempI+=1;
            }
        }

        //Create Notes
        tempI=0;
        for(int i=0;i<poles.length;i++){
            if(poles[i]==1){
                createNote(idIndex,tempI,poleAnchors.get(i));
                dict.put("note"+Integer.toString(i),idIndex);
                idIndex+=1;
                tempI+=1;
            }
        }

        //Create NPC
        ImageView npc=new ImageView(this);
        npc.setId(idIndex);
        dict.put("npc",idIndex);
        npc.setImageResource(R.drawable.littleblack);
        ConstraintLayout.LayoutParams parms = new ConstraintLayout.LayoutParams(150,150);
        npc.setLayoutParams(parms);
        npc.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //check
        npc.setX((float)(poleAnchors.get(0) -0.5*scaledWhiteWidth+75));
        npc.setY(height-scaledWhiteHeight-150);
        constraintLayout.addView(npc);

        idIndex+=1;

    }

    private void loadMusic(){
        musicSoundPool = new SoundPool(9, AudioManager.STREAM_SYSTEM, 5);
        for(int i=0;i<9;i++){
            soundID.put(i, musicSoundPool.load(this, R.raw.sample1, 1));
//            soundID.put(2 , musicSoundPool.load(this, , 1));  //需要捕获IO异常
//            soundID.put(3, musicSoundPool.load(this, R.raw.duang, 1));
//            soundID.put(4, musicSoundPool.load(this, R.raw.duang, 1));
//            soundID.put(5, musicSoundPool.load(this, R.raw.duang, 1));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createText(int id, int typeId, int leftMargin){
        TextView textView=new TextView(this);
        textView.setId(id);
        textView.setText(Integer.toString(texts[typeId]));
        textView.setTextSize(70);
        textView.setTextColor(getResources().getColor(R.color.black));
        textView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        ConstraintLayout.LayoutParams parms = new ConstraintLayout.LayoutParams(100,100);

        textView.setX((int)(leftMargin+0.5*scaledWhiteWidth-50));
        textView.setY(200);

//        parms.setMarginStart();
        textView.setLayoutParams(parms);

        constraintLayout.addView(textView);
    }

    private void createNote(int id, int typeId, int leftMargin){
        ImageView note=new ImageView(this);
        note.setId(id);
        note.setImageResource(notes[typeId]);
        ConstraintLayout.LayoutParams parms = new ConstraintLayout.LayoutParams(100,100);
        note.setLayoutParams(parms);
        note.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //check
        note.setX((float)(leftMargin+0.5*scaledWhiteWidth-50));
        note.setY(100);

        constraintLayout.addView(note);

    }
//
//    private createPole(int type, int leftMargin){
//        ImageView note=new ImageView(this);
//        note.setId(1);
//        note.setImageResource(notes[typeId]);
//        ConstraintLayout.LayoutParams parms = new ConstraintLayout.LayoutParams(100,100);
//        note.setLayoutParams(parms);
//        note.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        //check
//        note.setX((float)(leftMargin+0.5*scaledWhiteWidth-50));
//        note.setY(height-scaledWhiteHeight);
//
//        constraintLayout.addView(note);
//    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createButton(int id, final int typeId, int type, int leftMargin){
        Button button = new Button(this);
        button.setId(id);
        ConstraintLayout btnConstraint=new ConstraintLayout(this);


        ConstraintLayout.LayoutParams btnParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        if(type==0){
            button.setWidth(scaledWhiteWidth);
            button.setHeight(scaledWhiteHeight);
//            button.setBackgroundColor(getResources().getColor(R.color.transparent));

//            Drawable drawable = getResources().getDrawable(R.drawable.white_pole);
//            drawable = new ScaleDrawable(drawable, 0, scaledWhiteWidth, scaledWhiteHeight).getDrawable();
//            drawable.setBounds(0, 0, scaledWhi teWidth, scaledWhiteHeight);
            button.setBackground(getResources().getDrawable(R.drawable.white_pole));

        }else{
            button.setWidth(scaledBrownWidth);
            button.setHeight(scaledBrownHeight);
//            button.setBackgroundColor(R.color.transparent);

//            Drawable drawable = getResources().getDrawable(R.drawable.brown_pole);
//            drawable = new ScaleDrawable(drawable, 0, scaledBrownWidth, scaledBrownHeight).getDrawable();
//            drawable.setBounds(0, 0, scaledBrownWidth, scaledBrownHeight);
//            button.setBackground(drawable);
//            button.setBackgroundResource(R.drawable.brown_pole);
            button.setBackground(getResources().getDrawable(R.drawable.brown_pole));
        }

//            button.setText("Hello");

        btnParams.setMarginStart(leftMargin);

        ConstraintSet constraintSet = new ConstraintSet();

        constraintSet.clone(btnConstraint);

        constraintSet.connect(button.getId(),ConstraintSet.BOTTOM,R.id.root,ConstraintSet.BOTTOM,0);
        constraintSet.applyTo(btnConstraint);

        btnConstraint.setLayoutParams(btnParams);

        button.setLayoutParams(btnParams);


        button.setOnClickListener(new View.OnClickListener() {
            int Id=typeId;
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                musicSoundPool.play(soundID.get(Id), 1, 1, 0, 0, 1);
                ImageView npc=findViewById(dict.get("npc")); //get npc
                ImageView note=findViewById(dict.get("note"+Integer.toString(Id)));


            }

        });

        constraintLayout.addView(button);

    }

//    private void transition(){
//        TransitionManager.beginDelayedTransition(transitionsContainer,
//                new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(500));
//
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) button.getLayoutParams();
//        params.gravity = isReturnAnimation ? (Gravity.LEFT | Gravity.TOP) :
//                (Gravity.BOTTOM | Gravity.RIGHT);
//        button.setLayoutParams(params);
//    }
}
