package com.maqway.wxht.interceptor;

import com.alibaba.fastjson.JSON;
import com.maqway.wxht.Enums.ResultEnum;
import com.maqway.wxht.Exception.UserOperationException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/11 15:45
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    int userId = (int) request.getSession().getAttribute("userId");
    if(userId!=-1){
      return true;
    }else{
      throw new UserOperationException(ResultEnum.NOTLOGIN.getState(),ResultEnum.NOTLOGIN.getStateInfo());
    }
  }
}
