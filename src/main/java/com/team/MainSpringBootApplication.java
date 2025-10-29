package com.team;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:8081",allowCredentials = "true")
@SpringBootApplication
@MapperScan(basePackages ="com.team.mapper")
public class MainSpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainSpringBootApplication.class, args);
    }
}
