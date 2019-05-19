/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 80015
Source Host           : localhost:3306
Source Database       : easyweb

Target Server Type    : MYSQL
Target Server Version : 80015
File Encoding         : 65001

Date: 2019-05-15 12:53:44
*/

USE `easyweb`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for appointments
-- ----------------------------
DROP TABLE IF EXISTS `appointments`;
CREATE TABLE `appointments` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `doc_id` int(10) DEFAULT NULL,
  `pat_id` int(10) DEFAULT NULL,
  `describe` varchar(100) DEFAULT NULL COMMENT '症状描述',
  `question1` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `answer1` varchar(100) DEFAULT NULL,
  `question2` varchar(50) DEFAULT NULL,
  `answer2` varchar(100) DEFAULT NULL,
  `department_id` int(10) DEFAULT NULL COMMENT '科室id',
  `apmt_time` datetime DEFAULT NULL COMMENT '预约时间',
  `supply_time` datetime DEFAULT NULL COMMENT '申请时间',
  `type` int(1) DEFAULT NULL COMMENT '预约类型 1-远程 2-线下',
  `completed` int(1) DEFAULT NULL COMMENT '完成情况 0-否 1-是',
  PRIMARY KEY (`id`),
  KEY `doc_id` (`doc_id`),
  KEY `pat_id` (`pat_id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`doc_id`) REFERENCES `doctors` (`doc_id`) ON UPDATE RESTRICT,
  CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`pat_id`) REFERENCES `patients` (`pat_id`) ON UPDATE RESTRICT,
  CONSTRAINT `appointments_ibfk_3` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of appointments
-- ----------------------------
INSERT INTO `appointments` VALUES ('1', '3', '2', '头晕，恶心', '想挂什么科？', '神经内科', '想找哪位医生？', '刘主任', '1', '2019-03-19 09:30:00', '2019-03-16 19:42:07', '1', '0');
INSERT INTO `appointments` VALUES ('2', '3', '2', null, null, null, null, null, '2', '2019-03-19 13:30:00', '2019-03-19 09:47:36', '2', '0');
INSERT INTO `appointments` VALUES ('3', '3', '4', null, null, null, null, null, '3', '2019-03-19 14:30:00', '2019-03-19 12:05:21', '1', '0');

-- ----------------------------
-- Table structure for chat_record
-- ----------------------------
DROP TABLE IF EXISTS `chat_record`;
CREATE TABLE `chat_record` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `pat_id` int(10) DEFAULT NULL,
  `doc_id` int(10) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL COMMENT '内容',
  `sender` int(3) DEFAULT NULL COMMENT '发送方：1-医生 2-病人',
  `send_time` datetime DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`),
  KEY `pat_id` (`pat_id`),
  KEY `doc_id` (`doc_id`),
  CONSTRAINT `chat_record_ibfk_1` FOREIGN KEY (`pat_id`) REFERENCES `patients` (`pat_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `chat_record_ibfk_2` FOREIGN KEY (`doc_id`) REFERENCES `doctors` (`doc_id`) ON DELETE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of chat_record
-- ----------------------------
INSERT INTO `chat_record` VALUES ('1', '2', '3', '您好，大夫', '2', '2019-03-16 19:44:42');
INSERT INTO `chat_record` VALUES ('2', '2', '3', '您好，请讲', '1', '2019-03-16 19:44:57');
INSERT INTO `chat_record` VALUES ('3', '2', '3', '我的胃不舒服，食欲不振', '2', '2019-03-16 19:45:16');
INSERT INTO `chat_record` VALUES ('4', '2', '3', '请再描述得详细些', '1', '2019-03-16 19:45:28');
INSERT INTO `chat_record` VALUES ('5', '2', '3', '我最近在饭点也不会饿', '2', '2019-03-16 19:45:39');
INSERT INTO `chat_record` VALUES ('6', '2', '3', '以前食欲很好，现在是不吃也不会饿，早午饭都没有吃，也没有饿意，空腹时甚至会闻到饭的香味而全然没有进食的欲望', '2', '2019-03-16 19:45:58');
INSERT INTO `chat_record` VALUES ('7', '2', '3', '您的症状应该是胃肠功能紊乱了', '1', '2019-03-16 19:46:12');
INSERT INTO `chat_record` VALUES ('8', '2', '3', '是否有胃胀气，打嗝等？', '1', '2019-03-16 19:46:25');
INSERT INTO `chat_record` VALUES ('9', '2', '3', '会有，最近很频繁', '2', '2019-03-16 19:46:51');
INSERT INTO `chat_record` VALUES ('10', '2', '3', '好的，您的情况我了解了，是压力过大导致的消化功能紊乱', '1', '2019-03-16 19:47:21');
INSERT INTO `chat_record` VALUES ('11', '2', '3', '我现在给您写处方', '1', '2019-03-16 19:47:36');
INSERT INTO `chat_record` VALUES ('12', '2', '3', '建议服用斯达舒，按时吃药', '1', '2019-03-16 19:47:50');
INSERT INTO `chat_record` VALUES ('13', '2', '3', '好的，大夫，谢谢您', '2', '2019-03-16 19:48:03');
INSERT INTO `chat_record` VALUES ('14', '2', '3', '不客气，祝您健康', '1', '2019-03-16 19:48:13');
INSERT INTO `chat_record` VALUES ('15', '2', '3', '再见', '2', '2019-03-16 19:48:26');

-- ----------------------------
-- Table structure for clinics
-- ----------------------------
DROP TABLE IF EXISTS `clinics`;
CREATE TABLE `clinics` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `longitude` int(10) DEFAULT NULL COMMENT '经度',
  `latitude` int(10) DEFAULT NULL COMMENT '纬度',
  `support_insurance` varchar(500) DEFAULT NULL COMMENT '支持的保险种类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of clinics
-- ----------------------------
INSERT INTO `clinics` VALUES ('1', '约翰·霍普金斯医院', '美国马里兰州巴尔的摩市21287', '50', '60', '医院保险、内外科保险、重要医疗保险、特种疾病保险');
INSERT INTO `clinics` VALUES ('2', '密歇根大学医院和健康中心', '密歇根州安阿伯市1500', '55', '65', '医疗保险、特种疾病保险');
INSERT INTO `clinics` VALUES ('3', '哈尔滨工业大学校医院', '校外街', null, null, null);

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL COMMENT '科室名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of departments
-- ----------------------------
INSERT INTO `departments` VALUES ('1', '全科');
INSERT INTO `departments` VALUES ('2', '精神科');
INSERT INTO `departments` VALUES ('3', '皮肤科');
INSERT INTO `departments` VALUES ('4', '儿科');
INSERT INTO `departments` VALUES ('5', '疼痛康复科');
INSERT INTO `departments` VALUES ('6', '心血管科');
INSERT INTO `departments` VALUES ('7', '牙科');
INSERT INTO `departments` VALUES ('8', '肠胃科');
INSERT INTO `departments` VALUES ('9', '肿瘤科');
INSERT INTO `departments` VALUES ('10', '神经内科');
INSERT INTO `departments` VALUES ('11', '妇产科');
INSERT INTO `departments` VALUES ('12', '过敏科');
INSERT INTO `departments` VALUES ('17', '妇科');

-- ----------------------------
-- Table structure for department_questions
-- ----------------------------
DROP TABLE IF EXISTS `department_questions`;
CREATE TABLE `department_questions` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `question_no` int(10) DEFAULT NULL COMMENT '该科室的第几个问题',
  `question` varchar(100) DEFAULT NULL,
  `department_id` int(10) DEFAULT NULL COMMENT '科室id',
  PRIMARY KEY (`id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `department_questions_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of department_questions
-- ----------------------------
INSERT INTO `department_questions` VALUES ('1', '1', '您需要诊治的部位是哪里？若不明确，请说大致区域', '1');
INSERT INTO `department_questions` VALUES ('2', '2', '请描述一下患处的异样感，不适感觉的特点，变化，疼痛频率等', '1');
INSERT INTO `department_questions` VALUES ('3', '3', '上述情况持续多久了', '1');
INSERT INTO `department_questions` VALUES ('4', '4', '您曾经是否有过相关的诊疗史？', '1');
INSERT INTO `department_questions` VALUES ('5', '1', '请您描述一下口腔内出现的问题', '7');
INSERT INTO `department_questions` VALUES ('6', '2', '上述情况持续多久了？', '7');
INSERT INTO `department_questions` VALUES ('7', '3', '患处是否会持续疼痛？', '7');
INSERT INTO `department_questions` VALUES ('8', '4', '现在对于进食是否有影响？', '7');

-- ----------------------------
-- Table structure for doctors
-- ----------------------------
DROP TABLE IF EXISTS `doctors`;
CREATE TABLE `doctors` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `doc_id` int(10) NOT NULL COMMENT '医生id，即users表的user_id字段',
  `name` varchar(20) DEFAULT NULL COMMENT '姓名',
  `cli_id` int(10) DEFAULT NULL COMMENT '所属诊所id',
  `dep_id` int(10) DEFAULT NULL COMMENT '科室',
  `title` varchar(50) DEFAULT NULL COMMENT '职称',
  `attending` varchar(50) DEFAULT NULL COMMENT '主诊',
  `introduction` varchar(200) DEFAULT NULL COMMENT '简介',
  `language` varchar(100) DEFAULT NULL COMMENT '语言',
  PRIMARY KEY (`id`),
  KEY `cli_id` (`cli_id`) USING BTREE,
  KEY `dep_id` (`dep_id`),
  KEY `doctors_ibfk_1` (`doc_id`),
  CONSTRAINT `doctors_ibfk_1` FOREIGN KEY (`doc_id`) REFERENCES `sys_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `doctors_ibfk_2` FOREIGN KEY (`cli_id`) REFERENCES `clinics` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `doctors_ibfk_3` FOREIGN KEY (`dep_id`) REFERENCES `departments` (`id`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of doctors
-- ----------------------------
INSERT INTO `doctors` VALUES ('2', '3', '刘颖', '3', '4', '主任医师', '儿科综合、小儿内科、小儿外科、新生儿科、儿童营养保健科', '1986年毕业于首都医科大学儿科系，从事儿科临床工作20年。', '普通话，英语，粤语');
INSERT INTO `doctors` VALUES ('3', '5', '杨杰瑞', '1', '4', '主任医师', '儿科', '1985年毕业于复旦大学医学部儿科系，从事儿科临床工作31年。', '普通话，英语，粤语');

-- ----------------------------
-- Table structure for doctor_time
-- ----------------------------
DROP TABLE IF EXISTS `doctor_time`;
CREATE TABLE `doctor_time` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `doc_id` int(10) NOT NULL COMMENT '医生id 外键',
  `date` date DEFAULT NULL COMMENT '日期',
  `begin_time` time DEFAULT NULL COMMENT '开始时间',
  `end_time` time DEFAULT NULL COMMENT '结束时间',
  `type` int(1) DEFAULT NULL COMMENT '预约类型1线上2线下',
  `appointed` int(1) DEFAULT '0' COMMENT '是否已被预约 1-是 0-否',
  PRIMARY KEY (`id`),
  KEY `doc_id` (`doc_id`),
  CONSTRAINT `doctor_time_ibfk_1` FOREIGN KEY (`doc_id`) REFERENCES `doctors` (`doc_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of doctor_time
-- ----------------------------
INSERT INTO `doctor_time` VALUES ('1', '3', '2019-03-19', '08:30:00', '09:30:00', '1', '0');
INSERT INTO `doctor_time` VALUES ('2', '3', '2019-03-19', '09:30:00', '10:00:00', '1', '1');
INSERT INTO `doctor_time` VALUES ('3', '3', '2019-03-19', '10:00:00', '10:30:00', '1', '0');
INSERT INTO `doctor_time` VALUES ('4', '3', '2019-03-19', '10:30:00', '11:00:00', '1', '0');
INSERT INTO `doctor_time` VALUES ('5', '3', '2019-03-19', '11:00:00', '11:30:00', '1', '0');
INSERT INTO `doctor_time` VALUES ('6', '3', '2019-03-19', '13:30:00', '14:00:00', '2', '0');
INSERT INTO `doctor_time` VALUES ('7', '3', '2019-03-19', '14:00:00', '14:30:00', '2', '0');
INSERT INTO `doctor_time` VALUES ('8', '3', '2019-03-19', '14:30:00', '15:00:00', '2', '0');
INSERT INTO `doctor_time` VALUES ('9', '3', '2019-03-19', '15:00:00', '15:30:00', '2', '0');
INSERT INTO `doctor_time` VALUES ('10', '3', '2019-03-19', '15:30:00', '16:00:00', '2', '0');
INSERT INTO `doctor_time` VALUES ('11', '3', '2019-03-19', '16:00:00', '16:30:00', '2', '0');
INSERT INTO `doctor_time` VALUES ('12', '5', '2019-03-19', '13:00:00', '13:30:00', '1', '0');
INSERT INTO `doctor_time` VALUES ('13', '5', '2019-03-19', '14:00:00', '14:30:00', '2', '0');

-- ----------------------------
-- Table structure for medical_records
-- ----------------------------
DROP TABLE IF EXISTS `medical_records`;
CREATE TABLE `medical_records` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `pat_id` int(10) NOT NULL,
  `doc_id` int(10) NOT NULL,
  `symptom` varchar(200) DEFAULT NULL COMMENT '症状',
  `prescription` varchar(500) DEFAULT NULL COMMENT '处方',
  `time` datetime DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`),
  KEY `pat_id` (`pat_id`),
  KEY `doc_id` (`doc_id`),
  CONSTRAINT `medical_records_ibfk_1` FOREIGN KEY (`pat_id`) REFERENCES `patients` (`pat_id`) ON UPDATE RESTRICT,
  CONSTRAINT `medical_records_ibfk_2` FOREIGN KEY (`doc_id`) REFERENCES `doctors` (`doc_id`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of medical_records
-- ----------------------------
INSERT INTO `medical_records` VALUES ('1', '2', '3', '消化不良', '斯达舒1盒', '2019-03-16 19:53:04');

-- ----------------------------
-- Table structure for nurses
-- ----------------------------
DROP TABLE IF EXISTS `nurses`;
CREATE TABLE `nurses` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `nur_id` int(10) NOT NULL COMMENT '外键 users表的user_id字段',
  `doc_id` int(10) DEFAULT NULL COMMENT '关联的医生id 外键',
  `name` varchar(30) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `cli_id` int(10) NOT NULL COMMENT '所属单位',
  `telephone` varchar(30) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `doc_id` (`doc_id`),
  KEY `cli_id` (`cli_id`),
  KEY `nurses_ibfk_1` (`nur_id`),
  CONSTRAINT `nurses_ibfk_1` FOREIGN KEY (`nur_id`) REFERENCES `sys_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `nurses_ibfk_2` FOREIGN KEY (`doc_id`) REFERENCES `doctors` (`doc_id`) ON UPDATE RESTRICT,
  CONSTRAINT `nurses_ibfk_3` FOREIGN KEY (`cli_id`) REFERENCES `clinics` (`id`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of nurses
-- ----------------------------
INSERT INTO `nurses` VALUES ('1', '2', '3', '张啟航', '24', '男', '1', '15854507450', '1003225245@qq.com');

-- ----------------------------
-- Table structure for oauth_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_token`;
CREATE TABLE `oauth_token` (
  `token_id` int(11) NOT NULL AUTO_INCREMENT,
  `access_token` varchar(128) NOT NULL,
  `user_id` varchar(128) NOT NULL,
  `permissions` varchar(512) DEFAULT NULL,
  `roles` varchar(512) DEFAULT NULL,
  `refresh_token` varchar(128) DEFAULT NULL,
  `expire_time` datetime DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB AUTO_INCREMENT=306 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of oauth_token
-- ----------------------------
INSERT INTO `oauth_token` VALUES ('189', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNTU2MTkyNjAzfQ.hIL-LopxFE-pDxpSMm-LEPPUVc9da0MvpdzVeiM4sRo', '1', '[\"delete:/v1/oauth/client/{clientId}\",\"delete:/v1/role/{id}\",\"get:/v1/authorities\",\"get:/v1/oauth/client\",\"get:/v1/oauth/client/{clientId}\",\"get:/v1/role\",\"get:/v1/userInfo\",\"post:/v1/authorities/role\",\"post:/v1/authorities/sync\",\"post:/v1/oauth/client\",\"post:/v1/role\",\"post:/v1/user\",\"post:/v1/user/query\",\"put:/v1/oauth/client/{clientId}\",\"put:/v1/role\",\"put:/v1/user\",\"put:/v1/user/psw\",\"put:/v1/user/psw/{id}\",\"put:/v1/user/state\",\"delete:/v1/authorities/role\"]', '[\"1\"]', null, '2019-04-25 19:43:23', '2019-04-24 19:43:23', '2019-04-24 19:43:23');
INSERT INTO `oauth_token` VALUES ('190', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNTU2MTkyNjA1fQ.gEtSyQSMJC_1f_9FcltcjEk3bK0IjgzFzg3LZGS9WiA', '1', '[\"delete:/v1/oauth/client/{clientId}\",\"delete:/v1/role/{id}\",\"get:/v1/authorities\",\"get:/v1/oauth/client\",\"get:/v1/oauth/client/{clientId}\",\"get:/v1/role\",\"get:/v1/userInfo\",\"post:/v1/authorities/role\",\"post:/v1/authorities/sync\",\"post:/v1/oauth/client\",\"post:/v1/role\",\"post:/v1/user\",\"post:/v1/user/query\",\"put:/v1/oauth/client/{clientId}\",\"put:/v1/role\",\"put:/v1/user\",\"put:/v1/user/psw\",\"put:/v1/user/psw/{id}\",\"put:/v1/user/state\",\"delete:/v1/authorities/role\"]', '[\"1\"]', null, '2019-04-25 19:43:26', '2019-04-24 19:43:25', '2019-04-24 19:43:25');
INSERT INTO `oauth_token` VALUES ('191', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNTU2MTkyNjA4fQ.zPAdb5rodO0enect-n4upXEmrcotOsBJRWi5loNM9lY', '1', '[\"delete:/v1/oauth/client/{clientId}\",\"delete:/v1/role/{id}\",\"get:/v1/authorities\",\"get:/v1/oauth/client\",\"get:/v1/oauth/client/{clientId}\",\"get:/v1/role\",\"get:/v1/userInfo\",\"post:/v1/authorities/role\",\"post:/v1/authorities/sync\",\"post:/v1/oauth/client\",\"post:/v1/role\",\"post:/v1/user\",\"post:/v1/user/query\",\"put:/v1/oauth/client/{clientId}\",\"put:/v1/role\",\"put:/v1/user\",\"put:/v1/user/psw\",\"put:/v1/user/psw/{id}\",\"put:/v1/user/state\",\"delete:/v1/authorities/role\"]', '[\"1\"]', null, '2019-04-25 19:43:28', '2019-04-24 19:43:28', '2019-04-24 19:43:28');
INSERT INTO `oauth_token` VALUES ('263', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiZXhwIjoxNTU3Mjk3MTI4fQ.QAfR8AHvVCk9paDBOlC82lWVeG1Ur-k1-mFByKa1WGM', '3', '[]', '[\"3\"]', null, '2019-05-08 14:32:08', '2019-05-07 14:32:08', '2019-05-07 14:32:08');
INSERT INTO `oauth_token` VALUES ('264', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiZXhwIjoxNTU3Mjk3MjE5fQ.6jmJHSu5qbpZnxU5ZxAFOT4k7SRTuV9x-p5cg2hshzg', '3', '[]', '[\"3\"]', null, '2019-05-08 14:33:39', '2019-05-07 14:33:39', '2019-05-07 14:33:39');
INSERT INTO `oauth_token` VALUES ('293', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIzIiwiZXhwIjoxNTU3NTU3NjE4fQ.HrYwoFA-TXIl9jKoDI2E1RjdgEdw5vQj3vHVzaYViQU', '3', '[]', '[\"3\"]', null, '2019-05-11 14:53:38', '2019-05-10 14:53:38', '2019-05-10 14:53:38');
INSERT INTO `oauth_token` VALUES ('303', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyNiIsImV4cCI6MTU1NzU2NzM1N30.K8uiszf7zEP2loODk3RmHL14wk1iMQENaUOfhYRInLI', '26', '[]', '[\"4\"]', null, '2019-05-11 17:35:57', '2019-05-10 17:35:57', '2019-05-10 17:35:57');
INSERT INTO `oauth_token` VALUES ('304', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyNiIsImV4cCI6MTU1NzU2NzQ0Nn0.MBeqLjGU1WpzKLM7vWVGJgJiJRV5H4VoyXCF2MOtOrg', '26', '[]', '[\"4\"]', null, '2019-05-11 17:37:26', '2019-05-10 17:37:26', '2019-05-10 17:37:26');
INSERT INTO `oauth_token` VALUES ('305', 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyNiIsImV4cCI6MTU1NzU2NzU0M30.qsVShLwo-5u6rBTnj02l_TLjJZ1vmXkx29fZn51TQTI', '26', '[]', '[\"4\"]', null, '2019-05-11 17:39:03', '2019-05-10 17:39:03', '2019-05-10 17:39:03');

-- ----------------------------
-- Table structure for oauth_token_key
-- ----------------------------
DROP TABLE IF EXISTS `oauth_token_key`;
CREATE TABLE `oauth_token_key` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token_key` varchar(128) NOT NULL COMMENT '生成token时的key',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of oauth_token_key
-- ----------------------------
INSERT INTO `oauth_token_key` VALUES ('1', '87b5b5c6cb2306a48b70744c78e6ab9af288b40ce5d5c4bc85f9896236e2baf0', '2019-01-04 16:04:32');

-- ----------------------------
-- Table structure for patients
-- ----------------------------
DROP TABLE IF EXISTS `patients`;
CREATE TABLE `patients` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `pat_id` int(10) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `pharmacy_id` int(10) DEFAULT NULL COMMENT '常去药房id',
  `medicare` varchar(100) DEFAULT NULL COMMENT '医疗保险',
  `allergic_history` varchar(100) DEFAULT NULL COMMENT '过敏史',
  `address` varchar(50) DEFAULT NULL,
  `emergency_people` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `emergency_tel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `pharmacy_id` (`pharmacy_id`) USING BTREE,
  KEY `patiens_ibfk_1` (`pat_id`) USING BTREE,
  CONSTRAINT `patients_ibfk_1` FOREIGN KEY (`pat_id`) REFERENCES `sys_user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `patients_ibfk_2` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacies` (`id`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of patients
-- ----------------------------
INSERT INTO `patients` VALUES ('2', '2', '徐佳钰', '1', '内外科保险', '无', null, null, null);
INSERT INTO `patients` VALUES ('3', '4', '董大力', '2', '内外科保险', null, null, null, null);
INSERT INTO `patients` VALUES ('5', '24', '吴华君', null, null, null, '庆生药房', '吴纯月', '13559012871');
INSERT INTO `patients` VALUES ('6', '25', '名字', null, null, null, '地址', '联系人', '15847693254');
INSERT INTO `patients` VALUES ('7', '26', '名字', null, null, null, '地址', '联系人', '13865479823');
INSERT INTO `patients` VALUES ('8', '27', 'hxhd', null, null, null, 'hd', 'hdhd', '13846792314');

-- ----------------------------
-- Table structure for pat_files
-- ----------------------------
DROP TABLE IF EXISTS `pat_files`;
CREATE TABLE `pat_files` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `pat_id` int(10) NOT NULL,
  `file_name` varchar(30) DEFAULT NULL COMMENT '文件名',
  `address` blob COMMENT '文件二进制流',
  `date` datetime(6) DEFAULT NULL COMMENT '上传日期',
  PRIMARY KEY (`id`),
  KEY `pat_id` (`pat_id`),
  CONSTRAINT `pat_files_ibfk_1` FOREIGN KEY (`pat_id`) REFERENCES `patients` (`pat_id`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of pat_files
-- ----------------------------
INSERT INTO `pat_files` VALUES ('1', '2', '大头照', null, '2019-03-29 14:22:19.000000');

-- ----------------------------
-- Table structure for pharmacies
-- ----------------------------
DROP TABLE IF EXISTS `pharmacies`;
CREATE TABLE `pharmacies` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `longitude` int(10) DEFAULT NULL COMMENT '经度',
  `latitude` int(10) DEFAULT NULL COMMENT '维度',
  `telephone` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of pharmacies
-- ----------------------------
INSERT INTO `pharmacies` VALUES ('1', '庆生药房', '3948 N. Peck Rd.,#5 El Monte, CA 91732', '51', '60', ' 626-448-2507');
INSERT INTO `pharmacies` VALUES ('2', '广安药房', '3120 S Hacienda Blvd.ste105,Anaheim,CA 92804', '55', '63', ' 626-281-6800');
INSERT INTO `pharmacies` VALUES ('3', '西阿凯迪亚西药房', ' 1002 S. Baldwin Ave., #CArcadia, CA 91007', '54', '62', ' 626-447-2138');

-- ----------------------------
-- Table structure for suggestion
-- ----------------------------
DROP TABLE IF EXISTS `suggestion`;
CREATE TABLE `suggestion` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(10) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL COMMENT '内容',
  `time` datetime(6) DEFAULT NULL COMMENT '提交时间',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `suggestion_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of suggestion
-- ----------------------------
INSERT INTO `suggestion` VALUES ('1', '3', '医生回复不及时', '2019-03-16 19:51:46.000000');

-- ----------------------------
-- Table structure for sys_authorities
-- ----------------------------
DROP TABLE IF EXISTS `sys_authorities`;
CREATE TABLE `sys_authorities` (
  `authority` varchar(128) NOT NULL COMMENT '授权标识',
  `authority_name` varchar(128) NOT NULL COMMENT '名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='权限';

-- ----------------------------
-- Records of sys_authorities
-- ----------------------------
INSERT INTO `sys_authorities` VALUES ('delete:/v1/authorities/role', '移除角色权限', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('delete:/v1/oauth/client/{clientId}', 'delete', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('delete:/v1/role/{id}', '删除角色', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('get:/v1/authorities', '查询所有权限', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('get:/v1/oauth/client', 'list', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('get:/v1/oauth/client/{clientId}', 'get', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('get:/v1/role', '查询所有角色', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('get:/v1/userInfo', '获取个人信息', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('post:/v1/authorities/role', '给角色添加权限', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('post:/v1/authorities/sync', '同步权限', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('post:/v1/oauth/client', 'add', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('post:/v1/role', '添加角色', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('post:/v1/user', '添加用户', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('post:/v1/user/query', '查询所有用户', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('put:/v1/oauth/client/{clientId}', 'update', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('put:/v1/role', '修改角色', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('put:/v1/user', '修改用户', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('put:/v1/user/psw', '修改自己密码', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('put:/v1/user/psw/{id}', '重置密码', '2018-12-19 23:10:15');
INSERT INTO `sys_authorities` VALUES ('put:/v1/user/state', '修改用户状态', '2018-12-19 23:10:15');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(64) NOT NULL COMMENT '角色名称',
  `comments` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', '管理员', '2018-12-19 23:11:29', '2018-12-19 23:11:29');
INSERT INTO `sys_role` VALUES ('2', '护士', '护士', '2018-12-19 23:12:09', '2019-03-17 20:25:42');
INSERT INTO `sys_role` VALUES ('3', '医生', '医生', '2019-01-04 16:29:53', '2019-03-17 20:25:51');
INSERT INTO `sys_role` VALUES ('4', '病人', '病人', '2019-04-17 15:11:40', '2019-04-17 15:11:40');

-- ----------------------------
-- Table structure for sys_role_authorities
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authorities`;
CREATE TABLE `sys_role_authorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `authority` varchar(128) NOT NULL COMMENT '权限id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_role_permission_pm` (`authority`),
  KEY `FK_sys_role_permission_role` (`role_id`),
  CONSTRAINT `sys_role_authorities_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色权限';

-- ----------------------------
-- Records of sys_role_authorities
-- ----------------------------
INSERT INTO `sys_role_authorities` VALUES ('3', '1', 'delete:/v1/oauth/client/{clientId}', '2018-12-19 23:11:35');
INSERT INTO `sys_role_authorities` VALUES ('4', '1', 'delete:/v1/role/{id}', '2018-12-19 23:11:36');
INSERT INTO `sys_role_authorities` VALUES ('5', '1', 'get:/v1/authorities', '2018-12-19 23:11:37');
INSERT INTO `sys_role_authorities` VALUES ('6', '1', 'get:/v1/oauth/client', '2018-12-19 23:11:37');
INSERT INTO `sys_role_authorities` VALUES ('7', '1', 'get:/v1/oauth/client/{clientId}', '2018-12-19 23:11:38');
INSERT INTO `sys_role_authorities` VALUES ('8', '1', 'get:/v1/role', '2018-12-19 23:11:40');
INSERT INTO `sys_role_authorities` VALUES ('9', '1', 'get:/v1/userInfo', '2018-12-19 23:11:41');
INSERT INTO `sys_role_authorities` VALUES ('10', '1', 'post:/v1/authorities/role', '2018-12-19 23:11:41');
INSERT INTO `sys_role_authorities` VALUES ('11', '1', 'post:/v1/authorities/sync', '2018-12-19 23:11:42');
INSERT INTO `sys_role_authorities` VALUES ('12', '1', 'post:/v1/oauth/client', '2018-12-19 23:11:42');
INSERT INTO `sys_role_authorities` VALUES ('13', '1', 'post:/v1/role', '2018-12-19 23:11:43');
INSERT INTO `sys_role_authorities` VALUES ('14', '1', 'post:/v1/user', '2018-12-19 23:11:44');
INSERT INTO `sys_role_authorities` VALUES ('15', '1', 'post:/v1/user/query', '2018-12-19 23:11:44');
INSERT INTO `sys_role_authorities` VALUES ('16', '1', 'put:/v1/oauth/client/{clientId}', '2018-12-19 23:11:46');
INSERT INTO `sys_role_authorities` VALUES ('17', '1', 'put:/v1/role', '2018-12-19 23:11:46');
INSERT INTO `sys_role_authorities` VALUES ('18', '1', 'put:/v1/user', '2018-12-19 23:11:46');
INSERT INTO `sys_role_authorities` VALUES ('19', '1', 'put:/v1/user/psw', '2018-12-19 23:11:47');
INSERT INTO `sys_role_authorities` VALUES ('20', '1', 'put:/v1/user/psw/{id}', '2018-12-19 23:11:47');
INSERT INTO `sys_role_authorities` VALUES ('21', '1', 'put:/v1/user/state', '2018-12-19 23:11:50');
INSERT INTO `sys_role_authorities` VALUES ('22', '2', 'get:/v1/authorities', '2018-12-19 23:12:35');
INSERT INTO `sys_role_authorities` VALUES ('23', '2', 'get:/v1/oauth/client', '2018-12-19 23:12:40');
INSERT INTO `sys_role_authorities` VALUES ('24', '2', 'get:/v1/oauth/client/{clientId}', '2018-12-19 23:12:41');
INSERT INTO `sys_role_authorities` VALUES ('25', '2', 'get:/v1/role', '2018-12-19 23:13:06');
INSERT INTO `sys_role_authorities` VALUES ('26', '2', 'get:/v1/userInfo', '2018-12-19 23:13:10');
INSERT INTO `sys_role_authorities` VALUES ('28', '2', 'post:/v1/oauth/client', '2018-12-19 23:13:21');
INSERT INTO `sys_role_authorities` VALUES ('29', '2', 'post:/v1/role', '2018-12-19 23:13:22');
INSERT INTO `sys_role_authorities` VALUES ('30', '2', 'post:/v1/user', '2018-12-19 23:13:23');
INSERT INTO `sys_role_authorities` VALUES ('31', '2', 'post:/v1/user/query', '2018-12-19 23:13:25');
INSERT INTO `sys_role_authorities` VALUES ('32', '2', 'put:/v1/user/psw', '2018-12-19 23:13:40');
INSERT INTO `sys_role_authorities` VALUES ('33', '1', 'delete:/v1/authorities/role', '2018-12-26 10:32:09');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(32) NOT NULL COMMENT '账号',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `nick_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像',
  `sex` varchar(1) NOT NULL DEFAULT '男' COMMENT '性别',
  `phone` varchar(12) DEFAULT NULL COMMENT '手机号',
  `email` varchar(256) DEFAULT NULL COMMENT '邮箱',
  `email_verified` int(1) DEFAULT '0' COMMENT '邮箱是否验证，0未验证，1已验证',
  `true_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `id_card` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '驾驶证号',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `department_id` int(11) DEFAULT NULL COMMENT '部门id',
  `postal_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮编',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态，0正常，1冻结',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_account` (`username`),
  KEY `FK_sys_user` (`true_name`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '21232f297a57a5a743894a0e4a801fc3', '管理员', null, '男', '12345678901', null, '0', null, null, null, null, null, '0', '2018-12-19 23:30:05', '2019-03-04 20:49:42');
INSERT INTO `sys_user` VALUES ('2', 'patient', 'e10adc3949ba59abbe56e057f20f883e', '徐病人', null, '女', '12345678901', null, '0', '徐佳钰', null, '1990-10-19', null, null, '0', '2018-12-19 23:31:25', '2019-03-19 11:55:27');
INSERT INTO `sys_user` VALUES ('3', 'doctor', '96e79218965eb72c92a549dd5a330112', '刘医生', null, '女', '18846117436', '874139670@qq.com', '0', '刘颖', '17189031', '1990-01-24', null, null, '0', '2019-03-17 20:16:52', '2019-04-28 18:25:42');
INSERT INTO `sys_user` VALUES ('4', 'patient1', 'e10adc3949ba59abbe56e057f20f883e', '董病人', null, '男', '12345678901', null, '0', '董大力', null, '1979-03-19', null, null, '0', '2019-03-19 12:03:09', '2019-03-19 12:03:09');
INSERT INTO `sys_user` VALUES ('5', 'doctor1', 'e10adc3949ba59abbe56e057f20f883e', '杨医生', null, '男', '13346778889', '77788@163.com', '0', '杨杰瑞', null, '1976-06-01', null, null, '0', '2019-04-22 11:17:46', '2019-04-22 11:17:46');
INSERT INTO `sys_user` VALUES ('6', 'angelababy', 'e10adc3949ba59abbe56e057f20f883e', null, null, '女', '18950567469', null, '0', '杨颖', null, '1989-03-31', null, null, '0', '2019-04-22 22:37:16', '2019-04-22 22:37:16');
INSERT INTO `sys_user` VALUES ('24', 'vin', 'fe560b2d83ac02c29307574faa4fb952', null, null, '女', '13267782205', null, '0', '吴华君', null, '1990-02-24', null, '352100', '0', '2019-04-24 17:09:46', '2019-04-24 17:09:46');
INSERT INTO `sys_user` VALUES ('25', 'xdg', 'e10adc3949ba59abbe56e057f20f883e', null, null, '女', '15847856985', null, '0', '名字', null, null, null, '邮编', '0', '2019-04-24 19:44:39', '2019-04-24 19:44:39');
INSERT INTO `sys_user` VALUES ('26', 'abc', 'e10adc3949ba59abbe56e057f20f883e', null, null, '男', '13758694532', null, '0', '名字', null, '1998-03-01', null, '邮编', '0', '2019-04-24 19:47:59', '2019-04-24 19:47:59');
INSERT INTO `sys_user` VALUES ('27', 'hdhh', 'e10adc3949ba59abbe56e057f20f883e', null, null, '男', '13758642354', null, '0', 'hxhd', null, null, null, 'ush', '0', '2019-04-24 20:13:16', '2019-04-24 20:13:16');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `FK_sys_user_role` (`user_id`),
  KEY `FK_sys_user_role_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1', '2018-12-19 23:30:06');
INSERT INTO `sys_user_role` VALUES ('3', '2', '2', '2019-01-04 17:18:10');
INSERT INTO `sys_user_role` VALUES ('4', '3', '3', '2019-03-17 20:27:17');
INSERT INTO `sys_user_role` VALUES ('5', '6', '4', '2019-04-22 22:37:17');
INSERT INTO `sys_user_role` VALUES ('6', '9', '4', '2019-04-23 21:41:23');
INSERT INTO `sys_user_role` VALUES ('7', '20', '4', '2019-04-23 21:49:54');
INSERT INTO `sys_user_role` VALUES ('8', '22', '4', '2019-04-23 21:51:19');
INSERT INTO `sys_user_role` VALUES ('9', '24', '4', '2019-04-24 17:09:46');
INSERT INTO `sys_user_role` VALUES ('10', '25', '4', '2019-04-24 19:44:39');
INSERT INTO `sys_user_role` VALUES ('11', '26', '4', '2019-04-24 19:47:59');
INSERT INTO `sys_user_role` VALUES ('12', '27', '4', '2019-04-24 20:13:16');

-- ----------------------------
-- Table structure for test_index
-- ----------------------------
DROP TABLE IF EXISTS `test_index`;
CREATE TABLE `test_index` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `pat_id` int(10) NOT NULL,
  `height` double(10,2) DEFAULT NULL COMMENT 'cm',
  `weight` double(10,2) DEFAULT NULL COMMENT 'kg',
  `temperature` double(10,2) DEFAULT NULL COMMENT '℃',
  `blood_pressure` varchar(10) DEFAULT NULL COMMENT '血压 -',
  `blood_oxygen` double(10,2) DEFAULT NULL COMMENT '%',
  `heart_rate` double(10,0) DEFAULT NULL COMMENT '心率 次/分',
  `record_time` datetime DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`id`),
  KEY `pat_id` (`pat_id`),
  CONSTRAINT `test_index_ibfk_1` FOREIGN KEY (`pat_id`) REFERENCES `patients` (`pat_id`) ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of test_index
-- ----------------------------
INSERT INTO `test_index` VALUES ('1', '2', '166.80', '50.35', '37.20', '80-120', '70.00', '95', '2019-03-15 19:51:00');
INSERT INTO `test_index` VALUES ('2', '2', '168.10', '52.08', '36.80', '85-128', '75.00', '97', '2019-04-18 20:04:43');
INSERT INTO `test_index` VALUES ('3', '4', '188.80', '88.80', '37.10', '84-116', '80.00', '88', '2019-04-24 00:00:00');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) DEFAULT NULL COMMENT '账号',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `ptc_question` varchar(100) DEFAULT NULL COMMENT '密保问题',
  `ptc_answer` varchar(100) DEFAULT NULL COMMENT '密保答案',
  `user_type` int(10) DEFAULT NULL COMMENT '用户类型：1-医生 2-病人 3-护士',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'lwy', '123456', '你妈妈叫什么名字？', '吴纯月', '1');
INSERT INTO `users` VALUES ('2', 'zqh', '123456', '你最亲密的人是？', '父亲', '2');
INSERT INTO `users` VALUES ('3', 'xjy', '123456', '你父亲叫什么名字？', '徐嘉余', '3');
