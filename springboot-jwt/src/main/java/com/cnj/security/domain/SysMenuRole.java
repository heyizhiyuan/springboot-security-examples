package com.cnj.security.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by hsdev-05 on 2019-02-22.
 */
@Data
@Entity(name = "Sys_Menu_Role")
public class SysMenuRole {

    @Id
    private String id;

    @Column(nullable = false)
    private String menuId;

    @Column(nullable = false)
    private String roleId;

}
