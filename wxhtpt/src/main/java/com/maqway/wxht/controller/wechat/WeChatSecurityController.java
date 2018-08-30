package com.maqway.wxht.controller.wechat;

import com.maqway.wxht.dto.TextMessage;
import com.maqway.wxht.util.wechat.ConfigUtil;
import com.maqway.wxht.util.wechat.MessageUtil;
import com.maqway.wxht.util.wechat.SignUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * Ma.li.ran 2017/10/27  12:47
 */
@RestController
@RequestMapping("/wechat")
public class WeChatSecurityController {

  private static Logger logger = Logger.getLogger(WeChatSecurityController.class);

  @RequestMapping(method = {RequestMethod.GET})
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    String signature = req.getParameter("signature");
    String timestamp = req.getParameter("timestamp");
    String nonce = req.getParameter("nonce");
    String echostr = req.getParameter("echostr");
    if (SignUtil.checkSignature(signature, timestamp, nonce)) {
      PrintWriter out = null;
      try {
        out = resp.getWriter();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      out.println(echostr);
      out.close();
    } else {
      logger.info("配置失败");
    }
  }

  @RequestMapping(method = {RequestMethod.POST})
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException, DocumentException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    String serverPath = "";
    serverPath = req.getServletContext().getRealPath("/");
    PrintWriter out = resp.getWriter();
    Map<String, String> map;
    try {
      map = MessageUtil.xmlToMap(req);
      String fromUserName = map.get("FromUserName");
      System.out.println(fromUserName);
      String toUserName = map.get("ToUserName");
      String msgType = map.get("MsgType");
      String message = "";
      TextMessage text = new TextMessage();
      String content = "";
      content = "这是一个微信互推平台!在这里你能找到你想要的微信也能推广你自己的微信号！\n联系合作请直接回复\n或加qq:3336860352\n<a href='http://www.maqway.com/wxht/frontend/index'>1、进入微信公众号互推平台</a>\n<a href='http://www.maqway.com/wxht/local/login'>2、去推广自己的微信号</a>\n";
      text.setContent(content);
      text.setCreateTime(new Date().toString());
      text.setFromUserName(toUserName);
      text.setToUserName(fromUserName);
      text.setMsgType("text");
      message = MessageUtil.textMessageToXml(text);
      out.print(message);
      out.close();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
