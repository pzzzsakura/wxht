package com.maqway.wxht.service;

import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.HeadLine;
import java.awt.image.RescaleOp;
import java.util.List;

/**
 * @author: Ma.li.ran
 * @datetime: 2018/01/07 17:14
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface HeadLineService {

  Result addHeadLine(HeadLine headLine,ImageHolder imageHolder);

  Result<List<HeadLine>> getHeadLineList();
}
