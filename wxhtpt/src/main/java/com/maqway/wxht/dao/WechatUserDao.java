package com.maqway.wxht.dao;

import com.maqway.wxht.entity.WechatUser;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/20 09:39
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface WechatUserDao {

  WechatUser queryWechatUserByOpenId();

  int insertWechatUser(WechatUser wechatUser);

}
