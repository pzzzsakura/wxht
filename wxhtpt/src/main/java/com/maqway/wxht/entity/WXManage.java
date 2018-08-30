package com.maqway.wxht.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/24 16:14
 * @desc: 微信号组
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class WXManage {

  private Integer wxManageId;
  private String wxManageName;
  private String wxManageDesc;
  private String wxManageImg;
  private Integer priority;
  private Date createTime;
  private Date updateTime;
  private Integer enableStatus;
  private String advice;
  private User user;
  private WXManageCategory wxManageCategory;


  public Integer getWxManageId() {
    return wxManageId;
  }

  public void setWxManageId(Integer wxManageId) {
    this.wxManageId = wxManageId;
  }

  public String getWxManageName() {
    return wxManageName;
  }

  public void setWxManageName(String wxManageName) {
    this.wxManageName = wxManageName;
  }

  public String getWxManageDesc() {
    return wxManageDesc;
  }

  public void setWxManageDesc(String wxManageDesc) {
    this.wxManageDesc = wxManageDesc;
  }

  public String getWxManageImg() {
    return wxManageImg;
  }

  public void setWxManageImg(String wxManageImg) {
    this.wxManageImg = wxManageImg;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
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

  public String getAdvice() {
    return advice;
  }

  public void setAdvice(String advice) {
    this.advice = advice;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public WXManageCategory getWxManageCategory() {
    return wxManageCategory;
  }

  public void setWxManageCategory(WXManageCategory wxManageCategory) {
    this.wxManageCategory = wxManageCategory;
  }
}
