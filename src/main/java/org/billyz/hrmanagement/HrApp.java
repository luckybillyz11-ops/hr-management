package org.billyz.hrmanagement;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("org.billyz.hrmanagement.dao")

public class HrApp {
    public static void main(String[] args) {
        SpringApplication.run(HrApp.class, args);
    }
}
