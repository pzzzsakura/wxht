package com.maqway.wxht.Enums;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/26 14:28
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public enum ResultEnum {
  SUCCESS(1,"成功"),
  NOTLOGIN(-1,"登录状态过期"),
  ARGSISNULL(8000,"入参为空"),
  ERROR(0,"系统错误"),
  PHONEISEXIST(8001,"该电话号已被注册"),
  NAMEISEXIST(8002,"该用户名已被注册"),
  PHONEISNONE(8003,"账号不存在"),
  PSWISERROR(8004,"密码错误"),
  VERIFYCODEERROR(8005,"验证码错误"),
  PHONEINVALID(8006,"手机号不匹配"),
  CANNOTDEL(8009,"此组还存在微信号成员不能删除"),
  IMAGEISNULL(8008,"上传图片不能为空"),
  WMCNAMEISEXIST(8007,"类名已存在");


  private int state;
  private String stateInfo;

  ResultEnum(int state, String stateInfo) {
    this.state = state;
    this.stateInfo = stateInfo;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public String getStateInfo() {
    return stateInfo;
  }

  public void setStateInfo(String stateInfo) {
    this.stateInfo = stateInfo;
  }
}
