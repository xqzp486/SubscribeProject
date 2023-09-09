/*
SQLyog Community
MySQL - 5.7.42 : Database - db_proxy
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_proxy` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `db_proxy`;

/*Table structure for table `server` */

DROP TABLE IF EXISTS `server`;

CREATE TABLE `server` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `type` char(5) DEFAULT NULL,
  `server` varchar(50) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `udp` tinyint(1) DEFAULT '1',
  `tls` tinyint(1) DEFAULT '1',
  `skip_cert_verify` tinyint(1) DEFAULT '1',
  `network` varchar(6) DEFAULT NULL,
  `opts` json DEFAULT NULL,
  `alpn` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `subscription_link` */

DROP TABLE IF EXISTS `subscription_link`;

CREATE TABLE `subscription_link` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `titile` varchar(50) DEFAULT NULL,
  `link` varchar(500) NOT NULL,
  `expire_time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` char(36) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `token` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `user_link` */

DROP TABLE IF EXISTS `user_link`;

CREATE TABLE `user_link` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` int(10) unsigned DEFAULT NULL,
  `lid` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4;

/*Table structure for table `user_server` */

DROP TABLE IF EXISTS `user_server`;

CREATE TABLE `user_server` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  `sid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=273 DEFAULT CHARSET=utf8mb4;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
