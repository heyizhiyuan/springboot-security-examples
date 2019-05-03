package com.cnj.security.dao;

import com.cnj.security.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hsdev-05 on 2019-02-21.
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser,String> {

    SysUser findByUserName(String logonId);

}
