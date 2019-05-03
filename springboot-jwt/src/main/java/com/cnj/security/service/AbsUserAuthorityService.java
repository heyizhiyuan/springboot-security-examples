package com.cnj.security.service;

import com.cnj.security.dao.SysUserRepository;
import com.cnj.security.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Create by cnj on 2019-5-3
 */
@Service
public abstract class AbsUserAuthorityService implements UserDetailsService,UserAuthorityService<SysUser> {

    @Autowired
    SysUserRepository sysUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser userDetails = sysUserRepository.findByUserName(username);
        if(Objects.isNull(userDetails)){
            throw new UsernameNotFoundException(String.format("username=%s",username));
        }
        return loadUserAuthorities(userDetails);
    }

}
