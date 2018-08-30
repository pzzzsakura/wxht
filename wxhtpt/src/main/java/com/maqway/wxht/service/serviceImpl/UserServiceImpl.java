package com.maqway.wxht.service.serviceImpl;

import com.maqway.wxht.Enums.ResultEnum;
import com.maqway.wxht.Exception.UserOperationException;
import com.maqway.wxht.dao.UserDao;
import com.maqway.wxht.dto.Execution.Result;
import com.maqway.wxht.dto.ImageHolder;
import com.maqway.wxht.entity.User;
import com.maqway.wxht.service.UserService;
import com.maqway.wxht.util.ImageUtil;
import com.maqway.wxht.util.PathUtil;
import java.awt.Image;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Ma.li.ran
 * @datetime: 2017/12/26 15:11
 * @desc:
 * @environment: jdk1.8.0_121/IDEA 2017.2.6/Tomcat8.0.47/mysql5.7
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserDao userDao;

  /**
   * 注册用户信息
   * @param user
   * @return
   */
  @Transactional
  @Override
  public Result addUser(User user,ImageHolder imageHolder) {
    if(user!=null){
      user.setCreateTime(new Date());
      user.setEnableStatus(1);//1 可用 0 黑名单
      user.setUpdateTime(new Date());

      //判断用户名是否被注册
      User userConditionName= new User();
      userConditionName.setUserPhone(user.getUserName());
      User userResultName = userDao.queryUserByCondition(userConditionName);
      if(userResultName!=null){
        return new Result(ResultEnum.NAMEISEXIST);
      }
      //判断电话号是否被注册
      User userCondition = new User();
      userCondition.setUserPhone(user.getUserPhone());
      User userResultPhone = userDao.queryUserByCondition(userCondition);
      if(userResultPhone!=null){
        return new Result(ResultEnum.PHONEISEXIST);
      }
      int effectedNum;
      try {
        effectedNum = userDao.insertUser(user);
        if(effectedNum!=-1){
          //如果有头像上传头像
          if(imageHolder!=null){
            String img = addThumbnail(user,imageHolder);
            User newUser = new User();
            newUser.setUserId(user.getUserId());
            newUser.setUserImg(img);
            try {
              userDao.updateUserById(newUser);
            }catch (Exception e){
              throw new UserOperationException(ResultEnum.ERROR.getState(),"上传头像失败");
            }
          }
          return new Result(ResultEnum.SUCCESS,user);
        }else{
          throw new UserOperationException(ResultEnum.ERROR.getState(),"addUser error");
        }
      }catch (Exception e){
        throw new UserOperationException(ResultEnum.ERROR.getState(),e.getMessage());
      }
    }else{
      return new Result(ResultEnum.ARGSISNULL);
    }
  }

  /**
   * 根据条件获得用户信息
   * @param userCondition
   * @return
   */
  @Override
  public Result getUserByCondition(User userCondition) {
    if(userCondition!=null){
      User user = userDao.queryUserByCondition(userCondition);
      return new Result(ResultEnum.SUCCESS,user);
    }else{
      return new Result(ResultEnum.ARGSISNULL);
    }
  }

  /**
   * 根据id更新用户信息
   * @param user
   * @return
   */
  @Transactional
  @Override
  public Result ModifyUserById(User user,ImageHolder imageHolder) {
    if(user!=null&&user.getUserId()!=-1){
      user.setUpdateTime(new Date());
      deleteUserImg(user.getUserId());
      addThumbnail(user,imageHolder);
      int effectedNum;
      try {
        effectedNum = userDao.updateUserById(user);
      if(effectedNum!=-1){
        return new Result(ResultEnum.SUCCESS,user);
      }else{
        throw new UserOperationException(ResultEnum.ERROR.getState(),"ModifyUserById error");
      }
      }catch (Exception e){
        throw new UserOperationException(ResultEnum.ERROR.getState(),e.getMessage());
      }
    }else{
      return new Result(ResultEnum.ARGSISNULL);
    }
  }

  /**
   * 上传头像
   * @param user
   * @param thumbnail
   */
  private String addThumbnail(User user, ImageHolder thumbnail) {
    String dest = PathUtil.getUserImagePath(user.getUserId());
    String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
    return thumbnailAddr;
  }

  /**
   * 删除头像
   *
   * @param userId
   */
  private void deleteUserImg(int userId) {
      User user = new User();
      user.setUserId(userId);
      User userResult = userDao.queryUserByCondition(user);
      if(userResult!=null&&userResult.getUserImg()!=null){
        ImageUtil.deleteFileOrPath(userResult.getUserImg());
      }
  }
}
