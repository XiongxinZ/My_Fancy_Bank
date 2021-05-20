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


 DROP TABLE IF EXISTS `timer`;
CREATE TABLE IF NOT EXISTS `timer`  (
   `u_id` TINYINT NOT NULL COMMENT 'id',
  `u_date` date NOT NULL COMMENT 'Last Update Time') ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for transaction
-- ----------------------------
 DROP TABLE IF EXISTS `transactionLog`;
CREATE TABLE IF NOT EXISTS `transactionLog`  (
  `t_date` date NOT NULL COMMENT 'Transaction Time',
  `t_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Transaction Type',
  `f_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'FromCustomer',
  `f_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'FromAccount',
  `t_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'ToCustomer',
  `t_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'ToAccount',
  `t_money` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Amount',
  `f_balance` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  COMMENT 'FromBalance',
  `f_currency` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'FromCurrency',
  `t_balance` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci  COMMENT 'ToBalance',
  `t_currency` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'ToCurrency',
  INDEX `f_id`(`f_id`) USING BTREE,
  INDEX `t_id`(`t_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
 DROP TABLE IF EXISTS `customer`;
CREATE TABLE IF NOT EXISTS `customer`  (
  `c_ID` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `c_Name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'Name',
  `c_PSWD` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'password',
  `c_email` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'email',
--  `a_Saving` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'Saving Account',
--  `a_Checking` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'Checking Account',
--  `a_Loan` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'Loan Account',
--  `a_Security` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'Security Account',
  PRIMARY KEY (`c_ID`) USING BTREE
--  CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`a_Saving`) REFERENCES `saving` (`a_ID`) ON DELETE SET NULL ON UPDATE CASCADE,
--  CONSTRAINT `customer_ibfk_2` FOREIGN KEY (`a_Checking`) REFERENCES `checking` (`a_ID`) ON DELETE SET NULL ON UPDATE CASCADE,
--  CONSTRAINT `customer_ibfk_3` FOREIGN KEY (`a_Loan`) REFERENCES `loan` (`a_ID`) ON DELETE SET NULL ON UPDATE CASCADE,
--  CONSTRAINT `customer_ibfk_4` FOREIGN KEY (`a_Security`) REFERENCES `security` (`a_ID`) ON NULL SET FALSE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for account
-- ----------------------------
 DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account`  (
  `c_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Customer ID',
  `a_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Account ID',
  `c_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Account Type',
  `c_Balance_USD` decimal(16, 2) NOT NULL DEFAULT 0.00 COMMENT 'USD',
  `c_Balance_CNY` decimal(16, 2) NOT NULL DEFAULT 0.00 COMMENT 'CNY',
  `c_Balance_JPY` decimal(16, 2) NOT NULL DEFAULT 0.00 COMMENT 'JPY',
  INDEX `c_ID`(`c_ID`) USING BTREE,
  PRIMARY KEY (`a_id`) USING BTREE,
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`c_ID`) REFERENCES `customer` (`c_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for collateral
-- ----------------------------
 DROP TABLE IF EXISTS `collateral`;
CREATE TABLE IF NOT EXISTS `collateral`  (
  `c_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Customer ID',
  `co_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Collateral ID',
  `co_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Collateral Name',
  `co_value` decimal(16,2) NOT NULL COMMENT 'Collateral value',
  `co_used` TINYINT NOT NULL COMMENT 'used',
  INDEX `c_ID`(`c_ID`) USING BTREE,
  PRIMARY KEY(`co_id`) USING BTREE,
  CONSTRAINT `collateral_ibfk_1` FOREIGN KEY (`c_ID`) REFERENCES `customer` (`c_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stockInfo
-- ----------------------------
INSERT INTO `collateral` VALUES("670820845","1234323445920849" ,"House2", 5000.0, 0);
INSERT INTO `collateral` VALUES("670820845","1244592024524849" ,"Car3", 50000.0, 0);

-- ----------------------------
-- Table structure for stock
-- ----------------------------
 DROP TABLE IF EXISTS `stock`;
CREATE TABLE IF NOT EXISTS `stock`  (
  `c_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Customer ID',
  `s_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Stock Name',
  `s_curr` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Stock Currency',
  `s_quantity` INT(16) NOT NULL COMMENT 'Stock Quantity',
  `b_price` decimal(16,2) NOT NULL COMMENT 'Buy Price',
  `c_price` decimal(16,2) NOT NULL COMMENT 'Current Price',
  INDEX `c_ID`(`c_ID`) USING BTREE,
  INDEX `s_name`(`s_name`) USING BTREE,
--  PRIMARY KEY (`co_id`) USING BTREE,
  CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`c_ID`) REFERENCES `customer` (`c_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stockInfo
-- ----------------------------
 DROP TABLE IF EXISTS `stockInfo`;
CREATE TABLE IF NOT EXISTS `stockInfo`  (
  `s_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Stock Name',
  `s_curr` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Stock Currency',
  `c_price` decimal(16,2) NOT NULL COMMENT 'Current Price',
--  INDEX `s_name`(`s_name`) USING BTREE,
  PRIMARY KEY (`s_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Records of stockInfo
-- ----------------------------
INSERT INTO `stockInfo` VALUES ("Apple", "USD", 100.0);
INSERT INTO `stockInfo` VALUES ("KWEICHOW MOUTAI ", "CNY", 2000.0);
INSERT INTO `stockInfo` VALUES ("TOKYO ELECTRON", "JPY", 48320.0);

-- ----------------------------
-- Table structure for stock
-- ----------------------------
 DROP TABLE IF EXISTS `realizedStock`;
CREATE TABLE IF NOT EXISTS `realizedStock`  (
  `c_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Customer ID',
  `s_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Stock Name',
  `s_curr` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Stock Currency',
  `r_profit` decimal(16,2) NOT NULL COMMENT 'Realized Profit',
  INDEX `c_ID`(`c_ID`) USING BTREE,
  INDEX `s_name`(`s_name`) USING BTREE,
--  PRIMARY KEY (`co_id`) USING BTREE,
  CONSTRAINT `realizedStock_ibfk_1` FOREIGN KEY (`c_ID`) REFERENCES `customer` (`c_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for loan
-- ----------------------------
 DROP TABLE IF EXISTS `loan`;
CREATE TABLE IF NOT EXISTS `loan`  (
  `l_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Loan ID',
  `c_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Customer ID',
  `co_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Collateral ID',
  `l_currency` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Collateral Currency',
  `l_balance` decimal(16,2) NOT NULL COMMENT 'Balance',
  INDEX `c_ID`(`c_ID`) USING BTREE,
  INDEX `co_id`(`co_id`) USING BTREE,
  PRIMARY KEY (`l_id`) USING BTREE,
  CONSTRAINT `loan_ibfk_1` FOREIGN KEY (`c_ID`) REFERENCES `customer` (`c_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

 DROP TABLE IF EXISTS `stockTransactionLog`;
CREATE TABLE IF NOT EXISTS `stockTransactionLog`  (
  `t_date` date NOT NULL COMMENT 'Transaction Time',
  `c_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Customer ID',
  `s_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Stock Name',
  `t_type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Trade Type',
  `t_price` decimal(8,2) NOT NULL COMMENT 'Trade Price',
  `t_quantity` INT(16) NOT NULL COMMENT 'Trade Quantity',
  `t_amount` decimal(8,2) NOT NULL COMMENT 'Trade Amount',
  INDEX `c_id`(`c_id`) USING BTREE,
  INDEX `s_name`(`s_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Table structure for collateralValuation
-- ----------------------------
 DROP TABLE IF EXISTS `collateralValuation`;
CREATE TABLE IF NOT EXISTS `collateralValuation`  (
  `r_date` date NOT NULL COMMENT 'Request Time',
  `c_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Customer ID',
  `cv_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Request ID',
  `co_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Collateral Name',
  `f_path` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'File Path',
  `co_value` decimal(16,2) COMMENT 'Collateral value',
  `cv_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci COMMENT 'Request Status',
  `s_date` date COMMENT 'Solve Time',
  INDEX `c_ID`(`c_ID`) USING BTREE,
  PRIMARY KEY (`cv_id`) USING BTREE,
  CONSTRAINT `collateralValuation_ibfk_1` FOREIGN KEY (`c_ID`) REFERENCES `customer` (`c_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

DROP TRIGGER IF EXISTS `updateStockPrice`;
delimiter ;;
CREATE TRIGGER `updateStockPrice` AFTER UPDATE ON `stockInfo` FOR EACH ROW BEGIN
    UPDATE stock SET stock.c_price = NEW.c_price
    WHERE stock.s_name = OLD.s_name;
END
;;
delimiter ;