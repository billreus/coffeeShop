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