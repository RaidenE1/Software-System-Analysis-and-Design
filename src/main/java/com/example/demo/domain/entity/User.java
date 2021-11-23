
package com.example.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class User {
	@Id
	private int id;
	private String username;
	private String password;
	private String email;
	private String realName;   // 真实姓名，有学术成果的话
	private String company;     // 所属单位(研究机构或者学校)
	private String resume;      //
}
