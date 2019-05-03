package com.cnj.security.service;

import com.cnj.security.domain.SysUser;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Create by cnj on 2019-5-3
 */
public interface UserAuthorityService<T extends UserDetails> {

    UserDetails loadUserAuthorities(T user);

}
