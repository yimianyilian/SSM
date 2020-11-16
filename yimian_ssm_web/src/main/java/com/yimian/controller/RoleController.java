package com.yimian.controller;


import com.yimian.domain.Permission;
import com.yimian.domain.Role;
import com.yimian.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/role")
@Controller
public class RoleController {


    @Autowired
    private IRoleService roleService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws  Exception{
     ModelAndView mv = new ModelAndView();
    List<Role> roleList=   roleService.findAll();
    mv.addObject("roleList",roleList);
    mv.setViewName("role-list");


        return mv;
    }


    @RequestMapping("/save.do")
    public String save(Role role) throws  Exception{
        roleService.save(role);
        return  "redirect:findAll.do";
    }

    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam(name="id",required= true)String  roleId) throws Exception{
        ModelAndView mv = new ModelAndView();
       Role role = roleService.findById(roleId);
       List<Permission> otherPermissions = roleService.findOtherPermissions(roleId);
        mv.addObject("role",role);
        mv.addObject("permissionList",otherPermissions);
        mv.setViewName("role-permission-add");
        return mv;
    }

    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(name="roleId",required= true)String  roleId,@RequestParam(name="ids",required= true )String[] ids) throws Exception{
        roleService.addPermissionToRole(roleId,ids);
        return "redirect:findAll.do";
    }
}
