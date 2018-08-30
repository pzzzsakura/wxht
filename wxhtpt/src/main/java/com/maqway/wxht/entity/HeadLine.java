package com.maqway.wxht.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/24 16:07
 * @desc: 主页轮播图
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class HeadLine {

  private Integer lineId;
  private String lineName;
  private String lineLink;
  private String lineImg;
  private Integer priority;
  private Date createTime;
  private Date updateTime;
  private Integer enableStatus;

  public Integer getLineId() {
    return lineId;
  }

  public void setLineId(Integer lineId) {
    this.lineId = lineId;
  }

  public String getLineName() {
    return lineName;
  }

  public void setLineName(String lineName) {
    this.lineName = lineName;
  }

  public String getLineLink() {
    return lineLink;
  }

  public void setLineLink(String lineLink) {
    this.lineLink = lineLink;
  }

  public String getLineImg() {
    return lineImg;
  }

  public void setLineImg(String lineImg) {
    this.lineImg = lineImg;
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
}
