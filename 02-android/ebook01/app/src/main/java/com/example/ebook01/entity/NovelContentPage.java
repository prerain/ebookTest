package com.example.ebook01.entity;

public class NovelContentPage {
    private int page_id;
    private int start_pos;
    private int end_pos;
    private String page_content;
    private boolean isFirstPage;//当前页是否属于本章的首页
    private String title;//首页所包含的标题，仅在isFirstPage = true 时适用
    private int belong_to_chapID;
    private boolean isTempPage;
    private int isTempPage2;//数据库中用1和0来表示true/false
    private int bookId;

    public int getStart_pos() {
        return start_pos;
    }

    public void setStart_pos(int start_pos) {
        this.start_pos = start_pos;
    }

    public int getEnd_pos() {
        return end_pos;
    }

    public void setEnd_pos(int end_pos) {
        this.end_pos = end_pos;
    }

    public int getPage_id() {
        return page_id;
    }

    public void setPage_id(int page_id) {
        this.page_id = page_id;
    }

    public String getPage_content() {
        return page_content;
    }

    public void setPage_content(String page_content) {
        this.page_content = page_content;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBelong_to_chapID() {
        return belong_to_chapID;
    }

    public boolean isTempPage() {
        return isTempPage;
    }

    public void setTempPage(boolean tempPage) {
        isTempPage = tempPage;
    }

    public void setBelong_to_chapID(int belong_to_chapID) {
        this.belong_to_chapID = belong_to_chapID;
    }

    public int getIsTempPage2() {
        return isTempPage2;
    }

    public void setIsTempPage2(int isTempPage2) {
        this.isTempPage2 = isTempPage2;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "NovelContentPage{" +
                "start_pos=" + start_pos +
                ", end_pos=" + end_pos +
                ", page_id=" + page_id +
                ", page_content='" + page_content + '\'' +
                ", isFirstPage=" + isFirstPage +
                ", title='" + title + '\'' +
                ", belong_to_chapID=" + belong_to_chapID +
                ", isTempPage=" + isTempPage +
                ", isTempPage2=" + isTempPage2 +
                ", bookId=" + bookId +
                '}';
    }
}
