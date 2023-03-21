
--MySQL----------------------------------------------------------------------------

--注意: 加binary使大小写敏感

-- 建用户(root权限)
CREATE USER 'test'@'%' IDENTIFIED BY 'test';
GRANT ALL ON test.* TO 'test'@'%';

-- 建库(root权限)
CREATE DATABASE IF NOT EXISTS test DEFAULT CHARACTER SET = utf8mb4;

-- 建表
USE test;

CREATE TABLE IF NOT EXISTS `users` (
`userid` INT PRIMARY KEY auto_increment COMMENT '用户ID',
`username` VARCHAR(255) NOT NULL COMMENT '用户名',
`password` VARCHAR(255) NOT NULL COMMENT '密码',
`enabled` VARCHAR(5) NOT NULL COMMENT '启用',
`phone` VARCHAR(255) COMMENT '手机',
UNIQUE `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户表';

CREATE TABLE IF NOT EXISTS `user_auth` (
`authid` INT PRIMARY KEY auto_increment COMMENT '权限ID',
`username` VARCHAR(255) NOT NULL COMMENT '用户名',
`authority` VARCHAR(255) NOT NULL COMMENT '权限',
INDEX `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='权限表';

