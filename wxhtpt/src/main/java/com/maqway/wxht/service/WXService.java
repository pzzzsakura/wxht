package com.maqway.wxht.service;

import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.WX;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/08 17:39
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface WXService {

  Result addWX(WX wx,ImageHolder imageHolder,List<ImageHolder> imageHolderList);

  Result getWXList(WX wx,int pageIndex,int pageSize);

  Result modifyWX(WX wx,ImageHolder imageHolder,List<ImageHolder> imageHolderList);

  Result removeWX(Integer wxId);

  WX getWXById(Integer wxId);
}
