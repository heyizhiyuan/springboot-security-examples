package com.cnj.security.config.handler;

import com.alibaba.fastjson.JSON;
import com.cnj.common.util.HttpRequestUtil;
import com.cnj.common.util.ResultUtils;
import com.cnj.security.config.JwtAuthenticationProducer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Create by cnj on 2019-2-24
 */
@Component
@Slf4j
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        if (HttpRequestUtil.isJsonRequest(httpServletRequest)){
            String jwtToken = JwtAuthenticationProducer.generateJwtToken(authentication);
            httpServletResponse.addHeader(JwtAuthenticationProducer.HEADER_NAME, jwtToken);
            httpServletResponse.setContentType("application/json;charset=utf-8");
            PrintWriter out = httpServletResponse.getWriter();
            out.write(JSON.toJSONString(ResultUtils.ok(jwtToken,"ok")));
            out.flush();
            out.close();
        }else{
            super.setDefaultTargetUrl("/index");
            super.onAuthenticationSuccess(httpServletRequest,httpServletResponse,authentication);
        }
    }
}
