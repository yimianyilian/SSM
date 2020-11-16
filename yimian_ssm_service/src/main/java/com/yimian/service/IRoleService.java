package com.yimian.service;

import com.yimian.domain.Permission;
import com.yimian.domain.Role;

import java.util.List;

public interface IRoleService {

    public List<Role> findAll()  throws Exception;

    void save(Role role) throws Exception;

    Role findById(String roleId) throws Exception;

    List<Permission> findOtherPermissions(String roleId) throws Exception;

    void addPermissionToRole(String roleId, String[] ids)throws Exception;
}
