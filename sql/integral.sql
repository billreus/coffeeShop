DROP TABLE IF EXISTS `shop_integral`;
CREATE TABLE `shop_integral`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(63) NOT NULL COMMENT '用户名',
    `change_integral` decimal(10,2) DEFAULT NULL COMMENT '积分变化',
    `current_integral` decimal(10,2) DEFAULT NULL COMMENT  '总积分',
    `user_id` int(11) DEFAULT NULL COMMENT '用户id',
    `order_id` int(11) DEFAULT NULL COMMENT '订单id',
    `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除（0:正常1:删除）',
    PRIMARY KEY (`id`),
    KEY `user_id` (`user_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户积分表';