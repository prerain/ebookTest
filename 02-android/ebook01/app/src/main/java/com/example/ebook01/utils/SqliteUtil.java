package com.example.ebook01.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.ebook01.entity.User;

public class SqliteUtil extends SQLiteOpenHelper {
    public static final String dataBaseName = "ebook01.db";
    public static final int dataBaseVersion = 1;
    public static final String create_user= "create table user ("
            + "userId integer primary key, "
            + "userName text, "
            + "userPassword text, "
            + "userState text, "
            + "userPoint integer)";
    public static final String cerate_bookshelf="create table bookshelf("
            + "shelfId integer primary key autoincrement, "
            + "shelfName text)";
    public static final String cerate_book="create table book("
            + "bookId integer primary key autoincrement, "
            + "bookName text, "
            + "bookAuthor text, "
            + "bookPrice integer, "
            + "bookDetalis text, "
            + "bookPath text, "
            + "shelfId integer, "
            + "source text)";
    public static final String cerate_bookmark="create table bookmark("
            + "markId integer primary key autoincrement, "
            + "markName text, "
            + "markLocation integer, "
            + "bookId integer)";
    public static final String cerate_booknote="create table booknote("
            + "noteId integer primary key autoincrement, "
            + "noteTitle text, "
            + "noteContent text, "
            + "noteLocation integer, "
            + "bookId integer)";

    public static final String create_chapter = "create table chapter("
            + "chapterId integer primary key autoincrement, "
            + "chapterTitle text, "
            + "page_content text, "
            + "belong_to_chapID int, "
            + "isTempPage2 int, "
            + "bookId integer)";
    public SqliteUtil(@Nullable Context context) {
        super(context, dataBaseName, null, dataBaseVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建用户表，书架表，书籍表，笔记表，书签表
        db.execSQL(create_user);
        db.execSQL(cerate_bookshelf);
        db.execSQL(cerate_book);
        db.execSQL(cerate_booknote);
        db.execSQL(cerate_bookmark);
        db.execSQL(create_chapter);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}


}
