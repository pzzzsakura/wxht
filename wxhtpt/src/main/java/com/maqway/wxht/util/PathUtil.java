package com.maqway.wxht.util;

/**
 * 得到存储图片路径
 * Ma.li.ran
 * 2017/11/1 0001 15:21
 */
public class PathUtil {

  private static String seperator = System.getProperty("file.separator");

  /**
   * 项目图片根路径
   * @return
   */
  public static String getImgBasePath() {

    //得到当前系统
    String os = System.getProperty("os.name");
    String basePath = "";
    //通常专用一台服务器存储图片等资源
    if (os.toLowerCase().startsWith("win")) {
      basePath = "D:/wxht/images/";
    } else {
      basePath = "/mlr/project/wxht/images";
    }
    basePath = basePath.replace("/", seperator);
    return basePath;
  }

  /**
   * 头像图片子路径
   * @param userId
   * @return
   */
  public static String getUserImagePath(int userId) {
    String imagePath = "/upload/userImgs/" + userId + "/";
    return imagePath.replace("/", seperator);
  }

  /**
   * 类别缩略图子路径
   * @param  WMCId
   * * @return
   */
  public static String getWMCImagePath(int WMCId) {
    String imagePath = "/upload/wxManageCategoryImgs/" + WMCId + "/";
    return imagePath.replace("/", seperator);
  }

  /**
   * 轮播图子路径
   * @param  lineId
   * * @return
   */
  public static String getHeadImagePath(int lineId) {
    String imagePath = "/upload/headline/"+lineId+"/";
    return imagePath.replace("/", seperator);
  }

  /**
   * 群组路径
   * @param wxManageId
   * @return
   */
  public static String getWXManagePath(Integer wxManageId) {
    String imagePath = "/upload/wxManage/"+wxManageId+"/";
    return imagePath.replace("/", seperator);
  }

  /**
   * 微信缩略图路径
   * @param wxId
   * @return
   */
  public static String getWXThumbnail(Integer wxId) {
    String imagePath = "/upload/wx/thumbnail/"+wxId+"/";
    return imagePath.replace("/", seperator);
  }
  /**
   * 微信详情图路径
   * @param wxId
   * @return
   */
  public static String getWXDetailImg(Integer wxId) {
    String imagePath = "/upload/wx/detailImg/"+wxId+"/";
    return imagePath.replace("/", seperator);
  }
}
