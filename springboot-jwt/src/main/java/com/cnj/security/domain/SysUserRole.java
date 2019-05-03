package com.cnj.security.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by hsdev-05 on 2019-02-22.
 */
@Data
@Entity(name = "sys_user_role")
public class SysUserRole {

    @Id
    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String roleId;


}
