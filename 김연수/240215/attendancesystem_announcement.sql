-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: attendancesystem
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `announcement` (
  `announcement_num` int unsigned NOT NULL AUTO_INCREMENT,
  `announcement_title` varchar(255) NOT NULL,
  `announcement_content` varchar(255) NOT NULL,
  `announcement_date` date NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `announcement_writer` varchar(45) NOT NULL,
  `announcement_class` varchar(45) NOT NULL,
  PRIMARY KEY (`announcement_num`),
  KEY `announcement_user_id_foreign` (`user_id`),
  CONSTRAINT `announcement_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `announcement`
--

LOCK TABLES `announcement` WRITE;
/*!40000 ALTER TABLE `announcement` DISABLE KEYS */;
INSERT INTO `announcement` VALUES (19,'글제목입니다.','글내용 글내용..','2024-02-11','user_id_1','Name 1','cad1급반'),(22,'글제목입니다.','글내용 글내용..','2024-02-12','user_id_11','Name 11','java반'),(23,'글제목입니다.','글내용 글내용..','2024-02-13','user_id_11','Name 11','cad2급반'),(24,'wqrrwqqrw','글내용 글내용..','2024-02-13','user_id_12','Name 12','java반'),(25,'wqewqrwrq','글내용 글내용..qw','2024-02-14','user_id_11','Name 11','cad2급반'),(26,'qwr','qwr','2024-02-14','user_id_10','Name 10','cad1급반'),(27,'글제목입니다.','글내용 글내용..','2024-02-14','user_id_10','Name 10','java반'),(28,'글제목입니다.','글내용 글내용..','2024-02-14','user_id_10','Name 10','cad2급반'),(29,'글제목입니다.','글내용 글내용..','2024-02-14','user_id_1','Name 1','java반'),(31,'글제목입니다.','글내용 글내용..','2024-02-14','user_id_1','Name 1','java반'),(32,'글제목입니다.','글내용 글내용..','2024-02-14','user_id_4','Name 4','cad1급반');
/*!40000 ALTER TABLE `announcement` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-15 17:53:18
