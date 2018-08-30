package com.maqway.wxht.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/24 16:17
 * @desc: 微信号组种类
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class WXManageCategory {

  private Integer wmcId;
  private String wmcName;
  private String wmcImg;
  private Integer priority;
  private Date createTime;
  private Date updateTime;
  private Integer parentId;

  public String getWmcName() {
    return wmcName;
  }

  public void setWmcName(String wmcName) {
    this.wmcName = wmcName;
  }

  public String getWmcImg() {
    return wmcImg;
  }

  public void setWmcImg(String wmcImg) {
    this.wmcImg = wmcImg;
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

  public Integer getWmcId() {
    return wmcId;
  }

  public void setWmcId(Integer wmcId) {
    this.wmcId = wmcId;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }
}
