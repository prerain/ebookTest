package com.example.controller;


import com.example.entity.Manager;
import com.example.entity.User;
import com.example.service.ManagerService;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {
    @Resource(name = "ManagerService")
    private ManagerService managerService;
    @Resource(name = "UserService")
    private UserService userService;

    @RequestMapping("userlist")
    //@@RequestParam(value = "pageNum",defaultValue = "1")，定义默认值为1，参数名为pageNUm
    public String Userlist(Model model,@RequestParam(value = "pageNum",defaultValue = "1")int pageNum){
        PageInfo<User> pageInfo = userService.users(pageNum);
        List<User> userList = pageInfo.getList();
//        List<User> userList =userService.users();
//        System.out.println(pageInfo);
//        System.out.println(userList);
        for (int i = 0; i <userList.size() ; i++) {
            if (userList.get(i).getUserState().equals("3")){
                userList.get(i).setUserState("正常");
            }else if (userList.get(i).getUserState().equals("4")){
                userList.get(i).setUserState("禁用");
            }
        }
        if(userList.size() > 0){
            model.addAttribute("userList",userList);
            model.addAttribute("pageInfo",pageInfo);
        }else{
            model.addAttribute("msg","暂无数据");
        }
        return "user/user-list";
    }
    @PostMapping("searchuser")
    public String SearchUsers(String keyword,Model model){
//       PageInfo<User> pageInfo = userService.searchUser(keyword,pageNum);
//       List<User> userList = pageInfo.getList();
        List<User> userList = userService.searchUser(keyword);
        for (int i = 0; i <userList.size() ; i++) {
            if (userList.get(i).getUserState().equals("3")){
                userList.get(i).setUserState("正常");
            }else if (userList.get(i).getUserState().equals("4")){
                userList.get(i).setUserState("禁用");
            }
        }
        if(userList.size() > 0){
            model.addAttribute("userList",userList);
        }else{
            model.addAttribute("msg","暂无数据请重新查询");
        }
        return "user/user-list";
    }


    @PostMapping("update")//update页面提交数据的位置
    public String Userupdate(User user, Model model){
        if(user.getUserPoint()==0){
            model.addAttribute("msg","用户积分不能为0");
            model.addAttribute("user",userService.findbyId(user.getUserId()));
            return "user/user-update";
        }else if(!user.getUserState().equals("3") && !user.getUserState().equals("4")){
            model.addAttribute("msg","请输入3或4");
            model.addAttribute("user",userService.findbyId(user.getUserId()));
            return "user/user-update";
        }
        if(userService.updateUser(user)==1){
            return "redirect:/user/userlist";
        }else{
            model.addAttribute("str","用户信息编辑失败");
            return "error";
        }
    }
    @GetMapping("update") //从list跳转update页面
    public String Userupdate(int userId,Model model){
        User user =  userService.findbyId(userId);
        model.addAttribute("user",user);
        return "user/user-update";
    }


    @RequestMapping("adminlist")
    public String Adminlist(Model model,@RequestParam(value = "pageNum",defaultValue = "1")int pageNum){
        PageInfo<Manager> pageInfo =managerService.managers(pageNum);
        List<Manager> managerList = pageInfo.getList();
        for (int i = 0; i <managerList.size() ; i++) {
            if (managerList.get(i).getManagerState().equals("0")){
                managerList.get(i).setManagerState("正常");
            }else if (managerList.get(i).getManagerState().equals("1")){
                managerList.get(i).setManagerState("禁用");
            }
        }
        if(managerList.size() > 0){
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("managerList",managerList);
        }else{
            model.addAttribute("msg","暂无数据");
        }
        return "user/manager-list";
    }
    @PostMapping("searchadmin")
    public String SearchAdmin(String keyword,Model model){
        List<Manager> managerList = managerService.searchManage(keyword);
        for (int i = 0; i <managerList.size() ; i++) {
            if (managerList.get(i).getManagerState().equals("0")){
                managerList.get(i).setManagerState("正常");
            }else if (managerList.get(i).getManagerState().equals("1")){
                managerList.get(i).setManagerState("禁用");
            }
        }
        if(managerList.size() > 0){
            model.addAttribute("managerList",managerList);
        }else{
            model.addAttribute("msg","暂无数据请重新查询");
        }
        return "user/manager-list";
    }


}
