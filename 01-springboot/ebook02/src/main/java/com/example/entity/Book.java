package com.example.entity;

import lombok.Data;


@Data
public class Book {
    private int bookId;
    private String bookName;
    private String bookAuthor;
    private int bookPrice;
    private String bookDetalis;
    private String bookPath;

}
