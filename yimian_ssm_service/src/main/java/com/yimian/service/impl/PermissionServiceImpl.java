package com.yimian.service.impl;

import com.yimian.dao.IPermissionDao;
import com.yimian.domain.Permission;
import com.yimian.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService {


   @Autowired
   private IPermissionDao permissionDao;
    @Override
    public List<Permission> findAll() throws Exception {
        return permissionDao.findAll() ;
    }


    @Override
    public void save(Permission permission) throws Exception {
        permissionDao.save(permission);
    }
}
