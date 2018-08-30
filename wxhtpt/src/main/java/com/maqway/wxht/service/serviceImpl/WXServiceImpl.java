package com.maqway.wxht.service.serviceImpl;

import com.maqway.wxht.Enums.ResultEnum;
import com.maqway.wxht.Exception.WXOperationException;
import com.maqway.wxht.dao.WXDao;
import com.maqway.wxht.dao.WXImgDao;
import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.WX;
import com.maqway.wxht.entity.WXImg;
import com.maqway.wxht.service.WXService;
import com.maqway.wxht.util.ImageUtil;
import com.maqway.wxht.util.PageCalculator;
import com.maqway.wxht.util.PathUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.smartcardio.CardChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.rmi.runtime.NewThreadAction;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/08 17:41
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class WXServiceImpl implements WXService {

  @Autowired
  private WXDao wxDao;
  @Autowired
  private WXImgDao wxImgDao;

  @Transactional
  @Override
  public Result addWX(WX wx,ImageHolder imageHolder,List<ImageHolder> imageHolderList) {
    if(wx!=null&&imageHolder!=null&&imageHolderList!=null){
        try {
          int effectedNum = wxDao.insertWX(wx);
          if(effectedNum>0){
            addWXImgList(wx,imageHolderList);
            String url = addThumbnail(wx,imageHolder);
            WX newWX = new WX();
            newWX.setWxImg(url);
            newWX.setWxId(wx.getWxId());
            try {
              int result = wxDao.updateWX(newWX);
              if(result>0){
                return new Result(ResultEnum.SUCCESS,wx.getWxId());
              }else{
                throw new WXOperationException(ResultEnum.ERROR.getState(),"updateWX error");
              }
            }catch (Exception e){
              throw new WXOperationException(ResultEnum.ERROR.getState(),e.getMessage());
            }
          }else {
            throw new WXOperationException(ResultEnum.ERROR.getState(),"insertWX error");
          }
        } catch (Exception e){
          throw new WXOperationException(ResultEnum.ERROR.getState(),e.getMessage());
        }
    }else{
      return new Result(ResultEnum.ARGSISNULL);
    }
  }

  @Override
  public Result getWXList(WX wx,int pageIndex,int pageSize) {
    int rowIndex = PageCalculator.caculateRowIndex(pageIndex,pageSize);
    int count = wxDao.queryCount(wx);
    List<WX> wxList = wxDao.queryWXList(wx,rowIndex,pageSize);
    return new Result(ResultEnum.SUCCESS,wxList,count);
  }

  @Transactional
  @Override
  public Result modifyWX(WX wx,ImageHolder imageHolder,List<ImageHolder> imageHolderList) {
    if(wx!=null){
      if(imageHolder!=null){
        ImageUtil.deleteFileOrPath(wx.getWxImg());
        String url = addThumbnail(wx,imageHolder);
        wx.setWxImg(url);
      }
      if(imageHolderList!=null){
        deleteImgList(wx.getWxId());
        addWXImgList(wx,imageHolderList);
      }
      try {
        int effectedNum = wxDao.updateWX(wx);
        if(effectedNum>0){
          try {
            int result = wxDao.updateWX(wx);
            if(result>0){
              return new Result(ResultEnum.SUCCESS,wx.getWxId());
            }else{
              throw new WXOperationException(ResultEnum.ERROR.getState(),"updateWX error");
            }
          }catch (Exception e){
            throw new WXOperationException(ResultEnum.ERROR.getState(),e.getMessage());
          }
        }else {
          throw new WXOperationException(ResultEnum.ERROR.getState(),"updateWX error");
        }
      } catch (Exception e){
        throw new WXOperationException(ResultEnum.ERROR.getState(),e.getMessage());
      }
    }else{
      return new Result(ResultEnum.ARGSISNULL);
    }
  }




  @Transactional
  @Override
  public Result removeWX(Integer wxId){
    if(wxId!=-1){
    try {
      deleteImgList(wxId);
      WX wx = new WX();
      wx.setWxId(wxId);
      List<WX> wxList = wxDao.queryWXList(wx,0,999);
      ImageUtil.deleteFileOrPath(wx.getWxImg());
      int result = wxDao.deleteWX(wxId);
      if(result>0){
        return new Result(ResultEnum.SUCCESS);
      }else{
        throw new WXOperationException(ResultEnum.ERROR.getState(),"deleteWXImgById error");
      }
    }catch (Exception e){
      throw new WXOperationException(ResultEnum.ERROR.getState(),e.getMessage());
    }}else{
      return new Result(ResultEnum.ARGSISNULL);
    }
  }

  @Override
  public WX getWXById(Integer wxId) {
   WX wx  = wxDao.queryWXById(wxId);
    return wx;
  }

  /**
   * 删除所有详情图和缩略图
   *
   * @param wxId
   */
  private void deleteImgList(int wxId) {
    // 根据productId获取原来的图片
    List<WXImg> wxImgList = wxImgDao.queryWXImgList(wxId);
    // 干掉原来的图片
    for (WXImg wxImg : wxImgList) {
      ImageUtil.deleteFileOrPath(wxImg.getImgAddr());
    }
    int result = wxImgDao.deleteWXImgById(wxId);
  }

  @Transactional
  public void addWXImgList(WX wx, List<ImageHolder> wxImgHolderList) {
    // 获取图片存储路径，这里直接存放到相应店铺的文件夹底下
    String dest = PathUtil.getWXDetailImg(wx.getWxId());
    List<WXImg> wxImgList = new ArrayList<WXImg>();
    // 遍历图片一次去处理，并添加进productImg实体类里
    int i=1;
    for (ImageHolder wxImgHolder : wxImgHolderList) {
      String imgAddr = ImageUtil.generateNormalImg(wxImgHolder, dest);
      WXImg wxImg = new WXImg();
      wxImg.setCreateTime(new Date());
      wxImg.setImgAddr(imgAddr);
      wxImg.setPriority(i);
      i++;
      wxImg.setWxId(wx.getWxId());
      wxImgList.add(wxImg);
    }
    // 如果确实是有图片需要添加的，就执行批量添加操作
    if (wxImgList.size() > 0) {
      try {
        int effectedNum = wxImgDao.batchInsertWXImg(wxImgList);
        if (effectedNum <= 0) {
          throw new WXOperationException(ResultEnum.ERROR.getState(),"batchInsertWXImg error");
        }
      } catch (Exception e) {
        throw new WXOperationException(ResultEnum.ERROR.getState(),"batchInsertWXImg error:" + e.toString());
      }
    }
  }

  private String  addThumbnail(WX wx, ImageHolder thumbnail) {
    String dest = PathUtil.getWXThumbnail(wx.getWxId());
    String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
    return thumbnailAddr;
  }

}
