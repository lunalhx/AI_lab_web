-- ===========================
-- 实验室网站管理功能菜单配置
-- ===========================

-- 添加网站管理一级菜单
INSERT INTO sys_menu VALUES('5', '网站管理', '0', '5', 'website', null, '', '', 1, 0, 'M', '0', '0', '', 'website', 'admin', sysdate(), '', null, '网站管理目录');

-- 添加网站管理二级菜单
INSERT INTO sys_menu VALUES('118', '新闻管理', '5', '1', 'news', 'lab_web_sys/news/index', '', '', 1, 0, 'C', '0', '0', 'lab_web_sys:news:list', 'documentation', 'admin', sysdate(), '', null, '新闻管理菜单');
INSERT INTO sys_menu VALUES('119', '活动管理', '5', '2', 'activities', 'lab_web_sys/activities/index', '', '', 1, 0, 'C', '0', '0', 'lab_web_sys:activities:list', 'peoples', 'admin', sysdate(), '', null, '活动管理菜单');
INSERT INTO sys_menu VALUES('120', '通知管理', '5', '3', 'notifications', 'lab_web_sys/notifications/index', '', '', 1, 0, 'C', '0', '0', 'lab_web_sys:notifications:list', 'message', 'admin', sysdate(), '', null, '通知管理菜单');

-- 新闻管理按钮权限
INSERT INTO sys_menu VALUES('1061', '新闻查询', '118', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:news:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1062', '新闻新增', '118', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:news:add', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1063', '新闻修改', '118', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:news:edit', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1064', '新闻删除', '118', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:news:remove', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1065', '新闻导出', '118', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:news:export', '#', 'admin', sysdate(), '', null, '');

-- 活动管理按钮权限
INSERT INTO sys_menu VALUES('1066', '活动查询', '119', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:activities:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1067', '活动新增', '119', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:activities:add', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1068', '活动修改', '119', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:activities:edit', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1069', '活动删除', '119', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:activities:remove', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1070', '活动导出', '119', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:activities:export', '#', 'admin', sysdate(), '', null, '');

-- 通知管理按钮权限
INSERT INTO sys_menu VALUES('1071', '通知查询', '120', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:notifications:query', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1072', '通知新增', '120', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:notifications:add', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1073', '通知修改', '120', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:notifications:edit', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1074', '通知删除', '120', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:notifications:remove', '#', 'admin', sysdate(), '', null, '');
INSERT INTO sys_menu VALUES('1075', '通知导出', '120', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'lab_web_sys:notifications:export', '#', 'admin', sysdate(), '', null, '');

-- 为超级管理员角色分配网站管理权限
INSERT INTO sys_role_menu VALUES ('1', '5');   -- 网站管理目录
INSERT INTO sys_role_menu VALUES ('1', '118'); -- 新闻管理
INSERT INTO sys_role_menu VALUES ('1', '119'); -- 活动管理  
INSERT INTO sys_role_menu VALUES ('1', '120'); -- 通知管理

-- 新闻管理权限
INSERT INTO sys_role_menu VALUES ('1', '1061');
INSERT INTO sys_role_menu VALUES ('1', '1062');
INSERT INTO sys_role_menu VALUES ('1', '1063');
INSERT INTO sys_role_menu VALUES ('1', '1064');
INSERT INTO sys_role_menu VALUES ('1', '1065');

-- 活动管理权限
INSERT INTO sys_role_menu VALUES ('1', '1066');
INSERT INTO sys_role_menu VALUES ('1', '1067');
INSERT INTO sys_role_menu VALUES ('1', '1068');
INSERT INTO sys_role_menu VALUES ('1', '1069');
INSERT INTO sys_role_menu VALUES ('1', '1070');

-- 通知管理权限
INSERT INTO sys_role_menu VALUES ('1', '1071');
INSERT INTO sys_role_menu VALUES ('1', '1072');
INSERT INTO sys_role_menu VALUES ('1', '1073');
INSERT INTO sys_role_menu VALUES ('1', '1074');
INSERT INTO sys_role_menu VALUES ('1', '1075');

-- 为普通角色也分配相应权限（可选）
INSERT INTO sys_role_menu VALUES ('2', '5');   -- 网站管理目录
INSERT INTO sys_role_menu VALUES ('2', '118'); -- 新闻管理
INSERT INTO sys_role_menu VALUES ('2', '119'); -- 活动管理  
INSERT INTO sys_role_menu VALUES ('2', '120'); -- 通知管理

-- 普通角色的按钮权限（根据需要调整）
INSERT INTO sys_role_menu VALUES ('2', '1061'); -- 新闻查询
INSERT INTO sys_role_menu VALUES ('2', '1062'); -- 新闻新增
INSERT INTO sys_role_menu VALUES ('2', '1063'); -- 新闻修改
INSERT INTO sys_role_menu VALUES ('2', '1066'); -- 活动查询
INSERT INTO sys_role_menu VALUES ('2', '1067'); -- 活动新增
INSERT INTO sys_role_menu VALUES ('2', '1068'); -- 活动修改
INSERT INTO sys_role_menu VALUES ('2', '1071'); -- 通知查询
INSERT INTO sys_role_menu VALUES ('2', '1072'); -- 通知新增
INSERT INTO sys_role_menu VALUES ('2', '1073'); -- 通知修改 