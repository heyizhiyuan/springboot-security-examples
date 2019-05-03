package com.cnj.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by cnj on 2019-5-3
 */
public class JwtAuthenticationProducer {

    public static final long EXPIRATION = 60_000;

    public static final String TOKEN_PREFIX = "Bearer";        // Token前缀

    public static final String HEADER_NAME = "Authorization";

    public static final String AUTH_KEY = "authorities";

    public static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateJwtToken(Authentication authentication){
        return Jwts.builder()
                .claim(AUTH_KEY,authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()))
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Authentication generateAuthentication(HttpServletRequest request) throws SignatureException {
        // 从Header中拿到token
        String token = request.getHeader(HEADER_NAME);
        if (token == null){
            return null;
        }
        String user;
        List<GrantedAuthority> authorities;
        try {
            Claims claims = Jwts.parser()
                    // 验签
                    .setSigningKey(SECRET_KEY)
                    // 去掉 Bearer
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            // 拿用户名
            user = claims.getSubject();
            // 得到 权限（角色）
            authorities =  AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get(AUTH_KEY));
        }catch (SignatureException e){
            throw e;
        }
            // 解析 Token

        // 返回验证令牌
        return user != null
                ? new UsernamePasswordAuthenticationToken(user, null, authorities)
                : null;
    }

}
