package com.maqway.wxht.service;

import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.WXManage;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 19:00
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface WXManageService {

  Result addWXManage(WXManage wxManage,ImageHolder imageHolder);

  Result getWXManageList(WXManage wxManage,int pageIndex,int pageSize);

  Result modifyWXMange(WXManage wxManage,ImageHolder ImageHolder);

  Result removeWXMange(Integer wxMangeId);

  WXManage queryWXManageById(Integer wxManageId);
}
