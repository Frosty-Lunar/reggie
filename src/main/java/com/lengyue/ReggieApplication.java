package com.lengyue;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类
 *
 * @author 陌年
 * @date 2022/12/20
 */
@SpringBootApplication
@Slf4j
@MapperScan(value = "com.lengyue.mapper")
@ServletComponentScan
@EnableTransactionManagement
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class, args);
    }
}
