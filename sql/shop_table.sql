DROP TABLE IF EXISTS `shop_user`;
CREATE TABLE `shop_user`(
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `username` varchar(63) NOT NULL COMMENT '用户名',
    `password` varchar(63) NOT NULL COMMENT '密码',
    `gender` tinyint(3) NOT NULL DEFAULT '0' COMMENT '性别：0未知，1男，2女',
    `birthday` date DEFAULT NULL COMMENT '生日',
    `last_login_time` datetime DEFAULT NULL COMMENT '最近一次登录时间',
    `user_level` tinyint(3) DEFAULT '0' COMMENT '0 普通用户，1 VIP用户，2 高级VIP用户',
    `nickname` varchar(63) NOT NULL DEFAULT '' COMMENT '用户昵称或网络名称',
    `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '用户手机号码',
    `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '用户头像图片',
    `weixin_openid` varchar(63) NOT NULL DEFAULT '' COMMENT '微信登录openid',
    `session_key` varchar(100) NOT NULL DEFAULT '' COMMENT '微信登录会话KEY',
    `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '0 可用, 1 禁用, 2 注销',
    `add_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_name` (`username`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';