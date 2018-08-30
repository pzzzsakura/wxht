package com.maqway.wxht.service;

import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.User;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/26 12:36
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface UserService {

  Result addUser(User user,ImageHolder imageHolder);

  Result getUserByCondition(User userCondition);

  Result ModifyUserById(User user,ImageHolder imageHolder);
}
