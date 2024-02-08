-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: attendancesystem
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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL,
  `user_idValue` varchar(255) NOT NULL,
  `user_password` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `user_birth` date NOT NULL,
  `user_address` varchar(255) NOT NULL,
  `user_gender` varchar(255) NOT NULL,
  `user_phoneNum` varchar(255) NOT NULL,
  `user_major` varchar(255) NOT NULL,
  `class_name` varchar(255) NOT NULL,
  `access_level` int NOT NULL,
  `profile_image` blob NOT NULL,
  `profile_name` varchar(255) NOT NULL,
  `profile_uploadName` varchar(255) NOT NULL,
  `profile_extension` varchar(255) NOT NULL,
  `profile_size` int NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('user_id_1','user_idValue_1','password_1','Name 1','1990-01-01','Address 1','Male','123456789','Major 1','Class 1',1,_binary 'image_blob_1','Profile Name 1','Upload Name 1','png',100),('user_id_2','user_idValue_2','password_2','Name 2','1991-02-02','Address 2','Female','987654321','Major 2','Class 2',2,_binary 'image_blob_2','Profile Name 2','Upload Name 2','jpg',150),('user_id_3','user_idValue_3','password_3','Name 3','1992-03-03','Address 3','Male','555555555','Major 3','Class 3',3,_binary 'image_blob_3','Profile Name 3','Upload Name 3','jpeg',200),('user_id_4','user_idValue_4','password_4','Name 4','1993-04-04','Address 4','Female','333333333','Major 4','Class 4',4,_binary 'image_blob_4','Profile Name 4','Upload Name 4','png',250);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-06  9:41:31
