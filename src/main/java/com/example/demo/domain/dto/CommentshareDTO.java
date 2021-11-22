package com.example.demo.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentshareDTO {
    private int commentshareid;
    private int shareid;                  //外键代表被链接
    private Date commentsharecreatetime;  //评论时间
    private String commentcontent;        //评论内容
}
