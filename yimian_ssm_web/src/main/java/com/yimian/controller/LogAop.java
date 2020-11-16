package com.yimian.controller;


import com.yimian.domain.SysLog;
import com.yimian.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {
    @Autowired
    private HttpServletRequest request;


    @Autowired
    private ISysLogService sysLogService;
    private Date visitTime;
    private Class clazz;
    private Method method;

    //前置通知  主要是获取开始时间
    @Before("execution(* com.yimian.controller.*.*(..))")
    public void doBefore(JoinPoint jp) throws Exception{
       visitTime= new Date();
       clazz = jp.getTarget().getClass();
      String methodName = jp.getSignature().getName();
      Object[] args = jp.getArgs();
      if(args==null || args.length==0) {
          method = clazz.getMethod(methodName);//只能获取无参数的方法
      }else{
          Class[] classArgs = new Class[args.length];
          for(int i=0;i<args.length;i++) {
             classArgs[i]=args[i].getClass();
          }
          clazz.getMethod(methodName,classArgs);
      }

    }

    @After("execution(* com.yimian.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
        String url="";
        long time= new Date().getTime()-visitTime.getTime();

         if(clazz!= null&& method!=null && clazz!= LogAop.class){
        RequestMapping  ClassAnnotation   = ( RequestMapping )   clazz.getAnnotation(RequestMapping.class);
         if(ClassAnnotation !=null){
             String[] classValue = ClassAnnotation.value();
             RequestMapping methodAnnotation   = method.getAnnotation(RequestMapping.class);
             if(methodAnnotation!=null){
                 String[] methodValue = methodAnnotation.value();
                 url = classValue[0] + methodValue[0];
                 //获取访问的ip
                 String ip = request.getRemoteAddr();

                 //获取当前操作的用户
                 SecurityContext context = SecurityContextHolder.getContext();//从上下文中获了当前登录的用户
                 User user = (User) context.getAuthentication().getPrincipal();
                 String username = user.getUsername();
                 //将日志相关信息封装到SysLog对象
                 SysLog sysLog = new SysLog();
                 sysLog.setExecutionTime(time); //执行时长
                 sysLog.setIp(ip);
                 sysLog.setMethod("[类名] " + clazz.getName() + "[方法名] " + method.getName());
                 sysLog.setUrl(url);
                 sysLog.setUsername(username);
                 sysLog.setVisitTime(visitTime);

                 //调用Service完成操作
                 sysLogService.save(sysLog);

             }
           }
         }

    }
}
