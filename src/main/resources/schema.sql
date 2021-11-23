Use articlestore;

CREATE TABLE IF NOT EXISTS `articles` (
  `article_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `title` varchar(64) DEFAULT NULL,
  `category` varchar(64) DEFAULT NULL,
  `publication_date` datetime NOT NULL,
  `content` TEXT DEFAULT NULL,
  `description` TEXT DEFAULT NULL,
  PRIMARY KEY (`article_id`));

CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `full_name` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
);
 
CREATE TABLE IF NOT EXISTS `roles` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`)
);
 
CREATE TABLE IF NOT EXISTS `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  KEY `user_fk_idx` (`user_id`),
  KEY `role_fk_idx` (`role_id`),
  CONSTRAINT `role_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
);