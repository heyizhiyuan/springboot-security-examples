package com.cnj.security.config.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@ConfigurationProperties(prefix = "spring.security.user")
public class SecurityUser {

    private String name;

    private String password;

    private String role;

}
