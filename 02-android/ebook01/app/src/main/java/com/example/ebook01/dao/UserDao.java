package com.example.ebook01.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ebook01.entity.User;
import com.example.ebook01.utils.SqliteUtil;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private SQLiteDatabase db;
    private final SqliteUtil sqlutil;

    public UserDao(Context context) {
        sqlutil = new SqliteUtil(context);
    }

    public int addUser(User user){
        //放数据
        ContentValues cv = new ContentValues();
        cv.put("userId",user.getUserId());
        cv.put("userName",user.getUserName());
        cv.put("userPassword",user.getUserPassword());
        cv.put("userState",user.getUserState());
        cv.put("userPoint",user.getUserPoint());
        db = sqlutil.getWritableDatabase();
        long insert = db.insert("user", null, cv);
        if(insert==-1){
            return 0;//失败
        }
        db.close();//关闭数据库
        return 1;
    }
    //
    public User searchbyName(String username){
        db = sqlutil.getReadableDatabase();
        User user = new User();
        String sql="SELECT * FROM user WHERE username = ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        int idIndex=cursor.getColumnIndex("userId");
        int nameIndex=cursor.getColumnIndex("userName");
        int pwdIndex=cursor.getColumnIndex("userPassword");
        int stateIndex=cursor.getColumnIndex("userState");
        int pointIndex=cursor.getColumnIndex("userPoint");
        if(cursor.moveToFirst()){//只返回一个数据
            user.setUserId(cursor.getInt(idIndex));
            user.setUserName(cursor.getString(nameIndex));
            user.setUserPassword(cursor.getString(pwdIndex));
            user.setUserState(cursor.getString(stateIndex));
            user.setUserPoint(cursor.getInt(pointIndex));
        }
        cursor.close();
        db.close();
        return user;
    }
}
