/*
Navicat MySQL Data Transfer

Source Server         : localhost_3307
Source Server Version : 50717
Source Host           : localhost:3307
Source Database       : mydb

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-01-31 17:36:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for players
-- ----------------------------
DROP TABLE IF EXISTS `players`;
CREATE TABLE `players` (
  `id` varchar(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `account_id` varchar(64) NOT NULL,
  `platform` varchar(64) DEFAULT NULL,
  `open_id` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `players_name_uindex` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='玩家';
