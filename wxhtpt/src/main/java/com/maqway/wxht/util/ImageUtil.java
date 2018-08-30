package com.maqway.wxht.util;

import com.maqway.wxht.dto.ImageHolder;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Ma.li.ran
 * 2017/11/1 0001 15:45
 */
public class ImageUtil {

  //当前目录
  private static String basePath = Thread.currentThread().getContextClassLoader().getResource("")
      .getPath();
  private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
  private static final Random r = new Random();
  private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

  /**
   * 将CommonsMultipartFile转换成File类
   */
  public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
    File newFile = new File(cFile.getOriginalFilename());
    try {
      cFile.transferTo(newFile);
    } catch (IllegalStateException e) {
      logger.error(e.toString());
      e.printStackTrace();
    } catch (IOException e) {
      logger.error(e.toString());
      e.printStackTrace();
    }
    return newFile;
  }

  /**
   * 处理缩略图，并返回新生成图片的相对值路径
   */
  public static String generateThumbnail(ImageHolder image,
      String targetAddr) {
    String realFileName = getRandomFileName();
    String extension = getFileExtension(image.getImageName());
    makeDirPath(targetAddr);
    String relativeAddr = targetAddr + realFileName + extension;
    logger.debug("current relativeAddr is :" + relativeAddr);
    File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
    logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
    logger.debug("basePath is :" + basePath);
    try {
      //改变图片大小，添加水印在右下角，压缩为0.8f输出，第三方net.coobird/thumbnailator,0.25f水印透明度，size,合成图片大小
      //0.8f压缩比率
      Thumbnails.of(image.getImage()).size(200, 200)
          .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/resources/watermark.jpg")),
              0.25f).outputQuality(0.8f).toFile(dest);
    } catch (IOException e) {
      logger.error(e.toString());
      e.printStackTrace();
    }
    return relativeAddr;
  }

  public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
    // 获取不重复的随机名
    String realFileName = getRandomFileName();
    // 获取文件的扩展名如png,jpg等
   System.out.print(thumbnail.getImageName());
    String extension = getFileExtension(thumbnail.getImageName());
    // 如果目标路径不存在，则自动创建
    makeDirPath(targetAddr);
    // 获取文件存储的相对路径(带文件名)
    String relativeAddr = targetAddr + realFileName + extension;
    logger.debug("current relativeAddr is :" + relativeAddr);
    // 获取文件要保存到的目标路径
    File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
    logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
    // 调用Thumbnails生成带有水印的图片
    try {
      Thumbnails.of(thumbnail.getImage()).size(337, 640)
          .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/resources/watermark.jpg")), 0.25f)
          .outputQuality(0.9f).toFile(dest);
    } catch (IOException e) {
      logger.error(e.toString());
      throw new RuntimeException("创建缩图片失败：" + e.toString());
    }
    // 返回图片相对路径地址
    return relativeAddr;
  }


  /**
   * 创建目标路径所涉及到的目录，即/home/work/xiangze/xxx.jpg, 那么 home work xiangze
   * 这三个文件夹都得自动创建
   */
  private static void makeDirPath(String targetAddr) {
    String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
    File dirPath = new File(realFileParentPath);
    if (!dirPath.exists()) {
      dirPath.mkdirs();
    }
  }

  /**
   * 获取输入文件流的扩展名
   */
  private static String getFileExtension(String fileName) {
    return fileName.substring(fileName.lastIndexOf("."));
  }

  /**
   * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
   */
  public static String getRandomFileName() {
    // 获取随机的五位数
    int rannum = r.nextInt(89999) + 10000;
    String nowTimeStr = sDateFormat.format(new Date());
    return nowTimeStr + rannum;
  }

  public static void main(String[] args) throws IOException {
    Thumbnails.of(new File("/Users/baidu/work/image/xiaohuangren.jpg")).size(200, 200)
        .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/resources/watermark.jpg")),
            0.25f).outputQuality(0.8f).toFile("/Users/baidu/work/image/xiaohuangrennew.jpg");
  }

  /**
   * storePath是文件路径删除该文件，如是目录则删除该文件目录下所有文件
   */
  public static void deleteFileOrPath(String storePath) {

    File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
    if (fileOrPath.exists()) {
      if (fileOrPath.isDirectory()) {
        File[] files = fileOrPath.listFiles();
        for (int i = 0; i < files.length; i++) {
          files[i].delete();
        }
      }
      fileOrPath.delete();
    }
  }
}
