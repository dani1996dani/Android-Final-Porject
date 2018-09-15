CREATE DATABASE  IF NOT EXISTS `to_do_list` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;
USE `to_do_list`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: to_do_list
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tasks` (
  `user_id` int(11) NOT NULL,
  `task_title` varchar(50) NOT NULL,
  `task_content` varchar(255) NOT NULL,
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  UNIQUE KEY `task_id` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (3,'go on walk','need to poop',1),(3,'bark','at birds',2),(3,'bark','at birds',3),(3,'bark','at birds',4),(3,'bark','at birds',5),(3,'bark','at birds',6),(3,'bark','at birds',7),(3,'bark','at birds',8),(3,'bark','at birds',9),(3,'bark','at birds',10),(3,'bark','at birds',11),(3,'bark','at birds',12),(3,'bark','at birds',13),(3,'bark','at birds',14),(3,'bark','at birds',15),(3,'bark','at birds',16),(44,'Grace','Upon you',29),(3,'Do a Mlem','Drink the waters <3',30),(51,'Hello','test',37),(55,'hello','123',119),(56,'ðŸ˜�','gendsflkg',120),(56,'','ðŸ˜�',122),(2,'why','cant i use emojis',125),(59,'hello','hello123',131),(1,'weg','weg',135),(1,'weg','weg',136);
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `user_token` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`email`),
  UNIQUE KEY `user_token_UNIQUE` (`user_token`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'dani1996dani@gmail.com','132456','first'),(2,'yolanda@gmail.com','234234','second'),(3,'bonnie@gmail.com','bork1','third'),(37,'yes@gmail.com','123','707dc9e6-0d9e-4333-adf6-00207c27ba41'),(38,'maybe@v.s','123','b5d377b1-ef77-47b3-a1d5-7f1d57f69e0b'),(39,'sdf@sefg.wer','wer','8525e445-937e-47c6-a9ef-62658bcb9a86'),(40,'sadf@sdf.wer','wer','961008b7-50b5-4ea4-ba80-8f755db3efe4'),(41,'ass@sdf.sdf','wer','cec4237f-61fc-474a-ab8f-7840860c20f6'),(42,'test@test.com','132456','d8c59b4a-c2dc-473d-b0c7-bc831794f98a'),(43,'test@tester.com','123456','fa58328e-04a3-49a3-b1fa-06672cb7bd69'),(44,'jesus@please.work','123456','fdb63eb7-c4db-444a-84a6-65d97728d8e1'),(45,'daniel@walla.co.il','123','0fbd4b16-c313-4b86-b6b1-6ff319758fb1'),(46,'daniel@walla.co.ee','123','7ed0bfb4-7d84-4c61-b6f6-17a9935d2bcf'),(47,'daniel@walla.co.ddd','123','6631c1cb-f0e5-4d71-8bba-00b3ba1e55ee'),(48,'welcome@welcome.com','123','2273748d-ae38-411d-8a21-0150639136e4'),(49,'welcome@welcome.codm','123','40ee6fd2-ba49-4c7a-8d5f-19a3ad873f10'),(50,'1@1.a','1','4eabdd96-d64e-47a2-8e08-92e2c91ab251'),(51,'qwer@qwer.qwer','qwer','ac8e4354-96fb-424a-9883-8c20e2c2dce1'),(52,'query@gmail.com','123','ab9b9d9e-5359-4772-92a3-c991d1d76079'),(53,'dsfsk@dng.dsfklg','      ','a916823c-1b0d-42e8-ba7a-385b5f9b511d'),(54,'dani1995dani@gmail.com','132456','1678874a-8a1c-410c-b5f3-1a02ae2566fd'),(55,'qaz@qaz.qaz','qaz1','d6823fa5-8c91-4c90-9b2e-f00133402d84'),(56,'hsdgkjlh@jkfsdhkl.fdsjlf','gdg5','9177df4e-cb02-40e0-bea5-c8d0a0a578ee'),(57,'buddy@gmail.com','work1','58fe381c-4cc3-487a-be17-37347f66e0e5'),(58,'z@z.z','3333','9d084545-c514-4f93-9fdb-ac6999448bfe'),(59,'testing@testing.com','testing1','b0b35465-f709-4917-8772-dff0d486be77');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'to_do_list'
--

--
-- Dumping routines for database 'to_do_list'
--
/*!50003 DROP PROCEDURE IF EXISTS `checkTask` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `checkTask`(OUT id INTEGER(11))
BEGIN
	SELECT task_id INTO id FROM to_do_list.tasks WHERE task_id=2;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `getTasks` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `getTasks`()
BEGIN
	select * FROM to_do_list.tasks;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `insertTaskAndGetTaskId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertTaskAndGetTaskId`(IN user_id integer(11),
											IN task_title varchar(50),
											 IN task_content varchar(255)
											,OUT taskId integer(11))
BEGIN
insert into to_do_list.tasks (user_id,task_title,task_content) values
 (user_id,task_title,task_content);
 set taskId = last_insert_id();
 select taskId;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-15 15:40:21
