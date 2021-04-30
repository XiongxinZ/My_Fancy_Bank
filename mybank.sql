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
-- DROP TABLE IF EXISTS `transactionLog`;
CREATE TABLE IF NOT EXISTS `transactionLog`  (
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

-- ----------------------------
-- Table structure for customer
-- ----------------------------
-- DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer`  (
  `c_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `c_Name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'Name',
  `c_PSWD` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'password',
  `c_email` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'email',
  `a_Saving` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Saving Account',
  `a_Checking` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Checking Account',
  `a_Loan` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Loan Account',
  `a_Security` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Security Account',
  PRIMARY KEY (`c_ID`) USING BTREE,
  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`a_Saving`) REFERENCES `saving` (`a_ID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `customer_ibfk_2` FOREIGN KEY (`a_Checking`) REFERENCES `checking` (`a_ID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `customer_ibfk_3` FOREIGN KEY (`a_Loan`) REFERENCES `loan` (`a_ID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `customer_ibfk_4` FOREIGN KEY (`a_Security`) REFERENCES `security` (`a_ID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for account
-- ----------------------------
-- DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account`  (
  `c_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Customer ID',
  `a_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Account ID',
  `c_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Account Type',
  `c_Balance_USD` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT 'USD',
  `c_Balance_CNY` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT 'CNY',
  `c_Balance_JPY` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT 'JPY',
  INDEX `c_ID`(`c_ID`) USING BTREE,
  PRIMARY KEY (`a_id`) USING BTREE,
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`c_ID`) REFERENCES `customer` (`c_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
--  ----------------------------
--  Table structure for transfer
--  ----------------------------
--  DROP TABLE IF EXISTS `security`;
-- CREATE TABLE IF NOT EXISTS `security`  (
--   `c_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Customer ID',
--   `a_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Account ID',
--   `c_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Account Type',
--   `c_Balance` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT 'Balance',
--   INDEX `c_ID`(`c_ID`) USING BTREE,
--   INDEX `c_account`(`c_account`) USING BTREE,
--   PRIMARY KEY (`c_ID`, `c_account`) USING BTREE,
--   CONSTRAINT `security_ibfk_1` FOREIGN KEY (`c_ID`) REFERENCES `customer` (`c_ID`) ON DELETE CASCADE ON UPDATE CASCADE
-- ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
--
-- -- ----------------------------
-- -- Table structure for account
-- -- ----------------------------
-- -- DROP TABLE IF EXISTS `saving`;
-- CREATE TABLE IF NOT EXISTS `account`  (
--   `c_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Customer ID',
--   `a_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Account ID',
--   `c_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Account Type',
--   `c_Balance_USD` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT 'USD',
--   `c_Balance_CNY` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT 'CNY',
--   `c_Balance_JPY` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT 'JPY',
--   INDEX `c_ID`(`c_ID`) USING BTREE,
--   PRIMARY KEY (`c_ID`, `c_account`) USING BTREE,
--   CONSTRAINT `account_ibfk_1` FOREIGN KEY (`c_ID`) REFERENCES `customer` (`c_ID`) ON DELETE CASCADE ON UPDATE CASCADE
-- ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
--
-- -- ----------------------------
-- -- Table structure for account
-- -- ----------------------------
-- -- DROP TABLE IF EXISTS `checking`;
-- CREATE TABLE IF NOT EXISTS `checking`  (
--   `c_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Customer ID',
--   `a_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Account ID',
--   `c_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Account Type',
--   `c_Balance_USD` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT 'USD',
--   `c_Balance_CNY` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT 'CNY',
--   `c_Balance_JPY` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT 'JPY',
--   INDEX `c_ID`(`c_ID`) USING BTREE,
--   CONSTRAINT `checking_ibfk_1` FOREIGN KEY (`c_ID`) REFERENCES `customer` (`c_ID`) ON DELETE CASCADE ON UPDATE CASCADE
-- ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
--
-- -- ----------------------------
-- -- Table structure for transfer
-- -- ----------------------------
-- -- DROP TABLE IF EXISTS `loan`;
-- CREATE TABLE IF NOT EXISTS `loan`  (
--   `c_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Customer ID',
--   `a_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Account ID',
--   `c_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Account Type',
--   `c_Balance` decimal(8, 2) NOT NULL DEFAULT 0.00 COMMENT 'Balance',
--   INDEX `c_ID`(`c_ID`) USING BTREE,
--   INDEX `c_account`(`c_account`) USING BTREE,
--   PRIMARY KEY (`c_ID`, `c_account`) USING BTREE,
--   CONSTRAINT `loan_ibfk_1` FOREIGN KEY (`c_ID`) REFERENCES `customer` (`c_ID`) ON DELETE CASCADE ON UPDATE CASCADE
-- ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
