package com.example.service;

import com.example.dao.NoticeMapper;
import com.example.entity.Notice;
import com.example.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("NoticeService")
public class NoticeService {
    @Resource(name = "NoticeMapper")
    private NoticeMapper noticeMapper;
    public PageInfo<Notice> notices(int pageNum){
        PageHelper.startPage(pageNum,5);
        List<Notice> noticeList = noticeMapper.findAll();
        PageInfo<Notice> pageInfo = new PageInfo<>(noticeList);
        return pageInfo;
    }
    public List<Notice> notices(){
        return noticeMapper.findAll();
    }
    public List<Notice> noticesearch(String keyword){
        return noticeMapper.findNoticeBykeyword(keyword);
    }
    public Notice findbyId(@Param("noticeId")int noticeId){
        return noticeMapper.findbyId(noticeId);
    }
    public int addNotice(Notice notice){
        return noticeMapper.addNotice(notice);
    }
    public int deleteNotice(@Param("noticeId")int noticeId){
        return noticeMapper.deleteNotice(noticeId);
    }
    public int updateNotice(Notice notice){
        return noticeMapper.updateNotice(notice);
    }

}
