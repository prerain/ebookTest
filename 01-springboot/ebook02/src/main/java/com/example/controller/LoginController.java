package com.example.controller;

import com.example.entity.Manager;
import com.example.service.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller

public class LoginController {
    @Resource(name = "ManagerService")
    private ManagerService managerService;
    @GetMapping("login")
    public String login(){return "login";}
    @PostMapping("login")
    public String loginIn(String username,String password, Model model, HttpSession session) {
        Manager managerLogin = new Manager(username,password);
        Map<String, Object> map = managerService.loginIn(managerLogin);
        if (map.get("message").equals("登录成功")){
            session.setAttribute("manager",managerLogin);
            return "mainindex";
        }
        else {
            model.addAttribute("str", map.get("message"));
            return "login";
        }
    }
    @RequestMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.invalidate();
        return "login";
    }
}
