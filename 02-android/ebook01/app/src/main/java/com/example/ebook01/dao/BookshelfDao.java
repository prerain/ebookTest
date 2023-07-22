package com.example.ebook01.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ebook01.entity.Bookshelf;
import com.example.ebook01.utils.SqliteUtil;

import java.util.ArrayList;
import java.util.List;

public class BookshelfDao {
    public static final String TABLE_NAME = "bookshelf";
    private SQLiteDatabase db;
    private final SqliteUtil sqlutil;
    public BookshelfDao(Context context){
        sqlutil = new SqliteUtil(context);
    }

    //添加
    public int addShlef(Bookshelf bookshelf){
        ContentValues cv = new ContentValues();
        cv.put("shelfName",bookshelf.getShelfName());
        db = sqlutil.getWritableDatabase();
        long insert = db.insert(TABLE_NAME, "shelfId", cv);
        db.close();
        if(insert==-1){
            return 0;
        }
        return 1;
    }
    //删除
    public int delshelf(Bookshelf bookshelf){
        db = sqlutil.getWritableDatabase();
        int delete = db.delete(TABLE_NAME, "shelfId" + "=?", new String[]{String.valueOf(bookshelf.getShelfId())});
        db.close();
        if (delete==0){
            return 0;
        }
        return 1;
    }
    //修改
    public int updateShlef(Bookshelf bookshelf){
        ContentValues cv = new ContentValues();
        cv.put("shelfName",bookshelf.getShelfName());
        db = sqlutil.getWritableDatabase();
        long update = db.update(TABLE_NAME,cv,"shelfId" + "=?", new String[]{String.valueOf(bookshelf.getShelfId())});
        db.close();

        if(update==0){
            return 0;
        }
        return 1;
    }
    //查询
    public List<Bookshelf> finall(){
        List<Bookshelf> bookshelfList =new ArrayList<>();
        db=sqlutil.getReadableDatabase();
        String sql = " SELECT shelfId,shelfName FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        int idIndex = cursor.getColumnIndex("shelfId");
        int nameIndex = cursor.getColumnIndex("shelfName");
        while (cursor.moveToNext()){
            Bookshelf bookshelf = new Bookshelf();
            bookshelf.setShelfId(cursor.getInt(idIndex));
            bookshelf.setShelfName(cursor.getString(nameIndex));
            bookshelfList.add(bookshelf);
        }
        cursor.close();
        db.close();
        return bookshelfList;
    }
}
