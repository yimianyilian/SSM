package com.yimian.service;

import com.yimian.domain.Role;
import com.yimian.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService  extends UserDetailsService {
    public List<UserInfo>  findAll() throws Exception;


    void save(UserInfo userInfo)throws Exception;

    UserInfo findById(String id) throws Exception;

    List<Role> findOtherRoles(String userId) throws Exception;

    void addRoleToUser(String userId, String[] roleIds) throws Exception;
}
