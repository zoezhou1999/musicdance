package com.example.zhouyuhong.musicdance;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LearnNotesCopy2 extends AppCompatActivity {
    private RelativeLayout constraintLayout;

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
    private HashMap<Integer,Integer> res_dict=new HashMap<>();

    private List<Integer> buttons; //id
    private int[] notes={
            R.drawable.note_dkblue,
            R.drawable.note_dkbrown,
            R.drawable.note_dkpurple,
            R.drawable.note_gray,
            R.drawable.note_green,
            R.drawable.note_lgpurple,
            R.drawable.note_red,
            R.drawable.note_yellow,
            R.drawable.note_white
    };
    private int[] texts={1,2,3,4,5,6,7,8,9};

    private List<Integer> poleAnchors=new ArrayList<>();

    private int coin=0;

    private TextView play_coin_num;
    private NumberProgressBar numberProgressBar;

    private int remain_note_num;

    String section;
    int resId,learingPart,level;
    int[] keys={1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7};
    boolean[] hasDots={true,true,true,true,true,true,true,false,false,false,false,false,false,false,true,true,true,true,true,true,true};
    boolean[] isTop={false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true};
    int[] dotsNum={1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1};
    int [] finalPoles={0,1,0,1,0,0,1,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,1,0};

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_notes);

        remain_note_num=notes.length;

        res_dict.put(0,R.raw.fairy_tale);
        res_dict.put(1,R.raw.meet);
        res_dict.put(2,R.raw.sky_city);
        res_dict.put(3,R.raw.big_fish);
        res_dict.put(4,R.raw.if_i_stay);
        res_dict.put(5,R.raw.spirited_away);

        section=getIntent().getStringExtra("section");
        resId= Integer.parseInt(getIntent().getStringExtra("resId"));

        String tmpLearingPart=getIntent().getStringExtra("learningPart");
        System.out.print("tmpLearingPart:"+tmpLearingPart);

        if(tmpLearingPart.equals("None")){
            learingPart=-1;
        }else{
            learingPart=Integer.parseInt(tmpLearingPart);
        }

        level= Integer.parseInt(getIntent().getStringExtra("level"));

        //initialize playcontent
        TextView playcontent=findViewById(R.id.playcontent);
        if(section.equals("learning")){
            if(learingPart==0){
                //音符
                playcontent.setText("音符");

            }else if (learingPart==1){
                //节奏
                playcontent.setText("节奏");

            }else{
                //和弦
                playcontent.setText("和弦");
            }
        }

        //initialize NumberProgressBar
        numberProgressBar=findViewById(R.id.number_progress_bar);
        numberProgressBar.setProgress(0);
        play_coin_num=findViewById(R.id.play_coin_num);

        WindowManager windowManager=(WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width=displayMetrics.widthPixels;
        height=displayMetrics.heightPixels;

        int [] poles={0,0,1,0,1,0,0,1,0,1,0,1,0,0,1};

        final ImageView pole1=findViewById(R.id.tmppole1); //0
        final ImageView pole2=findViewById(R.id.tmppole2); //1
        ImageView pole3=findViewById(R.id.tmppole3); //0
        ImageView pole4=findViewById(R.id.tmppole4); //0

        final int[] whiteWidth = new int[1];// = {pole1.getMeasuredWidth()};//pole1.getDrawable().getIntrinsicWidth();//pole1.getWidth(); ///image
        final int[] whiteHeight = new int[1];// = {pole1.getMeasuredHeight()};//pole1.getDrawable().getIntrinsicHeight();//.getHeight(); //image
        final int[] brownWidth = new int[1];//=pole2.getMeasuredWidth();//pole2.getDrawable().getIntrinsicWidth(); //image
        final int[] brownHeight = new int[1];//=pole2.getMeasuredHeight();//pole2.getDrawable().getIntrinsicHeight(); //iamge

        whiteHeight[0] = (int)(374*2);//142;pole1.getMeasuredHeight();
        whiteWidth[0] = (int)(142*1.5);//pole1.getMeasuredWidth();
        brownHeight[0] = (int)(310*2);//pole2.getMeasuredHeight();
        brownWidth[0] = (int)(142*1.5);//pole2.getMeasuredWidth();

        loadMusic();

        RelativeLayout.LayoutParams pole4LeftParams = (RelativeLayout.LayoutParams) pole4.getLayoutParams();
        int pole4leftMargin=pole4LeftParams.leftMargin;
        RelativeLayout.LayoutParams pole3LeftParams = (RelativeLayout.LayoutParams) pole3.getLayoutParams();
        int pole3leftMargin=pole3LeftParams.leftMargin;
        int whiteWidthOffset=pole4leftMargin-pole3leftMargin; //Real width

        RelativeLayout.LayoutParams pole2LeftParams = (RelativeLayout.LayoutParams) pole2.getLayoutParams();
        int pole2leftMargin=pole2LeftParams.leftMargin;
        RelativeLayout.LayoutParams pole1LeftParams = (RelativeLayout.LayoutParams) pole1.getLayoutParams();
        int pole1leftMargin=pole1LeftParams.leftMargin;
        int remainWhiteMargin= whiteWidth[0] -whiteWidthOffset;

        int brownWidthOffset=pole3leftMargin-pole2leftMargin; //real width

        int totalWidth=0;
        for(int i=0;i<finalPoles.length;i++)
            if (finalPoles[i] == 0) {
                totalWidth += whiteWidth[0];
            } else {
                totalWidth += brownWidth[0];
            }
        float scale=1;

        scaledWhiteWidth= (int) (whiteWidth[0] *scale);
        scaledWhiteHeight=(int)(whiteHeight[0] *scale);
        scaledBrownWidth=(int)(brownWidth[0] *scale);

        scaledBrownHeight=(int)(brownHeight[0] *scale);

        scaledRemainWhiteMargin=(int)(remainWhiteMargin*scale);
        scaledBrownWidthOffset=(int)(brownWidthOffset*scale);//real height
        scaledWhiteWidthOffset=(int)(whiteWidthOffset*scale);//Real width
//        int scaledRemainWhiteMargin=(int)(remainWhiteMargin*scale);

//        int finalTotalWidth=0; //real width
//
//        for(int i=0;i<finalPoles.length;i++){
//            if(finalPoles[i]==0){
//                finalTotalWidth+=scaledWhiteWidthOffset;
//            }else{
//                finalTotalWidth+=scaledBrownWidthOffset;
//            }
//        }
//
//        int remainWidth=width-finalTotalWidth;

        constraintLayout = (RelativeLayout) findViewById(R.id.keyboard);


        int finalLeftMargin=0;


        //Create Buttons
        buttons=new ArrayList<>();
        int idIndex=0;
        int tempI=0;

        for(int i=0;i<finalPoles.length;i++){

            if(finalPoles[i]==0){
                poleAnchors.add(finalLeftMargin);
                createButton(idIndex,tempI,0,finalLeftMargin);

                dict.put("btn"+Integer.toString(i),idIndex);
                finalLeftMargin+=scaledWhiteWidthOffset;
                idIndex+=1;
                tempI+=1;
            }else{
//                poleAnchors.add(finalLeftMargin);
                createButton(idIndex,tempI,1,finalLeftMargin);

                dict.put("btn"+Integer.toString(i),idIndex);
                finalLeftMargin+=scaledBrownWidthOffset;
                idIndex+=1;
            }

        }
        /*
        *
        * int[] keys={1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7};
    boolean[] hasDots={true,true,true,true,true,true,true,false,false,false,false,false,false,false,true,true,true,true,true,};
    boolean[] isTop={false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true};
    int[] dotsNum={1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1};
    int [] finalPoles={0,1,0,1,0,0,1,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,1,0};
*/
        //Create Texts
        tempI=0;
        for(int i=0;i<finalPoles.length;i++){
            if(finalPoles[i]==0){
                //createText(idIndex,keynum,isTop,hasDots,position:poleAnchors.get(tempI));
                createText(idIndex,keys[tempI],isTop[tempI],hasDots[tempI],poleAnchors.get(tempI));
                dict.put("text"+Integer.toString(i),idIndex);
                idIndex+=1;
                tempI+=1;
            }
        }

        //Create Notes
        tempI=0;
        for(int i=0;i<finalPoles.length;i++){
            if(finalPoles[i]==0){
                int noteIndex=(int)(Math.random() * (8 - 0 + 1) + 0);
                createNote(idIndex,noteIndex,poleAnchors.get(tempI));
                dict.put("note"+Integer.toString(tempI),idIndex);
                idIndex+=1;
                tempI+=1;
            }
        }


        //Create NPC
        ImageView npc=new ImageView(this);
        npc.setId(idIndex);
        dict.put("npc",idIndex);
        npc.setImageResource(R.drawable.littleblack);
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(250,250);
        npc.setLayoutParams(parms);
        npc.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //check
        npc.setX((float)(poleAnchors.get(0) -0.5*scaledWhiteWidth+75));
        npc.setY(height-scaledWhiteHeight-100);
        constraintLayout.addView(npc);

        idIndex+=1;

    }

    private void loadMusic(){
        musicSoundPool = new SoundPool(9, AudioManager.STREAM_SYSTEM, 5);
        for(int i=0;i<9;i++){
            //soundID.put(i, musicSoundPool.load(this, R.raw.sample1, 1));
//            soundID.put(2 , musicSoundPool.load(this, , 1));  //需要捕获IO异常
//            soundID.put(3, musicSoundPool.load(this, R.raw.duang, 1));
//            soundID.put(4, musicSoundPool.load(this, R.raw.duang, 1));
//            soundID.put(5, musicSoundPool.load(this, R.raw.duang, 1));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    //createText(idIndex,keynum,isTop,hasDots,position:poleAnchors.get(tempI));
    private void createText(int id, int key,boolean isTop, boolean hasDots, int leftMargin){
        //key
        TextView textView1=new TextView(this);
        textView1.setId(id);
        textView1.setText(Integer.toString(key));
        textView1.setTextSize(70);
        textView1.setTextColor(getResources().getColor(R.color.color4));
        textView1.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);

        RelativeLayout.LayoutParams parms1 = new RelativeLayout.LayoutParams(150,150);

        textView1.setX((int)(leftMargin+0.5*scaledWhiteWidth-75));
        textView1.setY(height-200);
        textView1.setLayoutParams(parms1);
        constraintLayout.addView(textView1);

        //dots
        TextView textView2=new TextView(this);
//        textView2.setId(id);
        if(hasDots){
            if(isTop){
                textView2.setText(".");
                textView2.setTextSize(70);
                textView2.setTextColor(getResources().getColor(R.color.color4));
                textView2.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);

                RelativeLayout.LayoutParams parms2 = new RelativeLayout.LayoutParams(150,150);

                textView2.setX((int)(leftMargin+0.5*scaledWhiteWidth-75));
                textView2.setY(height-370);
                textView2.setLayoutParams(parms2);
                constraintLayout.addView(textView2);

            }else{
                textView2.setText(".");
                textView2.setTextSize(70);
                textView2.setTextColor(getResources().getColor(R.color.color4));
                textView2.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);

                RelativeLayout.LayoutParams parms2 = new RelativeLayout.LayoutParams(150,150);

                textView2.setX((int)(leftMargin+0.5*scaledWhiteWidth-75));
                textView2.setY(height-30);
                textView2.setLayoutParams(parms2);
                constraintLayout.addView(textView2);
            }
        }

    }

    private void createNote(int id, int typeId, int leftMargin){
        ImageView note=new ImageView(this);
        note.setId(id);
        note.setImageResource(notes[typeId]);
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(150,150);
        note.setLayoutParams(parms);
        note.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //check
        note.setX((float)(leftMargin+0.5*scaledWhiteWidth-100));
        note.setY(180);

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
        final ImageView button = new ImageView(this);

        button.setId(id);

//        ConstraintLayout btnConstraint=new ConstraintLayout(this);
//
//
//        ConstraintLayout.LayoutParams btnParams = new ConstraintLayout.LayoutParams(
//                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        if(type==0){

            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(scaledWhiteWidth,scaledWhiteHeight);
            button.setLayoutParams(parms);
            button.setBackgroundResource(R.drawable.white_pole);
//            button.setBackgroundColor(getResources().getColor(R.color.color3));
//            button.setImageResource(R.drawable.white_pole);
            button.setScaleType(ImageView.ScaleType.FIT_CENTER);
            button.setX(leftMargin);
            button.setY(height-scaledWhiteHeight);
//            button.setWidth(scaledWhiteWidth);
//            button.setHeight(scaledWhiteHeight);
//            button.setBackgroundColor(getResources().getColor(R.color.transparent));

//            Drawable drawable = getResources().getDrawable(R.drawable.white_pole);
//            drawable = new ScaleDrawable(drawable, 0, scaledWhiteWidth, scaledWhiteHeight).getDrawable();
//            drawable.setBounds(0, 0, scaledWhi teWidth, scaledWhiteHeight);
//            button.setBackground(getResources().getDrawable(R.drawable.white_pole));

            button.setOnClickListener(new View.OnClickListener() {
                int Id=typeId;
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //musicSoundPool.play(soundID.get(Id), 1, 1, 0, 0, 1);
                    final ImageView npc=findViewById(dict.get("npc")); //get npc
                    final ImageView note=findViewById(dict.get("note"+Integer.toString(Id)));
//                    Path path=new Path();
//                    path.arcTo(npc.getX(),npc.getY(), (float) (poleAnchors.get(Id)-0.5*scaledWhiteWidth+75),height-scaledWhiteHeight-100,60f,120f,true);
//
//                    ObjectAnimator arcAnimator=ObjectAnimator.ofFloat(npc,View.X,View.Y,path);
//                    arcAnimator.setDuration(2000);
                    final float x1=npc.getX();
                    ArcTranslateAnimation upAnimation = new ArcTranslateAnimation(0,(float) ((poleAnchors.get(Id)-0.5*scaledWhiteWidth+75-x1)/2), 0,-200);
                    upAnimation.setDuration(500);
                    upAnimation.setFillAfter(true);

                    upAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            TranslateAnimation dropAnimation = new TranslateAnimation(0,0, 0,(float)(height-scaledWhiteHeight-100-note.getY()));

                            dropAnimation.setFillAfter(true);

                            dropAnimation.setDuration(1000);
                            dropAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    note.clearAnimation();
                                    note.setY((float)(height-scaledWhiteHeight-100));
                                    note.setImageDrawable(null);

                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });

                            note.startAnimation(dropAnimation);

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            npc.clearAnimation();
                            npc.setX((float)((poleAnchors.get(Id) -0.5*scaledWhiteWidth+75+x1)/2));
                            npc.setY(height-scaledWhiteHeight-300);

                            ArcTranslateAnimation downAnimation = new ArcTranslateAnimation(0,(float) ((poleAnchors.get(Id)-0.5*scaledWhiteWidth+75-x1)/2), 0,200);
                            downAnimation.setDuration(500);
                            downAnimation.setFillAfter(true);

                            downAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    npc.clearAnimation();
                                    npc.setX((float)((poleAnchors.get(Id) -0.5*scaledWhiteWidth+75)));
                                    npc.setY(height-scaledWhiteHeight-100);
                                    coin+=50;
                                    play_coin_num.setText(String.valueOf(coin));
                                    if(remain_note_num!=0){
                                        remain_note_num-=1;

                                        numberProgressBar.setProgress(100*(notes.length-remain_note_num)/notes.length);
                                        if(remain_note_num==0){
                                            saveCoin();
                                            gameOver();
                                        }
                                    }


                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }

                            });
                            npc.startAnimation(downAnimation);

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }

                    });
                    npc.startAnimation(upAnimation);


//                    ArcTranslateAnimation downAnimation = new ArcTranslateAnimation(0,(float) ((poleAnchors.get(Id)-0.5*scaledWhiteWidth+75-npc.getX())/2), 0,-200);
//                    downAnimation.setDuration(500);
//                    downAnimation.setFillAfter(true);
//                    npc.startAnimation(upAnimation);
//                    npc.setX((float)(poleAnchors.get(Id) -0.5*scaledWhiteWidth+75));
//                    npc.setY(height-scaledWhiteHeight);


//                    npc.startAnimation(animation);

//
//                    ObjectAnimator directAnimator=ObjectAnimator.ofFloat(note,"translationX",(float) (poleAnchors.get(Id) - (0.5 * scaledWhiteWidth)+ 75));
//                    directAnimator.setDuration(2000);
//
//                    directAnimator.addListener(new AnimatorListenerAdapter() {
//                        public void onAnimationEnd(ObjectAnimator animation) {
//                            note.setImageDrawable(null);
//                        }
//                    });


//
//                    arcAnimator.start();
//                    directAnimator.start();

//                    ValueAnimator arcAnimator = ValueAnimator.ofFloat(0, 1); // values from 0 to 1
//                    arcAnimator.setDuration(2000); // 5 seconds duration from 0 to 1
//                    arcAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
//                    {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            float value = ((Float) (animation.getAnimatedValue()))
//                                    .floatValue();
//                            // Set translation of your view here. Position can be calculated
//                            // out of value. This code should move the view in a half circle.
//                            npc.setTranslationX((float)(150.0 * Math.sin(value*Math.PI)));
//                            npc.setTranslationY((float)(150.0 * Math.cos(value*Math.PI)));
////                            note.set()
//                        }
//                    });
//
//                    arcAnimator.start();


                }}
            );

        }else{

            RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(scaledBrownWidth,scaledBrownHeight);
            button.setLayoutParams(parms);
//            button.setBackgroundColor(getResources().getColor(R.color.color3));
            button.setBackgroundResource(R.drawable.brown_pole);
//            button.setImageResource(R.drawable.brown_pole);
            button.setScaleType(ImageView.ScaleType.FIT_CENTER);
            button.setX(leftMargin);
            button.setY(height-scaledBrownHeight);
//            button.setWidth(scaledBrownWidth);
//            button.setHeight(scaledBrownHeight);
//            button.setBackgroundColor(R.color.transparent);

//            Drawable drawable = getResources().getDrawable(R.drawable.brown_pole);
//            drawable = new ScaleDrawable(drawable, 0, scaledBrownWidth, scaledBrownHeight).getDrawable();
//            drawable.setBounds(0, 0, scaledBrownWidth, scaledBrownHeight);
//            button.setBackground(drawable);
//            button.setBackgroundResource(R.drawable.brown_pole);
//            button.setBackground(getResources().getDrawable(R.drawable.brown_pole));
        }

//            button.setText("Hello");
//
//        btnParams.setMarginStart(leftMargin);
//
//        ConstraintSet constraintSet = new ConstraintSet();
//
//        constraintSet.clone(btnConstraint);
//
//        constraintSet.connect(button.getId(),ConstraintSet.BOTTOM,R.id.root,ConstraintSet.BOTTOM,0);
//        constraintSet.applyTo(btnConstraint);
//
//        btnConstraint.setLayoutParams(btnParams);
//
//        button.setLayoutParams(btnParams);




        constraintLayout.addView(button);

    }

    public void saveCoin(){
        SharedPreferences sharedpreferences = getSharedPreferences("myPreference",
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        int preCoin=Integer.parseInt(sharedpreferences.getString("currentCoin",""));

        editor.putString("currentCoin", String.valueOf(preCoin+coin));
        editor.commit();
    }

    public void gameOver(){
        //
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_learning_end);

        TextView nextGame=dialog.findViewById(R.id.next_game);
        TextView returnMainMenu=dialog.findViewById(R.id.return_main_menu);
        TextView onceAgain=dialog.findViewById(R.id.once_again);


        nextGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(v.getContext(),SelectingMenu.class);
                startActivity(intent);
            }
        });

        returnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(v.getContext(),MainMenu.class);
                startActivity(intent);
            }
        });

        onceAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent intent=new Intent(v.getContext(), LearnNotesCopy2.class);

                intent.putExtra("section", section);//String
                intent.putExtra("resId", String.valueOf(resId)); //int
                intent.putExtra("level", String.valueOf(level)); //int
                if(learingPart==-1){
                    intent.putExtra("learingPart", "None"); //int
                }else{
                    intent.putExtra("learingPart", String.valueOf(learingPart)); //int
                }

                startActivity(intent);

            }
        });
//
//        public void learnAgain(View view){
//
//            Intent intent=new Intent(this,MainMenu.class);
//            startActivity(intent);
//        }
//        public void taskChallenge(View view){
//            Intent intent=new Intent(this,MainMenu.class);
//            startActivity(intent);
//        }
//        public void returnMainMenu(View view){
//            Intent intent=new Intent(this,MainMenu.class);
//            startActivity(intent);
//        }

        dialog.show();

    }


    public void enterStopPage(final View view){
        //pause the game
        //
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.activity_learning_stop);

        TextView giveUpGame=dialog.findViewById(R.id.giveupgame);

        giveUpGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent;
                intent = new Intent(view.getContext(),SelectingMenu.class);
                startActivity(intent);
            }
        });

        TextView continueGame=dialog.findViewById(R.id.continuegame);
        continueGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
