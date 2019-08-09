DROP TABLE IF EXISTS `shop_address`;
CREATE TABLE `shop_address` (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `name` varchar(63) NOT NULL DEFAULT '' COMMENT '收货人名称',
     `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户表的用户ID',
     `province` varchar(63) NOT NULL COMMENT '行政区域表的省ID',
     `city` varchar(63) NOT NULL COMMENT '行政区域表的市ID',
     `county` varchar(63) NOT NULL COMMENT '行政区域表的区县ID',
     `address_detail` varchar(127) NOT NULL DEFAULT '' COMMENT '详细收货地址',
     `tel` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号码',
     `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认地址',
     `add_time` datetime DEFAULT NULL COMMENT '创建时间',
     `update_time` datetime DEFAULT NULL COMMENT '更新时间',
     `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
     PRIMARY KEY (`id`),
     KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收货地址表';