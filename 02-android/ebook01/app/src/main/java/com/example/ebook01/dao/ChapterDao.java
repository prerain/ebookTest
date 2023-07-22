package com.example.ebook01.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ebook01.entity.Chapter;
import com.example.ebook01.utils.SqliteUtil;

import java.util.ArrayList;
import java.util.List;

public class ChapterDao {

    private SQLiteDatabase db;
    private final SqliteUtil sqlutil;
    public static String  TABLE_NAME = "chapter";

    public ChapterDao(Context context) {
        sqlutil = new SqliteUtil(context);
    }

    public List<Chapter> chapFindAll(int bookId){
        db = sqlutil.getReadableDatabase();
        List<Chapter> chapters = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE bookId = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(bookId)});

        int chapIdIndex = cursor.getColumnIndex("chapterId");
        int titleIndex = cursor.getColumnIndex("chapterTitle");
            while (cursor.moveToNext()){
                Chapter chap = new Chapter();
                chap.setChapId(cursor.getInt(chapIdIndex));
                chap.setChaptitle(cursor.getString(titleIndex));
                chap.setBookId(bookId);
                chapters.add(chap);
        }
        cursor.close();
        db.close();
        return chapters;
    }
    public void delPage(int bookId){
        db = sqlutil.getWritableDatabase();
        db.delete(TABLE_NAME,"bookId=?", new String[]{String.valueOf(bookId)});
        db.close();
    }
}
