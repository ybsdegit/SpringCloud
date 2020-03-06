/*
Navicat MySQL Data Transfer

Source Server         : 192.168.2.133
Source Server Version : 80019
Source Host           : 192.168.2.133:3306
Source Database       : db01

Target Server Type    : MYSQL
Target Server Version : 80019
File Encoding         : 65001

Date: 2020-03-07 00:49:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dept
-- ----------------------------
DROP TABLE IF EXISTS `dept`;
CREATE TABLE `dept` (
  `deptno` bigint NOT NULL AUTO_INCREMENT,
  `dname` varchar(60) DEFAULT NULL,
  `db_source` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of dept
-- ----------------------------
INSERT INTO `dept` VALUES ('1', 'test', '01');
INSERT INTO `dept` VALUES ('2', '开发部', 'db01');
INSERT INTO `dept` VALUES ('3', '人事部', 'db01');
INSERT INTO `dept` VALUES ('4', '财务部', 'db01');
INSERT INTO `dept` VALUES ('5', '市场部', 'db01');
INSERT INTO `dept` VALUES ('6', '运维部', 'db01');
