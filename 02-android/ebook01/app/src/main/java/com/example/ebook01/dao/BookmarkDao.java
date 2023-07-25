package com.example.ebook01.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ebook01.entity.Book;
import com.example.ebook01.entity.Bookmark;
import com.example.ebook01.entity.Notes;
import com.example.ebook01.utils.SqliteUtil;

import java.util.ArrayList;
import java.util.List;


public class BookmarkDao {
    private SQLiteDatabase db;
    private final SqliteUtil sqlutil;

    public BookmarkDao(Context context) {
        sqlutil = SqliteUtil.getInstance(context);
    }

    public int addMark(Bookmark bookmark){
        ContentValues cv = new ContentValues();
        cv.put("markName",bookmark.getMarkName());
        cv.put("markLocation",bookmark.getLocation());
        cv.put("bookId",bookmark.getBookId());
        db = sqlutil.getWritableDatabase();
        long insert = db.insert("bookmark", null, cv);
        db.close();
        if(insert==-1){
            return 0;
        }
        return 1;
    }

    public int delMark(int chapId,int bookId){
        db = sqlutil.getWritableDatabase();
        int delete = db.delete("bookmark","markLocation=? AND bookId = ?", new String[]{String.valueOf(chapId), String.valueOf(bookId)});
        db.close();
        if (delete==0){
            return 0;
        }
        return 1;
    }
    public int delMarkALL(int bookId){
        db = sqlutil.getWritableDatabase();
        int delete = db.delete("bookmark","bookId = ?", new String[]{String.valueOf(bookId)});
        db.close();
        if (delete==0){
            return 0;
        }
        return 1;
    }
    public Bookmark findbyChapAndBook(int bookId,int ChapId){
        Bookmark mark = new Bookmark();
        db = sqlutil.getReadableDatabase();
        String sql = "SELECT * FROM bookmark WHERE markLocation = ? AND bookId = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{String.valueOf(ChapId), String.valueOf(bookId)});
        int idIndex = cursor.getColumnIndex("markId");
        int nameIndex = cursor.getColumnIndex("markName");
        int locaIndex = cursor.getColumnIndex("markLocation");
        int bookid = cursor.getColumnIndex("bookId");
        if (cursor.moveToFirst()){
            mark.setMarkId(cursor.getInt(idIndex));
            mark.setMarkName(cursor.getString(nameIndex));
            mark.setLocation(cursor.getInt(locaIndex));
            mark.setBookId(cursor.getInt(bookid));
        }
        return mark;
    }
    public List<Bookmark> findbybookid(int bookid){
        List<Bookmark> bookmarks = new ArrayList<>();
        db = sqlutil.getReadableDatabase();
        String sql = "SELECT * FROM bookmark WHERE bookId = ? ORDER BY markLocation ASC";
        Cursor cursor = db.rawQuery(sql,new String[]{String.valueOf(bookid)});
        int idIndex = cursor.getColumnIndex("markId");
        int nameIndex = cursor.getColumnIndex("markName");
        int locaIndex = cursor.getColumnIndex("markLocation");
        while (cursor.moveToNext()){
            Bookmark mark = new Bookmark();
            mark.setMarkId(cursor.getInt(idIndex));
            mark.setMarkName(cursor.getString(nameIndex));
            mark.setLocation(cursor.getInt(locaIndex));
            mark.setBookId(bookid);
            bookmarks.add(mark);
        }
        return bookmarks;
    }

}
