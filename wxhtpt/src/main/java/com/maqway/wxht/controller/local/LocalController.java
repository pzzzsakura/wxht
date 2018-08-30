package com.maqway.wxht.controller.local;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.maqway.wxht.Enums.ResultEnum;
import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.User;
import com.maqway.wxht.service.UserService;
import com.maqway.wxht.util.CodeUtil;
import com.maqway.wxht.util.HttpServletRequestUtil;
import java.io.IOException;
import java.lang.ref.ReferenceQueue;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties.Session;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/26 12:08
 * @desc: 用户注册登陆
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping(value = "local")
public class LocalController {

  //产品名称:云通信短信API产品,开发者无需替换
  static final String product = "Dysmsapi";
  //产品域名,开发者无需替换
  static final String domain = "dysmsapi.aliyuncs.com";

  // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
  static final String accessKeyId = "LTAImYEREOIjicRS";
  static final String accessKeySecret = "CVhtHPza1Sys92R6pbUYHeGbgW9vCB";
  @Autowired
  private UserService userService;

  /**
   * 用户注册
   */
  @RequestMapping(value = "bindlocalauth", method = RequestMethod.POST)
  private Map<String, Object> bindLocalAuth(HttpServletRequest request) throws IOException {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String name = HttpServletRequestUtil.getString(request, "userName");
    String psw = HttpServletRequestUtil.getString(request, "password");
    String verifyCode = HttpServletRequestUtil.getString(request, "verifyCode");
    String userPhone = HttpServletRequestUtil.getString(request, "userPhone");
    Integer sex = HttpServletRequestUtil.getInt(request, "sex");
    String verifyCodeActual = (String) request.getSession().getAttribute("verifyCode");
    String userPhoneActual = (String) request.getSession().getAttribute("tel");
    if (userPhone != null && !userPhone.equals(userPhoneActual)) {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.PHONEINVALID.getState());
      modelMap.put("errMsg", ResultEnum.PHONEINVALID.getStateInfo());
      return modelMap;
    }
    if (verifyCode != null && !verifyCode.equals(verifyCodeActual)) {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.VERIFYCODEERROR.getState());
      modelMap.put("errMsg", ResultEnum.VERIFYCODEERROR.getStateInfo());
      return modelMap;
    }
    ImageHolder thumbnail = null;
    User user = new User();
    if (userPhone != null && name != null && psw != null && sex != -1 && verifyCode != null
        && sex != -1) {
      thumbnail = createUser(request, user, userPhone, name, psw, sex, thumbnail);
    } else {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.ARGSISNULL.getState());
      modelMap.put("errMsg", ResultEnum.ARGSISNULL.getStateInfo());
      return modelMap;
    }
    Result result = userService.addUser(user, thumbnail);
    if (result.getState() == ResultEnum.SUCCESS.getState()) {
      request.getSession().setAttribute("userId", user.getUserId());
      modelMap.put("success", true);
      modelMap.put("userId", user.getUserId());
      return modelMap;
    } else {
      modelMap.put("success", false);
      modelMap.put("errCode", result.getState());
      modelMap.put("errMsg", result.getStateInfo());
      return modelMap;
    }
  }

  /**
   * 登陆
   */
  @RequestMapping(value = "logincheck", method = RequestMethod.POST)
  private Map<String, Object> loginCheck(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
    if (needVerify && !CodeUtil.checkVerifyCode(request)) {
      modelMap.put("success", false);
      modelMap.put("errMsg", "输入了错误的验证码");
      return modelMap;
    }
    String userPhone = HttpServletRequestUtil.getString(request, "userPhone");
    String psw = HttpServletRequestUtil.getString(request, "password");
    User userCondition = new User();
    userCondition.setUserPhone(userPhone);
    Result result = userService.getUserByCondition(userCondition);
    if (result.getState() == ResultEnum.SUCCESS.getState()) {
      if (result.getData() != null) {
        userCondition.setUserPsw(psw);
        if (psw == null || psw == "" || psw == "null") {
          modelMap.put("success", false);
          modelMap.put("errCode", ResultEnum.PSWISERROR.getState());
          modelMap.put("errMsg", ResultEnum.PSWISERROR.getStateInfo());
          return modelMap;
        }
        Result result2 = userService.getUserByCondition(userCondition);
        User user = (User) result2.getData();
        if (user != null) {
          request.getSession().setAttribute("userId", user.getUserId());
          modelMap.put("success", true);
          modelMap.put("userId", user.getUserId());
          return modelMap;
        } else {
          modelMap.put("success", false);
          modelMap.put("errCode", ResultEnum.PSWISERROR.getState());
          modelMap.put("errMsg", ResultEnum.PSWISERROR.getStateInfo());
          return modelMap;
        }
      } else {
        modelMap.put("success", false);
        modelMap.put("errCode", ResultEnum.PHONEISNONE.getState());
        modelMap.put("errMsg", ResultEnum.PHONEISNONE.getStateInfo());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errCode", result.getState());
      modelMap.put("errMsg", result.getStateInfo());
      return modelMap;
    }

  }

  /**
   * 获取短信验证码
   */
  @RequestMapping(value = "getverifycode", method = RequestMethod.POST)
  private Map<String, Object> getVerifyCode(HttpServletRequest req) throws ClientException {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String tel = HttpServletRequestUtil.getString(req, "tel");
    String code = new Random().nextInt(8999) + 1000 + "";
    //设置超时时间-可自行调整
    System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
    System.setProperty("sun.net.client.defaultReadTimeout", "10000");
    //初始化ascClient,暂时不支持多region（请勿修改）
    IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
    DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
    IAcsClient acsClient = new DefaultAcsClient(profile);
    //组装请求对象
    SendSmsRequest request = new SendSmsRequest();
    //使用post提交
    request.setMethod(MethodType.POST);
    //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
    request.setPhoneNumbers(tel);
    //必填:短信签名-可在短信控制台中找到
    request.setSignName("码酷喂");
    //必填:短信模板-可在短信控制台中找到
    request.setTemplateCode("SMS_119087126");
    //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
    //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
    request.setTemplateParam("{\"code\":" + code + "}");
    //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
    //request.setSmsUpExtendCode("90997");
    //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
    //request.setOutId("yourOutId");
    //请求失败这里会抛ClientException异常
    SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
    if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
      req.getSession().setAttribute("verifyCode", code);
      req.getSession().setAttribute("tel", tel);
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
      modelMap.put("errCode", sendSmsResponse.getCode());
      modelMap.put("errMsg", sendSmsResponse.getMessage());
    }
    return modelMap;
  }

  private ImageHolder createUser(HttpServletRequest request, User user, String userPhone,
      String name, String psw, Integer sex, ImageHolder thumbnail)
      throws IOException {
    user.setUserPhone(userPhone);
    user.setUserSex(sex);
    user.setUserPsw(psw);
    user.setUserName(name);
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    // 取出头像并构建ImageHolder对象
    CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("userImg");
    if (thumbnailFile != null) {
      thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),
          thumbnailFile.getInputStream());
    }
    return thumbnail;
  }

  /**
   * 获取用户信息
   */

  @RequestMapping(value = "getuserinfo", method = RequestMethod.GET)
  private Map<String, Object> getUserInfo(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    Integer userId = (Integer) request.getSession().getAttribute("userId");
    User userCondition = new User();
    if (userId != null) {
      userCondition.setUserId(userId);
      Result result = userService.getUserByCondition(userCondition);
      if (result.getState() == ResultEnum.SUCCESS.getState()) {
        modelMap.put("success", true);
        modelMap.put("user", (User) result.getData());
      } else {
        modelMap.put("success", false);
        modelMap.put("errCode", result.getState());
        modelMap.put("errMsg", result.getStateInfo());
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.ARGSISNULL);
      modelMap.put("errMsg", ResultEnum.ARGSISNULL);
    }
    return modelMap;
  }

  /**
   * 修改资料
   */
  @RequestMapping(value = "modifyuserinfo",method = RequestMethod.POST)
  private Map<String, Object> modifyUserInfo(HttpServletRequest request) throws IOException {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String username = HttpServletRequestUtil.getString(request,"username");
    Integer sex = HttpServletRequestUtil.getInt(request, "sex");
    Integer userId = (Integer) request.getSession().getAttribute("userId");
    if(username!=null&&sex!=null&&userId!=-1){
      ImageHolder thumbnail = null;
      User user = new User();
      thumbnail = createUser(request, user, null, username, null,  sex, thumbnail);
      Result result = userService.ModifyUserById(user,thumbnail);
      if(result.getState()==ResultEnum.SUCCESS.getState()){
        modelMap.put("success", true);
        modelMap.put("userId",userId);
      }else{
        modelMap.put("success", false);
        modelMap.put("errCode", result.getState());
        modelMap.put("errMsg", result.getStateInfo());
      }
    }else{
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.ARGSISNULL);
      modelMap.put("errMsg", ResultEnum.ARGSISNULL);
    }
    return modelMap;
  }

  /**
   * 修改密码
   */
  @RequestMapping(value = "modifypsw",method = RequestMethod.POST)
  private  Map<String,Object> modifyPsw(HttpServletRequest request){
    Map<String, Object> modelMap = new HashMap<String, Object>();
    String verifyCodeActual = (String) request.getSession().getAttribute("verifyCode");
    String psw = HttpServletRequestUtil.getString(request, "password");
    String verifyCode = HttpServletRequestUtil.getString(request, "verifyCode");
    String userPhone = HttpServletRequestUtil.getString(request, "userPhone");
    String userPhoneActual = (String) request.getSession().getAttribute("tel");
    if (userPhone != null && !userPhone.equals(userPhoneActual)) {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.PHONEINVALID.getState());
      modelMap.put("errMsg", ResultEnum.PHONEINVALID.getStateInfo());
    }
    if (verifyCode != null && !verifyCode.equals(verifyCodeActual)) {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.VERIFYCODEERROR.getState());
      modelMap.put("errMsg", ResultEnum.VERIFYCODEERROR.getStateInfo());
    }
    User userConditon = new User();
    userConditon.setUserPhone(userPhone);
    Result result = userService.getUserByCondition(userConditon);
    if(result!=null&&result.getData()!=null){
      User user = new User();
      userConditon = (User)result.getData();
      user.setUserId(userConditon.getUserId());
      user.setUserPsw(psw);
      Result result1 = userService.ModifyUserById(user,null);
      if(result1.getState()==ResultEnum.SUCCESS.getState()){
        modelMap.put("success", true);
        modelMap.put("userId",user.getUserId());
      }else{
        modelMap.put("success", false);
        modelMap.put("errCode", result.getState());
        modelMap.put("errMsg", result.getStateInfo());
      }
    }else{
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.PHONEISNONE.getState());
      modelMap.put("errMsg", ResultEnum.PHONEISNONE.getStateInfo());
    }
    return modelMap;
  }

  /**
   * 当用户点击登出按钮的时候注销session
   */
  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  private Map<String, Object> logout(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    // 将用户session置为空
    request.getSession().setAttribute("userId", null);
    modelMap.put("success", true);
    return modelMap;
  }
}
