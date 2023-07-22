package com.example.ebook01.entity;

import java.util.List;

public class NovelPageWindow {
    private List<NovelContentPage> pages;
    private boolean isSingleWindow = true;//表示当前Window仅装载一个章节的内容
    private int chapID;//仅single window有效

    public List<NovelContentPage> getPages() {
        return pages;
    }

    public void setPages(List<NovelContentPage> pages) {
        this.pages = pages;
    }

    public int getPageNum(){
        return pages!=null?pages.size():1;
    }

    public boolean isSingleWindow() {
        return isSingleWindow;
    }

    public void setSingleWindow(boolean singleWindow) {
        isSingleWindow = singleWindow;
    }

    public int getChapID() {
        return chapID;
    }

    public void setChapID(int chapID) {
        this.chapID = chapID;
    }

    @Override
    public String toString() {
        return "NovelPageWindow{" +
                "pages=" + pages +
                ", isSingleWindow=" + isSingleWindow +
                ", chapID=" + chapID +
                '}';
    }
}

