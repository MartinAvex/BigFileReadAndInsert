-- manager_system.user_case definition

CREATE TABLE `user_case` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID主键',
  `user_name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '名字',
  `email` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  PRIMARY KEY (`user_id`),
  KEY `user_case_user_name_IDX` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2500256 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;