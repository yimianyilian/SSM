package com.yimian.controller;


import com.yimian.domain.Role;
import com.yimian.domain.UserInfo;
import com.yimian.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private IUserService userService ;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception{
      ModelAndView mv = new ModelAndView();
      List<UserInfo> userList = userService.findAll();
      mv.addObject("userList",userList);
      mv.setViewName("user-list");
        return mv;
    }



    @RequestMapping("/save.do")
    public String save(UserInfo userInfo) throws Exception{
        userService.save(userInfo);
        return "redirect:findAll.do";

    }



    //查询指定ID的用户
    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) throws Exception{
        ModelAndView mv= new ModelAndView();
        UserInfo userInfo = userService.findById(id);
        mv.addObject("user",userInfo);
        mv.setViewName("user-show");
        return mv;
    }

    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name="id",required = true)  String userid) throws Exception{
        ModelAndView mv= new ModelAndView();
        UserInfo userInfo = userService.findById(userid);
        List<Role> otherRoles =  userService.findOtherRoles(userid);

        mv.addObject("user",userInfo);
        mv.addObject("roleList",otherRoles);
        mv.setViewName("user-role-add");

        return mv;
    }

@RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name="userId",required =true)   String  userId,@RequestParam(name="ids",required =true) String[] roleIds) throws Exception{

      userService.addRoleToUser(userId,roleIds);

       return "redirect:findAll.do";
    }

}
