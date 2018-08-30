package com.maqway.wxht.Exception;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 18:52
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class HeadLineOperationException extends RuntimeException {
  private int state;

  public HeadLineOperationException(int state,String msg) {
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
