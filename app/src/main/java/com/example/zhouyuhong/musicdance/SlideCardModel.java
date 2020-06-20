package com.example.zhouyuhong.musicdance;

public class SlideCardModel {
    private int id;
    private int image;
    private String title;
    private String author;
    private int color;
    public SlideCardModel(int id, int image, String title, String author, int color){
        this.id=id;
        this.image=image;
        this.title=String.valueOf(id+1)+". "+title;
        this.author=author;
        this.color=color;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
