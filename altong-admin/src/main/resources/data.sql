/* admin 계정 : master / dkfflaxhdgkq1 */
INSERT INTO at_admin_user (user_id,authority,email,encoded_key,last_login_date,login_fail_count,mobile_no,name,password,password_change_date,temp_password) VALUES ('master','ROLE_USER,ROLE_ADMIN','master@test.co.kr',NULL,'2018-07-13 00:00:00',0,'01012345678','마스터','0c85844e2042cd31b11b3355b1ce547270c0177b716f86312973fa88d411c0cd98d37bc0563c4a09b3646873b480c6ae5cb9f93042b76516d834bae51614117b','2018-07-13 00:00:00','1');
INSERT INTO at_admin_user (user_id,authority,email,encoded_key,last_login_date,login_fail_count,mobile_no,name,password,password_change_date,temp_password) VALUES ('suzhy','ROLE_USER,ROLE_ADMIN','suzhy@daou.co.kr',NULL,'2018-07-13 00:00:00',0,'01092896777','한영수','f2b35cc73daeabb8b6152a02ec2a7e26bd1aef8147420c7116435e97dce5ff3a353b998ad47860a58568cbf1efc1149e0344be5df24392320060ce33fa86c692','2018-07-13 00:00:00','1');
INSERT INTO at_admin_user (user_id,authority,email,encoded_key,last_login_date,login_fail_count,mobile_no,name,password,password_change_date,temp_password) VALUES ('yoonsm','ROLE_USER','yoonsm@test.co.kr',NULL,'2018-07-13 00:00:00',0,'01072342293','윤순무','dd297b789199b0d8dc4f1287c4ca94e4105b208dc7e1842c16c73d3cee3664dfcadf8d11e3db416d9c89db541ce1af2121b96ee0650a48d128b7c90871ac2e1c','2018-07-13 00:00:00','1');
INSERT INTO at_admin_user (user_id,authority,email,encoded_key,last_login_date,login_fail_count,mobile_no,name,password,password_change_date,temp_password) VALUES ('sniper798','ROLE_USER','sniper798@test.co.kr',NULL,'2018-07-13 00:00:00',0,'01049041835','최정환','fe57f9a4bd33432c74a0585ffd45b380ced8de4d1d2b780c424534c7a58d2a0d9be873412d9c106e904fc358ad99ec266082b5b09e2a80ea9ad9eca99e3a3c6a','2018-07-13 00:00:00','1');
INSERT INTO at_admin_user (user_id,authority,email,encoded_key,last_login_date,login_fail_count,mobile_no,name,password,password_change_date,temp_password) VALUES ('jeejon','ROLE_USER','jeejon@test.co.kr',NULL,'2018-07-13 00:00:00',0,'01089463430','지민근','f1298da8a467addc36697d7e37cf4b656f7a1a171140d61f81abc45ecb997dd09f3498a2d3af070bf4523d52811b100422e07d9fe04303a27c10c812bdd869b4','2018-07-13 00:00:00','1');
INSERT INTO at_admin_user (user_id,authority,email,encoded_key,last_login_date,login_fail_count,mobile_no,name,password,password_change_date,temp_password) VALUES ('ksh00','ROLE_USER','ksh00@test.co.kr',NULL,'2018-07-13 00:00:00',0,'01032159238','김승희','02b2ebe417fa6618fe12d350899af6bbffb47ce913d07378934fc17390c7e36618e754f24eb82230c08e7c0b45d05b7d29bcacffd3de79cd9bd5d827a4e3fb71','2018-07-13 00:00:00','1');

INSERT INTO at_service (service_id,service_name,service_status) VALUES ('ADPPURIO','애드뿌리오','1');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('BIZCALLMIX','비즈콜믹스','1');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('BIZPLUS','비즈플러스','0');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('BIZPPURIO','비즈뿌리오','0');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('CALLMIX','콜믹스','1');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('DONUTBOOK','도넛북','1');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('PHOTO_EDITOR','포토에디터','0');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('PPURIO','뿌리오','1');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('SKYSMS','문자천국','1');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('TEMP_10','TEMP_10','0');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('TEMP_2','TEMP_2','1');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('TEMP_3','TEMP_3','1');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('TEMP_4','TEMP_4','1');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('TEMP_5','TEMP_5','0');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('TEMP_6','TEMP_6','0');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('TEMP_7','TEMP_7','0');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('TEMP_8','TEMP_8','0');
INSERT INTO at_service (service_id,service_name,service_status) VALUES ('test','teste23232323','0');

INSERT INTO `at_project` (project_no,project_name,project_status,service_id,mod_datetime,reg_datetime,reg_user_id,send_limit_yn,resend_type,send_type,project_id) VALUES (1,'포토에디터 사업팀','0','PHOTO_EDITOR','2017-11-18 00:28:41','2017-11-18 00:27:14','suzhy','1','P','D','PHOTO_API');
INSERT INTO `at_project` (project_no,project_name,project_status,service_id,mod_datetime,reg_datetime,reg_user_id,send_limit_yn,resend_type,send_type,project_id)  VALUES (2,'콜믹스 링커','1','CALLMIX','2017-11-18 00:27:14','2017-11-13 00:44:09','suzhy','0','D','P','C');
INSERT INTO `at_project` (project_no,project_name,project_status,service_id,mod_datetime,reg_datetime,reg_user_id,send_limit_yn,resend_type,send_type,project_id)  VALUES (3,'비즈뿌리오 배치','1','BIZPPURIO','2017-11-18 00:20:30','2017-11-13 00:44:28','suzhy','0','S','D','BIZPPURIO_BATCH');
INSERT INTO `at_project` (project_no,project_name,project_status,service_id,mod_datetime,reg_datetime,reg_user_id,send_limit_yn,resend_type,send_type,project_id)  VALUES (4,'도넛북 웹','1','DONUTBOOK','2017-11-18 00:20:28','2017-11-13 00:46:13','suzhy','1','S','P','WEB_MONITOR');
INSERT INTO `at_project` (project_no,project_name,project_status,service_id,mod_datetime,reg_datetime,reg_user_id,send_limit_yn,resend_type,send_type,project_id)  VALUES (5,'포토에디터 개발자','1','PHOTO_EDITOR','2017-11-18 00:20:26','2017-11-13 23:28:38','suzhy','1','E','P','a4');
INSERT INTO `at_project` (project_no,project_name,project_status,service_id,mod_datetime,reg_datetime,reg_user_id,send_limit_yn,resend_type,send_type,project_id)  VALUES (6,'ㅀㅀ','1','BIZCALLMIX','2017-11-18 00:19:35','2017-11-15 00:46:42','suzhy','1','S','P','a6');

INSERT INTO `at_member` (member_no,email,member_name,member_status,mobile_no,remarks,push_no,smart_phone_type,staff_no) VALUES (1,'suzhy@naver.com','한영수','1','01092896777','','dpVkkJymCc0:APA91bF_BC3d3vpa8ir1NHi5cj26pKZ4zPFnaacubAb4fdnb2txgoGMEfCYhe7Nck4cNKn1HB6t1ylYa5kKNnhhZQS4t5792bhPvsFcq_SobxjjRqjjWDFZjmNqG91DwViaOzbhpCoIQ','A',3075);
INSERT INTO `at_member` (member_no,email,member_name,member_status,mobile_no,remarks,push_no,smart_phone_type,staff_no) VALUES (8,'jeejon@daou.co.kr','지민근','1','01012345678','dd','cbLOaclTjzQ:APA91bEE_5nlwe8VZ0XeMMcEdulqNRQSPYKEGj-9tE1-olzsP04r0XCd2KACpqYOp78KFa7FJM8pyrr9pvg_kJLRq9XtsGSH5gpBHd6i4iTyjF7kSUunzqYDoBSJP90OdvWK2enYVBz3','I',0);
INSERT INTO `at_member` (member_no,email,member_name,member_status,mobile_no,remarks,push_no,smart_phone_type,staff_no) VALUES (9,'hong@daou.co.kr','홍길동','1','01078945615','홍길동 테스트','','A',1);
INSERT INTO `at_member` (member_no,email,member_name,member_status,mobile_no,remarks,push_no,smart_phone_type,staff_no) VALUES (10,'sewi@daou.co.kr','홍종완','1','01031314646','','fsar34trfsdff','I',2);
INSERT INTO `at_member` (member_no,email,member_name,member_status,mobile_no,remarks,push_no,smart_phone_type,staff_no) VALUES (11,'sniper798@daou.co.kr','최정환','1','01049041835','','fhlN8bIvD2s:APA91bHsZTwifwT2qWetYrgSWl53H6hSUsuRcFQ4TPDHsDKSi_R7UeEoFslbxm7H9LqeRDXkwd0Dyceq_FxL-3nlC4ZjJt-Jp8vi-mOZqz3TVGVvR3YSBNfFNtXokE8c-UBBW4Z8kAYL','A',2110);
INSERT INTO `at_member` (member_no,email,member_name,member_status,mobile_no,remarks,push_no,smart_phone_type,staff_no) VALUES (12,'yoonsm@daou.co.kr','윤순무','1','01072342293','','eMWn6OLRQtQ:APA91bHu2TCt5itrXZwfnN7f5F2W0lVtQxqGIDeCrnxeYBgMVld9VTocMCbZKZ2kHyqiGdEaA20zcfaK8mfvwUsAWbBIBIn37-LENONOsVlNTcl7H9Us09vd-e5ruoOdxHs-yYy443eZ','A',3068);


INSERT INTO `at_project_member` (project_no, member_no) VALUES (1,1);
INSERT INTO `at_project_member` (project_no, member_no) VALUES (1,10);
INSERT INTO `at_project_member` (project_no, member_no) VALUES (1,8);
INSERT INTO `at_project_member` (project_no, member_no) VALUES (1,9);
INSERT INTO `at_project_member` (project_no, member_no) VALUES (4,1);
INSERT INTO `at_project_member` (project_no, member_no) VALUES (4,8);
INSERT INTO `at_project_member` (project_no, member_no) VALUES (4,11);
INSERT INTO `at_project_member` (project_no, member_no) VALUES (4,12);

insert into at_service_admin_user (user_id, service_id) values ('suzhy', 'PHOTO_EDITOR');
insert into at_service_admin_user (user_id, service_id) values ('suzhy', 'CALLMIX');
insert into at_service_admin_user (user_id, service_id) values ('yoonsm', 'BIZPPURIO');
