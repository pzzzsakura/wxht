package com.maqway.wxht.service;

import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.WXManageCategory;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 15:10
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface WXManageCategoryService {

  Result addWXManageCategory(WXManageCategory wxManageCategory,ImageHolder imageHolder);

  Result getWXManageCategoryList(WXManageCategory wxManageCategory);

  List<WXManageCategory> getAllList();
}
