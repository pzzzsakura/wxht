package com.maqway.wxht.dao;

import com.maqway.wxht.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/26 12:14
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
public interface UserDao {

  int insertUser(User user);

  User queryUserByCondition(@Param("userCondition") User userCondition);

  int updateUserById(User user);

}
