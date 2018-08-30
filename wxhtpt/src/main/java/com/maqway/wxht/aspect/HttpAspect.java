package com.maqway.wxht.aspect;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Created by Ma.li.ran on 2017/09/23 16:46
 * 统一日志
 * AOP拦截处理
 */
@Aspect
@Component//注入spring
public class HttpAspect {

  private final static Logger logger = (Logger) LoggerFactory.getLogger(HttpAspect.class);

  @Pointcut("execution(* com.maqway.wxht.controller.*.*(..))")
  public void log() {
  }

  @Before("log()")
  public void doBefore(JoinPoint joinPoint) {
    //获取url
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
        .getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    logger.info("url={}", request.getRequestURL());
    //请求方式
    logger.info("method={}", request.getMethod());
    //ip
    logger.info("ip={}", request.getRemoteAddr());
    //请求类方法
    logger.info("class_method={}",
        joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    //请求参数
    logger.info("args={}", joinPoint.getArgs());
  }

  @After("log()")
  public void doAfter() {
//    logger.info("您已登录");
  }

//    @AfterReturning(returning = "object",pointcut = "log()")
//    public void doAfterReturning(Object object){
//        logger.info("response={}",object.toString());
//    }
}
