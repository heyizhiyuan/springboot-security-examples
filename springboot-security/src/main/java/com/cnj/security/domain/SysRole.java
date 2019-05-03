package com.cnj.security.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by hsdev-05 on 2019-02-22.
 */
@Data
@Entity(name="Sys_Role")
public class SysRole implements GrantedAuthority,Serializable {

    @Id
    private String id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    private String remark;


    @Override
    public String getAuthority() {
        return code;
    }
}











