package com.maqway.wxht.service.serviceImpl;

import com.maqway.wxht.Enums.ResultEnum;
import com.maqway.wxht.Exception.WXManageOperationException;
import com.maqway.wxht.dao.WXDao;
import com.maqway.wxht.dao.WXManageDao;
import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.WX;
import com.maqway.wxht.entity.WXManage;
import com.maqway.wxht.service.WXManageService;
import com.maqway.wxht.util.ImageUtil;
import com.maqway.wxht.util.PageCalculator;
import com.maqway.wxht.util.PathUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 19:02
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class WXManageServiceImpl implements WXManageService {

  @Autowired
  private WXDao wxDao;

  @Autowired
  private WXManageDao wxManageDao;

  @Transactional
  @Override
  public Result addWXManage(WXManage wxManage,ImageHolder imageHolder) {
    if(wxManage!=null){
      try {
        int result = wxManageDao.insertWXManage(wxManage);
        if(result!=-1){
          WXManage newWXManage = new WXManage();
          newWXManage.setWxManageId(wxManage.getWxManageId());
         if(imageHolder!=null){
           String newUrl = addThumbnail(wxManage,imageHolder);
           try {
             newWXManage.setWxManageImg(newUrl);
             int effectedNum = wxManageDao.updateWXMange(newWXManage);
             if(effectedNum!=-1){
               return new Result(ResultEnum.SUCCESS,wxManage.getWxManageId());
             }else{
               throw new WXManageOperationException(ResultEnum.ERROR.getState(),"updateWXMange error");
             }
           }catch (Exception e){
             throw new WXManageOperationException(ResultEnum.ERROR.getState(),e.getMessage());
           }
         }else{
           return new Result(ResultEnum.IMAGEISNULL);         }
        }else{
          throw new WXManageOperationException(ResultEnum.ERROR.getState(),"insertWXManage error");
        }
      }catch (Exception e){
        throw new WXManageOperationException(ResultEnum.ERROR.getState(),e.getMessage());
      }
    }else {
      return new Result(ResultEnum.ARGSISNULL);
    }
  }
  /**
   * 上传群组缩略图
   * @param wxManage
   * @param thumbnail
   */
  private String addThumbnail(WXManage wxManage, ImageHolder thumbnail) {
    String dest = PathUtil.getWXManagePath(wxManage.getWxManageId());
    String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
    return thumbnailAddr;
  }
  @Override
  public Result getWXManageList(WXManage wxManage,int pageIndex,int pageSize) {
    int rowIndex = PageCalculator.caculateRowIndex(pageIndex,pageSize);
    if(wxManage!=null){
      List<WXManage> wxManageList =  wxManageDao.queryWXManageList(wxManage,rowIndex,pageSize);
      int count = wxManageDao.queryCount(wxManage);
      return new Result(ResultEnum.SUCCESS,wxManageList,count);
    }else{
      return new Result(ResultEnum.ERROR);
    }
  }

  @Transactional
  @Override
  public Result modifyWXMange(WXManage wxManage,ImageHolder imageHolder) {
    if(wxManage!=null){
      WXManage w = new WXManage();
      w.setWxManageId(wxManage.getWxManageId());
      List<WXManage> wxManageList = wxManageDao.queryWXManageList(w,0,9999);
      if(imageHolder!=null){
        WXManage newWXManage = new WXManage();
        newWXManage.setWxManageId(wxManage.getWxManageId());
        String url = wxManageList.get(0).getWxManageImg();
        ImageUtil.deleteFileOrPath(url);
        String newUrl = addThumbnail(wxManage,imageHolder);
        newWXManage.setWxManageImg(newUrl);
        try {
         wxManageDao.updateWXMange(newWXManage);
        }catch (Exception e){
          throw new WXManageOperationException(ResultEnum.ERROR.getState(),e.getMessage());
        }
      }
      try {
        int result = wxManageDao.updateWXMange(wxManage);
        if(result!=-1) {
          return new Result(ResultEnum.SUCCESS, wxManage.getWxManageId());
        }else{
          throw new WXManageOperationException(ResultEnum.ERROR.getState(),"insertWXManage error");
        }
      }catch (Exception e){
        throw new WXManageOperationException(ResultEnum.ERROR.getState(),e.getMessage());
      }
    }else {
      return new Result(ResultEnum.ARGSISNULL);
    }
  }

  @Override
  public Result removeWXMange(Integer wxMangeId) {
    WXManage wxManage = new WXManage();
    wxManage.setWxManageId(wxMangeId);
    WX wx = new WX();
    wx.setWxManage(wxManage);
    int count = wxDao.queryCount(wx);
    if(count>0){
      return new Result(ResultEnum.CANNOTDEL);
    }
    List<WXManage> wxManageList = wxManageDao.queryWXManageList(wxManage,0,9999);
    if(wxManageList!=null&&wxManageList.size()>0){
      ImageUtil.deleteFileOrPath(wxManageList.get(0).getWxManageImg());
    }
    try {
      int result = wxManageDao.deleteWXMange(wxMangeId);
      if(result!=-1){
        return new Result(ResultEnum.SUCCESS);
      }else{
        throw  new WXManageOperationException(ResultEnum.ERROR.getState(),"deleteWXMange error");
      }
    }catch (Exception e){
      throw  new WXManageOperationException(ResultEnum.ERROR.getState(),e.getMessage());
    }
  }

  @Override
  public WXManage queryWXManageById(Integer wxManageId) {
   WXManage wxManage =  wxManageDao.queryWXManageById(wxManageId);
   return wxManage;
  }
}
