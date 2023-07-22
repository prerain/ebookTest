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

public class NotesDao {
    private SQLiteDatabase db;
    private final SqliteUtil sqlutil;

    public static String  TABLE_NAME = "booknote";
    public NotesDao(Context context) {
        sqlutil = new SqliteUtil(context);
    }

    public int addNote(Notes notes, int bookId){
        ContentValues cv = new ContentValues();
        cv.put("noteTitle",notes.getNoteTitle());
        cv.put("noteContent",notes.getNoteTitle());
        cv.put("noteLocation",notes.getLocation());
        cv.put("bookId",bookId);
        db = sqlutil.getWritableDatabase();
        long insert = db.insert("booknote", null, cv);
        db.close();
        if(insert==-1){
            return 0;
        }
        return 1;
    }
    public int delNoteALL(int bookId){
        db = sqlutil.getWritableDatabase();
        int delete = db.delete(TABLE_NAME,"bookId=?", new String[]{String.valueOf(bookId)});
        db.close();
        if (delete==0){
            return 0;
        }
        return 1;
    }
    public int delNotebyNoteId(int noteId,int bookId){
        db = sqlutil.getWritableDatabase();
        int delete = db.delete(TABLE_NAME,"noteId=? AND bookId = ?", new String[]{String.valueOf(noteId), String.valueOf(bookId)});
        db.close();
        if (delete==0){
            return 0;
        }
        return 1;
    }
    public int update(Notes note){
        db = sqlutil.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("noteTitle",note.getNoteTitle());
        cv.put("noteContent",note.getNoteContent());
        int update = db.update(TABLE_NAME, cv, "noteId"+"=?", new String[]{String.valueOf(note.getNoteId())});
        db.close();
        if (update==0){
            return 0;
        }
        return 1;
    }
    public List<Notes> findnoteList(int bookId){
        List<Notes> bookmarkList = new ArrayList<>();

        db = sqlutil.getReadableDatabase();
        String sql="SELECT * FROM "+TABLE_NAME+" WHERE bookId = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(bookId)});
        int idIndex = cursor.getColumnIndex("noteId");
        int nameIndex = cursor.getColumnIndex("noteTitle");
        int contentIndex = cursor.getColumnIndex("noteContent");
        int bookIndex = cursor.getColumnIndex("bookId");
        while (cursor.moveToNext()){
            Notes notes = new Notes();
            notes.setNoteId(cursor.getInt(idIndex));
            notes.setNoteTitle(cursor.getString(nameIndex));
            notes.setNoteContent(cursor.getString(contentIndex));
            notes.setBookId(cursor.getInt(bookIndex));
            bookmarkList.add(notes);
        }
        cursor.close();
        db.close();
        return bookmarkList;
    }
}
