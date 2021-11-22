package com.example.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

@Entity
public class Commentshare {
    @Id
    private int commentshareid;
    private int shareid;                  //外键代表被链接
    private Date commentsharecreatetime;  //评论时间
    private String commentcontent;        //评论内容

}
