/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-04-08 00:51:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(255) DEFAULT '' COMMENT '菜单名称',
  `pid` bigint(20) DEFAULT '0' COMMENT '父级ID',
  `url` varchar(255) DEFAULT '' COMMENT 'URL地址',
  `icon` varchar(255) DEFAULT '' COMMENT '图标',
  `type` tinyint(4) DEFAULT '0' COMMENT '类型（1：一级菜单；2：二级菜单；3：按钮）',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `created_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updated_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态（0：正常；1：冻结；-1：删除）',
  `auth_code` varchar(255) NOT NULL DEFAULT '' COMMENT '授权码(多个用逗号分隔，如：sys:user:user,sys:role:role)',
  PRIMARY KEY (`id`,`auth_code`),
  KEY `created_date_index` (`created_date`),
  KEY `updated_date_index` (`updated_date`)
) ENGINE=InnoDB AUTO_INCREMENT=147 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '0', '#', 'fa fa-cog', '1', '1', '', '2018-09-29 00:05:50', '2018-11-01 11:51:08', '0', '');
INSERT INTO `sys_menu` VALUES ('2', '部门管理', '1', '/sys/dept/index', '', '2', '2', '', '2018-09-29 00:05:50', '2018-11-01 11:51:08', '0', 'sys:dept:index');
INSERT INTO `sys_menu` VALUES ('3', '员工管理', '1', '/sys/user/list', '', '2', '3', '', '2018-09-29 00:05:50', '2018-11-01 11:51:08', '0', 'sys:user:list');
