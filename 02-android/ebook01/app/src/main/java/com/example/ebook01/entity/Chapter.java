package com.example.ebook01.entity;

public class Chapter {
    private int chapId;
    private String chaptitle;
    private int bookId;

    public Chapter() {
    }

    public Chapter(int chapId, String chaptitle, int bookId) {
        this.chapId = chapId;
        this.chaptitle = chaptitle;
        this.bookId = bookId;
    }

    public int getChapId() {
        return chapId;
    }

    public void setChapId(int chapId) {
        this.chapId = chapId;
    }

    public String getChaptitle() {
        return chaptitle;
    }

    public void setChaptitle(String chaptitle) {
        this.chaptitle = chaptitle;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapId=" + chapId +
                ", chaptitle='" + chaptitle + '\'' +
                ", bookId=" + bookId +
                '}';
    }
}
