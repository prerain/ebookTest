package com.example.service;

import com.example.dao.UserMapper;
import com.example.entity.Manager;
import com.example.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("UserService")
public class UserService {
    @Resource(name = "UserMapper")
    private UserMapper userMapper;

    public PageInfo<User> users(int pageNum){
        //每页5个数据
        PageHelper.startPage(pageNum,5);
        List<User> userList = userMapper.findAll();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

//    public List<User> users(){
//        return userMapper.findAll();
//    }
    public PageInfo<User> searchUser(String keyword,int pageNum){
        PageHelper.startPage(pageNum,5);
        List<User> userList = userMapper.findBykeyword(keyword);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    public List<User> searchUser(String keyword){
        return userMapper.findBykeyword(keyword);
    }

    public User findbyId(int userId){
        return userMapper.findbyId(userId);
    }

    public int updateUser(User user){
        return userMapper.updateUser(user);
    }

    public List<User> findUserByName(String username){
        return userMapper.findByUsername(username);
    }
    public int addUser(User user){
        return userMapper.addUser(user);
    }

}
