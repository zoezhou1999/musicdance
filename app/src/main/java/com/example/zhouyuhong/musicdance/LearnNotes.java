package com.example.zhouyuhong.musicdance;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jessyan.autosize.internal.CustomAdapt;
import android.os.SystemClock;
public class LearnNotes extends AppCompatActivity implements CustomAdapt {
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

    private HashMap<Integer,Integer> buttonMap=new HashMap<>();
    private HashMap<Integer,Integer> noteMap=new HashMap<>();
    private HashMap<Integer,Integer> musicMap=new HashMap<>();
    private String[] instructions={"这里是更加细节的乐理科普～～～",
            "将调式中的音，从以主音开始到以主音结束，\n"+"由低到高（叫做上行），或者由高到低（叫做下行）\n"+"以阶梯状排列起来，就叫做音阶。",
            "音阶是调式的一种形态，全称叫做调式音阶，\n" +"音阶一定是调式，调式不一定是音阶。",
            "直白来讲：调式就是我们听歌曲所听到的旋律。\n"+"音阶也是旋律，它只是旋律的一种。",
            "自然七声音阶是应用最广的七声音阶，其音程组织是，\n"+"每个八度之内有5处全音，分成两个一串和3个一串，两串之间以半音隔开。",
            "五声音阶详称'不带半音的五声音阶'或'全音五声音阶'。"};

    private ImageView targetKey;


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
    private List<Integer> noteQueue=new ArrayList<>();
    private int[] texts={1,2,3,4,5,6,7,8,9};

    private List<Integer> poleAnchors=new ArrayList<>();

    private int coin=0;

    private TextView play_coin_num;
    private NumberProgressBar numberProgressBar;

    private int remain_note_num;
    MediaPlayer mediaPlayer;

    String section;
    int resId,learingPart,level;
    int[] keys={1,2,3,4,5,6,7,1,2,3,4,5,6,7,1,2,3,4,5,6,7};
    boolean[] hasDots={true,true,true,true,true,true,true,false,false,false,false,false,false,false,true,true,true,true,true,true,true};
    boolean[] isTop={false,false,false,false,false,false,false,false,false,false,false,false,false,false,true,true,true,true,true,true,true};
    int[] dotsNum={1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1};
    int [] finalPoles={0,1,0,1,0,0,1,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,1,0,0,1,0,1,0,0,1,0,1,0,1,0};
    int targetNotePos;
    private float[] lastTouchDownXY = new float[2];


    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 900;
    }

    @SuppressLint({"ResourceAsColor", "NewApi"})
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_notes);

        buttonMap.put(1,R.id.pole1_1);buttonMap.put(2,R.id.pole1_2);buttonMap.put(3,R.id.pole1_3);
        buttonMap.put(4,R.id.pole1_4);buttonMap.put(5,R.id.pole1_5);buttonMap.put(6,R.id.pole1_6);
        buttonMap.put(7,R.id.pole1_7);buttonMap.put(8,R.id.pole1_8);buttonMap.put(9,R.id.pole1_9);
        buttonMap.put(10,R.id.pole1_10);buttonMap.put(11,R.id.pole1_11);buttonMap.put(12,R.id.pole1_12);
        buttonMap.put(13,R.id.pole1_13);buttonMap.put(14,R.id.pole1_14);buttonMap.put(15,R.id.pole1_15);
        buttonMap.put(16,R.id.pole1_16);buttonMap.put(17,R.id.pole1_17);buttonMap.put(18,R.id.pole1_18);
        buttonMap.put(19,R.id.pole1_19);buttonMap.put(20,R.id.pole1_20);buttonMap.put(21,R.id.pole1_21);

        noteMap.put(1,R.id.note1);noteMap.put(2,R.id.note2);noteMap.put(3,R.id.note3);
        noteMap.put(4,R.id.note4);noteMap.put(5,R.id.note5);noteMap.put(6,R.id.note6);
        noteMap.put(7,R.id.note7);noteMap.put(8,R.id.note8);noteMap.put(9,R.id.note9);
        noteMap.put(10,R.id.note10);noteMap.put(11,R.id.note11);noteMap.put(12,R.id.note12);
        noteMap.put(13,R.id.note13);noteMap.put(14,R.id.note14);noteMap.put(15,R.id.note15);
        noteMap.put(16,R.id.note16);noteMap.put(17,R.id.note17);noteMap.put(18,R.id.note18);
        noteMap.put(19,R.id.note19);noteMap.put(20,R.id.note20);noteMap.put(21,R.id.note21);

        musicMap.put(1,R.raw.one);musicMap.put(2,R.raw.two);musicMap.put(3,R.raw.three);
        musicMap.put(4,R.raw.four);musicMap.put(5,R.raw.five);musicMap.put(6,R.raw.six);
        musicMap.put(7,R.raw.seven);musicMap.put(8,R.raw.eight);musicMap.put(9,R.raw.nine);
        musicMap.put(10,R.raw.ten);musicMap.put(11,R.raw.eleven);musicMap.put(12,R.raw.twelve);
        musicMap.put(13,R.raw.thirteen);musicMap.put(14,R.raw.fourteen);musicMap.put(15,R.raw.fifteen);
        musicMap.put(16,R.raw.sixteen);musicMap.put(17,R.raw.seventeen);musicMap.put(18,R.raw.eighteen);
        musicMap.put(19,R.raw.ninteen);musicMap.put(20,R.raw.twenty);musicMap.put(21,R.raw.twentyone);

        remain_note_num=notes.length;
        for(int i=0;i<30;i++){
            int randomNum=(int)Math.floor(Math.random()*(20));
            noteQueue.add(randomNum);
        }

        final int noteQueueLength=noteQueue.size();
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
        constraintLayout = (RelativeLayout) findViewById(R.id.root);

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

        float scale=1;
        scaledWhiteWidth= (int) (whiteWidth[0] *scale);
        scaledWhiteHeight=(int)(whiteHeight[0] *scale);
        scaledBrownWidth=(int)(brownWidth[0] *scale);
        scaledBrownHeight=(int)(brownHeight[0] *scale);
        scaledBrownWidthOffset=(int)(brownWidthOffset*scale);//real height
        scaledWhiteWidthOffset=(int)(whiteWidthOffset*scale);//Real width

        int finalLeftMargin=0;
        for(int i=0;i<finalPoles.length;i++){
            if(finalPoles[i]==0){
                poleAnchors.add(finalLeftMargin);
                finalLeftMargin+=scaledWhiteWidthOffset;
            }else{
                finalLeftMargin+=scaledBrownWidthOffset;
            }
        }

        //Create NPC
        int idIndex=0;
        ImageView npc=new ImageView(this);
        npc.setId(idIndex);
        dict.put("npc",idIndex);
        npc.setImageResource(R.drawable.littleblack);
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(250,250);
        npc.setLayoutParams(parms);
        npc.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //check
        npc.setX((float)(100-0.5*scaledWhiteWidth+75));
        npc.setY(height-scaledWhiteHeight-100);
        constraintLayout.addView(npc);

        constraintLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // save the X,Y coordinates
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    lastTouchDownXY[0] = event.getX();
                    lastTouchDownXY[1] = event.getY();
                }
                return false;
            }
        });
        //init the note
        int nextNote=noteQueue.get(0);
        noteQueue.remove(0);
        targetNotePos=nextNote;
        targetKey=findViewById(buttonMap.get(targetNotePos));
        if(targetKey!=null&&!isImageFilered(targetKey)){
            highlightenImage(targetKey);
        }

        TextView hintText=findViewById(R.id.hinttext);
        hintText.setText("开始闯关!下一个音是"+targetNotePos);
        for (Map.Entry<Integer, Integer> entry : noteMap.entrySet()) {
            ImageView note=findViewById(entry.getValue());
            int randomIndex=(int)Math.floor(Math.random()*((8)+0));
            note.setImageResource(notes[randomIndex]);
        }
        final TextView detailed=new TextView(this);
        detailed.setText(instructions[(int)Math.floor(Math.random()*(instructions.length-1))]);
        detailed.setX((float) (width*0.2));
        detailed.setY((float) (height*0.3));
        detailed.setTextColor(R.color.orange);
        detailed.setVisibility(View.INVISIBLE);
//        detailed.setWidth(2000);
//        detailed.setWidth(1600);
        detailed.setTextSize(20);
//        detailed.setAutoSizeTextTypeUniformWithPresetSizes(newTextSizes,TypedValue.COMPLEX_UNIT_DIP);

        constraintLayout.addView(detailed);
//        mediaPlayer=MediaPlayer.create(this, R.raw.one);
        //first load music
        loadMusic();
        //for button
        for (Map.Entry<Integer, Integer> entry : buttonMap.entrySet()) {
            ImageView btn=findViewById(entry.getValue());
            final int keyId=entry.getKey();
            btn.setOnClickListener(new View.OnClickListener() {

                                          int Id=keyId;
                                          int curId;
                                          @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                          @Override
                                          public void onClick(final View v) {
                                              if(GlobalVariable.mLastClickTime==-1){
                                                  GlobalVariable.mLastClickTime= SystemClock.elapsedRealtime();
                                              }else if (SystemClock.elapsedRealtime() - GlobalVariable.mLastClickTime < 600) {
                                                  return;
                                              }
                                              musicSoundPool.play(soundID.get(Id), 1, 1, 0, 0, 1);
//
                                              final TextView hintText=findViewById(R.id.hinttext);
                                              HorizontalScrollView horizontalScrollView=findViewById(R.id.scrollview);
                                              final float btnX = poleAnchors.get(Id-1)-horizontalScrollView.getScrollX();
                                              final ImageView note=findViewById(noteMap.get(Id));
                                              float noteX=note.getX();
                                              final float noteY=note.getY();
                                              final boolean correct;
                                              detailed.setText(instructions[(int)Math.floor(Math.random()*(instructions.length-1))]);
                                              detailed.setVisibility(View.VISIBLE);

                                              if(Id==targetNotePos){
                                                  correct=true;
                                                  if(targetKey!=null&&isImageFilered(targetKey)){
                                                      unHighlightenImage(targetKey);
                                                      darkenImage(targetKey);
                                                  }


                                              }else{
                                                  correct=false;
                                                  curId=targetNotePos;
                                                  hintText.setText("点击错误!下一个音是"+curId);
                                                  if(!isImageFilered((ImageView) v)){
                                                      darkenImage((ImageView) v);
                                                  }
                                              }
                                              // TODO Auto-generated method stub
                                              //musicSoundPool.play(soundID.get(Id), 1, 1, 0, 0, 1);
                                              final ImageView npc=findViewById(dict.get("npc")); //get npc
                                              //final ImageView note=findViewById(dict.get("note"+Integer.toString(Id)));

                                              final float x1=npc.getX();
                                              ArcTranslateAnimation upAnimation = new ArcTranslateAnimation(0,(float) ((btnX-x1)/2), 0,-200);
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
                                                              note.setY(noteY);//return back
                                                              int randomIndex=(int)Math.floor(Math.random()*((8)+0));
                                                              note.setImageResource(notes[randomIndex]);

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
                                                      npc.setX((float)((btnX+x1)/2));
                                                      npc.setY(height-scaledWhiteHeight-100);

                                                      ArcTranslateAnimation downAnimation = new ArcTranslateAnimation(0,(float) ((btnX-x1)/2), 0,200);
                                                      downAnimation.setDuration(500);
                                                      downAnimation.setFillAfter(true);

                                                      downAnimation.setAnimationListener(new Animation.AnimationListener() {
                                                          @Override
                                                          public void onAnimationStart(Animation animation) {

                                                          }

                                                          @Override
                                                          public void onAnimationEnd(Animation animation) {
                                                              detailed.setVisibility(View.INVISIBLE);
                                                              npc.clearAnimation();
                                                              npc.setX(((btnX)));
                                                              npc.setY(height-scaledWhiteHeight-100);

                                                              if(correct){
                                                                  undarkenImage(targetKey);
                                                              }else{
                                                                  undarkenImage((ImageView) v);
                                                              }
//                                                              if(mediaPlayer.isPlaying()){//没有
//                                                                  mediaPlayer.stop();
//                                                              }

                                                              if(correct){

                                                                  coin+=50;
                                                                  if(noteQueue.size()>0){
                                                                      remain_note_num-=1;
                                                                      noteQueue.remove(0);
                                                                      numberProgressBar.setProgress(100*(noteQueueLength-noteQueue.size())/noteQueueLength);
                                                                      if(noteQueue.size()==0){

                                                                          saveCoin();
                                                                          hintText.setText("完成任务!");
//                                                                          if(mediaPlayer.isPlaying()){//没有
//                                                                              mediaPlayer.stop();
//                                                                          }
                                                                          gameOver();

                                                                      }else{
//                                                                          ImageView nextNote=findViewById(noteMap.get(noteQueue.get(0)));
                                                                          targetKey=findViewById(buttonMap.get(noteQueue.get(0)));
                                                                          if(targetKey!=null&&!isImageFilered(targetKey)){
                                                                              highlightenImage(targetKey);
                                                                          }
//                                                                          int randomIndex=(int)(Math.random()*((8)+0));
//                                                                          nextNote.setImageResource(notes[randomIndex]);
                                                                          hintText.setText("下一个音是"+String.valueOf(noteQueue.get(0)));
                                                                          targetNotePos=noteQueue.get(0);
                                                                      }
                                                                  }

                                                              }else{

                                                                  coin-=20;
                                                              }
                                                              play_coin_num.setText(String.valueOf(coin));

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

                                          }}
            );
        }
    }

    private void darkenImage(ImageView imageView)
    {
        imageView.setColorFilter(Color.rgb(123, 123, 123), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    private void undarkenImage(ImageView imageView)
    {
        imageView.clearColorFilter();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private boolean isImageFilered(ImageView image)
    {
        return image.getColorFilter() != null;
    }

    private void highlightenImage(ImageView imageView)
    {
        imageView.setColorFilter(Color.rgb(0, 245, 255), android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    private void unHighlightenImage(ImageView imageView)
    {
        imageView.clearColorFilter();
    }

    private void loadMusic(){
        musicSoundPool = new SoundPool(21, AudioManager.STREAM_SYSTEM, 5);
        for(int i=1;i<=21;i++){
            soundID.put(i, musicSoundPool.load(this, musicMap.get(i), 1));
        }
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

                Intent intent=new Intent(v.getContext(), LearnNotes.class);

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

}
