package com.example.controller;

import com.example.entity.Book;
import com.example.entity.User;
import com.example.service.BookService;
import com.example.service.UserService;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

@RestController
@RequestMapping("app")
public class AppContoller {
    @Resource(name = "UserService")
    private UserService userService;
    @Resource(name = "BookService")
    private BookService bookService;

    @PostMapping("login")
    public List<User> AppUserLogin(@RequestParam String username){
        List<User> userList = userService.findUserByName(username);
        if(userList.size()==0){
            User user =new User();
            userList.add(user);
        }
        return userList;
    }
    @PostMapping("register")
    public String AppUserRegister(@RequestBody User user){
        System.out.println("user="+user);
        String username = user.getUserName();
        List<User> userList = userService.findUserByName(username);
        if (userList.size()==1){
            System.out.println("用户名已存在");
            return "exist";
        }else{
            int result = userService.addUser(user);
                if(result==1){
                    System.out.println("创建成功");
                    return "success";
                } else {return "error";}
        }
    }
    @PostMapping("searchbook")
    public List<Book> SearchBookNet(@RequestParam String keyword){
        System.out.println("keyword="+keyword);
        List<Book> bookList = bookService.findbyKeyword(keyword);
        if(bookList.size()==0){
            Book book =new Book();
            bookList.add(book);
        }
        return bookList;
    }
}
