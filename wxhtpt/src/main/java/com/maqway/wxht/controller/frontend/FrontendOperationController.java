package com.maqway.wxht.controller.frontend;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/09 19:11
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Controller
@RequestMapping("frontend")
public class FrontendOperationController {

  @RequestMapping(value = "wxmanagelist",method = RequestMethod.GET)
  private String wxManageList(){
    return "/frontend/wxmanagelist";
  }
  @RequestMapping(value = "index",method = RequestMethod.GET)
  private String index(){
    return "/frontend/index";
  }
  @RequestMapping(value = "wxdetail",method = RequestMethod.GET)
  private String wxdetail(){
    return "/frontend/wxdetail";
  }
  @RequestMapping(value = "wxmanagedetail",method = RequestMethod.GET)
  private String wxManageDetail(){
    return "/frontend/wxmanagedetail";
  }
}
