package com.cnj.security.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by hsdev-05 on 2019-02-22.
 */
@Data
@Entity(name = "Sys_Menu")
public class SysMenu {

    @Id
    private String id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String path;

    private String component;

    private String name;

    private String icon;

    private String keepAlive;

    private String requireAuth;

    private String parentId;

    @Column(columnDefinition = "int default 1")
    private Integer enabled;

}
