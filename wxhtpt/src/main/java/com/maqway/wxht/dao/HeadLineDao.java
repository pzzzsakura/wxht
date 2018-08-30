package com.maqway.wxht.dao;

import com.maqway.wxht.entity.HeadLine;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 17:01
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface HeadLineDao {

  int insertHeadLine(HeadLine headLine);

  List<HeadLine> queryHeadLineList(HeadLine headLine);

  int deleteHeadLine(int lineId);

  HeadLine queryHeadLine(Integer lineId);


  int updateHeadLine(HeadLine newHeadLine);
}
