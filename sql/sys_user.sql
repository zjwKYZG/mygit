/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-04-08 00:51:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(255) DEFAULT '' COMMENT '用户名',
  `nickname` varchar(255) DEFAULT '' COMMENT '用户昵称',
  `password` varchar(255) DEFAULT '' COMMENT '密码',
  `is_admin` tinyint(4) DEFAULT '0' COMMENT '1：管理员；0：非管理员',
  `sys_dept_id` bigint(20) DEFAULT '0' COMMENT '部门ID',
  `head_pic` varchar(255) DEFAULT '' COMMENT '头像',
  `sex` tinyint(4) DEFAULT '0' COMMENT '性别（0：女；1：男）',
  `email` varchar(255) DEFAULT '' COMMENT '邮箱',
  `mobile` varchar(255) DEFAULT '' COMMENT '手机号',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `created_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updated_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态（0：正常；1：冻结；-1：删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_index` (`username`),
  KEY `created_date_index` (`created_date`),
  KEY `updated_date_index` (`updated_date`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '管理员', 'd0af8fa1272ef5a152d9e27763eea293', '1', '1', '/upload/picture/20181207/1f7767bff6dd4fe0ac4aa8720c6e5a47.png', '0', '10086@163.com', '10086', '超级管理员', '2018-08-09 23:00:03', '2018-12-08 20:21:38', '0');
INSERT INTO `sys_user` VALUES ('2', 'zjw', '章DD', '8f3073143f200f496066ee4b10d83a94', '0', '2', '/images/user-picture.jpg', '1', '15257113549@163.com', '15257113549', '章章章章章章章章章', '2018-09-30 16:25:22', '2018-12-09 00:17:57', '0');
INSERT INTO `sys_user` VALUES ('4', 'test', '测试', '25bb7c42604b0e7aba7bcae50e7762a9', '0', '19', '', '0', '110@163.com', '110', '最后一波测试。。。。。。', '2019-04-08 00:44:24', '2019-04-08 00:44:24', '0');
