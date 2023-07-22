package com.example.ebook01.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ebook01.entity.NovelContentPage;
import com.example.ebook01.utils.SqliteUtil;
import java.util.ArrayList;
import java.util.List;

public class NovelContentPageDao {
    private SQLiteDatabase db;
    private final SqliteUtil sqlutil;
    public static String  TABLE_NAME = "chapter";

    public NovelContentPageDao(Context context) {
        sqlutil = new SqliteUtil(context);
    }


    public void addNovelContentPage(List<NovelContentPage> pages){
        // 获取数据库对象
        db = sqlutil.getWritableDatabase();
        // 开始插入
        db.beginTransaction();
        try {
            for (int i = 0; i < pages.size(); i++) {
                // 绑定参数
                ContentValues cv = new ContentValues();
                cv.put("chapterTitle", pages.get(i).getTitle());
                cv.put("page_content", pages.get(i).getPage_content());
                cv.put("belong_to_chapID", pages.get(i).getBelong_to_chapID());
                cv.put("isTempPage2", pages.get(i).getIsTempPage2());
                cv.put("bookId", pages.get(i).getBookId());
                db.insert(TABLE_NAME, null, cv);
            }
            // 提交事务
            db.setTransactionSuccessful();
        } finally {
            // 结束事务
            db.endTransaction();
            db.close();

        }
    }
    public List<NovelContentPage> findByBookId(int bookId){
        List<NovelContentPage> pages = new ArrayList<>();
        db = sqlutil.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE bookId = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(bookId)});

        int chapIdIndex = cursor.getColumnIndex("chapterId");
        int titleIndex = cursor.getColumnIndex("chapterTitle");
        int contentIndex = cursor.getColumnIndex("page_content");
        int belongToChapIDIndex = cursor.getColumnIndex("belong_to_chapID");
        int isTempPage2Index = cursor.getColumnIndex("isTempPage2");
        int bookIdIndex = cursor.getColumnIndex("bookId");
        while (cursor.moveToNext()){
            NovelContentPage page = new NovelContentPage();
            page.setPage_id(cursor.getInt(chapIdIndex));
            page.setTitle(cursor.getString(titleIndex));
            page.setPage_content(cursor.getString(contentIndex));
            page.setBelong_to_chapID(cursor.getInt(belongToChapIDIndex));
            page.setIsTempPage2(cursor.getInt(isTempPage2Index));
            page.setBookId(cursor.getInt(bookIdIndex));
            pages.add(page);
        }
        cursor.close();
        db.close();
        return pages;
    }
    public void delPage(int bookId){
        db = sqlutil.getWritableDatabase();
        int delete = db.delete(TABLE_NAME,"bookId=?", new String[]{String.valueOf(bookId)});
        db.close();
    }
}
