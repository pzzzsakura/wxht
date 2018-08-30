package com.maqway.wxht.util.wechat;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


/**
 * 微信接口工具类
 *
 */
public class AuthUtil {
	
	public static JSONObject doGetJson(String url) throws IOException {
		JSONObject jsonObject = null;
		DefaultHttpClient client =  new DefaultHttpClient();
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		HttpEntity entity = response.getEntity();
		if(entity!=null){
			String result = EntityUtils.toString(entity, "UTF-8");
			jsonObject = JSONObject.parseObject(result);
		}
		get.releaseConnection();
		return jsonObject;
	}

}
