/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : ds0

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 22/08/2020 15:46:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_sharding
-- ----------------------------
DROP TABLE IF EXISTS `base_sharding`;
CREATE TABLE `base_sharding`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sharding_key` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '分片键',
  `sharding_key_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '分片键名',
  `logic_table` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '逻辑表',
  `target_table` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '目标表',
  `target_db` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '目标数据库别名',
  `db_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '数据库真实名',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sharding_key`(`sharding_key`) USING BTREE,
  INDEX `idx_logic_table`(`logic_table`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '分片表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_sharding
-- ----------------------------
INSERT INTO `base_sharding` VALUES (1, 'test', 'test', 'base_user', 'base_user', 'ds0', 'ds0');
INSERT INTO `base_sharding` VALUES (2, 'test1', 'test1', 'base_user', 'base_user1', 'ds0', 'ds0');
INSERT INTO `base_sharding` VALUES (3, 'test2', 'test2', 'base_user', 'base_user2', 'ds0', 'ds0');
INSERT INTO `base_sharding` VALUES (4, 'test3', 'test3', 'base_user', 'base_user', 'ds1', 'ds1');
INSERT INTO `base_sharding` VALUES (5, 'test4', 'test4', 'base_user', 'base_user1', 'ds1', 'ds1');
INSERT INTO `base_sharding` VALUES (6, 'test5', 'test5', 'base_user', 'base_user2', 'ds1', 'ds1');

-- ----------------------------
-- Table structure for base_user
-- ----------------------------
DROP TABLE IF EXISTS `base_user`;
CREATE TABLE `base_user`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `school_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_user
-- ----------------------------

-- ----------------------------
-- Table structure for base_user1
-- ----------------------------
DROP TABLE IF EXISTS `base_user1`;
CREATE TABLE `base_user1`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `school_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_user1
-- ----------------------------

-- ----------------------------
-- Table structure for base_user2
-- ----------------------------
DROP TABLE IF EXISTS `base_user2`;
CREATE TABLE `base_user2`  (
  `id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `school_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `create_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of base_user2
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
