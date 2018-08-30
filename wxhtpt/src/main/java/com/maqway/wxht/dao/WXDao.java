package com.maqway.wxht.dao;

import com.maqway.wxht.entity.WX;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 16:24
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface WXDao {

  int insertWX(WX wx);

  List<WX> queryWXList(@Param("wx") WX wx,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);

  int updateWX(WX wx);

  int queryCount(@Param("wx") WX wx);

  int deleteWX(Integer wxId);

  WX queryWXById(@Param("wxId") Integer wxId);
}
