package com.example.ebook01.entity;

public class Notes {
    private int noteId; //笔记编号
    private String noteTitle;//笔记所选内容
    private String noteContent;//笔记内容
    private int location;//在书中的位置-页数
    private int bookId;//所属书本编号

    public Notes() {
    }

    public Notes(int noteId, String noteTitle, String noteContent, int location, int bookId) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.location = location;
        this.bookId = bookId;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
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
        return "Notes{" +
                "noteId=" + noteId +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteContent='" + noteContent + '\'' +
                ", location=" + location +
                ", bookId=" + bookId +
                '}';
    }
}
