package com.example.zhouyuhong.musicdance;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class KeyAdapter extends FragmentStatePagerAdapter {
    private LayoutInflater layoutInflater;
    private List<KeyBoard> keyBoards;
    private Context context;
    private String section;
    private int resId;
    private int keyboardPart;
    private int totalNum;

    public KeyAdapter(FragmentManager fm, int totalNum, List<KeyBoard> keyBoards) {
        super(fm);
        this.totalNum=totalNum;
    }

    @Override
    public int getCount() {
        return this.keyBoards.size();
    }

    public Fragment getItem(int position) {
//        KeyBoard fragment = new KeyBoard(position,this.context);
//        fragment.setTitle(titles.get(position));
//        return fragment;
        return null;
    }

    public int getItemPosition(Object item) {
        KeyBoard fragment = (KeyBoard)item;
        int position=fragment.getPartId();
        if (position >= 0) {
            return position;
        } else {
            return POSITION_NONE;
        }
    }

//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
////        super.destroyItem(container, position, object);
//        container.removeView((View)object);
//    }

    @Override
    public float getPageWidth(int position){
        return (0.5f);
    }
}
