-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('比赛管理', '0', '30', 'activity', null, 1, 0, 'M', '0', '0', '', 'excel', 'admin', sysdate(), '', null, '比赛管理目录');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 队伍管理菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队伍管理', @parentId, '1', 'teams', 'activity/teams/index', 1, 0, 'C', '0', '0', 'activity:teams:list', 'excel', 'admin', sysdate(), '', null, '队伍管理菜单');

-- 队伍管理按钮
SELECT @teamParentId := LAST_INSERT_ID();
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队伍查询', @teamParentId, '1',  '', '', 1, 0, 'F', '0', '0', 'activity:teams:query', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队伍新增', @teamParentId, '2',  '', '', 1, 0, 'F', '0', '0', 'activity:teams:add', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队伍修改', @teamParentId, '3',  '', '', 1, 0, 'F', '0', '0', 'activity:teams:edit', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队伍删除', @teamParentId, '4',  '', '', 1, 0, 'F', '0', '0', 'activity:teams:remove', '#', 'admin', sysdate(), '', null, '');

-- 队员管理菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队员管理', @parentId, '2', 'athletes', 'activity/athletes/index', 1, 0, 'C', '0', '0', 'activity:athletes:list', 'excel', 'admin', sysdate(), '', null, '队员管理菜单');

-- 队员管理按钮
SELECT @athleteParentId := LAST_INSERT_ID();
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队员查询', @athleteParentId, '1',  '', '', 1, 0, 'F', '0', '0', 'activity:athletes:query', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队员新增', @athleteParentId, '2',  '', '', 1, 0, 'F', '0', '0', 'activity:athletes:add', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队员修改', @athleteParentId, '3',  '', '', 1, 0, 'F', '0', '0', 'activity:athletes:edit', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('队员删除', @athleteParentId, '4',  '', '', 1, 0, 'F', '0', '0', 'activity:athletes:remove', '#', 'admin', sysdate(), '', null, '');

-- 赛程管理菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('赛程管理', @parentId, '3', 'matches', 'activity/matches/index', 1, 0, 'C', '0', '0', 'activity:matches:list', 'excel', 'admin', sysdate(), '', null, '赛程管理菜单');

-- 赛程管理按钮
SELECT @matchParentId := LAST_INSERT_ID();
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('赛程查询', @matchParentId, '1',  '', '', 1, 0, 'F', '0', '0', 'activity:matches:query', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('赛程新增', @matchParentId, '2',  '', '', 1, 0, 'F', '0', '0', 'activity:matches:add', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('赛程修改', @matchParentId, '3',  '', '', 1, 0, 'F', '0', '0', 'activity:matches:edit', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('赛程删除', @matchParentId, '4',  '', '', 1, 0, 'F', '0', '0', 'activity:matches:remove', '#', 'admin', sysdate(), '', null, '');

-- 小组排名管理菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('小组排名', @parentId, '4', 'standings', 'activity/standings/index', 1, 0, 'C', '0', '0', 'activity:standings:list', 'excel', 'admin', sysdate(), '', null, '小组排名菜单');

-- 小组排名管理按钮
SELECT @standingParentId := LAST_INSERT_ID();
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('小组排名查询', @standingParentId, '1',  '', '', 1, 0, 'F', '0', '0', 'activity:standings:query', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('小组排名新增', @standingParentId, '2',  '', '', 1, 0, 'F', '0', '0', 'activity:standings:add', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('小组排名修改', @standingParentId, '3',  '', '', 1, 0, 'F', '0', '0', 'activity:standings:edit', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('小组排名删除', @standingParentId, '4',  '', '', 1, 0, 'F', '0', '0', 'activity:standings:remove', '#', 'admin', sysdate(), '', null, '');

-- 场次成员管理菜单
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('场次成员', @parentId, '5', 'matchesAthletes', 'activity/matchesAthletes/index', 1, 0, 'C', '0', '0', 'activity:matches:athletes:list', 'excel', 'admin', sysdate(), '', null, '场次成员管理菜单');

-- 场次成员管理按钮
SELECT @matchesAthletesParentId := LAST_INSERT_ID();
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('场次成员查询', @matchesAthletesParentId, '1',  '', '', 1, 0, 'F', '0', '0', 'activity:matches:athletes:query', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('场次成员新增', @matchesAthletesParentId, '2',  '', '', 1, 0, 'F', '0', '0', 'activity:matches:athletes:add', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('场次成员修改', @matchesAthletesParentId, '3',  '', '', 1, 0, 'F', '0', '0', 'activity:matches:athletes:edit', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('场次成员删除', @matchesAthletesParentId, '4',  '', '', 1, 0, 'F', '0', '0', 'activity:matches:athletes:remove', '#', 'admin', sysdate(), '', null, '');