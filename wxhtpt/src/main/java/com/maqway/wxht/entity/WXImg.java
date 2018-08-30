package com.maqway.wxht.entity;

import java.util.Date;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/24 16:19
 * @desc: 微信详情图
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class WXImg {

  private Integer imgId;
  private String imgAddr;
  private Integer priority;
  private Date createTime;
  private Integer wxId;

  public Integer getImgId() {
    return imgId;
  }

  public void setImgId(Integer imgId) {
    this.imgId = imgId;
  }

  public Integer getWxId() {
    return wxId;
  }

  public void setWxId(Integer wxId) {
    this.wxId = wxId;
  }

  public String getImgAddr() {
    return imgAddr;
  }

  public void setImgAddr(String imgAddr) {
    this.imgAddr = imgAddr;
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

}
