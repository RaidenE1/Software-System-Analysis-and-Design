package com.example.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * Follows {@code Follows} 类表示用户与用户之间的关注关系。
 *
 * @param follow_id 表示数据库中的唯一id
 * @param userid 表示被关注者的userid
 * @param follower_userid 表示关注者的userid
 * 关系为 follower_userid 关注 userid
 */


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Follows {
    @Id
    private int followId;
    private int userId;
    private int followerUserid;
}
