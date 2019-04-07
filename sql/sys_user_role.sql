/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-04-08 00:52:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sys_user_id` bigint(20) DEFAULT '0' COMMENT '用户ID',
  `sys_role_id` bigint(20) DEFAULT '0' COMMENT '角色ID',
  PRIMARY KEY (`id`),
  KEY `sys_user_id_index` (`sys_user_id`),
  KEY `sys_role_id_index` (`sys_role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '2', '2');
INSERT INTO `sys_user_role` VALUES ('2', '2', '3');
