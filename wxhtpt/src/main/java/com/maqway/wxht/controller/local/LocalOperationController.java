package com.maqway.wxht.controller.local;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/25 20:23
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Controller
@RequestMapping(value = "/local")
public class LocalOperationController {
  /**
   * 注册帐号页路由
   *
   * @return
   */
  @RequestMapping(value = "accountbind", method = RequestMethod.GET)
  private String accountbind() {
    return "local/accountbind";
  }
  /**
   * 修改信息页路由
   *
   * @return
   */
  @RequestMapping(value = "/changepsw", method = RequestMethod.GET)
  private String changepsw() {
    return "local/changepsw";
  }
  /**
   * 登录页路由
   *
   * @return
   */
  @RequestMapping(value = "/login", method = RequestMethod.GET)
  private String login() {
    return "local/login";
  }
}
