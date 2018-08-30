package com.maqway.wxht.service.serviceImpl;

import com.maqway.wxht.Enums.ResultEnum;
import com.maqway.wxht.Exception.WXManageCategoryException;
import com.maqway.wxht.dao.WXManageCategoryDao;
import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.WXManageCategory;
import com.maqway.wxht.service.WXManageCategoryService;
import com.maqway.wxht.util.ImageUtil;
import com.maqway.wxht.util.PathUtil;
import java.awt.Image;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 15:11
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class WXManageCategoryServiceImpl implements WXManageCategoryService {

  @Autowired
  private WXManageCategoryDao wxManageCategoryDao;
@Transactional
  @Override
  public Result addWXManageCategory(WXManageCategory wxManageCategory,ImageHolder imageHolder) {
    if(wxManageCategory!=null&&wxManageCategory.getWmcName()!=null){
      wxManageCategory.setCreateTime(new Date());
      wxManageCategory.setUpdateTime(new Date());
      String wmcName = wxManageCategory.getWmcName();
      WXManageCategory w = new WXManageCategory();
      w.setWmcName(wmcName);
      List<WXManageCategory> wxManageCategoryList =  wxManageCategoryDao.queryWXManageCategoryList(w);
      if(wxManageCategoryList!=null&&wxManageCategoryList.size()>0){
        return new Result(ResultEnum.ARGSISNULL);
      }
      try {
        int effecteNum = wxManageCategoryDao.insertWXManageCategory(wxManageCategory);
        if(effecteNum!=-1){
          //上传类别缩略图
          String url = addThumbnail(wxManageCategory,imageHolder);
          WXManageCategory newWmc = new WXManageCategory();
          newWmc.setWmcImg(url);
          newWmc.setWmcId(wxManageCategory.getWmcId());
          try {
            int result = wxManageCategoryDao.updateWXManageCategory(newWmc);
            if(result!=-1){
              return new Result(ResultEnum.SUCCESS,wxManageCategory.getWmcId());
            }else{
              throw new WXManageCategoryException(ResultEnum.ERROR.getState(),"updateWXManageCategory error");
            }
          }catch (Exception e){
            throw new WXManageCategoryException(ResultEnum.ERROR.getState(),"updateWXManageCategory error"+e.getMessage());
          }

        } else {
          throw new WXManageCategoryException(ResultEnum.ERROR.getState(),
              "addWXManageCategory error");
        }
      }catch (Exception e){
        throw new WXManageCategoryException(ResultEnum.ERROR.getState(),"addWXManageCategory error"+e.getMessage());
      }

      } else{
      return new Result(ResultEnum.ARGSISNULL);
    }
  }

  @Override
  public Result getWXManageCategoryList(WXManageCategory wxManageCategoryCondition) {
    List<WXManageCategory> wxManageCategoryList = wxManageCategoryDao.queryWXManageCategoryList(wxManageCategoryCondition);
    return new Result(ResultEnum.SUCCESS,wxManageCategoryList);
  }

  @Override
  public List<WXManageCategory> getAllList() {
    return wxManageCategoryDao.queryAllList();
  }

  /**
   * 上传类别缩略图
   * @param wxManageCategory
   * @param thumbnail
   */
  private String addThumbnail(WXManageCategory wxManageCategory, ImageHolder thumbnail) {
    String dest = PathUtil.getWMCImagePath(wxManageCategory.getWmcId());
    String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
    return thumbnailAddr;
  }
}
