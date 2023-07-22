package com.example.service;

import com.example.dao.ManagerMapper;
import com.example.entity.Manager;
import com.example.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("ManagerService")
public class ManagerService{
    @Resource(name = "ManagerMapper")
    private ManagerMapper managerMapper;

    public Map<String,Object> loginIn(Manager managerLogin){
        Map<String, Object> map = new HashMap<>();
        List<Manager> loginList =managerMapper.findByManagername(managerLogin.getManagerName());
        if(loginList.isEmpty()) {
            map.put("message","用户名错误");
            return map;
        } else{
            String viewPassword=managerLogin.getManagerPassword();
            String viewState = loginList.get(0).getManagerState();
            if(!viewPassword.equals(loginList.get(0).getManagerPassword())) map.put("message","密码错误");
            else if(viewState.equals("1")){map.put("message","该账号已禁用");}
            else {
                map.put("message","登录成功");
            }
            return map;
        }
    }
    public PageInfo<Manager> managers(int pageNum ){
        PageHelper.startPage(pageNum,5);
        List<Manager> managerList = managerMapper.findAll();
        PageInfo<Manager> pageInfo = new PageInfo<>(managerList);
        return pageInfo;
    }
//    public List<Manager> managers(){
//        return managerMapper.findAll();
//    }

    public List<Manager> searchManage(String keyword){
        return managerMapper.findBykeyword(keyword);
    }
}