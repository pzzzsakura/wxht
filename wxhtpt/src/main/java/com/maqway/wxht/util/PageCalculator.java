package com.maqway.wxht.util;

/**
 * Ma.li.ran
 * 2017/11/3 0003 14:07
 */
public class PageCalculator {

  public static int caculateRowIndex(int pageIndex, int pageSize) {
    return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
  }
}
