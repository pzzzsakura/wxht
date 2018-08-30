package com.maqway.wxht.controller.frontend;

import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.entity.User;
import com.maqway.wxht.entity.WX;
import com.maqway.wxht.entity.WXManage;
import com.maqway.wxht.entity.WXManageCategory;
import com.maqway.wxht.service.HeadLineService;
import com.maqway.wxht.service.WXManageCategoryService;
import com.maqway.wxht.service.WXManageService;
import com.maqway.wxht.service.WXService;
import com.maqway.wxht.util.HttpServletRequestUtil;
import java.util.HashMap;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/09 17:08
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@RestController
@RequestMapping("frontend")
public class FrontendController {

  @Autowired
  private HeadLineService headLineService;
  @Autowired
  private WXManageCategoryService wxManageCategoryService;
  @Autowired
  private WXManageService wxManageService;
  @Autowired
  private WXService wxService;
  /**
   * 获得轮播图列表和一级种类
   * @param request
   * @return
   */
  @RequestMapping(value = "getmainlist",method = RequestMethod.GET)
  private Map<String,Object> getMainList(HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    Result result = headLineService.getHeadLineList();
    modelMap.put("success",true);
    modelMap.put("headLineList",result.getData());
    WXManageCategory wxManageCategory = new WXManageCategory();
    Result cResult = wxManageCategoryService.getWXManageCategoryList(wxManageCategory);
    modelMap.put("wxManageCategoryList",cResult.getData());
    return modelMap;
  }

  /**
   * 获得子种类和群组列表
   * @param request
   * @return
   */
  @RequestMapping(value = "getwxmanagelist",method = RequestMethod.GET)
  private Map<String,Object> getWXManageList(HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    Integer parentId = HttpServletRequestUtil.getInt(request,"parentId");
    String wxManageName = HttpServletRequestUtil.getString(request,"wxManageName");
    // 获取前台传过来的页码
    int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
    // 获取前台传过来的每页要求返回的商品数上限
    int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
    WXManageCategory wxManageCategory = new WXManageCategory();
    if(parentId!=-1){
      wxManageCategory.setParentId(parentId);
    }
    Result result = wxManageCategoryService.getWXManageCategoryList(wxManageCategory);
    modelMap.put("wxManageCategoryList",result.getData());
    User user = new User();
    WXManage wxManage = new WXManage();
    WXManageCategory wmc = new WXManageCategory();
    wmc.setWmcId(parentId);
    wxManage.setWxManageCategory(wmc);
    wxManage.setWxManageName(wxManageName);
    Result wxManageResult = wxManageService.getWXManageList(wxManage,pageIndex,pageSize);
    modelMap.put("wxManageList",wxManageResult.getData());
    modelMap.put("count",wxManageResult.getCount());
    modelMap.put("success",true);
    return modelMap;
  }
  /**
   * 获得微信列表
   * @param request
   * @return
   */
  @RequestMapping(value = "getwxlist",method = RequestMethod.GET)
  private Map<String,Object> getWXList(HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    Integer wxManageId = HttpServletRequestUtil.getInt(request,"wxManageId");
    String wxName = HttpServletRequestUtil.getString(request,"wxName");
    // 获取前台传过来的页码
    int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
    // 获取前台传过来的每页要求返回的商品数上限
    int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
    WXManage wxManage = new WXManage();
    WX wx = new WX();
    if(wxManageId!=-1){
      wxManage.setWxManageId(wxManageId);
      wx.setWxManage(wxManage);
    }
    wx.setWxName(wxName);
    Result result = wxService.getWXList(wx,pageIndex,pageSize);
    modelMap.put("wxList",result.getData());
    modelMap.put("count",result.getCount());
    modelMap.put("success",true);
    return modelMap;
  }

  /**
   * 得到子种类列表
   * @param request
   * @return
   */
  @RequestMapping(value = "getwxmanagecategorylist",method = RequestMethod.GET)
  private Map<String,Object> getWXManageCategoryList(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    Integer parentId = HttpServletRequestUtil.getInt(request, "parentId");
    WXManageCategory wxManageCategory = new WXManageCategory();
    if (parentId != -1) {
      wxManageCategory.setParentId(parentId);
    }
    Result result = wxManageCategoryService.getWXManageCategoryList(wxManageCategory);
    modelMap.put("wxManageCategoryList", result.getData());
    modelMap.put("success",true);
    return modelMap;
  }




  /**
   * 得到单个微信群组信息
   * @param request
   * @return
   */
  @RequestMapping(value = "getwxmanagedetail",method = RequestMethod.GET)
  private Map<String,Object> getWXManageDetail(HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    Integer wxManageId = HttpServletRequestUtil.getInt(request,"wxManageId");
    WXManage wxManage = wxManageService.queryWXManageById(wxManageId);
    modelMap.put("wxManage",wxManage);
    modelMap.put("success",true);
    return modelMap;
  }

  /**
   * 得到单个微信号信息
   * @param request
   * @return
   */
  @RequestMapping(value = "getwxdetail",method = RequestMethod.GET)
  private Map<String,Object> getWXDetail(HttpServletRequest request){
    Map<String,Object> modelMap = new HashMap<String,Object>();
    Integer wxId = HttpServletRequestUtil.getInt(request,"wxId");
    WX wx = wxService.getWXById(wxId);
    modelMap.put("wx",wx);
    modelMap.put("success",true);
    return modelMap;
  }
}
