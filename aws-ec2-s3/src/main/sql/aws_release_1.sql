/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 5.7.40-log : Database - aws
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`aws` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `aws`;

/*Table structure for table `ec2_instance` */

DROP TABLE IF EXISTS `ec2_instance`;

CREATE TABLE `ec2_instance` (
  `id` bigint(20) NOT NULL,
  `instance_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsjj2fc4mtvdhhi1xgqh1lrrd7` (`instance_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ec2_instance` */

/*Table structure for table `ec2_instance_seq` */

DROP TABLE IF EXISTS `ec2_instance_seq`;

CREATE TABLE `ec2_instance_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ec2_instance_seq` */

insert  into `ec2_instance_seq`(`next_val`) values 
(1001);

/*Table structure for table `job` */

DROP TABLE IF EXISTS `job`;

CREATE TABLE `job` (
  `id` bigint(20) NOT NULL,
  `serivces` varchar(255) DEFAULT NULL,
  `status` enum('SUCCESS','INPROGRESS','FAILED') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `job` */

/*Table structure for table `job_seq` */

DROP TABLE IF EXISTS `job_seq`;

CREATE TABLE `job_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `job_seq` */

insert  into `job_seq`(`next_val`) values 
(1001);

/*Table structure for table `s3_bucket` */

DROP TABLE IF EXISTS `s3_bucket`;

CREATE TABLE `s3_bucket` (
  `id` bigint(20) NOT NULL,
  `bucket_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKmeujet2mo1hyj0gi9am4ii268` (`bucket_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `s3_bucket` */

/*Table structure for table `s3_bucket_object` */

DROP TABLE IF EXISTS `s3_bucket_object`;

CREATE TABLE `s3_bucket_object` (
  `id` bigint(20) NOT NULL,
  `object_key` varchar(255) DEFAULT NULL,
  `bucket_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK79f43m2bt3inv0cc0alp7tkjr` (`bucket_id`),
  CONSTRAINT `FK79f43m2bt3inv0cc0alp7tkjr` FOREIGN KEY (`bucket_id`) REFERENCES `s3_bucket` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `s3_bucket_object` */

/*Table structure for table `s3_bucket_object_seq` */

DROP TABLE IF EXISTS `s3_bucket_object_seq`;

CREATE TABLE `s3_bucket_object_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `s3_bucket_object_seq` */

insert  into `s3_bucket_object_seq`(`next_val`) values 
(1001);

/*Table structure for table `s3_bucket_seq` */

DROP TABLE IF EXISTS `s3_bucket_seq`;

CREATE TABLE `s3_bucket_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `s3_bucket_seq` */

insert  into `s3_bucket_seq`(`next_val`) values 
(1001);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
