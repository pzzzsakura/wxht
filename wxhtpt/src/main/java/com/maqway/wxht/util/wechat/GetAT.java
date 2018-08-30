package com.maqway.wxht.util.wechat;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Ma.li.ran
 * 2017/10/27 0027 16:38
 */
public class GetAT {

  /**
   * 获取access_token
   * @return
   * @throws IOException
   */
  public static Map<String,String> getTicket() throws  IOException {
    Map<String,String> map = new HashMap<String,String>();
    String turl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ ConfigUtil.APPID
        +"&secret="+ConfigUtil.APP_SECRECT;
    JSONObject jsonObject = AuthUtil.doGetJson(turl);
    String access_token = jsonObject.getString("access_token");
    String expires_in = String.valueOf(jsonObject.getInteger("expires_in"));
    String turl2 = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+access_token
        +"&type=jsapi";
    JSONObject jsonObject1 = AuthUtil.doGetJson(turl2);
    String ticket = jsonObject1.getString("ticket");
    map.put("expires_in", expires_in);
    map.put("access_token", access_token);
    map.put("ticket", ticket);
    return map;
  }
}
