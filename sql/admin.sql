DROP TABLE IF EXISTS `shop_admin`;
CREATE TABLE `shop_admin`(
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `username` varchar(63) NOT NULL COMMENT '管理员名',
                             `password` varchar(63) NOT NULL COMMENT '管理员密码',
                             `last_time` datetime DEFAULT NULL COMMENT '最近一次登录时间',
                             `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '用户头像图片',
                             `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                             `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
                             `role_ids` varchar(127) DEFAULT '[]' COMMENT '角色列表',
                             PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

INSERT INTO `shop_admin` VALUES (1,'admin123','E10ADC3949BA59ABBE56E057F20F883E',NULL,'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','2018-02-01 00:00:00','2018-02-01 00:00:00',0,'[1]'),
                                (4,'promotion123','E10ADC3949BA59ABBE56E057F20F883E',NULL,'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','2019-01-07 15:16:59','2019-01-07 15:17:34',0,'[3]'),
                                (5,'mall123','E10ADC3949BA59ABBE56E057F20F883E',NULL,'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','2019-01-07 15:17:25','2019-01-07 15:21:05',0,'[2]');

CREATE TABLE `shop_role` (
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `name` varchar(63) NOT NULL COMMENT '角色名称',
                                 `desc` varchar(1023) DEFAULT NULL COMMENT '角色描述',
                                 `enabled` tinyint(1) DEFAULT '1' COMMENT '是否启用',
                                 `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                                 `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                 `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
                                 PRIMARY KEY (`id`),
                                 UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

INSERT INTO `shop_role` VALUES (1,'超级管理员','所有模块的权限',1,'2019-01-01 00:00:00','2019-01-01 00:00:00',0),(2,'商场管理员','只有商场模块的操作权限',1,'2019-01-01 00:00:00','2019-01-07 15:15:12',0),(3,'推广管理员','只有推广模块的操作权限',1,'2019-01-01 00:00:00','2019-01-07 15:15:24',0);