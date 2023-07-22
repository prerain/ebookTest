package com.example.controller;

import com.example.entity.Notice;
import com.example.service.NoticeService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Resource(name = "NoticeService")
    private NoticeService noticeService;
    @RequestMapping("list")
    public String Noticelist(Model model,@RequestParam(value = "pageNum",defaultValue = "1")int pageNum){
        PageInfo<Notice> pageInfo = noticeService.notices(pageNum);
        List<Notice> noticeList = pageInfo.getList();
        if (noticeList.size()>0){
            model.addAttribute("pageInfo",pageInfo);
            model.addAttribute("noticeList",noticeList);
        }else {
            model.addAttribute("msg","暂无数据");
        }
        return "notice/notice-list";
    }

    @PostMapping("search")
    public String NoticeSearch(String keyword,Model model){
        List<Notice> noticesearchList = noticeService.noticesearch(keyword);
        if(noticesearchList.size() > 0){
            model.addAttribute("noticeList",noticesearchList);
        }else{
            model.addAttribute("msg","暂无数据请重新查询");
        }
        return "notice/notice-list";
    }

    @GetMapping("add")
    public String Noticeadd(){
        return "notice/notice-add";
    }
    @PostMapping("add")
    public String Noticeadd(Notice notice, Model model) {
        System.out.println(notice);
        if (noticeService.addNotice(notice) == 1) {
            return "redirect:/notice/list";
        } else {
            model.addAttribute("str", "公告添加失败");
            return "error";
        }
    }

    @GetMapping("update")
    public String Noticeupdate(int noticeId,Model model){
        System.out.println(noticeId);
        Notice notice =noticeService.findbyId(noticeId);
        model.addAttribute("notice",notice);
        return "notice/notice-update";
    }
    @PostMapping("update")
    public String Noticeupdate(Notice notice,Model model){
        if(noticeService.updateNotice(notice)==1){
            return "redirect:/notice/list";
        }else {
            model.addAttribute("str","公告编辑失败");
            return "error";
        }
    }

    @RequestMapping("delete")
    public String Noticedelete(int noticeId,Model model){
        if(noticeService.deleteNotice(noticeId)==1){
            return "redirect:/notice/list";
        }else {
            model.addAttribute("str","公告删除失败");
            return "error";
        }
    }

    @InitBinder
    public void initBinder(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(java.util.Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
