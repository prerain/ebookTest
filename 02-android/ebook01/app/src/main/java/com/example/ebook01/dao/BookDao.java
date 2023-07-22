package com.example.ebook01.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ebook01.entity.Book;
import com.example.ebook01.entity.Bookshelf;
import com.example.ebook01.utils.SqliteUtil;

import java.util.ArrayList;
import java.util.List;

public class BookDao {

    //还有需要调整的部分,暂时这样
    private SQLiteDatabase db;
    private final SqliteUtil sqlutil;



    public BookDao(Context context) {
        sqlutil = new SqliteUtil(context);
    }
    //添加书籍  在外面写的时候可以考虑多加一个id
    public void addBook(List<Book> books) {

        // 获取数据库对象
        db = sqlutil.getWritableDatabase();
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
    //删除书籍
    public int delBook(Book book){
        db = sqlutil.getWritableDatabase();
        int delete = db.delete("book", "bookId" + "=?", new String[]{String.valueOf(book.getBookId())});
        db.close();
        if (delete==0){
            return 0;
        }
        return 1;
    }
    //查询书籍(某个书架中的)
    public List<Book> findbooklist(int shelfId){
        List<Book> bookList = new ArrayList<>();
        db = sqlutil.getReadableDatabase();
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
    public List<Book> findBookByname(String bookName){
        List<Book> bookList = new ArrayList<>();
        db = sqlutil.getReadableDatabase();
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
    public Book findBookByid(int bookid){
        Book book =new Book();
        db = sqlutil.getReadableDatabase();
        String sql = "SELECT * FROM book WHERE bookId = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(bookid)});
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
}
