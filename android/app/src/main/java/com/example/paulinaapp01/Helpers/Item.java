package com.example.paulinaapp01.Helpers;

public class Item {
    private String name;
    private String time;
    private String size;
    private String url;

    public Item(String name, String time, String size, String url) {
        this.name = name;
        this.time = time;
        this.size = size;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }
}
