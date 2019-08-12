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

INSERT INTO shop_operate_integral VALUES (1, 1, 1, 0 ,null, null),
                                         (2, 2, 0.98, 0 ,null, null),
                                         (3, 3, 0.95, 0 ,null, null),
                                         (4, 4, 0.92, 0 ,null, null),
                                         (5, 5, 0.9, 0 ,null, null),
                                         (6, 6, 0.88, 0 ,null, null),
                                         (7, 7, 0.85, 0 ,null, null),
                                         (8, 8, 0.82, 0 ,null, null)