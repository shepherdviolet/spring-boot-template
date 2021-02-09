
--MySQL----------------------------------------------------------------------------

--注意: 加binary使大小写敏感

CREATE DATABASE IF NOT EXISTS test DEFAULT CHARACTER SET = utf8mb4;
USE test;

CREATE TABLE `USERS` (
`USERNAME` VARCHAR(255) BINARY PRIMARY KEY COMMENT '用户名',
`PASSWORD` VARCHAR(255) BINARY NOT NULL COMMENT '密码',
`PHONE` VARCHAR(255) BINARY COMMENT '手机'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
