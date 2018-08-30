package com.maqway.wxht.entity;

import java.util.Date;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/24 16:12
 * @desc: 微信号信息
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class WX {

  private Integer wxId;
  private String wxName;
  private String wxDesc;
  private String wxImg;
  private Integer priority;
  private Date createTime;
  private Date updateTime;
  private Integer enableStatus;
  private WXManage wxManage;
  private List<WXImg> wxImgList;



  public List<WXImg> getWxImgList() {
    return wxImgList;
  }

  public void setWxImgList(List<WXImg> wxImgList) {
    this.wxImgList = wxImgList;
  }

  public Integer getWxId() {
    return wxId;
  }

  public void setWxId(Integer wxId) {
    this.wxId = wxId;
  }

  public String getWxName() {
    return wxName;
  }

  public void setWxName(String wxName) {
    this.wxName = wxName;
  }

  public String getWxDesc() {
    return wxDesc;
  }

  public void setWxDesc(String wxDesc) {
    this.wxDesc = wxDesc;
  }

  public String getWxImg() {
    return wxImg;
  }

  public void setWxImg(String wxImg) {
    this.wxImg = wxImg;
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

  public WXManage getWxManage() {
    return wxManage;
  }

  public void setWxManage(WXManage wxManage) {
    this.wxManage = wxManage;
  }
}
