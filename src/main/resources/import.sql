-- import.sql

INSERT INTO authority (authority_seq, name) VALUES (1, 'ADMIN');
INSERT INTO authority (authority_seq, name) VALUES (2, 'USER');

INSERT INTO member (member_seq, email, password, name, profile_img, authority_seq) VALUES (1, 'admin@rocket.com', '1234', 'admin', NULL, 1);
INSERT INTO member (member_seq, email, password, name, profile_img, authority_seq) VALUES (2, 'user1@rocket.com', '1234', 'user1', NULL, 2);
INSERT INTO member (member_seq, email, password, name, profile_img, authority_seq) VALUES (3, 'user2@rocket.com', '1234', 'user2', NULL, 2);
INSERT INTO member (member_seq, email, password, name, profile_img, authority_seq) VALUES (4, 'jeongm0220@gmail.com', '1234', 'jeongm', NULL, 2);
INSERT INTO member (member_seq, email, password, name, profile_img, authority_seq) VALUES (5, 'je0ng22@naver.com', '1234', 'je0ng22', NULL, 2);

