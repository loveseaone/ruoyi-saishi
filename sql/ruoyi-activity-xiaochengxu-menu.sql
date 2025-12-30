/*
 微信小程序菜单初始化脚本
 用于初始化微信小程序的菜单配置
*/

-- 删除已存在的菜单项（如果需要重新初始化）
-- delete from sys_menu where menu_name in ('微信小程序', '活动报名', '赛程信息', '队伍信息', '积分排名', '公告通知', '我的信息');

-- 插入微信小程序根菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信小程序', '0', '6', 'wx', null, 1, 0, 'M', '0', '0', '', 'wechat', 'admin', sysdate(), '', null, '微信小程序');

-- 获取微信小程序根菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 微信小程序子菜单项

-- 1. 活动报名相关菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('活动报名', @parentId, '1', 'activity', null, 1, 0, 'M', '0', '0', '', 'form', 'admin', sysdate(), '', null, '活动报名');

-- 活动报名子菜单
SELECT @activityParentId := LAST_INSERT_ID();
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('活动报名', @activityParentId, '1',  '', '', 1, 0, 'C', '0', '0', 'wx:activity:register', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('活动签到', @activityParentId, '2',  '', '', 1, 0, 'C', '0', '0', 'wx:activity:checkin', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('报名状态', @activityParentId, '3',  '', '', 1, 0, 'C', '0', '0', 'wx:activity:status', '#', 'admin', sysdate(), '', null, '');


-- 2. 赛程信息相关菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('赛程信息', @parentId, '2', 'matches', null, 1, 0, 'M', '0', '0', '', 'guide', 'admin', sysdate(), '', null, '赛程信息');

-- 赛程信息子菜单
SELECT @matchesParentId := LAST_INSERT_ID();
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('全部赛程', @matchesParentId, '1',  '', '', 1, 0, 'C', '0', '0', 'wx:matches:list', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('赛程详情', @matchesParentId, '2',  '', '', 1, 0, 'C', '0', '0', 'wx:matches:detail', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('我的赛程', @matchesParentId, '3',  '', '', 1, 0, 'C', '0', '0', 'wx:matches:my', '#', 'admin', sysdate(), '', null, '');


-- 3. 队伍信息相关菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队伍信息', @parentId, '3', 'teams', null, 1, 0, 'M', '0', '0', '', 'peoples', 'admin', sysdate(), '', null, '队伍信息');

-- 队伍信息子菜单
SELECT @teamsParentId := LAST_INSERT_ID();
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队伍列表', @teamsParentId, '1',  '', '', 1, 0, 'C', '0', '0', 'wx:teams:list', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队伍详情', @teamsParentId, '2',  '', '', 1, 0, 'C', '0', '0', 'wx:teams:detail', '#', 'admin', sysdate(), '', null, '');


-- 4. 积分排名相关菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('积分排名', @parentId, '4', 'standings', null, 1, 0, 'M', '0', '0', '', 'excel', 'admin', sysdate(), '', null, '积分排名');

-- 积分排名子菜单
SELECT @standingsParentId := LAST_INSERT_ID();
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('小组积分', @standingsParentId, '1',  '', '', 1, 0, 'C', '0', '0', 'wx:standings:group', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('最终排名', @standingsParentId, '2',  '', '', 1, 0, 'C', '0', '0', 'wx:standings:final', '#', 'admin', sysdate(), '', null, '');


-- 5. 公告通知相关菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('公告通知', @parentId, '5', 'notices', null, 1, 0, 'M', '0', '0', '', 'message', 'admin', sysdate(), '', null, '公告通知');

-- 公告通知子菜单
SELECT @noticesParentId := LAST_INSERT_ID();
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('公告列表', @noticesParentId, '1',  '', '', 1, 0, 'C', '0', '0', 'wx:notices:list', '#', 'admin', sysdate(), '', null, '');


-- 6. 字典数据相关菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('字典数据', @parentId, '6', 'dict', null, 1, 0, 'M', '0', '0', '', 'dict', 'admin', sysdate(), '', null, '字典数据');

-- 字典数据子菜单
SELECT @dictParentId := LAST_INSERT_ID();
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('数据查询', @dictParentId, '1',  '', '', 1, 0, 'C', '0', '0', 'wx:dict:list', '#', 'admin', sysdate(), '', null, '');


-- 7. 登录认证相关菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('登录认证', @parentId, '7', 'auth', null, 1, 0, 'M', '0', '0', '', 'logininfor', 'admin', sysdate(), '', null, '登录认证');

-- 登录认证子菜单
SELECT @authParentId := LAST_INSERT_ID();
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信登录', @authParentId, '1',  '', '', 1, 0, 'C', '0', '0', 'wx:auth:loginByCode', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('信息登录', @authParentId, '2',  '', '', 1, 0, 'C', '0', '0', 'wx:auth:loginByInfo', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('身份绑定', @authParentId, '3',  '', '', 1, 0, 'C', '0', '0', 'wx:auth:bind', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('快速登录', @authParentId, '4',  '', '', 1, 0, 'C', '0', '0', 'wx:auth:login', '#', 'admin', sysdate(), '', null, '');