package com.cnj;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.Size;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@Slf4j
public class DemoApplicationTests {

    BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

    @Test
    public void contextLoads() {
        log.info("#{}",passwordEncoder.encode("123"));

    }

}
