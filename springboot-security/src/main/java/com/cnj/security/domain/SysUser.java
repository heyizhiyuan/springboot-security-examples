package com.cnj.security.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hsdev-05 on 2019-02-21.
 */
@Entity(name = "sys_user")
@Data
public class SysUser implements UserDetails, Serializable {

    @Id
    private String userId;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "int default 0")
    private Integer isLocked;

    @Transient
    private Set<SysRole> roleSet;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Objects.isNull(roleSet)||roleSet.isEmpty()
                ? AuthorityUtils.NO_AUTHORITIES
                : AuthorityUtils.createAuthorityList(
                    roleSet
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toArray(String[]::new));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isLocked==0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
