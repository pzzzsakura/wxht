package com.maqway.wxht.controller.wxmanage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/27 12:58
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Controller
@RequestMapping("wxmanage")
public class WXManageOperationController {

  @RequestMapping(value = "/wxmanage", method = RequestMethod.GET)
  private String wxManage() {
    return "wxmanage/wxmanage";
  }

  @RequestMapping(value = "/wxgrouplist", method = RequestMethod.GET)
  private String wxGroupList() {
    return "wxmanage/wxgrouplist";
  }

  @RequestMapping(value = "/addwxgroup", method = RequestMethod.GET)
  private String addGroup() {
    return "wxmanage/addWXGroup";
  }

  @RequestMapping(value = "/addwx", method = RequestMethod.GET)
  private String addWX() {
    return "wxmanage/addwx";
  }

  @RequestMapping(value = "/wxlist", method = RequestMethod.GET)
  private String wxList() {
    return "wxmanage/wxlist";
  }
}
