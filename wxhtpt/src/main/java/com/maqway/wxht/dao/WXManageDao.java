package com.maqway.wxht.dao;

import com.maqway.wxht.entity.WXManage;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 14:09
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface WXManageDao {

  int insertWXManage(WXManage wxManage);

  List<WXManage> queryWXManageList(@Param("wxManage") WXManage wxManage,@Param("rowIndex") int rowIndex,@Param("pageSize")int pageSize);

  int updateWXMange(WXManage wxManage);

  int deleteWXMange(Integer wxMangeId);

  int queryCount(@Param("wxManage") WXManage wxManage);

  WXManage queryWXManageById(Integer wxManageId);

}
