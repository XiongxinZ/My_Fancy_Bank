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
  `f_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'FromCustomer',
  `f_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'FromAccount',
  `t_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'ToCustomer',
  `t_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'ToAccount',
  `t_money` decimal(8, 2) NOT NULL COMMENT 'Amount',
  `f_balance` decimal(8, 2)  COMMENT 'FromBalance',
  `t_balance` decimal(8, 2)  COMMENT 'ToBalance',
  INDEX `f_id`(`f_id`) USING BTREE,
  INDEX `t_id`(`t_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of transfer
-- ----------------------------

INSERT INTO `transactionLog` VALUES ('2020-06-12', 'Transfer', '123', 'Saving', '333', 'Saving', 2000.00, 4800.00, 5000.0);
INSERT INTO `transactionLog` VALUES ('2020-06-12', 'Withdraw', '123', 'Saving', null, null, 100.00, 4700.00, null);
INSERT INTO `transactionLog` VALUES ('2020-06-13', 'Deposit', null, null, '333', 'Saving', 2000.00, null, 4800.00);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `c_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `c_Name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Name',
  `c_PSWD` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'password',
  `a_Saving` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Saving Account',
  `a_Checking` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Checking Account',
  `a_Loan` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Loan Account',
  `a_Security` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Security Account',
  PRIMARY KEY (`c_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
