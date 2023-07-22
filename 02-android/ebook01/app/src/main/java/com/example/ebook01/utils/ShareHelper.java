package com.example.ebook01.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class ShareHelper {
    private static final String file_name = "sp_name";
    private Context mContext;

    public ShareHelper() {
    }

    public ShareHelper(Context mContext) {
        this.mContext = mContext;
    }

    //定义一个保存数据的方法
    public void put(String key, Object object) {
        SharedPreferences sp = mContext.getSharedPreferences(file_name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    //定义一个读取SP文件的方法
    public Object get( String key, Object defaultObject) {
        SharedPreferences sp = mContext.getSharedPreferences(file_name, Context.MODE_PRIVATE);
        //defaultObject,是key的值不存在时返回的默认值。
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }
}
