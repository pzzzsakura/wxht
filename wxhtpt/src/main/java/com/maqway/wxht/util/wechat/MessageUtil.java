package com.maqway.wxht.util.wechat;

import com.maqway.wxht.dto.TextMessage;
import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MessageUtil {

	public static final String MESSAGE_SCAN="SCAN";
	public static final String CUSTOM = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	public static final String MESSAGE_TEXT="text";
	public static final String MESSAGE_IMAGE="image";
	public static final String MESSAGE_VOICE="voice";
	public static final String MESSAGE_VIDEO="video";
	public static final String MESSAGE_LINK="link";
	public static final String MESSAGE_LOCATION="location";
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="CLICK";
	public static final String MESSAGE_VIEW="VIEW";
	
	/**
	 * xml转为map集合
	 * @MethodName：xmlToMap
	 *@author:maliran
	 *@ReturnType:Map<String,String>
	 *@param req
	 *@return
	 *@throws IOException
	 *@throws DocumentException
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest req) throws IOException, DocumentException{
		
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();//log4j.jar
		InputStream ins = req.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element e:list){
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}
	/**
	 * 文本转换为xml
	 * @MethodName：textMessageToXml
	 *@author:maliran
	 *@ReturnType:String
	 *@param textMessage
	 *@return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		
		XStream xstream = new XStream();//xstream.jar,xmlpull.jar
		xstream.alias("xml", textMessage.getClass());//置换根节点
		System.out.println(xstream.toXML(textMessage));
		return xstream.toXML(textMessage);
		
	}

	public static String initTextMessage(String content,String toUserName,String fromUserName){		
		String message = null;
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setCreateTime(new Date().toString());
		textMessage.setContent(content);
		textMessage.setMsgType(MESSAGE_TEXT);
		message = textMessageToXml(textMessage);
		return message;
	}

}
