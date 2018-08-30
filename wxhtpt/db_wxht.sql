/*
SQLyog  v12.2.6 (64 bit)
MySQL - 5.7.17-log : Database - db_wxht
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_wxht` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

/*Table structure for table `tb_head_line` */

DROP TABLE IF EXISTS `tb_head_line`;

CREATE TABLE `tb_head_line` (
  `line_id` int(2) NOT NULL AUTO_INCREMENT,
  `line_name` varchar(32) NOT NULL COMMENT '名称',
  `line_link` varchar(200) NOT NULL COMMENT '跳转链接',
  `line_img` varchar(200) NOT NULL COMMENT '图片链接',
  `priority` int(2) NOT NULL COMMENT '权重',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `enable_status` int(2) NOT NULL COMMENT '状态',
  PRIMARY KEY (`line_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_head_line` */

insert  into `tb_head_line`(`line_id`,`line_name`,`line_link`,`line_img`,`priority`,`create_time`,`update_time`,`enable_status`) values 
(1,'1','1','\\upload\\headLineImgs\\1.jpg',1,'2018-01-10 11:23:16','2018-01-10 11:23:19',1),
(2,'2','2','\\upload\\headLineImgs\\2.jpg',2,'2018-01-10 11:23:36','2018-01-10 11:23:38',1);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `user_id` int(2) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(32) NOT NULL COMMENT '名称',
  `user_sex` int(1) NOT NULL COMMENT '性别',
  `user_phone` varchar(13) NOT NULL COMMENT '电话',
  `user_psw` varchar(200) NOT NULL COMMENT '密码',
  `user_img` varchar(200) DEFAULT NULL COMMENT '头像',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `enable_status` int(11) NOT NULL COMMENT '状态',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`user_id`,`user_name`,`user_sex`,`user_phone`,`user_psw`,`user_img`,`create_time`,`update_time`,`enable_status`) values 
(1,'小明',1,'18940078762 ','123456','\\upload\\userImg\\7\\2017122719364845105.jpg','2017-12-26 16:06:08','2017-12-26 16:06:08',1),
(7,'ma',1,'18640468420','ma','\\upload\\userImg\\7\\2017122719364845105.jpg','2017-12-27 19:36:48','2017-12-27 19:36:48',1);

/*Table structure for table `tb_wx` */

DROP TABLE IF EXISTS `tb_wx`;

CREATE TABLE `tb_wx` (
  `wx_id` int(2) NOT NULL AUTO_INCREMENT,
  `wx_name` varchar(32) NOT NULL COMMENT '微信号名称',
  `wx_desc` varchar(200) NOT NULL COMMENT '描述',
  `wx_img` varchar(200) DEFAULT NULL COMMENT '图片链接',
  `priority` int(2) NOT NULL COMMENT '权重',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `enable_status` int(2) NOT NULL COMMENT '状态',
  `wxmanage_id` int(2) DEFAULT NULL COMMENT '管理组',
  PRIMARY KEY (`wx_id`),
  KEY `wxmanage_id` (`wxmanage_id`),
  CONSTRAINT `tb_wx_ibfk_1` FOREIGN KEY (`wxmanage_id`) REFERENCES `tb_wxmanage` (`wxmanage_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `tb_wx` */

insert  into `tb_wx`(`wx_id`,`wx_name`,`wx_desc`,`wx_img`,`priority`,`create_time`,`update_time`,`enable_status`,`wxmanage_id`) values 
(1,'王者荣耀小妹','专注讲解攻略噢','\\upload\\wx\\thumbnail\\1\\2018011114260181267.jpg',1,'2018-01-10 16:31:44','2018-01-10 16:31:47',1,1);

/*Table structure for table `tb_wx_img` */

DROP TABLE IF EXISTS `tb_wx_img`;

CREATE TABLE `tb_wx_img` (
  `img_id` int(2) NOT NULL AUTO_INCREMENT,
  `img_addr` varchar(200) NOT NULL COMMENT '链接',
  `priority` int(2) NOT NULL COMMENT '权重',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `wx_id` int(2) DEFAULT NULL COMMENT '微信号id',
  PRIMARY KEY (`img_id`),
  KEY `wx_id` (`wx_id`),
  CONSTRAINT `tb_wx_img_ibfk_1` FOREIGN KEY (`wx_id`) REFERENCES `tb_wx` (`wx_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `tb_wx_img` */

insert  into `tb_wx_img`(`img_id`,`img_addr`,`priority`,`create_time`,`wx_id`) values 
(3,'\\upload\\wx\\detailImg\\1\\2018011114260122360.jpg',1,'2018-01-11 14:26:01',1),
(4,'\\upload\\wx\\detailImg\\1\\2018011114260122711.jpg',2,'2018-01-11 14:26:01',1),
(5,'\\upload\\wx\\detailImg\\1\\2018011114260191987.jpg',3,'2018-01-11 14:26:01',1);

/*Table structure for table `tb_wxmanage` */

DROP TABLE IF EXISTS `tb_wxmanage`;

CREATE TABLE `tb_wxmanage` (
  `wxmanage_id` int(2) NOT NULL AUTO_INCREMENT,
  `wxmanage_name` varchar(32) NOT NULL COMMENT '管理组名称',
  `wxmanage_desc` varchar(200) NOT NULL COMMENT '描述',
  `wxmanage_img` varchar(200) DEFAULT NULL COMMENT '缩略图',
  `priority` int(2) NOT NULL COMMENT '权重',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `enable_status` int(2) NOT NULL COMMENT '状态',
  `owner_id` int(2) DEFAULT NULL COMMENT '拥有者',
  `wmc_id` int(2) DEFAULT NULL COMMENT '种类',
  `advice` varchar(200) DEFAULT NULL COMMENT '审核意见',
  PRIMARY KEY (`wxmanage_id`),
  KEY `wmc_id` (`wmc_id`),
  KEY `owner_id` (`owner_id`),
  CONSTRAINT `tb_wxmanage_ibfk_1` FOREIGN KEY (`wmc_id`) REFERENCES `tb_wxmanage_category` (`wmc_id`) ON DELETE SET NULL,
  CONSTRAINT `tb_wxmanage_ibfk_2` FOREIGN KEY (`owner_id`) REFERENCES `tb_user` (`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `tb_wxmanage` */

insert  into `tb_wxmanage`(`wxmanage_id`,`wxmanage_name`,`wxmanage_desc`,`wxmanage_img`,`priority`,`create_time`,`update_time`,`enable_status`,`owner_id`,`wmc_id`,`advice`) values 
(1,'王者荣耀','攻略攻','\\upload\\wxManage\\1\\2018011113490366604.jpg',1,'2018-01-10 16:30:14','2018-01-10 16:30:16',1,1,3,NULL),
(2,'全民K歌','来一起嗨','\\upload\\wxManage\\2\\2018011112054347903.jpg',3,'2018-01-11 12:05:44','2018-01-11 12:05:44',1,1,2,NULL);

/*Table structure for table `tb_wxmanage_category` */

DROP TABLE IF EXISTS `tb_wxmanage_category`;

CREATE TABLE `tb_wxmanage_category` (
  `wmc_id` int(2) NOT NULL AUTO_INCREMENT,
  `wmc_name` varchar(32) NOT NULL COMMENT '微信管理组种类名称',
  `wmc_img` varchar(200) NOT NULL COMMENT '缩略图链接',
  `priority` int(2) NOT NULL COMMENT '权重',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `parent_id` int(2) DEFAULT NULL COMMENT '父种类主键',
  PRIMARY KEY (`wmc_id`),
  KEY `parent_id` (`parent_id`),
  CONSTRAINT `tb_wxmanage_category_ibfk_1` FOREIGN KEY (`parent_id`) REFERENCES `tb_wxmanage_category` (`wmc_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Data for the table `tb_wxmanage_category` */

insert  into `tb_wxmanage_category`(`wmc_id`,`wmc_name`,`wmc_img`,`priority`,`create_time`,`update_time`,`parent_id`) values 
(1,'生活','\\upload\\wxManageCategoryImgs\\1\\shenghuo.jpg',1,'2018-01-10 11:16:21','2018-01-10 11:16:30',NULL),
(2,'娱乐','\\upload\\wxManageCategoryImgs\\2\\yule.jpg',2,'2018-01-10 11:21:17','2018-01-10 11:21:20',NULL),
(3,'科技','\\upload\\wxManageCategoryImgs\\3\\keji.jpg',3,'2018-01-10 11:21:46','2018-01-10 11:21:49',NULL),
(4,'社交','\\upload\\wxManageCategoryImgs\\4\\shejiao.jpg',4,'2018-01-10 11:22:11','2018-01-10 11:22:13',NULL),
(5,'文艺','1',5,'2018-01-10 16:36:49','2018-01-10 16:36:52',1),
(6,'情感','1',5,'2018-01-11 17:00:37','2018-01-11 17:00:40',1),
(7,'化妆','1',1,'2018-01-11 17:01:00','2018-01-11 17:01:02',1),
(8,'人性','1',1,'2018-01-11 17:01:23','2018-01-11 17:01:26',1),
(9,'服装','1',1,'2018-01-11 17:02:02','2018-01-11 17:02:04',1),
(10,'家具','1',1,'2018-01-11 17:02:23','2018-01-11 17:02:25',1),
(11,'房车','1',1,'2018-01-11 17:04:36','2018-01-11 17:04:39',1),
(12,'生活用品','1',1,'2018-01-11 17:05:07','2018-01-11 17:05:09',1),
(13,'音乐','1',1,'2018-01-11 17:05:56','2018-01-11 17:05:58',2),
(14,'文章','1',1,'2018-01-11 17:06:17','2018-01-11 17:06:19',2),
(15,'视频','1',1,'2018-01-11 17:06:45','2018-01-11 17:06:47',2),
(16,'购物','1',1,'2018-01-11 17:07:25','2018-01-11 17:07:27',2),
(17,'逛吃','1',1,'2018-01-11 17:07:54','2018-01-11 17:07:56',2),
(18,'旅游','1',1,'2018-01-11 17:08:25','2018-01-11 17:08:27',2),
(19,'医学','1',1,'2018-01-11 17:09:27','2018-01-11 17:09:29',3),
(20,'计算机','1',1,'2018-01-11 17:13:40','2018-01-11 17:13:45',3),
(21,'工业','1',1,'2018-01-11 17:14:23','2018-01-11 17:14:25',3),
(22,'建筑','1',1,'2018-01-11 17:14:48','2018-01-11 17:14:50',3),
(23,'自然','1',1,'2018-01-11 17:15:07','2018-01-11 17:15:09',3),
(24,'农业林业','1',1,'2018-01-11 17:15:27','2018-01-11 17:15:30',3),
(25,'动漫','1',1,'2018-01-11 17:17:59','2018-01-11 17:18:01',2),
(26,'教育','1',1,'2018-01-11 17:18:42','2018-01-11 17:18:44',1),
(27,'风水','1',1,'2018-01-11 17:19:22','2018-01-11 17:19:24',2),
(28,'学校','1',1,'2018-01-11 17:21:44','2018-01-11 17:21:47',4),
(29,'社会','1',1,'2018-01-11 17:22:04','2018-01-11 17:22:07',4),
(30,'职场','1',1,'2018-01-11 17:22:32','2018-01-11 17:22:34',4);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
