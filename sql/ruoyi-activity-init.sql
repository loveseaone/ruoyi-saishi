-- 设置环境
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 清空数据（开发/测试环境使用）
TRUNCATE TABLE `activity_athletes`;
TRUNCATE TABLE `activity_matches`;
TRUNCATE TABLE `activity_standings`;
TRUNCATE TABLE `activity_teams`;

-- 插入活动信息
INSERT INTO `activity_info` (`activity_name`, `activity_type`, `start_time`, `end_time`, `status`) VALUES
('羽毛球团体赛', '羽毛球', '2025-11-01 09:00:00', '2025-11-02 18:00:00', 0);

-- 获取活动ID
SET @activity_id = 1;

-- 一、插入16支队伍（按秩序册分组与顺序）
INSERT INTO `activity_teams` (`activity_id`, `team_name`, `group_code`, `display_order`, `leader_name`, `leader_phone`, `coach_name`, `union_contact`) VALUES
(1, '张家界分公司代表队', 'A', 101, '范茂林', NULL, '杜修政', NULL),
(1, '湘西分公司代表队', 'A', 102, '刘宛鑫', NULL, '杨吉', '龚继宏'),
(1, '怀化分公司代表队', 'A', 103, '王强', NULL, '权翠', '郑毛'),
(1, '株洲分公司代表队', 'A', 104, '唐劲松', NULL, '许颖', '解忠民'),

(1, '岳阳分公司代表队', 'B', 201, '黄希兵', NULL, '周璠', '任燕'),
(1, '娄底分公司代表队', 'B', 202, '刘业', NULL, '谢熊文', '陈郁英'),
(1, '郴州分公司代表队', 'B', 203, '陈萍', NULL, '肖飞', '曾繁胜'),
(1, '通服公司代表队', 'B', 204, '谢永东', NULL, '黄水清', '毛亚平'),

(1, '省直属单位代表队', 'C', 301, '舒济悦', NULL, '戴博文', NULL),
(1, '衡阳分公司代表队', 'C', 302, '佘国玮', NULL, NULL, '晏华龙'),
(1, '邵阳分公司代表队', 'C', 303, '方一杰', NULL, '刘凯', '车欣蔓'),
(1, '常德分公司代表队', 'C', 304, '姚勇', NULL, '李杨', '李慧'),

(1, '湘潭分公司代表队', 'D', 401, '周建新', NULL, '刘继英', '彭旭'),
(1, '益阳分公司代表队', 'D', 402, '胡壮林', NULL, '钟智慧', '阳鲲'),
(1, '长沙分公司代表队', 'D', 403, '陈学问', NULL, NULL, '何汉青'),
(1, '永州分公司代表队', 'D', 404, '李凌飞', NULL, '李民豪', '李亦兵');

-- 二、插入172名人员（按秩序册名单）
-- 辅助：获取各队ID
SET @zjj_id = (SELECT team_id FROM activity_teams WHERE team_name = '张家界分公司代表队');
SET @xx_id = (SELECT team_id FROM activity_teams WHERE team_name = '湘西分公司代表队');
SET @hh_id = (SELECT team_id FROM activity_teams WHERE team_name = '怀化分公司代表队');
SET @zz_id = (SELECT team_id FROM activity_teams WHERE team_name = '株洲分公司代表队');
SET @yy_id = (SELECT team_id FROM activity_teams WHERE team_name = '岳阳分公司代表队');
SET @ld_id = (SELECT team_id FROM activity_teams WHERE team_name = '娄底分公司代表队');
SET @cz_id = (SELECT team_id FROM activity_teams WHERE team_name = '郴州分公司代表队');
SET @tf_id = (SELECT team_id FROM activity_teams WHERE team_name = '通服公司代表队');
SET @sz_id = (SELECT team_id FROM activity_teams WHERE team_name = '省直属单位代表队');
SET @hy_id = (SELECT team_id FROM activity_teams WHERE team_name = '衡阳分公司代表队');
SET @sy_id = (SELECT team_id FROM activity_teams WHERE team_name = '邵阳分公司代表队');
SET @cd_id = (SELECT team_id FROM activity_teams WHERE team_name = '常德分公司代表队');
SET @xt_id = (SELECT team_id FROM activity_teams WHERE team_name = '湘潭分公司代表队');
SET @yyz_id = (SELECT team_id FROM activity_teams WHERE team_name = '益阳分公司代表队');
SET @cs_id = (SELECT team_id FROM activity_teams WHERE team_name = '长沙分公司代表队');
SET @yz_id = (SELECT team_id FROM activity_teams WHERE team_name = '永州分公司代表队');

-- 使用通用手机号模板（实际需替换）
INSERT INTO `activity_athletes` (`team_id`, `activity_id`, `openid`, `name`, `gender`, `phone`, `role`, `is_on_field`) VALUES
-- 张家界
(@zjj_id, @activity_id, NULL, '范茂林', 0, '13300000101', '领队', 0),
(@zjj_id, @activity_id, NULL, '杜修政', 0, '13300000102', '教练', 0),
(@zjj_id, @activity_id, NULL, '宿强', 0, '13300000103', '男队员', 1),
(@zjj_id, @activity_id, NULL, '向博', 0, '13300000104', '男队员', 1),
(@zjj_id, @activity_id, NULL, '王晖', 0, '13300000105', '男队员', 1),
(@zjj_id, @activity_id, NULL, '刘佩', 0, '13300000106', '男队员', 1),
(@zjj_id, @activity_id, NULL, '李坤鸿', 0, '13300000107', '男队员', 1),
(@zjj_id, @activity_id, NULL, '黄成', 0, '13300000108', '男队员', 1),
(@zjj_id, @activity_id, NULL, '石姿', 1, '13300000109', '女队员', 1),
(@zjj_id, @activity_id, NULL, '余静', 1, '13300000110', '女队员', 1),

-- 湘西
(@xx_id, @activity_id, NULL, '刘宛鑫', 0, '13300000201', '领队', 0),
(@xx_id, @activity_id, NULL, '杨吉', 0, '13300000202', '教练', 0),
(@xx_id, @activity_id, NULL, '龚继宏', 0, '13300000203', '工会人员', 0),
(@xx_id, @activity_id, NULL, '杨玉贤', 0, '13300000204', '男队员', 1),
(@xx_id, @activity_id, NULL, '谢霆宇', 0, '13300000205', '男队员', 1),
(@xx_id, @activity_id, NULL, '王真', 0, '13300000206', '男队员', 1),
(@xx_id, @activity_id, NULL, '舒易凯', 0, '13300000207', '男队员', 1),
(@xx_id, @activity_id, NULL, '谭凯冰', 0, '13300000208', '男队员', 1),
(@xx_id, @activity_id, NULL, '彭小华', 0, '13300000209', '男队员', 1),
(@xx_id, @activity_id, NULL, '李慧', 1, '13300000210', '女队员', 1),
(@xx_id, @activity_id, NULL, '张瀚元', 1, '13300000211', '女队员', 1),

-- 怀化
(@hh_id, @activity_id, NULL, '王强', 0, '13300000301', '领队', 0),
(@hh_id, @activity_id, NULL, '权翠', 1, '13300000302', '教练', 1),
(@hh_id, @activity_id, NULL, '郑毛', 0, '13300000303', '工会人员', 0),
(@hh_id, @activity_id, NULL, '胡一鸣', 0, '13300000304', '男队员', 1),
(@hh_id, @activity_id, NULL, '夏小闻', 0, '13300000305', '男队员', 1),
(@hh_id, @activity_id, NULL, '唐浩文', 0, '13300000306', '男队员', 1),
(@hh_id, @activity_id, NULL, '李金泉', 0, '13300000307', '男队员', 1),
(@hh_id, @activity_id, NULL, '向焜', 0, '13300000308', '男队员', 1),
(@hh_id, @activity_id, NULL, '唐海浪', 0, '13300000309', '男队员', 1),
(@hh_id, @activity_id, NULL, '张佳馨', 1, '13300000310', '女队员', 1),
(@hh_id, @activity_id, NULL, '王一帆', 1, '13300000311', '女队员', 1),

-- 株洲
(@zz_id, @activity_id, NULL, '唐劲松', 0, '13300000401', '领队', 0),
(@zz_id, @activity_id, NULL, '许颖', 1, '13300000402', '教练', 1),
(@zz_id, @activity_id, NULL, '解忠民', 0, '13300000403', '工会人员', 0),
(@zz_id, @activity_id, NULL, '唐祖威', 0, '13300000404', '男队员', 1),
(@zz_id, @activity_id, NULL, '何旻', 0, '13300000405', '男队员', 1),
(@zz_id, @activity_id, NULL, '解忠民', 0, '13300000406', '男队员', 1), -- 兼任
(@zz_id, @activity_id, NULL, '张宇鑫', 0, '13300000407', '男队员', 1),
(@zz_id, @activity_id, NULL, '沈亮', 0, '13300000408', '男队员', 1),
(@zz_id, @activity_id, NULL, '龙腾', 0, '13300000409', '男队员', 1),
(@zz_id, @activity_id, NULL, '王文凤', 1, '13300000410', '女队员', 1),
(@zz_id, @activity_id, NULL, '郑雪寒', 1, '13300000411', '女队员', 1),

-- 岳阳
(@yy_id, @activity_id, NULL, '黄希兵', 0, '13300000501', '领队', 0),
(@yy_id, @activity_id, NULL, '周璠', 1, '13300000502', '教练', 1),
(@yy_id, @activity_id, NULL, '任燕', 1, '13300000503', '工会人员', 1),
(@yy_id, @activity_id, NULL, '郑珉轶', 0, '13300000504', '男队员', 1),
(@yy_id, @activity_id, NULL, '徐雨辉', 0, '13300000505', '男队员', 1),
(@yy_id, @activity_id, NULL, '邓功博', 0, '13300000506', '男队员', 1),
(@yy_id, @activity_id, NULL, '秦哲', 0, '13300000507', '男队员', 1),
(@yy_id, @activity_id, NULL, '吴秋含', 0, '13300000508', '男队员', 1),
(@yy_id, @activity_id, NULL, '汪卓', 0, '13300000509', '男队员', 1),
(@yy_id, @activity_id, NULL, '彭朋', 1, '13300000510', '女队员', 1),
(@yy_id, @activity_id, NULL, '彭绵绵', 1, '13300000511', '女队员', 1),

-- 娄底
(@ld_id, @activity_id, NULL, '刘业', 0, '13300000601', '领队', 0),
(@ld_id, @activity_id, NULL, '谢熊文', 0, '13300000602', '教练', 0),
(@ld_id, @activity_id, NULL, '陈郁英', 1, '13300000603', '工会人员', 1),
(@ld_id, @activity_id, NULL, '谢雄亮', 0, '13300000604', '男队员', 1),
(@ld_id, @activity_id, NULL, '伍俊霖', 0, '13300000605', '男队员', 1),
(@ld_id, @activity_id, NULL, '何军', 0, '13300000606', '男队员', 1),
(@ld_id, @activity_id, NULL, '杨彪', 0, '13300000607', '男队员', 1),
(@ld_id, @activity_id, NULL, '刘佩', 0, '13300000608', '男队员', 1),
(@ld_id, @activity_id, NULL, '何峰', 0, '13300000609', '男队员', 1),
(@ld_id, @activity_id, NULL, '曾立红', 1, '13300000610', '女队员', 1),
(@ld_id, @activity_id, NULL, '毛强杰', 1, '13300000611', '女队员', 1),

-- 郴州
(@cz_id, @activity_id, NULL, '陈萍', 1, '13300000701', '领队', 1),
(@cz_id, @activity_id, NULL, '肖飞', 0, '13300000702', '教练', 0),
(@cz_id, @activity_id, NULL, '曾繁胜', 0, '13300000703', '工会人员', 0),
(@cz_id, @activity_id, NULL, '张宏钢', 0, '13300000704', '男队员', 1),
(@cz_id, @activity_id, NULL, '姜熙为', 0, '13300000705', '男队员', 1),
(@cz_id, @activity_id, NULL, '高军', 0, '13300000706', '男队员', 1),
(@cz_id, @activity_id, NULL, '周成杰', 0, '13300000707', '男队员', 1),
(@cz_id, @activity_id, NULL, '邓明磊', 0, '13300000708', '男队员', 1),
(@cz_id, @activity_id, NULL, '黄文昕', 0, '13300000709', '男队员', 1),
(@cz_id, @activity_id, NULL, '杨露娟', 1, '13300000710', '女队员', 1),
(@cz_id, @activity_id, NULL, '曹琛焱', 1, '13300000711', '女队员', 1),

-- 通服
(@tf_id, @activity_id, NULL, '谢永东', 0, '13300000801', '领队', 0),
(@tf_id, @activity_id, NULL, '黄水清', 0, '13300000802', '教练', 0),
(@tf_id, @activity_id, NULL, '毛亚平', 0, '13300000803', '工会人员', 0),
(@tf_id, @activity_id, NULL, '曾国祥', 0, '13300000804', '男队员', 1),
(@tf_id, @activity_id, NULL, '潘飞', 0, '13300000805', '男队员', 1),
(@tf_id, @activity_id, NULL, '欧阳宇光', 0, '13300000806', '男队员', 1),
(@tf_id, @activity_id, NULL, '尹杰', 0, '13300000807', '男队员', 1),
(@tf_id, @activity_id, NULL, '禹世杰', 0, '13300000808', '男队员', 1),
(@tf_id, @activity_id, NULL, '彭涵宇', 0, '13300000809', '男队员', 1),
(@tf_id, @activity_id, NULL, '张洁', 1, '13300000810', '女队员', 1),
(@tf_id, @activity_id, NULL, '阎照青', 1, '13300000811', '女队员', 1),

-- 省直
(@sz_id, @activity_id, NULL, '舒济悦', 0, '13300000901', '领队', 0),
(@sz_id, @activity_id, NULL, '戴博文', 0, '13300000902', '教练', 0),
(@sz_id, @activity_id, NULL, '文智慧', 0, '13300000903', '男队员', 1),
(@sz_id, @activity_id, NULL, '金哲', 0, '13300000904', '男队员', 1),
(@sz_id, @activity_id, NULL, '陈路', 0, '13300000905', '男队员', 1),
(@sz_id, @activity_id, NULL, '史臻琦', 0, '13300000906', '男队员', 1),
(@sz_id, @activity_id, NULL, '罗旭东', 0, '13300000907', '男队员', 1),
(@sz_id, @activity_id, NULL, '湛誉', 0, '13300000908', '男队员', 1),
(@sz_id, @activity_id, NULL, '刘华蕾', 1, '13300000909', '女队员', 1),
(@sz_id, @activity_id, NULL, '李佼婕', 1, '13300000910', '女队员', 1),

-- 衡阳
(@hy_id, @activity_id, NULL, '佘国玮', 0, '13300001001', '领队', 0),
(@hy_id, @activity_id, NULL, '晏华龙', 0, '13300001002', '工会人员', 0),
(@hy_id, @activity_id, NULL, '刘江彪', 0, '13300001003', '男队员', 1),
(@hy_id, @activity_id, NULL, '伍文', 0, '13300001004', '男队员', 1),
(@hy_id, @activity_id, NULL, '贺越', 0, '13300001005', '男队员', 1),
(@hy_id, @activity_id, NULL, '李振辉', 0, '13300001006', '男队员', 1),
(@hy_id, @activity_id, NULL, '潘璐辉', 0, '13300001007', '男队员', 1),
(@hy_id, @activity_id, NULL, '贺文辉', 0, '13300001008', '男队员', 1),
(@hy_id, @activity_id, NULL, '曾赛玲', 1, '13300001009', '女队员', 1),
(@hy_id, @activity_id, NULL, '张娜', 1, '13300001010', '女队员', 1),

-- 邵阳
(@sy_id, @activity_id, NULL, '方一杰', 0, '13300001101', '领队', 0),
(@sy_id, @activity_id, NULL, '刘凯', 0, '13300001102', '教练', 0),
(@sy_id, @activity_id, NULL, '车欣蔓', 1, '13300001103', '工会人员', 1),
(@sy_id, @activity_id, NULL, '黄费秋', 0, '13300001104', '男队员', 1),
(@sy_id, @activity_id, NULL, '谢东汛', 0, '13300001105', '男队员', 1),
(@sy_id, @activity_id, NULL, '李威', 0, '13300001106', '男队员', 1),
(@sy_id, @activity_id, NULL, '刘湘林', 0, '13300001107', '男队员', 1),
(@sy_id, @activity_id, NULL, '田昴', 0, '13300001108', '男队员', 1),
(@sy_id, @activity_id, NULL, '廖昊', 0, '13300001109', '男队员', 1),
(@sy_id, @activity_id, NULL, '乔志兰', 1, '13300001110', '女队员', 1),
(@sy_id, @activity_id, NULL, '姚金杏', 1, '13300001111', '女队员', 1),

-- 常德
(@cd_id, @activity_id, NULL, '姚勇', 0, '13300001201', '领队', 0),
(@cd_id, @activity_id, NULL, '李杨', 0, '13300001202', '教练', 0),
(@cd_id, @activity_id, NULL, '李慧', 1, '13300001203', '工会人员', 1),
(@cd_id, @activity_id, NULL, '曹宇', 0, '13300001204', '男队员', 1),
(@cd_id, @activity_id, NULL, '杨中志', 0, '13300001205', '男队员', 1),
(@cd_id, @activity_id, NULL, '张羽丘', 0, '13300001206', '男队员', 1),
(@cd_id, @activity_id, NULL, '周佳兴', 0, '13300001207', '男队员', 1),
(@cd_id, @activity_id, NULL, '杨希平', 0, '13300001208', '男队员', 1),
(@cd_id, @activity_id, NULL, '申俊龙', 0, '13300001209', '男队员', 1),
(@cd_id, @activity_id, NULL, '吴泽妤', 1, '13300001210', '女队员', 1),
(@cd_id, @activity_id, NULL, '谭晓玲', 1, '13300001211', '女队员', 1),

-- 湘潭
(@xt_id, @activity_id, NULL, '周建新', 0, '13300001301', '领队', 0),
(@xt_id, @activity_id, NULL, '刘继英', 0, '13300001302', '教练', 0),
(@xt_id, @activity_id, NULL, '彭旭', 0, '13300001303', '工会人员', 0),
(@xt_id, @activity_id, NULL, '吴舟', 0, '13300001304', '男队员', 1),
(@xt_id, @activity_id, NULL, '余述祥', 0, '13300001305', '男队员', 1),
(@xt_id, @activity_id, NULL, '李卓', 0, '13300001306', '男队员', 1),
(@xt_id, @activity_id, NULL, '何钳', 0, '13300001307', '男队员', 1),
(@xt_id, @activity_id, NULL, '欧阳明杰', 0, '13300001308', '男队员', 1),
(@xt_id, @activity_id, NULL, '罗志刚', 0, '13300001309', '男队员', 1),
(@xt_id, @activity_id, NULL, '黄娇', 1, '13300001310', '女队员', 1),
(@xt_id, @activity_id, NULL, '宾毅', 1, '13300001311', '女队员', 1),

-- 益阳
(@yyz_id, @activity_id, NULL, '胡壮林', 0, '13300001401', '领队', 0),
(@yyz_id, @activity_id, NULL, '钟智慧', 0, '13300001402', '教练', 0),
(@yyz_id, @activity_id, NULL, '阳鲲', 0, '13300001403', '工会人员', 0),
(@yyz_id, @activity_id, NULL, '张浩', 0, '13300001404', '男队员', 1),
(@yyz_id, @activity_id, NULL, '成长', 0, '13300001405', '男队员', 1),
(@yyz_id, @activity_id, NULL, '李翔', 0, '13300001406', '男队员', 1),
(@yyz_id, @activity_id, NULL, '蒋学东', 0, '13300001407', '男队员', 1),
(@yyz_id, @activity_id, NULL, '蒋学锋', 0, '13300001408', '男队员', 1),
(@yyz_id, @activity_id, NULL, '林顺先', 0, '13300001409', '男队员', 1),
(@yyz_id, @activity_id, NULL, '殷格兰', 1, '13300001410', '女队员', 1),
(@yyz_id, @activity_id, NULL, '芦芳', 1, '13300001411', '女队员', 1),

-- 长沙
(@cs_id, @activity_id, NULL, '陈学问', 0, '13300001501', '领队', 0),
(@cs_id, @activity_id, NULL, '何汉青', 0, '13300001502', '工会人员', 0),
(@cs_id, @activity_id, NULL, '邓江勇', 0, '13300001503', '男队员', 1),
(@cs_id, @activity_id, NULL, '喻国辉', 0, '13300001504', '男队员', 1),
(@cs_id, @activity_id, NULL, '曾坤', 0, '13300001505', '男队员', 1),
(@cs_id, @activity_id, NULL, '吴熙磊', 0, '13300001506', '男队员', 1),
(@cs_id, @activity_id, NULL, '邱志宏', 0, '13300001507', '男队员', 1),
(@cs_id, @activity_id, NULL, '彭巍', 0, '13300001508', '男队员', 1),
(@cs_id, @activity_id, NULL, '张冀湘', 1, '13300001509', '女队员', 1),
(@cs_id, @activity_id, NULL, '张平', 1, '13300001510', '女队员', 1),

-- 永州
(@yz_id, @activity_id, NULL, '李凌飞', 0, '13300001601', '领队', 0),
(@yz_id, @activity_id, NULL, '李民豪', 0, '13300001602', '教练', 0),
(@yz_id, @activity_id, NULL, '李亦兵', 0, '13300001603', '工会人员', 0),
(@yz_id, @activity_id, NULL, '陈彦', 0, '13300001604', '男队员', 1),
(@yz_id, @activity_id, NULL, '吴锋', 0, '13300001605', '男队员', 1),
(@yz_id, @activity_id, NULL, '郑和定', 0, '13300001606', '男队员', 1),
(@yz_id, @activity_id, NULL, '唐卫峰', 0, '13300001607', '男队员', 1),
(@yz_id, @activity_id, NULL, '刘博达', 0, '13300001608', '男队员', 1),
(@yz_id, @activity_id, NULL, '傅一轩', 0, '13300001609', '男队员', 1),
(@yz_id, @activity_id, NULL, '李宁波', 1, '13300001610', '女队员', 1),
(@yz_id, @activity_id, NULL, '蒋莉萍', 1, '13300001611', '女队员', 1);

-- 三、初始化 standings 表（16条记录）
INSERT INTO `activity_standings` (`activity_id`, `team_id`, `group_code`, `matches_played`, `wins`, `losses`, `points`, `sets_won`, `sets_lost`, `points_for`, `points_against`)
SELECT @activity_id, team_id, group_code, 0, 0, 0, 0, 0, 0, 0, 0 FROM activity_teams;

-- 四、插入40场比赛（24场小组赛 + 16场第二阶段）
-- 小组赛（24场）
INSERT INTO `activity_matches` (`activity_id`, `match_code`, `stage`, `format`, `round_desc`, `group_a`, `team_a_id`, `team_b_id`, `court`, `match_date`, `start_time`, `status`) VALUES
-- A组
(1, '1', 1, 3, 'A组：1-4', 'A', @zjj_id, @zz_id, 1, '2025-11-01', '09:00:00', 0),
(1, '5', 1, 3, 'A组：2-3', 'A', @xx_id, @hh_id, 2, '2025-11-01', '09:40:00', 0),
(1, '9', 1, 3, 'A组：1-3', 'A', @zjj_id, @hh_id, 3, '2025-11-01', '10:20:00', 0),
(1, '13', 1, 3, 'A组：4-2', 'A', @zz_id, @xx_id, 1, '2025-11-01', '11:40:00', 0),
(1, '17', 1, 3, 'A组：1-2', 'A', @zjj_id, @xx_id, 2, '2025-11-01', '14:00:00', 0),
(1, '21', 1, 3, 'A组：3-4', 'A', @hh_id, @zz_id, 3, '2025-11-01', '14:40:00', 0),

-- B组
(1, '2', 1, 3, 'B组：1-4', 'B', @yy_id, @tf_id, 2, '2025-11-01', '09:00:00', 0),
(1, '6', 1, 3, 'B组：2-3', 'B', @ld_id, @cz_id, 3, '2025-11-01', '09:40:00', 0),
(1, '10', 1, 3, 'B组：1-3', 'B', @yy_id, @cz_id, 1, '2025-11-01', '11:00:00', 0),
(1, '14', 1, 3, 'B组：4-2', 'B', @tf_id, @ld_id, 2, '2025-11-01', '11:40:00', 0),
(1, '18', 1, 3, 'B组：1-2', 'B', @yy_id, @ld_id, 3, '2025-11-01', '14:00:00', 0),
(1, '22', 1, 3, 'B组：3-4', 'B', @cz_id, @tf_id, 1, '2025-11-01', '15:20:00', 0),

-- C组
(1, '3', 1, 3, 'C组：1-4', 'C', @sz_id, @cd_id, 3, '2025-11-01', '09:00:00', 0),
(1, '7', 1, 3, 'C组：2-3', 'C', @hy_id, @sy_id, 1, '2025-11-01', '10:20:00', 0),
(1, '11', 1, 3, 'C组：1-3', 'C', @sz_id, @sy_id, 2, '2025-11-01', '11:00:00', 0),
(1, '15', 1, 3, 'C组：4-2', 'C', @cd_id, @hy_id, 3, '2025-11-01', '11:40:00', 0),
(1, '19', 1, 3, 'C组：1-2', 'C', @sz_id, @hy_id, 1, '2025-11-01', '14:40:00', 0),
(1, '23', 1, 3, 'C组：3-4', 'C', @sy_id, @cd_id, 2, '2025-11-01', '15:20:00', 0),

-- D组
(1, '4', 1, 3, 'D组：1-4', 'D', @xt_id, @yz_id, 1, '2025-11-01', '09:40:00', 0),
(1, '8', 1, 3, 'D组：2-3', 'D', @yyz_id, @cs_id, 2, '2025-11-01', '10:20:00', 0),
(1, '12', 1, 3, 'D组：1-3', 'D', @xt_id, @cs_id, 3, '2025-11-01', '11:00:00', 0),
(1, '16', 1, 3, 'D组：4-2', 'D', @yz_id, @yyz_id, 1, '2025-11-01', '14:00:00', 0),
(1, '20', 1, 3, 'D组：1-2', 'D', @xt_id, @yyz_id, 2, '2025-11-01', '14:40:00', 0),
(1, '24', 1, 3, 'D组：3-4', 'D', @cs_id, @yz_id, 3, '2025-11-01', '15:20:00', 0),

-- 第二阶段（16场）
(1, '25', 2, 3, 'A2-B3', NULL, NULL, NULL, 1, '2025-11-01', '16:00:00', 0),
(1, '26', 2, 3, 'C3-D2', NULL, NULL, NULL, 2, '2025-11-01', '16:00:00', 0),
(1, '27', 2, 3, 'C2-D3', NULL, NULL, NULL, 3, '2025-11-01', '16:00:00', 0),
(1, '28', 2, 3, 'A3-B2', NULL, NULL, NULL, 1, '2025-11-01', '16:40:00', 0),
(1, '29', 2, 3, 'C1-E1', NULL, NULL, NULL, 2, '2025-11-01', '16:40:00', 0),
(1, '30', 2, 3, 'B1-F1', NULL, NULL, NULL, 3, '2025-11-01', '16:40:00', 0),
(1, '31', 2, 3, 'A1-G1', NULL, NULL, NULL, 1, '2025-11-01', '17:20:00', 0),
(1, '32', 2, 3, 'D1-H1', NULL, NULL, NULL, 2, '2025-11-01', '17:20:00', 0),
(1, '33', 3, 3, '5-8附加赛', NULL, NULL, NULL, 1, '2025-11-02', '08:30:00', 0),
(1, '34', 3, 3, '5-8附加赛', NULL, NULL, NULL, 2, '2025-11-02', '08:30:00', 0),
(1, '35', 2, 3, '半决赛', NULL, NULL, NULL, 1, '2025-11-02', '09:10:00', 0),
(1, '36', 2, 3, '半决赛', NULL, NULL, NULL, 2, '2025-11-02', '09:10:00', 0),
(1, '37', 3, 3, '七八名', NULL, NULL, NULL, 1, '2025-11-02', '09:50:00', 0),
(1, '38', 3, 3, '五六名', NULL, NULL, NULL, 2, '2025-11-02', '09:50:00', 0),
(1, '39', 2, 3, '三四名', NULL, NULL, NULL, 1, '2025-11-02', '10:30:00', 0),
(1, '40', 2, 3, '一二名', NULL, NULL, NULL, 2, '2025-11-02', '10:30:00', 0);

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1;