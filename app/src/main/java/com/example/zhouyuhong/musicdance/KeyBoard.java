package com.example.zhouyuhong.musicdance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

@SuppressLint("ValidFragment")
public final class KeyBoard extends Fragment {
    private int partId;
    private Context context;
    private int[] keys;
    private boolean[] isTop;
    private boolean[] hasDots;
    private int noteLocation;
    private HashMap<Integer, Integer> textMap=new HashMap<>();
    private HashMap<Integer, Integer> upDotMap=new HashMap<>();
    private HashMap<Integer, Integer> downDotMap=new HashMap<>();
    private HashMap<Integer, Integer> poleMap=new HashMap<>();
    private ImageView npc;

    public KeyBoard(int partId, Context context, int[] keys, boolean[] isTop, boolean[] hasDots, int noteLocation, ImageView npc) {
        this.partId = partId;
        this.context = context;
        this.keys = keys;
        this.isTop = isTop;
        this.hasDots = hasDots;
        this.noteLocation = noteLocation;
        this.npc=npc;

    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setKeys(int[] keys) {
        this.keys = keys;
    }

    public void setIsTop(boolean[] isTop) {
        this.isTop = isTop;
    }

    public void setHasDots(boolean[] hasDots) {
        this.hasDots = hasDots;
    }

    public void setNoteLocation(int noteLocation) {
        this.noteLocation = noteLocation;
    }


    @Nullable
    @Override
    public Context getContext() {
        return context;
    }

    public int[] getKeys() {
        return keys;
    }

    public boolean[] getIsTop() {
        return isTop;
    }

    public boolean[] getHasDots() {
        return hasDots;
    }

    public int getNoteLocation() {
        return noteLocation;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        textMap.put(1,R.id.textView1);
        textMap.put(2,R.id.textView2);
        textMap.put(3,R.id.textView3);
        textMap.put(4,R.id.textView4);
        textMap.put(5,R.id.textView5);
        textMap.put(6,R.id.textView6);
        textMap.put(7,R.id.textView7);

        upDotMap.put(1,R.id.textView1_1);
        upDotMap.put(2,R.id.textView21);
//        upDotMap.put(3,R.id.textView31);
//        upDotMap.put(4,R.id.textView41);
//        upDotMap.put(5,R.id.textView51);
//        upDotMap.put(6,R.id.textView61);
//        upDotMap.put(7,R.id.textView71);

//        downDotMap.put(1,R.id.textView1_2);
//        downDotMap.put(2,R.id.textView22);
//        downDotMap.put(3,R.id.textView32);
//        downDotMap.put(4,R.id.textView42);
//        downDotMap.put(5,R.id.textView52);
//        downDotMap.put(6,R.id.textView62);
//        downDotMap.put(7,R.id.textView72);
//
//        poleMap.put(1,R.id.pole1);
//        poleMap.put(2,R.id.pole2);
//        poleMap.put(3,R.id.pole3);
//        poleMap.put(4,R.id.pole4);
//        poleMap.put(5,R.id.pole5);
//        poleMap.put(6,R.id.pole6);
//        poleMap.put(7,R.id.pole7);

        View view = inflater.inflate(R.layout.keyboard, null);

        //adjust accroiding to the partId and additional infomation
        for(int i=1;i<=keys.length;i++){
            TextView text=view.findViewById(textMap.get(i));
            text.setText(keys[i-1]);
            ImageView btn=view.findViewById(poleMap.get(i));
            //create button
//            createButton(i, view);
            //with sound~

        }
        for(int i=1;i<=isTop.length;i++){
            if(hasDots[i-1]){
                if(isTop[i-1]){
                    TextView text1=view.findViewById(upDotMap.get(i));
                    text1.setText(".");
                    TextView text2=view.findViewById(downDotMap.get(i));
                    text2.setText("");

                }else{
                    TextView text1=view.findViewById(upDotMap.get(i));
                    text1.setText("");
                    TextView text2=view.findViewById(downDotMap.get(i));
                    text2.setText(".");
                }
            }else{
                TextView text1=view.findViewById(upDotMap.get(i));
                text1.setText("");
                TextView text2=view.findViewById(downDotMap.get(i));
                text2.setText("");
            }
        }



        return view;
    }

    public int getPartId(){
        return this.partId;
    }

    /*@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createButton(int id, View view){
        final ImageView button = getView().findViewById(poleMap.get(id));
        button.setOnClickListener(new View.OnClickListener() {
            int Id=typeId;
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //musicSoundPool.play(soundID.get(Id), 1, 1, 0, 0, 1);
                final ImageView npc=view.findViewById(dict.get("npc")); //get npc
                final ImageView note=view.findViewById(dict.get("note"+Integer.toString(Id)));

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
            }}
        );

    }*/

}