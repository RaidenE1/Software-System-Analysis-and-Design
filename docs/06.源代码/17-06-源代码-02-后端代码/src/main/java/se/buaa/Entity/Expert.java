/*
 * @Author: your name
 * @Date: 2021-12-08 01:45:21
 * @LastEditTime: 2021-12-08 02:48:13
 * @LastEditors: your name
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: /Software-System-Analysis-and-Design/src/main/java/se/buaa/Entity/Expert.java
 */
package se.buaa.Entity;


import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;

@Entity
@Table(name = "expert")
public class Expert {
    @Id
    private String expertID;
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "org", length = 100)
    private String org;
    @Column(name = "domain", length = 100)
    private String domain;
    @Column(name = "cited_times")
    private Integer cited_Times;
    @Column(name = "achievements")//成果数
    private Integer achievements;
    @Column(name = "views")
    private Integer views;
    @Column(name = "is_verified")
    public Integer isVerified;
    public Integer is_focus;
    public Integer userID;

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public Integer getViews() {
        return views;
    }

    public String getOrg() {
        return org;
    }

    public String getDomain() {
        return domain;
    }

    public String getName() {
        return name;
    }

    public Integer getCited_Times() {
        return cited_Times;
    }

    public Integer getAchievements() {
        return achievements;
    }

    public String getExpertID() {
        return expertID;
    }

    //注入数据后执行该方法，用于处理非数据库字段，若该字段未设置get，set方法，则返回到前端无该字段
    @PostLoad
    public void test(){
        System.out.println("PostLoad success!");
    }
}



