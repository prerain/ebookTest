package com.example.ebook01.entity;

public class Bookshelf {
    private int shelfId;//书架编号
    private String shelfName;//书架名称

    public Bookshelf() {
    }

    public Bookshelf(int shelfId, String shelfName) {
        this.shelfId = shelfId;
        this.shelfName = shelfName;
    }

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public String getShelfName() {
        return shelfName;
    }

    public void setShelfName(String shelfName) {
        this.shelfName = shelfName;
    }

    @Override
    public String toString() {
        return "Bookshelf{" +
                "shelfId=" + shelfId +
                ", shelfName='" + shelfName + '\'' +
                '}';
    }
}
