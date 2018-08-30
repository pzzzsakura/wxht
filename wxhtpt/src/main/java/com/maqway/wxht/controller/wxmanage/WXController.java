package com.maqway.wxht.controller.wxmanage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maqway.wxht.Enums.ResultEnum;
import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.User;
import com.maqway.wxht.entity.WX;
import com.maqway.wxht.entity.WXManage;
import com.maqway.wxht.service.WXService;
import com.maqway.wxht.util.CodeUtil;
import com.maqway.wxht.util.HttpServletRequestUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import java.util.Map;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/08 17:26
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("wxmanage")
public class WXController {

  public static final int IMAGEMAXCOUNT = 6 ;

  @Autowired
  private WXService wxService;
  /**
   * 得到微信号列表
   */
  @RequestMapping(value = "getwxlist", method = RequestMethod.GET)
  private Map<String, Object> getWXList(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    WX wx = new WX();
    int wxManageId = HttpServletRequestUtil.getInt(request,"wxManageId");
    if (wxManageId != -1) {
      WXManage wxManage = new WXManage();
      wxManage.setWxManageId(wxManageId);
      wx.setWxManage(wxManage);
    } else {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.NOTLOGIN.getState());
      modelMap.put("errMsg", ResultEnum.NOTLOGIN.getStateInfo());
      return modelMap;
    }
    Result result = wxService.getWXList(wx,0,999);
    if (result.getState() == ResultEnum.SUCCESS.getState()) {
      List<WX> wxList = (List<WX>) result.getData();
      modelMap.put("success", true);
      modelMap.put("wxList", wxList);
      modelMap.put("count",result.getCount());
      return modelMap;
    } else {
      modelMap.put("success", false);
      modelMap.put("errCode", result.getState());
      modelMap.put("errMsg", result.getStateInfo());
      return modelMap;
    }
  }

  /**
   * 添加微信号
   */
  @RequestMapping(value = "addwx", method = RequestMethod.POST)
  private Map<String, Object> addWX(HttpServletRequest request) throws IOException {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    Integer userId = (Integer) request.getSession().getAttribute("userId");
    if (userId == -1) {
      modelMap.put("errCode", ResultEnum.NOTLOGIN.getState());
      modelMap.put("success", false);
      modelMap.put("errMsg", ResultEnum.NOTLOGIN.getStateInfo());
      return modelMap;
    }
    User user = new User();
    user.setUserId(userId);
    String wxStr = HttpServletRequestUtil.getString(request, "wxStr");
    boolean isTrue = CodeUtil.checkVerifyCode(request);
    if (!isTrue) {
      modelMap.put("errCode", ResultEnum.VERIFYCODEERROR.getState());
      modelMap.put("success", false);
      modelMap.put("errMsg", ResultEnum.VERIFYCODEERROR.getStateInfo());
      return modelMap;
    }
    if (wxStr!=null) {
      ObjectMapper objectMapper = new ObjectMapper();
      WX wx = objectMapper.readValue(wxStr,WX.class);

      ImageHolder thumbnail = null;
      List<ImageHolder> imageHolderList = new ArrayList<>();
      wx.setPriority(1);
      wx.setCreateTime(new Date());
      thumbnail = createWX(request, user, wx, thumbnail,imageHolderList);
      Result result = wxService.addWX(wx,thumbnail,imageHolderList);
      if (result.getState() == ResultEnum.SUCCESS.getState()) {
        modelMap.put("wxId", result.getData());
        modelMap.put("success", true);
      } else {
        modelMap.put("success", false);
        modelMap.put("errCode", result.getState());
        modelMap.put("errMsg", result.getStateInfo());
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.ARGSISNULL.getState());
      modelMap.put("errMsg", ResultEnum.ARGSISNULL.getStateInfo());
    }

    return modelMap;
  }

  private ImageHolder createWX(HttpServletRequest request, User user, WX wx,
      ImageHolder thumbnail,List<ImageHolder> imageHolderList) throws IOException {
   wx.setEnableStatus(1);
   wx.setUpdateTime(new Date());
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    // 取出缩略图并构建ImageHolder对象
    CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
    if (thumbnailFile != null) {
      thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),
          thumbnailFile.getInputStream());
    }
    // 取出详情图列表并构建List<ImageHolder>列表对象，最多支持六张图片上传
    for (int i = 0; i < IMAGEMAXCOUNT; i++) {
      CommonsMultipartFile wxImgFile = (CommonsMultipartFile) multipartRequest.getFile("wxImg" + i);
      if (wxImgFile != null) {
        // 若取出的第i个详情图片文件流不为空，则将其加入详情图列表
        ImageHolder wxImg = new ImageHolder(wxImgFile.getOriginalFilename(),
            wxImgFile.getInputStream());
        imageHolderList.add(wxImg);
      } else {
        // 若取出的第i个详情图片文件流为空，则终止循环
        break;
      }
    }
    return thumbnail;
  }

  /**
   * 删除微信号
   */
  @RequestMapping(value = "removewx", method = RequestMethod.POST)
  private Map<String, Object> removeWX(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    Integer wxId = HttpServletRequestUtil.getInt(request, "wxId");
    Result result = wxService.removeWX(wxId);
    if (result.getState() == ResultEnum.SUCCESS.getState()) {
      modelMap.put("success", true);
    } else {
      modelMap.put("errCode", result.getState());
      modelMap.put("success", false);
      modelMap.put("errMsg", result.getStateInfo());
    }
    return modelMap;
  }

  /**
   * 修改微信号
   */
  @RequestMapping(value = "modifywx", method = RequestMethod.POST)
  private Map<String, Object> modifyWX(HttpServletRequest request) throws IOException {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    Integer userId = (Integer) request.getSession().getAttribute("userId");
    if (userId == -1) {
      modelMap.put("success", false);
      modelMap.put("errMsg", ResultEnum.NOTLOGIN.getStateInfo());
      modelMap.put("errCode", ResultEnum.NOTLOGIN.getState());
      return modelMap;
    }
    User user = new User();
    user.setUserId(userId);
    String wxStr = HttpServletRequestUtil.getString(request, "wxStr");
    boolean isTrue = CodeUtil.checkVerifyCode(request);
    if (!isTrue) {
      modelMap.put("success", false);
      modelMap.put("errMsg", ResultEnum.VERIFYCODEERROR.getStateInfo());
      modelMap.put("errCode", ResultEnum.VERIFYCODEERROR.getState());
      return modelMap;
    }
    if (wxStr!=null) {
      ObjectMapper objectMapper = new ObjectMapper();
      WX wx = objectMapper.readValue(wxStr,WX.class);
      ImageHolder thumbnail = null;
      List<ImageHolder> imageHolderList = new ArrayList<>();
      thumbnail = createWX(request, user, wx, thumbnail,imageHolderList);
      Result result = wxService.modifyWX(wx,thumbnail,imageHolderList);
      if (result.getState() == ResultEnum.SUCCESS.getState()) {
        modelMap.put("success", true);
        modelMap.put("wxId", result.getData());
      } else {
        modelMap.put("success", false);
        modelMap.put("errCode", result.getState());
        modelMap.put("errMsg", result.getStateInfo());
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.ARGSISNULL.getState());
      modelMap.put("errMsg", ResultEnum.ARGSISNULL.getStateInfo());
    }

    return modelMap;
  }


}
