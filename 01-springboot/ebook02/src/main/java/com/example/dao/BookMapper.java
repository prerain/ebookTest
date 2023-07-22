package com.example.dao;

import com.example.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("BookMapper")
public interface BookMapper {
    List<Book> findAll();
    //模糊查询
    List<Book> findBookList(@Param("keyword") String keyword);

    int updateBook(Book book);

    Book findbyId(@Param("bookId")int bookId);

    int addBook(Book book);

    int deleteBook(@Param("bookId")int bookId);
}
