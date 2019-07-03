/*
Navicat MySQL Data Transfer

Source Server         : 乐易为服务器
Source Server Version : 50721
Source Host           : 58.213.48.27:3306
Source Database       : demo_shiro

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-06-26 16:34:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dept_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '机构名',
  `address` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '地址',
  `code` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '编号',
  `icon` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '图标',
  `pid` int(11) DEFAULT NULL COMMENT '父级主键',
  `order_num` tinyint(2) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='组织表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '办公室', '综合楼2楼206', '001343455', '', '0', '0', '', '2019-06-16 15:39:13', '2019-06-16 14:57:14');
INSERT INTO `sys_dept` VALUES ('2', '财务部', '', '07', '', '0', '3', '', '2019-06-05 20:31:49', '2019-04-24 21:21:57');
INSERT INTO `sys_dept` VALUES ('9', '软件部', '王家桥', '001', null, null, '0', '', '2019-06-26 16:03:50', '2019-06-16 15:40:10');
INSERT INTO `sys_dept` VALUES ('10', '总部', '', '01', '', null, '0', '', '2019-06-16 14:59:50', '2019-04-24 21:21:57');
INSERT INTO `sys_dept` VALUES ('14', '厂房', '50555', '0192-3455', null, '10', '0', '', '2019-06-26 15:57:11', '2019-06-26 15:56:54');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '权限',
  `resource_name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '资源名称',
  `url` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '资源路径',
  `open_mode` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '打开方式 ajax,iframe',
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '资源介绍',
  `icon` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '资源图标',
  `pid` bigint(19) DEFAULT NULL COMMENT '父级资源id',
  `opened` tinyint(2) DEFAULT '1' COMMENT '打开状态',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态（1正常，0禁用）',
  `resource_type` tinyint(2) DEFAULT '0' COMMENT '资源类别',
  `order_num` tinyint(2) DEFAULT '0' COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8mb4 COMMENT='资源表';

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', 'sys', '权限管理', null, null, '系统权限管理', 'glyphicon-folder-open ', null, '1', '1', '0', '0', null, '2019-06-26 15:20:52', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('12', 'sys:role:manager', '角色管理', '/role/manager', 'iframe', '角色管理', 'glyphicon-eye-open ', '1', '1', '1', '0', '2', null, '2019-06-26 15:47:44', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('13', 'sys:user:manager', '用户管理', '/user/manager', 'iframe', '用户管理', 'glyphicon-user ', '1', '1', '1', '0', '3', null, '2019-06-26 15:47:43', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('14', 'sys:dept:manager', '部门管理', '/dept/manager', 'iframe', '部门管理', 'glyphicon-lock ', '1', '1', '1', '0', '4', null, '2019-06-26 15:21:07', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('121', 'sys:role:dataGrid', '列表', '/role/dataGrid', 'ajax', '角色列表', 'glyphicon-list ', '12', '1', '1', '1', '0', null, '2019-06-26 15:21:07', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('122', 'sys:role:add', '添加', '/role/add', 'ajax', '角色添加', 'glyphicon-plus icon-green', '12', '1', '1', '1', '0', null, '2019-06-26 15:21:07', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('123', 'sys:role:edit', '编辑', '/role/edit', 'ajax', '角色编辑', 'glyphicon-pencil icon-blue', '12', '1', '1', '1', '0', null, '2019-06-26 15:21:07', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('124', 'sys:role:delete', '删除', '/role/delete', 'ajax', '角色删除', 'glyphicon-trash icon-red', '12', '1', '1', '1', '0', null, '2019-06-26 15:21:07', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('125', 'sys:role:grant', '授权', '/role/grant', 'ajax', '角色授权', 'glyphicon-ok icon-green', '12', '1', '1', '1', '0', null, '2019-06-26 15:21:07', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('131', 'sys:user:dataGrid', '列表', '/user/dataGrid', 'ajax', '用户列表', 'glyphicon-list ', '13', '1', '1', '1', '0', null, '2019-06-26 15:21:07', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('132', 'sys:user:add', '添加', '/user/add', 'ajax', '用户添加', 'glyphicon-plus icon-green', '13', '1', '1', '1', '0', null, '2019-06-26 15:21:08', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('133', 'sys:user:edit', '编辑', '/user/edit', 'ajax', '用户编辑', 'glyphicon-pencil icon-blue', '13', '1', '1', '1', '0', null, '2019-06-26 15:21:08', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('134', 'sys:user:delete', '删除', '/user/delete', 'ajax', '用户删除', 'glyphicon-trash icon-red', '13', '1', '1', '1', '0', null, '2019-06-26 15:21:08', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('141', 'sys:dept:treeGrid', '列表', '/dept/treeGrid', 'ajax', '用户列表', 'glyphicon-list ', '14', '1', '1', '1', '0', null, '2019-06-26 15:21:08', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('142', 'sys:dept:add', '添加', '/dept/add', 'ajax', '部门添加', 'glyphicon-plus icon-green', '14', '1', '1', '1', '0', null, '2019-06-26 15:21:08', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('143', 'sys:dept:edit', '编辑', '/dept/edit', 'ajax', '部门编辑', 'glyphicon-pencil icon-blue', '14', '1', '1', '1', '0', null, '2019-06-26 15:21:08', '2019-05-24 21:16:19');
INSERT INTO `sys_resource` VALUES ('144', 'sys:dept:delete', '删除', '/dept/delete', 'ajax', '部门删除', 'glyphicon-trash icon-red', '14', '1', '1', '1', '0', null, '2019-06-26 15:21:08', '2019-05-24 21:16:19');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `order_num` tinyint(2) DEFAULT NULL COMMENT '排序',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态（1正常，0禁用）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'admin', '超级管理员', '0', '1', '超管', '2019-06-16 15:58:41', '2019-04-24 21:20:50');
INSERT INTO `sys_role` VALUES ('3', 'boss', '老板', '2', '1', '一把手', '2019-06-16 16:16:00', '2019-06-02 20:23:46');
INSERT INTO `sys_role` VALUES ('4', 'IT', '研发人员', '5', '1', '码农', '2019-06-16 15:59:12', '2019-06-02 20:24:15');
INSERT INTO `sys_role` VALUES ('5', 'MRP', '物资计划管理', '8', '0', '', '2019-06-16 15:54:14', '2019-06-16 15:47:02');
INSERT INTO `sys_role` VALUES ('6', 'pm', '产品经理', '5', '0', '', '2019-06-26 15:53:33', '2019-06-26 15:53:33');

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `resource_id` int(11) NOT NULL COMMENT '资源id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8mb4 COMMENT='角色-资源关联';

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('1', '1', '1');
INSERT INTO `sys_role_resource` VALUES ('2', '1', '11');
INSERT INTO `sys_role_resource` VALUES ('3', '1', '12');
INSERT INTO `sys_role_resource` VALUES ('4', '1', '13');
INSERT INTO `sys_role_resource` VALUES ('5', '1', '14');
INSERT INTO `sys_role_resource` VALUES ('6', '1', '111');
INSERT INTO `sys_role_resource` VALUES ('7', '1', '112');
INSERT INTO `sys_role_resource` VALUES ('8', '1', '113');
INSERT INTO `sys_role_resource` VALUES ('9', '1', '114');
INSERT INTO `sys_role_resource` VALUES ('10', '1', '121');
INSERT INTO `sys_role_resource` VALUES ('11', '1', '122');
INSERT INTO `sys_role_resource` VALUES ('12', '1', '123');
INSERT INTO `sys_role_resource` VALUES ('13', '1', '124');
INSERT INTO `sys_role_resource` VALUES ('14', '1', '125');
INSERT INTO `sys_role_resource` VALUES ('15', '1', '131');
INSERT INTO `sys_role_resource` VALUES ('16', '1', '132');
INSERT INTO `sys_role_resource` VALUES ('17', '1', '133');
INSERT INTO `sys_role_resource` VALUES ('18', '1', '134');
INSERT INTO `sys_role_resource` VALUES ('19', '1', '141');
INSERT INTO `sys_role_resource` VALUES ('20', '1', '142');
INSERT INTO `sys_role_resource` VALUES ('21', '1', '143');
INSERT INTO `sys_role_resource` VALUES ('22', '1', '144');
INSERT INTO `sys_role_resource` VALUES ('23', '2', '1');
INSERT INTO `sys_role_resource` VALUES ('24', '2', '11');
INSERT INTO `sys_role_resource` VALUES ('25', '2', '111');
INSERT INTO `sys_role_resource` VALUES ('26', '2', '112');
INSERT INTO `sys_role_resource` VALUES ('27', '2', '113');
INSERT INTO `sys_role_resource` VALUES ('28', '2', '114');
INSERT INTO `sys_role_resource` VALUES ('29', '2', '12');
INSERT INTO `sys_role_resource` VALUES ('30', '2', '121');
INSERT INTO `sys_role_resource` VALUES ('31', '2', '122');
INSERT INTO `sys_role_resource` VALUES ('32', '2', '123');
INSERT INTO `sys_role_resource` VALUES ('33', '2', '124');
INSERT INTO `sys_role_resource` VALUES ('34', '2', '125');
INSERT INTO `sys_role_resource` VALUES ('35', '2', '13');
INSERT INTO `sys_role_resource` VALUES ('36', '2', '131');
INSERT INTO `sys_role_resource` VALUES ('37', '2', '132');
INSERT INTO `sys_role_resource` VALUES ('38', '2', '133');
INSERT INTO `sys_role_resource` VALUES ('39', '2', '134');
INSERT INTO `sys_role_resource` VALUES ('40', '2', '14');
INSERT INTO `sys_role_resource` VALUES ('41', '2', '141');
INSERT INTO `sys_role_resource` VALUES ('42', '2', '142');
INSERT INTO `sys_role_resource` VALUES ('43', '2', '143');
INSERT INTO `sys_role_resource` VALUES ('44', '2', '144');
INSERT INTO `sys_role_resource` VALUES ('70', '5', '1');
INSERT INTO `sys_role_resource` VALUES ('71', '5', '13');
INSERT INTO `sys_role_resource` VALUES ('72', '5', '131');
INSERT INTO `sys_role_resource` VALUES ('90', '4', '1');
INSERT INTO `sys_role_resource` VALUES ('91', '4', '11');
INSERT INTO `sys_role_resource` VALUES ('92', '4', '111');
INSERT INTO `sys_role_resource` VALUES ('93', '4', '112');
INSERT INTO `sys_role_resource` VALUES ('94', '4', '12');
INSERT INTO `sys_role_resource` VALUES ('95', '4', '121');
INSERT INTO `sys_role_resource` VALUES ('96', '4', '13');
INSERT INTO `sys_role_resource` VALUES ('97', '4', '131');
INSERT INTO `sys_role_resource` VALUES ('98', '4', '14');
INSERT INTO `sys_role_resource` VALUES ('99', '3', '1');
INSERT INTO `sys_role_resource` VALUES ('100', '3', '12');
INSERT INTO `sys_role_resource` VALUES ('101', '3', '121');
INSERT INTO `sys_role_resource` VALUES ('102', '3', '122');
INSERT INTO `sys_role_resource` VALUES ('103', '3', '123');
INSERT INTO `sys_role_resource` VALUES ('104', '3', '124');
INSERT INTO `sys_role_resource` VALUES ('105', '3', '13');
INSERT INTO `sys_role_resource` VALUES ('106', '3', '131');
INSERT INTO `sys_role_resource` VALUES ('107', '3', '132');
INSERT INTO `sys_role_resource` VALUES ('108', '3', '133');
INSERT INTO `sys_role_resource` VALUES ('109', '3', '134');
INSERT INTO `sys_role_resource` VALUES ('110', '6', '121');
INSERT INTO `sys_role_resource` VALUES ('111', '6', '13');
INSERT INTO `sys_role_resource` VALUES ('112', '6', '131');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `login_name` varchar(255) DEFAULT NULL COMMENT '登录名',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) DEFAULT NULL COMMENT '加密盐',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话',
  `user_type` tinyint(2) DEFAULT '1' COMMENT '用户类型（0管理员，1普通）',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态（1正常，0禁用）',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门id',
  `order_num` varchar(255) DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '超级管理员', '72e1242b855fb038212135e0ad348842', 'test', '1', '25', '13521221123', '0', '1', '1', '1', null, '2019-06-16 11:37:16', '2019-04-23 21:39:16');
INSERT INTO `sys_user` VALUES ('2', 'lyw', '乐易为', '085de60f32b004402326e5ee425a6167', 'test', '1', '22', '13898968855', '0', '1', '1', '2', '', '2019-06-23 20:37:37', '2019-06-02 17:27:51');
INSERT INTO `sys_user` VALUES ('3', 'zhangsan', '张三', '77ceb3dd503715bd7fdc6e4362468282', 'b2091b0063b74dfb9c9b384b49174dc8', '1', '37', '13687879990', '1', '1', '2', null, '', '2019-06-23 20:37:32', '2019-06-23 11:02:54');
INSERT INTO `sys_user` VALUES ('4', 'lisi', '李四', '1eb873a21294abdf7760f8032948f2f3', '3700038ac8e84e4081a83b9873bab487', '1', '30', '15556574678', '1', '1', '9', null, '', '2019-06-26 16:04:09', '2019-06-26 15:58:10');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关联';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '1', '2');
INSERT INTO `sys_user_role` VALUES ('10', '5', '3');
INSERT INTO `sys_user_role` VALUES ('14', '6', '2');
INSERT INTO `sys_user_role` VALUES ('54', '2', '4');
INSERT INTO `sys_user_role` VALUES ('59', '3', '3');
INSERT INTO `sys_user_role` VALUES ('62', '4', '6');
