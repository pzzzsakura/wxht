package com.maqway.wxht.service.serviceImpl;

import com.maqway.wxht.Enums.ResultEnum;
import com.maqway.wxht.Exception.HeadLineOperationException;
import com.maqway.wxht.dao.HeadLineDao;
import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.HeadLine;
import com.maqway.wxht.service.HeadLineService;
import com.maqway.wxht.util.ImageUtil;
import com.maqway.wxht.util.PathUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 18:30
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {

  @Autowired
  private HeadLineDao headLineDao;

  @Transactional
  @Override
  public Result addHeadLine(HeadLine headLine,ImageHolder imageHolder) {
    if(headLine!=null){
      try {
        int result = headLineDao.insertHeadLine(headLine);
        if(result!=-1){
          if(imageHolder!=null){
            String url = addThumbnail(headLine,imageHolder);
            try {
              HeadLine newHeadLine = new HeadLine();
              newHeadLine.setLineId(headLine.getLineId());
              newHeadLine.setLineImg(url);
              int effectedNum = headLineDao.updateHeadLine(newHeadLine);
              if(effectedNum!=-1){
                return new Result(ResultEnum.SUCCESS,headLine.getLineId());
              }else{
                throw new HeadLineOperationException(ResultEnum.ERROR.getState(),"updateHeadLine error");
              }
            }catch (Exception e){
              throw new HeadLineOperationException(ResultEnum.ERROR.getState(),e.getMessage());
            }
          }
        }else{
          throw new HeadLineOperationException(ResultEnum.ERROR.getState(),"insertHeadLine error");
        }
      }catch (Exception e){
        throw new HeadLineOperationException(ResultEnum.ERROR.getState(),e.getMessage());
      }
    }

    return null;
  }

  @Override
  public Result<List<HeadLine>> getHeadLineList() {
   HeadLine headLine = new HeadLine();
   headLine.setEnableStatus(1);
    List<HeadLine> headLineList = headLineDao.queryHeadLineList(headLine);
    return new Result(ResultEnum.SUCCESS,headLineList);
  }

  /**
   * 上传轮播图
   * @param headLine
   * @param thumbnail
   */
  private String addThumbnail(HeadLine headLine, ImageHolder thumbnail) {
    String dest = PathUtil.getHeadImagePath(headLine.getLineId());
    String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
    return thumbnailAddr;
  }

  /**
   * 删除轮播图
   *
   * @param lineId
   */
  private void deleteHeadImg(int lineId) {
    HeadLine headLine = new HeadLine();
    HeadLine h = headLineDao.queryHeadLine(lineId);
    if(h==null){
    }else{
      int result = headLineDao.deleteHeadLine(lineId);
      if(result!=-1){
        ImageUtil.deleteFileOrPath(h.getLineImg());
      }
    }
  }
}
