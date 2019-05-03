package com.cnj.security.config.filter;

import com.cnj.security.config.JwtAuthenticationProducer;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Create by cnj on 2019-5-2
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends GenericFilterBean {

    private List<String> visaFreeUrls;

    JwtAuthenticationTokenFilter(){
        this.visaFreeUrls = Arrays.asList();
    }

    public JwtAuthenticationTokenFilter(String... visaFreeUrls){
        this.visaFreeUrls = Arrays.asList(visaFreeUrls);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        SecurityContext securityContext =  SecurityContextHolder.getContext();
        if (this.visaFreeUrls.contains(httpServletRequest.getServletPath())){
            filterChain.doFilter(request,response);
            return;
        }
        try{
            Authentication authentication = JwtAuthenticationProducer.generateAuthentication(httpServletRequest);
            securityContext.setAuthentication(authentication);
            filterChain.doFilter(request,response);
        }catch (SignatureException e){
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,e.getMessage());
            throw e;
        }
    }
}
