package com.example.zhouyuhong.musicdance;

public class KeyModel {
    private int[] id;
    private int[] keyNum;
    private boolean[] hasDot;
    private boolean[] isTop;


    public KeyModel(int[] id, int[] keyNum, boolean[] hasDot, boolean[] isTop){
        this.id=id;
        this.keyNum=keyNum;
        this.hasDot=hasDot;
        this.isTop=isTop;
    }

    public int[] getKeyNum() {
        return keyNum;
    }

    public boolean[] isHasDot() {
        return hasDot;
    }

    public boolean[] isTop() {
        return isTop;
    }

    public void setKeyNum(int[] keyNum) {
        this.keyNum = keyNum;
    }

    public void setHasDot(boolean[] hasDot) {
        this.hasDot = hasDot;
    }

    public void setTop(boolean[] top) {
        isTop = top;
    }

    public int[] getId() {
        return id;
    }

    public void setId(int[] id) {
        this.id = id;
    }
}
