package com.maqway.wxht.dao;

import com.maqway.wxht.entity.WXManageCategory;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 14:18
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface WXManageCategoryDao {

  int insertWXManageCategory(WXManageCategory wxManageCategory);
  List<WXManageCategory> queryWXManageCategoryList(WXManageCategory wxManageCategory);
  int updateWXManageCategory(WXManageCategory wxManageCategory);

  List<WXManageCategory> queryAllList();
}
