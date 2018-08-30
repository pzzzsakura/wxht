 /**
 * Ma.li.ran
 * 2017/10/27 0027 10:14
 */
 package com.maqway.wxht.util.wechat;

 import com.alibaba.fastjson.JSONObject;
 import java.security.MessageDigest;
 import java.security.NoSuchAlgorithmException;
 import java.util.Arrays;
 import org.apache.http.HttpResponse;
 import org.apache.http.client.methods.HttpPost;
 import org.apache.http.entity.StringEntity;
 import org.apache.http.impl.client.DefaultHttpClient;
 import org.apache.http.util.EntityUtils;


 /**
 * 微信验证服务器配置
 */
public class SignUtil {
  //自定义token
  private static String token = "wxht";

  /**
   *
   * @MethodName：checkSignature
   *@author:maliran
   *@ReturnType:Boolean
   *@param signature
   *@param timestamp
   *@param nonce
   *@return
   */

  public static Boolean  checkSignature(String signature,String timestamp,String nonce){
    //数组
    System.out.println(signature);
    String arr[] =new String[] {token,timestamp,nonce};
    Arrays.sort(arr);//字典序排序
    String str = "";
    for(int i=0;i<arr.length;i++){
      str+= arr[i];
    }

    String mParms = null;//sha1加密
    MessageDigest digest = null;
    try {
      digest = MessageDigest.getInstance("SHA");
    } catch (NoSuchAlgorithmException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    digest.update(str.getBytes());
    byte messageDigest[] = digest.digest();
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < messageDigest.length; i++) {
      String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
      if (shaHex.length() < 2) {
        hexString.append(0);
      }
      hexString.append(shaHex);
    }
    mParms = hexString.toString();
    if(mParms.equals(signature)){
      System.out.println(mParms);
      return true;
    } else{
      System.out.println(mParms);
      return false;
    }
  }

  /**
   *
   * @MethodName：byteToStr
   *@author:maliran
   *@ReturnType:String
   *@param byteArray
   *@return
   */
  public static String byteToStr(byte[] byteArray){

    String str = "";
    for(int i=0;i<byteArray.length;i++){
      str += byteToHexStr(byteArray[i]);
    }
    return str;
  }
  /**
   *
   * @MethodName：byteToHexStr
   *@author:maliran
   *@ReturnType:String
   *@param mbyte
   *@return
   */
  public static String byteToHexStr(byte mbyte) {
    char[] Digit = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'A', 'B', 'C', 'D', 'E', 'F'};
    char[] tempArr = new char[2];
    tempArr[0] = Digit[(mbyte >>> 4) & 0X0F];
    tempArr[1] = Digit[mbyte & 0X0F];
    String s = new String(tempArr);
    return s;

  }
  }
