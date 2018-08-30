package com.maqway.wxht.Exception;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 19:01
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class WXManageOperationException extends RuntimeException{

  private int state;

  public WXManageOperationException(int state,String msg) {
    super(msg);
    this.state =state;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }
}
