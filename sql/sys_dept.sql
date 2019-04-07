/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2019-04-08 00:51:25
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `dept_name` varchar(255) DEFAULT '' COMMENT '部门名称',
  `pid` bigint(20) DEFAULT '0' COMMENT '父级ID',
  `pids` varchar(255) DEFAULT '' COMMENT '所有父级ID',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT '' COMMENT '备注',
  `created_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updated_date` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态（0：正常；1：冻结；-1：删除）',
  PRIMARY KEY (`id`),
  KEY `created_date_index` (`created_date`) USING HASH,
  KEY `updated_date_index` (`updated_date`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '总公司', '0', '[0]', '1', '', '2018-12-02 17:41:23', '2018-12-02 18:07:03', '0');
INSERT INTO `sys_dept` VALUES ('2', 'A一级部门', '1', '[1]', '1', '', '2018-12-02 17:51:04', '2018-12-02 18:07:03', '0');
INSERT INTO `sys_dept` VALUES ('3', 'B一级部门', '1', '[1]', '2', '', '2018-12-02 17:51:42', '2019-04-07 23:26:19', '0');
INSERT INTO `sys_dept` VALUES ('4', 'C一级部门', '1', '[1]', '4', '', '2018-12-02 17:51:55', '2019-04-07 23:26:15', '0');
INSERT INTO `sys_dept` VALUES ('5', 'D一级部门', '1', '[1]', '3', '', '2018-12-02 17:52:07', '2019-04-07 23:26:06', '0');
INSERT INTO `sys_dept` VALUES ('6', 'A二级部门', '2', '[1],[2]', '5', '', '2018-12-02 17:52:07', '2019-04-07 23:26:47', '0');
INSERT INTO `sys_dept` VALUES ('7', 'B二级部门', '3', '[1],[3]', '6', '', '2018-12-02 17:52:07', '2019-04-07 23:26:19', '0');
INSERT INTO `sys_dept` VALUES ('8', 'C二级部门', '4', '[1],[4]', '7', '', '2018-12-02 17:52:07', '2019-04-07 23:26:15', '0');
INSERT INTO `sys_dept` VALUES ('9', 'D二级部门', '5', '[1],[5]', '8', '', '2018-12-02 17:52:07', '2019-04-07 23:26:06', '0');
INSERT INTO `sys_dept` VALUES ('10', 'A三级部门', '6', '[1],[2],[6]', '9', '', '2018-12-02 17:52:07', '2019-04-07 23:26:47', '0');
INSERT INTO `sys_dept` VALUES ('11', 'B三级部门', '7', '[1],[3],[7]', '10', '', '2018-12-02 17:52:07', '2019-04-07 23:26:19', '0');
INSERT INTO `sys_dept` VALUES ('12', 'C三级部门', '8', '[1],[4],[8]', '11', '', '2018-12-02 17:52:07', '2019-04-07 23:26:15', '0');
INSERT INTO `sys_dept` VALUES ('13', 'D三级部门', '9', '[1],[5],[9]', '12', '', '2018-12-02 17:52:07', '2019-04-07 23:26:06', '0');
INSERT INTO `sys_dept` VALUES ('14', 'A四级部门', '10', '[1],[2],[6],[10]', '13', '', '2018-12-02 17:52:07', '2019-04-07 23:26:47', '0');
INSERT INTO `sys_dept` VALUES ('15', 'B四级部门', '11', '[1],[3],[7],[11]', '14', '', '2018-12-02 17:52:07', '2019-04-07 23:26:19', '0');
INSERT INTO `sys_dept` VALUES ('16', 'C四级部门', '12', '[1],[4],[8],[12]', '15', '', '2018-12-02 17:52:07', '2019-04-07 23:26:15', '0');
INSERT INTO `sys_dept` VALUES ('17', 'D四级部门', '13', '[1],[5],[9],[13]', '16', '', '2018-12-02 17:52:07', '2019-04-07 23:26:06', '0');
INSERT INTO `sys_dept` VALUES ('19', '测试部门', '5', '[1],[5]', '0', 'ok', '2019-04-07 19:20:42', '2019-04-07 23:26:06', '0');
