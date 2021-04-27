/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : bankcustomersystem

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 28/07/2020 09:47:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;


-- ----------------------------
-- Table structure for transaction
-- ----------------------------
DROP TABLE IF EXISTS `transactionLog`;
CREATE TABLE `transactionLog`  (
  `t_date` date NOT NULL COMMENT 'Transaction Time',
  `t_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Transaction Type',
  `f_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'FromCustomer',
  `f_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'FromAccount',
  `t_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ToCustomer',
  `t_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ToAccount',
  `t_money` decimal(8, 2) NOT NULL COMMENT 'Amount',
  `c_balance` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT 'Balance',
  INDEX `f_ID`(`f_ID`) USING BTREE,
  INDEX `t_ID`(`t_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of transfer
-- ----------------------------

INSERT INTO `transactionLog` VALUES ('2020-06-12', 'Transfer', '123', 'Saving', '333', 'Saving', 2000.00, 4800.00);
INSERT INTO `transactionLog` VALUES ('2020-06-12', 'Withdraw', '123', 'Saving', '', '', 100.00, 4700.00);
INSERT INTO `transactionLog` VALUES ('2020-06-13', 'Deposit', '', '', '333', 'Saving', 2000.00, 4800.00);

