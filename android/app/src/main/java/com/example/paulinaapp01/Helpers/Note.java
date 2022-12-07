package com.example.paulinaapp01.Helpers;

public class Note {
    private String id;
    private String title;
    private String text;
    private int color;

    public Note(String id, String title, String text, int color) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
