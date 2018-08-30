package com.maqway.wxht.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码校验类
 * Ma.li.ran
 * 2017/11/2 0002 11:59
 */
public class CodeUtil {
  public static boolean checkVerifyCode(HttpServletRequest request) {
    String verifyCodeExpected = (String) request.getSession()
        .getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
    String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCode");
    if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
      return false;
    }
    return true;
  }

}
