package com.cj.product.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.cj.product.core", "com.cj.product.api"})
@SpringBootApplication
public class CjProductApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CjProductApiApplication.class, args);
    }

}
