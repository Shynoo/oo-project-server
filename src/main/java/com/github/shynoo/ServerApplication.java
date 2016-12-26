package com.github.shynoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class ServerApplication{
    
    public static void main(String[] args){
        SpringApplication.run(ServerApplication.class, args);
    }
    
}
