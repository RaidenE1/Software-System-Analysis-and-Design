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
    private int shareId;
    private int sharerUserId;
    private String shareContent;
    private Date shareCreateTime;
    private int supportCnt;
    private int unSupportCnt;
}
