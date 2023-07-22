package com.example.ebook01.entity;

public class Book {

    private int bookId;//书本编号
    private String bookName;//书名
    private String bookAuthor;//作者-下载
    private int bookPrice;//售价-下载
    private String bookDetalis;//简介-下载
    private String bookPath;//书本路径-本地的路径
    private int shelfId;  //所属书架编号
    private String source; //书籍来源，下载，本地

    public Book() {
    }

    public Book(String bookName, String bookPath, int shelfId, String source) {
        this.bookName = bookName;
        this.bookPath = bookPath;
        this.shelfId = shelfId;
        this.source = source;
    }

    public Book(int bookId, String bookName, String bookAuthor, int bookPrice, String bookDetalis, String bookPath, int shelfId, String source) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookPrice = bookPrice;
        this.bookDetalis = bookDetalis;
        this.bookPath = bookPath;
        this.shelfId = shelfId;
        this.source = source;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getBookDetalis() {
        return bookDetalis;
    }

    public void setBookDetalis(String bookDetalis) {
        this.bookDetalis = bookDetalis;
    }

    public String getBookPath() {
        return bookPath;
    }

    public void setBookPath(String bookPath) {
        this.bookPath = bookPath;
    }

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookPrice=" + bookPrice +
                ", bookDetalis='" + bookDetalis + '\'' +
                ", bookPath='" + bookPath + '\'' +
                ", shelfId=" + shelfId +
                ", source='" + source + '\'' +
                '}';
    }
}
