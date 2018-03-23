package com.sound.haolei.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan(value = "com.sound.haolei.provider.dao")
public class DubboProviderDoMain {
    public static void main(String[] args) {
        SpringApplication.run(DubboProviderDoMain.class,args);
    }

}
