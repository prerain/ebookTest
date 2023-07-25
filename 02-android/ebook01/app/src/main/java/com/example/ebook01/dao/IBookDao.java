package com.example.ebook01.dao;

import com.example.ebook01.entity.Book;

import java.util.List;

/**
 * @author lin 
 * <p>书籍查询Dao接口</p>
 * <p>他定义了当前Dao层数据处理的对象是{@link Book}</p>
 */
public interface IBookDao extends IDao<Book>{
    void addBook(List<Book> books);
    //查询书籍(某个书架中的)
    List<Book> findBookList(int shelfId);
    List<Book> findBookByName(String bookName);
    Book findBookById(int bookId);
}
