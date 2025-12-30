/*
 Navicat Premium Data Transfer

 Source Server         : resbai_prod
 Source Server Type    : MySQL
 Source Server Version : 80022 (8.0.22)
 Source Host           : 47.107.92.31:3306
 Source Schema         : ry-saishi

 Target Server Type    : MySQL
 Target Server Version : 80022 (8.0.22)
 File Encoding         : 65001

 Date: 18/11/2025 16:42:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity_athletes
-- ----------------------------
DROP TABLE IF EXISTS `activity_athletes`;
CREATE TABLE `activity_athletes`  (
  `athlete_id` bigint NOT NULL AUTO_INCREMENT COMMENT '人员ID',
  `team_id` bigint NOT NULL COMMENT '所属队伍ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信用户的唯一标识（小程序 openid）',
  `status` tinyint(1) NULL DEFAULT 0 COMMENT '0 录入 1 待审核 2 已确认 3 已驳回',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `gender` tinyint NOT NULL DEFAULT 0 COMMENT '性别：0-男，1-女',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号，用于小程序登录',
  `id_card` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证号',
  `role` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色：领队/教练/工会人员/男队员/女队员',
  `is_on_field` tinyint NOT NULL DEFAULT 0 COMMENT '是否为上场运动员',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`athlete_id`) USING BTREE,
  INDEX `idx_team_id`(`team_id` ASC) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE,
  INDEX `idx_phone`(`phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 173 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动运动员及工作人员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_info
-- ----------------------------
DROP TABLE IF EXISTS `activity_info`;
CREATE TABLE `activity_info`  (
  `activity_id` bigint NOT NULL AUTO_INCREMENT COMMENT '活动ID',
  `activity_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动名称',
  `activity_desc` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '活动描述',
  `activity_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '活动类型',
  `start_time` datetime NOT NULL COMMENT '活动开始时间',
  `end_time` datetime NOT NULL COMMENT '活动结束时间',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '活动状态：0-未开始，1-进行中，2-已结束',
  `organizer` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主办方',
  `contact_info` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `location` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '活动地点',
  `max_participants` int NULL DEFAULT NULL COMMENT '最大参与人数',
  `current_participants` int NULL DEFAULT 0 COMMENT '当前参与人数',
  `registration_start` datetime NULL DEFAULT NULL COMMENT '报名开始时间',
  `registration_end` datetime NULL DEFAULT NULL COMMENT '报名结束时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`activity_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_matches
-- ----------------------------
DROP TABLE IF EXISTS `activity_matches`;
CREATE TABLE `activity_matches`  (
  `match_id` bigint NOT NULL AUTO_INCREMENT COMMENT '场次ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `match_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '场次编号',
  `stage` tinyint NOT NULL DEFAULT 1 COMMENT '阶段：1-小组赛，2-淘汰赛，3-排位赛',
  `format` tinyint NOT NULL DEFAULT 3 COMMENT '赛制：3-三局两胜，5-五局三胜',
  `round_desc` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '轮次描述',
  `group_a` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'A方小组',
  `rank_a` tinyint NULL DEFAULT NULL COMMENT 'A方小组排名',
  `group_b` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'B方小组',
  `rank_b` tinyint NULL DEFAULT NULL COMMENT 'B方小组排名',
  `team_a_id` bigint NULL DEFAULT NULL COMMENT 'A方队伍ID',
  `team_b_id` bigint NULL DEFAULT NULL COMMENT 'B方队伍ID',
  `court` tinyint NOT NULL DEFAULT 1 COMMENT '场地编号：1/2/3',
  `match_date` date NOT NULL COMMENT '比赛日期',
  `start_time` time NOT NULL COMMENT '开始时间',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '状态：0-未开始，1-进行中，2-已结束',
  `score_a_set1` tinyint NULL DEFAULT NULL COMMENT 'A方第一局得分',
  `score_b_set1` tinyint NULL DEFAULT NULL COMMENT 'B方第一局得分',
  `score_a_set2` tinyint NULL DEFAULT NULL COMMENT 'A方第二局得分',
  `score_b_set2` tinyint NULL DEFAULT NULL COMMENT 'B方第二局得分',
  `score_a_set3` tinyint NULL DEFAULT NULL COMMENT 'A方第三局得分',
  `score_b_set3` tinyint NULL DEFAULT NULL COMMENT 'B方第三局得分',
  `score_a_set4` tinyint NULL DEFAULT NULL COMMENT 'A方第四局得分',
  `score_b_set4` tinyint NULL DEFAULT NULL COMMENT 'B方第四局得分',
  `score_a_set5` tinyint NULL DEFAULT NULL COMMENT 'A方第五局得分',
  `score_b_set5` tinyint NULL DEFAULT NULL COMMENT 'B方第五局得分',
  `winner_id` bigint NULL DEFAULT NULL COMMENT '胜方队伍ID',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`match_id`) USING BTREE,
  INDEX `idx_activity_date_court`(`activity_id` ASC, `match_date` ASC, `court` ASC) USING BTREE,
  INDEX `idx_team_a`(`team_a_id` ASC) USING BTREE,
  INDEX `idx_team_b`(`team_b_id` ASC) USING BTREE,
  INDEX `idx_winner`(`winner_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动比赛场次表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_matches_athletes
-- ----------------------------
DROP TABLE IF EXISTS `activity_matches_athletes`;
CREATE TABLE `activity_matches_athletes`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `match_id` bigint NULL DEFAULT NULL COMMENT '场次id',
  `team_id` bigint NULL DEFAULT NULL COMMENT '队伍id',
  `athlete_id` bigint NULL DEFAULT NULL COMMENT '参赛人员id',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_notices
-- ----------------------------
DROP TABLE IF EXISTS `activity_notices`;
CREATE TABLE `activity_notices`  (
  `notice_id` bigint NOT NULL AUTO_INCREMENT,
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '公告内容',
  `published_at` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `is_pinned` tinyint NOT NULL DEFAULT 0 COMMENT '是否置顶',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE,
  INDEX `idx_activity_pinned_time`(`activity_id` ASC, `is_pinned` ASC, `published_at` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_registration
-- ----------------------------
DROP TABLE IF EXISTS `activity_registration`;
CREATE TABLE `activity_registration`  (
  `reg_id` bigint NOT NULL AUTO_INCREMENT COMMENT '报名ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `participant_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参与者姓名',
  `participant_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '参与者手机号',
  `participant_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参与者邮箱',
  `participant_org` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '参与者单位',
  `registration_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名时间',
  `status` tinyint NOT NULL DEFAULT 0 COMMENT '报名状态：0-待审核，1-已通过，2-已拒绝',
  `checkin_status` tinyint NOT NULL DEFAULT 0 COMMENT '签到状态：0-未签到，1-已签到',
  `checkin_time` datetime NULL DEFAULT NULL COMMENT '签到时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`reg_id`) USING BTREE,
  INDEX `idx_activity_id`(`activity_id` ASC) USING BTREE,
  INDEX `idx_participant_phone`(`participant_phone` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动报名表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_standings
-- ----------------------------
DROP TABLE IF EXISTS `activity_standings`;
CREATE TABLE `activity_standings`  (
  `standing_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `team_id` bigint NOT NULL COMMENT '队伍ID',
  `group_code` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '小组标识 A/B/C/D',
  `matches_played` tinyint NOT NULL DEFAULT 0 COMMENT '已赛场次',
  `wins` tinyint NOT NULL DEFAULT 0 COMMENT '胜场',
  `losses` tinyint NOT NULL DEFAULT 0 COMMENT '负场',
  `points` int NOT NULL DEFAULT 0 COMMENT '积分',
  `sets_won` int NOT NULL DEFAULT 0 COMMENT '胜局数',
  `sets_lost` int NOT NULL DEFAULT 0 COMMENT '负局数',
  `points_for` int NOT NULL DEFAULT 0 COMMENT '总得分',
  `points_against` int NOT NULL DEFAULT 0 COMMENT '总失分',
  `c_value` decimal(6, 3) GENERATED ALWAYS AS ((case when (`sets_lost` > 0) then round((`sets_won` / `sets_lost`),3) else 999.000 end)) STORED COMMENT 'C值 = 胜局/负局' NULL,
  `z_value` decimal(6, 3) GENERATED ALWAYS AS ((case when (`points_against` > 0) then round((`points_for` / `points_against`),3) else 999.000 end)) STORED COMMENT 'Z值 = 总得分/总失分' NULL,
  `rank_in_group` tinyint NULL DEFAULT NULL COMMENT '小组内排名',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`standing_id`) USING BTREE,
  INDEX `idx_activity_team_group`(`activity_id` ASC, `team_id` ASC, `group_code` ASC) USING BTREE,
  INDEX `idx_activity_group_rank`(`activity_id` ASC, `group_code` ASC, `rank_in_group` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动小组赛积分排名表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for activity_teams
-- ----------------------------
DROP TABLE IF EXISTS `activity_teams`;
CREATE TABLE `activity_teams`  (
  `team_id` bigint NOT NULL AUTO_INCREMENT COMMENT '队伍ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `team_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '队伍全称',
  `status` tinyint(1) NULL DEFAULT 2 COMMENT '0 录入 1 待审核 2 已确认 3 已驳回',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `group_code` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '小组赛分组：A/B/C/D',
  `display_order` smallint NOT NULL DEFAULT 999 COMMENT '展示排序',
  `leader_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '领队姓名',
  `leader_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '领队电话',
  `coach_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教练姓名',
  `union_contact` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工会联络人',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`team_id`) USING BTREE,
  INDEX `idx_activity_group`(`activity_id` ASC, `group_code` ASC, `display_order` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '活动参赛队伍信息表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
