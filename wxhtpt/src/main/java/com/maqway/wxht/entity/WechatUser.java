package com.maqway.wxht.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/20 09:32
 * @desc: 微信用户信息
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class WechatUser {

  private Integer wechatId;//主键
  private String wechatName;//名称
  private String wechatSex;//性别
  private String wechatOpenId;//openid
  private String wechatAddress;//地址
  private Date wechatCreateTime;//创建时间
  private String wechatHeadImg;//头像

  public Integer getWechatId() {
    return wechatId;
  }

  public void setWechatId(Integer wechatId) {
    this.wechatId = wechatId;
  }

  public String getWechatName() {
    return wechatName;
  }

  public void setWechatName(String wechatName) {
    this.wechatName = wechatName;
  }

  public String getWechatSex() {
    return wechatSex;
  }

  public void setWechatSex(String wechatSex) {
    this.wechatSex = wechatSex;
  }

  public String getWechatOpenId() {
    return wechatOpenId;
  }

  public void setWechatOpenId(String wechatOpenId) {
    this.wechatOpenId = wechatOpenId;
  }

  public String getWechatAddress() {
    return wechatAddress;
  }

  public void setWechatAddress(String wechatAddress) {
    this.wechatAddress = wechatAddress;
  }

  public Date getWechatCreateTime() {
    return wechatCreateTime;
  }

  public void setWechatCreateTime(Date wechatCreateTime) {
    this.wechatCreateTime = wechatCreateTime;
  }

  public String getWechatHeadImg() {
    return wechatHeadImg;
  }

  public void setWechatHeadImg(String wechatHeadImg) {
    this.wechatHeadImg = wechatHeadImg;
  }
}
