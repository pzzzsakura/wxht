package com.maqway.wxht.controller.wxmanage;

import com.maqway.wxht.Enums.ResultEnum;
import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.User;
import com.maqway.wxht.entity.WXManage;
import com.maqway.wxht.entity.WXManageCategory;
import com.maqway.wxht.service.WXManageCategoryService;
import com.maqway.wxht.service.WXManageService;
import com.maqway.wxht.util.CodeUtil;
import com.maqway.wxht.util.HttpServletRequestUtil;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 19:18
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("wxmanage")
public class WXManageController {

  @Autowired
  private WXManageCategoryService wxManageCategoryService;
  @Autowired
  private WXManageService wxManageService;

  /**
   * 得到微信组列表
   */
  @RequestMapping(value = "getwxgrouplist", method = RequestMethod.GET)
  private Map<String, Object> getWXGroupList(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    WXManage wxManage = new WXManage();
    User user = new User();
    Integer userId = (Integer) request.getSession().getAttribute("userId");
    if (userId != -1) {
      user.setUserId(userId);
    } else {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.NOTLOGIN.getState());
      modelMap.put("errMsg", ResultEnum.NOTLOGIN.getStateInfo());
      return modelMap;
    }
    wxManage.setUser(user);
    Result result = wxManageService.getWXManageList(wxManage, 0, 99);
    if (result.getState() == ResultEnum.SUCCESS.getState()) {
      List<WXManage> wxManageList = (List<WXManage>) result.getData();
      modelMap.put("success", true);
      modelMap.put("wxManageList", wxManageList);
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
   * 添加微信组
   */
  @RequestMapping(value = "addwxmanage", method = RequestMethod.POST)
  private Map<String, Object> addWXManage(HttpServletRequest request) throws IOException {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    Integer userId = (Integer) request.getSession().getAttribute("userId");
    if (userId == -1) {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.NOTLOGIN.getState());
      modelMap.put("errMsg", ResultEnum.NOTLOGIN.getStateInfo());
      return modelMap;
    }
    User user = new User();
    user.setUserId(userId);
    WXManage w = new WXManage();
    w.setUser(user);
    Result r = wxManageService.getWXManageList(w, 0, 99);
    int count = r.getCount();
    if (count == 99) {
      modelMap.put("success", false);
      modelMap.put("errMsg", "不能创建更多了");
      return modelMap;
    }
    String wxManageName = HttpServletRequestUtil.getString(request, "wxManageName");
    String wxManageDesc = HttpServletRequestUtil.getString(request, "wxManageDesc");
    Integer wmcId = HttpServletRequestUtil.getInt(request, "wmcId");
    String verifyCode = HttpServletRequestUtil.getString(request, "verifyCode");
    Integer priority = 1;
    boolean isTrue = CodeUtil.checkVerifyCode(request);
    if (!isTrue) {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.VERIFYCODEERROR.getState());
      modelMap.put("errMsg", ResultEnum.VERIFYCODEERROR.getStateInfo());
      return modelMap;
    }
    if (wxManageName != null && wxManageDesc != null && wmcId != -1 && verifyCode != null
        && priority != -1) {
      WXManage wxManage = new WXManage();
      ImageHolder thumbnail = null;
      wxManage.setCreateTime(new Date());
      wxManage.setPriority(1);
      thumbnail = createGroup(request, user, wxManage, wxManageName, wxManageDesc, wmcId,
          thumbnail);
      Result result = wxManageService.addWXManage(wxManage, thumbnail);
      if (result.getState() == ResultEnum.SUCCESS.getState()) {
        modelMap.put("success", true);
        modelMap.put("wxManage", result.getData());
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

  private ImageHolder createGroup(HttpServletRequest request, User user, WXManage wxManage,
      String wxManageName, String wxManageDesc, Integer wmcId, ImageHolder thumbnail)
      throws IOException {
    wxManage.setUser(user);
    wxManage.setEnableStatus(0);
    wxManage.setUpdateTime(new Date());
    wxManage.setWxManageDesc(wxManageDesc);
    wxManage.setWxManageName(wxManageName);
    WXManageCategory wxManageCategory = new WXManageCategory();
    wxManageCategory.setWmcId(wmcId);
    wxManage.setWxManageCategory(wxManageCategory);
    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    // 取出缩略图并构建ImageHolder对象
    CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest
        .getFile("wxManageImg");
    if (thumbnailFile != null) {
      thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),
          thumbnailFile.getInputStream());
    }
    return thumbnail;
  }

  /**
   * 删除微信组
   */
  @RequestMapping(value = "removewxmanage", method = RequestMethod.POST)
  private Map<String, Object> removeWXManage(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    Integer wxManageId = HttpServletRequestUtil.getInt(request, "wxManageId");
    Result result = wxManageService.removeWXMange(wxManageId);
    if (result.getState() == ResultEnum.SUCCESS.getState()) {
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
      modelMap.put("errCode", result.getState());
      modelMap.put("errMsg", result.getStateInfo());
    }
    return modelMap;
  }

  @RequestMapping(value = "editwxmanage", method = RequestMethod.POST)
  private Map<String, Object> editWXManage(HttpServletRequest request) throws IOException {

    Map<String, Object> modelMap = new HashMap<String, Object>();
    String wxManageName = HttpServletRequestUtil.getString(request, "wxManageName");
    String wxManageDesc = HttpServletRequestUtil.getString(request, "wxManageDesc");
    String verifyCode = HttpServletRequestUtil.getString(request, "verifyCode");
    Integer wxManageId = HttpServletRequestUtil.getInt(request, "wxManageId");
    boolean isTrue = CodeUtil.checkVerifyCode(request);
    if (!isTrue) {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.VERIFYCODEERROR.getState());
      modelMap.put("errMsg", ResultEnum.VERIFYCODEERROR.getStateInfo());
      return modelMap;
    }
    Integer userId = (Integer) request.getSession().getAttribute("userId");
    if (userId == -1) {
      modelMap.put("success", false);
      modelMap.put("errCode", ResultEnum.NOTLOGIN.getState());
      modelMap.put("errMsg", ResultEnum.NOTLOGIN.getStateInfo());
      return modelMap;
    }
    User user = new User();
    user.setUserId(userId);
    if (wxManageName != null && wxManageDesc != null && verifyCode != null) {
      WXManage wxManage = new WXManage();
      ImageHolder thumbnail = null;
      thumbnail = createGroup(request, user, wxManage, wxManageName, wxManageDesc, null, thumbnail);
      wxManage.setWxManageId(wxManageId);
      Result result = wxManageService.modifyWXMange(wxManage, thumbnail);
      if (result.getState() == ResultEnum.SUCCESS.getState()) {
        modelMap.put("success", true);
        modelMap.put("wxManage", result.getData());
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

  @RequestMapping(value = "getwxmanagecategorylist", method = RequestMethod.GET)
  private Map<String, Object> getWXManageCategoryList(HttpServletRequest request)
      throws IOException {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    List<WXManageCategory> wxManageCategoryList = wxManageCategoryService.getAllList();
    modelMap.put("wxManageCategoryList", wxManageCategoryList);
    modelMap.put("success", true);
    return modelMap;
  }
}
