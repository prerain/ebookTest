package com.example.ebook01.entity;

public class Bookmark {
    private int markId; //书签编号
    private String markName;//书签名字默认书签1
    private int location;//在书中的位置-页数
    private int bookId;//所属书本编号

    public Bookmark() {
    }

    public Bookmark(int markId, String markName, int location, int bookId) {
        this.markId = markId;
        this.markName = markName;
        this.location = location;
        this.bookId = bookId;
    }

    public int getMarkId() {
        return markId;
    }

    public void setMarkId(int markId) {
        this.markId = markId;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "markId=" + markId +
                ", markName='" + markName + '\'' +
                ", location=" + location +
                ", bookId=" + bookId +
                '}';
    }
}
