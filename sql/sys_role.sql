/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-04-08 00:51:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(255) DEFAULT '' COMMENT '角色名称',
  `role_logo` varchar(255) DEFAULT '' COMMENT '角色标识',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `created_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updated_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态（0：正常；1：冻结；-1：删除）',
  PRIMARY KEY (`id`),
  KEY `created_date_index` (`created_date`),
  KEY `updated_date_index` (`updated_date`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', 'admin', '', '2018-09-29 00:12:40', '2019-01-17 23:33:27', '0');
INSERT INTO `sys_role` VALUES ('2', '开发组', 'dev', '', '2018-09-30 16:04:32', '2018-11-30 23:46:05', '0');
INSERT INTO `sys_role` VALUES ('3', '测试组', 'test', '', '2019-01-29 17:10:39', '2019-01-29 17:11:08', '0');
