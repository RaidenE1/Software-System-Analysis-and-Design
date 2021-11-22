package com.example.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Share {
    @Id
    private int shareid;
    private int shareruserid;
    private String sharecontent;
    private Date sharecreatetime;
    private int supportcnt;
    private int unsupportcnt;
}
