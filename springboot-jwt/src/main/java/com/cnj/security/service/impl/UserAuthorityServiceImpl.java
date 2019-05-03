package com.cnj.security.service.impl;

import com.cnj.security.domain.SysRole;
import com.cnj.security.domain.SysUser;
import com.cnj.security.service.AbsUserAuthorityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Create by cnj on 2019-5-3
 */
@Service
public class UserAuthorityServiceImpl extends AbsUserAuthorityService {
    @Override
    public UserDetails loadUserAuthorities(SysUser user) {
        if ("admin".equals(user.getUsername())){
            user.setRoleSet(new HashSet(Arrays.asList(new SysRole("1","admin","a",""))));
        }else{
            user.setRoleSet(new HashSet(Arrays.asList(new SysRole("1","user","a",""))));
        }
        return user;
    }
}

















