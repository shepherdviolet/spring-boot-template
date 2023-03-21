--H2初始表
CREATE TABLE users (
userid INT PRIMARY KEY auto_increment,
username VARCHAR(255) NOT NULL,
password VARCHAR(255) NOT NULL,
enabled VARCHAR(5) NOT NULL,
phone VARCHAR(255)
);

CREATE TABLE user_auth (
authid INT PRIMARY KEY auto_increment,
username VARCHAR(255) NOT NULL,
authority VARCHAR(255) NOT NULL
);
