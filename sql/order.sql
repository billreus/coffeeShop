DROP TABLE IF EXISTS `shop_order`;
CREATE TABLE `shop_order`(
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                             `user_id` int(11) NOT NULL COMMENT '用户表的id',
                             `order_sn` varchar(63) NOT NULL COMMENT '订单编号',
                             `order_status` smallint(1) NOT NULL COMMENT '订单状态',
                             `consignee` varchar(63) NOT NULL COMMENT '收货人名称',
                             `mobile` varchar(63) NOT NULL COMMENT '收货人手机号',
                             `address` varchar(127) NOT NULL COMMENT '收货地址',
                             `order_price` decimal(10,2) NOT NULL COMMENT '订单价格',
                             `coupon_price` decimal(10,2) NOT NULL COMMENT '优惠金额',
                             `order_integral` int(11) NOT NULL COMMENT  '订单积分',
                             `actual_price` decimal(10,2) DEFAULT '0.00' COMMENT '实际付款金额',
                             `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `end_time` datetime DEFAULT NULL COMMENT '结束时间',
                             `message` varchar(512) NOT NULL DEFAULT '' COMMENT '备注',
                             `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除（0:正常1:删除）',
                             PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

DROP TABLE IF EXISTS `shop_goods_order`;
CREATE TABLE `shop_goods_order`(
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `goods_id` int(11) DEFAULT NULL COMMENT '商品id',
                                   `order_id` int(11) DEFAULT NULL COMMENT '订单id',
                                   `goods_name` varchar(127) DEFAULT NULL COMMENT '商品名称',
                                   `pic_url` varchar(255) DEFAULT NULL COMMENT '商品图片或者商品货品图片',
                                   `goods_type` varchar(63) DEFAULT NULL COMMENT '商品类型',
                                   `actual_price` decimal(10,2) DEFAULT '0.00' COMMENT '实际付款金额',
                                   `goods_count` int(11) DEFAULT NULL COMMENT '购买数量',
                                   `comment` int(11) DEFAULT '0' COMMENT '订单商品评论，如果是-1，则超期不能评价；如果是0，则可以评价；',
                                   `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                   `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
                                   PRIMARY KEY (`id`),
                                   KEY `order_id` (`order_id`),
                                   KEY `goods_id` (`goods_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单表';


DROP TABLE IF EXISTS `shop_comment`;
CREATE TABLE `shop_comment`(
                               `id` int(11) NOT NULL AUTO_INCREMENT,
                               `value_id` int(11) NOT NULL DEFAULT '0' COMMENT '如果type=0，则是商品评论',
                               `type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '评论类型，如果type=0，则是商品评论；如果是type=1，则是专题评论；如果type=3，则是订单商品评论。',
                               `content` varchar(1023) NOT NULL COMMENT '评论内容',
                               `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户表的用户ID',
                               `has_picture` tinyint(1) DEFAULT '0' COMMENT '是否含有图片',
                               `pic_urls` varchar(1023) DEFAULT NULL COMMENT '图片地址列表，采用JSON数组格式',
                               `star` smallint(6) DEFAULT '1' COMMENT '评分， 1-5',
                               `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                               `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
                               PRIMARY KEY (`id`),
                               KEY `id_value` (`value_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单表';
