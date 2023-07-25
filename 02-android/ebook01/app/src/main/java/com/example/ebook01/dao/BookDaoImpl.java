package com.example.ebook01.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.example.ebook01.entity.Book;
import com.example.ebook01.utils.SqliteUtil;

import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements IBookDao{
    SqliteUtil sqliteUtil;

    public BookDaoImpl(Context ctx){
        sqliteUtil = SqliteUtil.getInstance(ctx);
    }
    @Override
    public void add(Book book) {

    }

    @Override
    public int del(Book book) {
        SQLiteDatabase db = sqliteUtil.getWritableDatabase();
        int delete = db.delete("book", "bookId" + "=?", new String[]{String.valueOf(book.getBookId())});
        db.close();
        return delete;
    }

    @Override
    public int update(Book book) {
        return 0;
    }

    @Override
    public Book find(Book book) {
        if (book == null) return null;
        SQLiteDatabase db = sqliteUtil.getWritableDatabase();
        String sql = "SELECT * FROM book WHERE bookId = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(book.getBookId())});
        int idIndex = cursor.getColumnIndex("bookId");
        int nameIndex = cursor.getColumnIndex("bookName");
        int authorIndex = cursor.getColumnIndex("bookAuthor");
        int detailIndex = cursor.getColumnIndex("bookDetalis");
        int pathIndex = cursor.getColumnIndex("bookPath");
        int shelfIdIndex = cursor.getColumnIndex("shelfId");
        int sourceIndex = cursor.getColumnIndex("source");
        if (cursor.moveToFirst()){
            book.setBookId(cursor.getInt(idIndex));
            book.setBookName(cursor.getString(nameIndex));
            book.setBookAuthor(cursor.getString(authorIndex));
            book.setBookDetalis(cursor.getString(detailIndex));
            book.setBookPath(cursor.getString(pathIndex));
            book.setShelfId(cursor.getInt(shelfIdIndex));
            book.setSource(cursor.getString(sourceIndex));
        }
        cursor.close();
        db.close();
        return book;
    }

    @Override
    public void addBook(List<Book> books) {
        // 获取数据库对象
        SQLiteDatabase db = sqliteUtil.getWritableDatabase();
        // 开始插入
        db.beginTransaction();
        try {
            for (int i = 0; i < books.size(); i++) {
                ContentValues cv = new ContentValues();
                cv.put("bookName", books.get(i).getBookName());
                cv.put("bookAuthor", books.get(i).getBookAuthor());
                cv.put("bookPrice", books.get(i).getBookPrice());
                cv.put("bookDetalis", books.get(i).getBookDetalis());
                cv.put("bookPath", books.get(i).getBookPath());
                cv.put("shelfId", books.get(i).getShelfId());
                cv.put("source", books.get(i).getSource());
                db.insert("book", null, cv);
            }    // 提交事务
            db.setTransactionSuccessful();
        } finally {
            // 结束事务
            db.endTransaction();
            db.close();
        }
    }

    //查询书籍(某个书架中的)
    @Override
    public List<Book> findBookList(int shelfId){
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = sqliteUtil.getWritableDatabase();
        String sql = "SELECT * FROM book WHERE shelfId = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(shelfId)});
        int idIndex = cursor.getColumnIndex("bookId");
        int nameIndex = cursor.getColumnIndex("bookName");
        int shelfIdIndex = cursor.getColumnIndex("shelfId");
        int sourceIndex = cursor.getColumnIndex("source");
        while (cursor.moveToNext()){
            Book book =new Book();
            book.setBookId(cursor.getInt(idIndex));
            book.setBookName(cursor.getString(nameIndex));
            book.setShelfId(cursor.getInt(shelfIdIndex));
            book.setSource(cursor.getString(sourceIndex));
            bookList.add(book);
        }
        cursor.close();
        db.close();
        return bookList;
    }

    @Override
    public List<Book> findBookByName(String bookName){
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = sqliteUtil.getWritableDatabase();
        String sql = "SELECT * FROM book WHERE bookName = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{bookName});
        int idIndex = cursor.getColumnIndex("bookId");
        int nameIndex = cursor.getColumnIndex("bookName");
        int authorIndex = cursor.getColumnIndex("bookAuthor");
        int detailIndex = cursor.getColumnIndex("bookDetalis");
        int pathIndex = cursor.getColumnIndex("bookPath");
        int shelfIdIndex = cursor.getColumnIndex("shelfId");
        int sourceIndex = cursor.getColumnIndex("source");
        while (cursor.moveToNext()){
            Book book =new Book();
            book.setBookId(cursor.getInt(idIndex));
            book.setBookName(cursor.getString(nameIndex));
            book.setBookAuthor(cursor.getString(authorIndex));
            book.setBookDetalis(cursor.getString(detailIndex));
            book.setBookPath(cursor.getString(pathIndex));
            book.setShelfId(cursor.getInt(shelfIdIndex));
            book.setSource(cursor.getString(sourceIndex));
            bookList.add(book);
        }
        cursor.close();
        db.close();
        return bookList;
    }

    @Override
    public Book findBookById(int bookId){
        Book book = new Book();
        book.setBookId(bookId);
        return find(book);
    }
}
