package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Notice {
    //分别是 公告序号，公告标题，公告内容，公告发布日期，公告编写人
    private int noticeId;
    private String noticeTitle;
    private String noticeContent;
    @JsonFormat(locale = "zh",pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date noticeDate;
    private String noticeAuthor;
}
