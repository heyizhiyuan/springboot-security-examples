package com.cnj.security.dao;

import com.cnj.security.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by hsdev-05 on 2019-02-22.
 */
@Repository
public interface SysRoleRepository extends JpaRepository<SysRole,String> {

}
