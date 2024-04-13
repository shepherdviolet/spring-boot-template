--H2初始数据
INSERT INTO USERS (username, password, enabled, phone) VALUES ('admin', 'admin', 'true', '13011112222');
INSERT INTO USERS (username, password, enabled, phone) VALUES ('user1', 'user1', 'true', '13011112223');
INSERT INTO USERS (username, password, enabled, phone) VALUES ('user2', 'user2', 'true', '13011112224');
INSERT INTO USER_AUTH (username, authority) VALUES ('admin', 'ADMIN');
INSERT INTO USER_AUTH (username, authority) VALUES ('admin', 'USER');
INSERT INTO USER_AUTH (username, authority) VALUES ('user1', 'USER');
INSERT INTO USER_AUTH (username, authority) VALUES ('user2', 'USER');