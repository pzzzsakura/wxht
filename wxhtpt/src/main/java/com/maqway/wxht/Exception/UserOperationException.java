package com.maqway.wxht.Exception;

import com.maqway.wxht.Enums.ResultEnum;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/26 14:54
 * @desc: 用户异常
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class UserOperationException extends RuntimeException{

  private int state;

  public UserOperationException(int state,String msg) {
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
