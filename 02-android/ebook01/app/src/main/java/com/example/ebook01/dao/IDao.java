package com.example.ebook01.dao;

/**
 * @author lin
 * 定义Dao层的增删改查
 * <p>{@link T}指代需要增删改茶的具体数据
 * </p>
 * @param <T>
 */
public interface IDao<T>{
    public void add(T t);

    public int del(T t);

    public int update(T t);

    public T find(T t);
}
