package com.example.dao;

import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("UserMapper")
public interface UserMapper {
    List<User> findAll();
    List<User> findBykeyword(@Param("keyword") String keyword);
    User findbyId(@Param("userId") int userId);
    int updateUser(User user);

    List<User> findByUsername(@Param("userName") String userName);
    int addUser(User user);
}
