package com.cnj.security.config;

import com.alibaba.fastjson.JSONObject;
import com.cnj.common.util.HttpRequestUtil;
import com.cnj.common.util.ResultUtils;
import com.cnj.security.config.handler.CustomAuthenticationFailureHandler;
import com.cnj.security.config.handler.CustomAuthenticationSuccessHandler;
import com.cnj.security.config.handler.CustomLogoutSuccessHandler;
import com.cnj.security.config.properties.SecurityUser;
import com.cnj.security.service.CustomUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by hsdev-05 on 2019-02-21.
 */
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserService customUserService;

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Autowired
    SecurityUser securityUser;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(customUserService)
                .passwordEncoder(this.customPasswordEncoder());
        auth.inMemoryAuthentication()
                .withUser(securityUser.getName()).password(securityUser.getPassword()).roles(securityUser.getRole());

    }

    @Bean
    public PasswordEncoder customPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/static/**","/login","/css/**","/js/*","/image/*")
                .permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                //.failureHandler(customAuthenticationFailureHandler)
                //.successHandler(customAuthenticationSuccessHandler)
            .and()
                .httpBasic()
                .authenticationEntryPoint((request, response, authException) -> {
                    if (HttpRequestUtil.isJsonRequest(request)){
                        try (PrintWriter writer = response.getWriter()){
                            response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
                            writer.write(JSONObject.toJSONString(ResultUtils.error(authException.getMessage())));
                        }
                    }else {
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
                    }
                })
                .authenticationDetailsSource(new WebAuthenticationDetailsSource())
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessHandler(customLogoutSuccessHandler)
            .and()
                .csrf()
                .ignoringAntMatchers("/login")
            .and()
                .exceptionHandling()
                .accessDeniedHandler((request,response,accessDeniedException) -> {
                    if (HttpRequestUtil.isJsonRequest(request)){
                        try (PrintWriter writer = response.getWriter()){
                            response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
                            writer.write(JSONObject.toJSONString(ResultUtils.error(accessDeniedException.getMessage())));
                        }
                    }else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN,accessDeniedException.getMessage());
                    }
                });
        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        //filter.setFilterProcessesUrl("/login");
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
