package com.maqway.wxht.dao;

import com.maqway.wxht.entity.WXImg;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 17:16
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface WXImgDao {


  int batchInsertWXImg(List<WXImg> wxImgList);

  int deleteWXImgById(Integer imgId);

  List<WXImg> queryWXImgList(Integer wxId);
}
