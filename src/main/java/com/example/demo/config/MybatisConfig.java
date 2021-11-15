package com.example.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.example.demo.dao.mapper")
@Configuration
public class MybatisConfig {
}
