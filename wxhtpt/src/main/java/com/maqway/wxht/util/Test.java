package com.maqway.wxht.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/20 22:30
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public class Test {

  public static void main(String[] args) throws ParseException {
    Scanner ds = new Scanner(System.in);
    String[] a = new String[4];
    for(int i=0;i<4;i++){
       a[i] = ds.next();
    }
    String b = new String(a[0]+a[1]+":"+a[2]+a[3]+"00");
    DateFormat format=new SimpleDateFormat("HH:mm");
    Date d = format.parse(b);
    System.out.print(d);
  }
}
