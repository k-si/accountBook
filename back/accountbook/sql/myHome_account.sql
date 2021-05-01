CREATE TABLE `account_record` (
  `id` char(19) NOT NULL COMMENT '账单id',
  `user_id` char(19) NOT NULL COMMENT '事件人id',
  `label_id` char(19) NOT NULL DEFAULT '0' COMMENT '事件标签id',
  `label` varchar(30)  DEFAULT '0' COMMENT '事件标签',
  `event` varchar(255) NOT NULL COMMENT '事件',
  `change` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '流水金额',
  `status` varchar(10) NOT NULL DEFAULT '1' COMMENT '流水 1消费  2收入',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_label_id` (`label_id`),
  KEY `idx_label` (`label`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='账单';


CREATE TABLE `account_user` (
  `id` char(19) NOT NULL COMMENT '使用者id',
  `name` varchar(20) NOT NULL COMMENT '使用者姓名',
  `balance` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '余额',
  `intro` varchar(500) NOT NULL DEFAULT '' COMMENT '使用者简介',
  `avatar` varchar(255) DEFAULT NULL COMMENT '讲师头像',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账单使用人';

CREATE TABLE `account_label` (
  `id` char(19) NOT NULL COMMENT '标签id',
  `name` varchar(20) NOT NULL COMMENT '标签名称',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流水标签';