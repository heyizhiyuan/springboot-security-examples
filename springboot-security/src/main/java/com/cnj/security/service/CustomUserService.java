package com.cnj.security.service;

import com.cnj.security.dao.SysUserRepository;
import com.cnj.security.domain.SysRole;
import com.cnj.security.domain.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by hsdev-05 on 2019-02-21.
 */
@Service
@Slf4j
public class CustomUserService implements UserDetailsService {

    @Autowired
    SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser userDetails = sysUserRepository.findByUserName(userName);
        if(Objects.isNull(userDetails)){
            throw new UsernameNotFoundException(String.format("userName=%s",userName));
        }
        return userDetails;
    }

}















