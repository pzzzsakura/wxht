package com.maqway.wxht.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/24 16:09
 * @desc: 用户
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class User {

  private Integer userId;
  private String userName;
  private Integer userSex;
  private String userPhone;
  private String userPsw;
  private String userImg;
  private Date createTime;
  private Date updateTime;
  private Integer enableStatus;


  public String getUserImg() {
    return userImg;
  }

  public void setUserImg(String userImg) {
    this.userImg = userImg;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getUserSex() {
    return userSex;
  }

  public void setUserSex(Integer userSex) {
    this.userSex = userSex;
  }

  public String getUserPhone() {
    return userPhone;
  }

  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }

  public String getUserPsw() {
    return userPsw;
  }

  public void setUserPsw(String userPsw) {
    this.userPsw = userPsw;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getEnableStatus() {
    return enableStatus;
  }

  public void setEnableStatus(Integer enableStatus) {
    this.enableStatus = enableStatus;
  }
}
