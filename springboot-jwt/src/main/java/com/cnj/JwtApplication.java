package com.cnj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class JwtApplication {

    public static void main(String[] args) {

        SpringApplication.run(JwtApplication.class, args);

    }

}
