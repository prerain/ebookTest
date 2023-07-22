package com.example.dao;

import com.example.entity.Book;
import com.example.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("NoticeMapper")
public interface NoticeMapper {
    List<Notice> findAll();

    List<Notice> findNoticeBykeyword(@Param("keyword") String keyword);

    int updateNotice(Notice notice);

    Notice findbyId(@Param("noticeId")int noticeId);

    int addNotice(Notice notice);

    int deleteNotice(@Param("noticeId")int noticeId);

}
