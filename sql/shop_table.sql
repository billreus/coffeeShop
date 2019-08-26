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

INSERT INTO `shop_admin` VALUES (1,'admin123','E10ADC3949BA59ABBE56E057F20F883E',NULL,'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif','2018-02-01 00:00:00','2018-02-01 00:00:00',0,'[1]');

DROP TABLE IF EXISTS `shop_attribute`;
CREATE TABLE `shop_attribute`(
                                 `id` int(11) NOT NULL AUTO_INCREMENT,
                                 `goods_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品id',
                                 `attribute` varchar(255) NOT NULL COMMENT '商品参数名',
                                 `value` varchar(255) NOT NULL COMMENT '商品参数',
                                 `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                                 `update_date` datetime DEFAULT NULL COMMENT '更新时间',
                                 `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除（0：正常 1：删除）',
                                 PRIMARY KEY (`id`),
                                 KEY `goods_id` (`goods_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品参数表';

DROP TABLE IF EXISTS `shop_comment`;
CREATE TABLE `shop_comment`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `value_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品关联id',
    `type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '评论类型，如果type=0，则是商品评论',
    `content` varchar(1023) NOT NULL COMMENT '评论内容',
    `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户表的用户ID',
    `star` smallint(6) DEFAULT '1' COMMENT '评分， 1-5',
    `add_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
    PRIMARY KEY (`id`),
    KEY `id_value` (`value_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表';

DROP TABLE IF EXISTS `shop_category`;
CREATE TABLE `shop_category`(
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `name` varchar(63) NOT NULL DEFAULT '' COMMENT '类目名称',
                                `keywords` varchar(1023) NOT NULL DEFAULT '' COMMENT '类目关键字，以JSON数组格式',
                                `pid` int(11) NOT NULL DEFAULT '0' COMMENT '父类目ID',
                                `icon_url` varchar(255) DEFAULT '' COMMENT '类目图标',
                                `pic_url` varchar(255) DEFAULT '' COMMENT '类目图片',
                                `sort_order` tinyint(3) DEFAULT '50' COMMENT '排序',
                                `level` varchar(255) DEFAULT 'L1' COMMENT '目录等级',
                                `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
                                PRIMARY KEY (`id`),
                                KEY `parent_id` (`pid`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品类别表';

DROP TABLE IF EXISTS `shop_cart`;
CREATE TABLE `shop_cart`(
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `user_id` int(11) DEFAULT NULL COMMENT '用户表的用户ID',
                            `goods_id` int(11) DEFAULT NULL COMMENT '商品表的商品ID',
                            `goods_sn` varchar(63) DEFAULT NULL COMMENT '商品编号',
                            `goods_name` varchar(127) DEFAULT NULL COMMENT '商品名称',
                            `price` decimal(10,2) DEFAULT '0.00' COMMENT '商品货品的价格',
                            `number` smallint(5) DEFAULT '0' COMMENT '商品货品的数量',
                            `checked` tinyint(1) DEFAULT '1' COMMENT '购物车中商品是否选择状态',
                            `pic_url` varchar(255) DEFAULT NULL COMMENT '商品图片或者商品货品图片',
                            `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
                            PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物车表';

DROP TABLE IF EXISTS `shop_goods`;
CREATE TABLE `shop_goods`(
                             `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                             `category_id` int(11) NOT NULL COMMENT '商品类别id',
                             `goods_id` varchar(63) NOT NULL COMMENT '商品编号id',
                             `gallery` varchar(1023) DEFAULT NULL COMMENT '商品宣传图片列表，采用JSON数组格式',
                             `name` varchar(127) DEFAULT NULL COMMENT '商品名称',
                             `brief` varchar(255) DEFAULT NULL COMMENT '商品介绍',
                             `keywords` varchar(255) DEFAULT NULL COMMENT '关键字',
                             `pic_url` varchar(255) DEFAULT NULL COMMENT '图片',
                             `unit` varchar(31) DEFAULT '’件‘' COMMENT '商品单位，例如件、盒',
                             `original_price` decimal(10,2) DEFAULT '0.00' COMMENT '原价',
                             `retail_price` decimal(10,2) DEFAULT '0.00' COMMENT '零售价',
                             `create_by` varchar(63) DEFAULT NULL COMMENT '创建者',
                             `create_date` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_by` varchar(63) DEFAULT NULL COMMENT '更新者',
                             `update_date` datetime DEFAULT NULL COMMENT '更新时间',
                             `detail` text DEFAULT NULL COMMENT '富文本商品介绍',
                             `on_sale` tinyint(1) DEFAULT '1' COMMENT '是否上架，默认上架',
                             `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除（0：正常 1：删除）',
                             PRIMARY KEY (`id`),
                             KEY `category_id` (`category_id`),
                             KEY `goods_id` (`goods_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

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
                                   `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                                   `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                   `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
                                   PRIMARY KEY (`id`),
                                   KEY `order_id` (`order_id`),
                                   KEY `goods_id` (`goods_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品订单表';

DROP TABLE IF EXISTS `shop_integral`;
CREATE TABLE `shop_integral`(
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `change_integral` decimal(10,2) DEFAULT 0 COMMENT '积分变化',
                                `current_integral` decimal(10,2) DEFAULT 0 COMMENT  '总积分',
                                `user_id` int(11) DEFAULT NULL COMMENT '用户id',
                                `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除（0:正常1:删除）',
                                PRIMARY KEY (`id`),
                                KEY `user_id` (`user_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户积分表';

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

DROP TABLE IF EXISTS `shop_operate_integral`;
CREATE TABLE `shop_operate_integral`(
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `level` int(11) DEFAULT 0 COMMENT '等级',
                                        `discount` decimal(10,2) DEFAULT '10000.00' COMMENT '折扣比例',
                                        `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
                                        `start_time` datetime DEFAULT NULL COMMENT '生效时间',
                                        `end_time` datetime DEFAULT NULL COMMENT '失效时间',
                                        PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分运营表';

DROP TABLE IF EXISTS `shop_stock`;
CREATE TABLE `shop_stock`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `goods_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品id',
    `stock` int(11) NOT NULL DEFAULT '0' COMMENT '库存',
    `sale_count` int(11) NOT NULL DEFAULT '0' COMMENT '销量',
    `create_date` datetime DEFAULT NULL COMMENT '创建时间',
    `update_date` datetime DEFAULT NULL COMMENT '更新时间',
    `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除（0：正常 1：删除）',
    PRIMARY KEY (`id`),
    KEY `goods_id` (`goods_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品库存表';

DROP TABLE IF EXISTS `shop_storage`;
CREATE TABLE `shop_storage` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `key` varchar(63) NOT NULL COMMENT '文件的唯一索引',
    `name` varchar(255) NOT NULL COMMENT '文件名',
    `type` varchar(20) NOT NULL COMMENT '文件类型',
    `size` int(11) NOT NULL COMMENT '文件大小',
    `url` varchar(255) DEFAULT NULL COMMENT '文件访问链接',
    `add_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文件存储表';

DROP TABLE IF EXISTS `shop_user`;
CREATE TABLE `shop_user`(
                            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
                            `username` varchar(63) NOT NULL COMMENT '用户名',
                            `password` varchar(63) NOT NULL COMMENT '密码',
                            `gender` tinyint(3) NOT NULL DEFAULT '0' COMMENT '性别：0未知，1男，2女',
                            `birthday` date DEFAULT NULL COMMENT '生日',
                            `login_time` datetime DEFAULT NULL COMMENT '最近一次登录时间',
                            `user_level` tinyint(3) NOT NULL DEFAULT '0' COMMENT '用户等级',
                            `nickname` varchar(63) NOT NULL DEFAULT '' COMMENT '用户昵称',
                            `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '用户手机号码',
                            `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '用户头像图片',
                            `wechat_openid` varchar(63) NOT NULL DEFAULT '' COMMENT '微信登录openid',
                            `session_key` varchar(100) NOT NULL DEFAULT '' COMMENT '微信登录会话KEY',
                            `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '0 可用, 1 禁用',
                            `add_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `user_name` (`username`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

