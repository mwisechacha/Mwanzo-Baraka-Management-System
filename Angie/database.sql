CREATE DATABASE  IF NOT EXISTS `baraka_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `baraka_db`;
-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: localhost    Database: baraka_db
-- ------------------------------------------------------
-- Server version	8.0.30

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
-- Table structure for table `contributions`
--

DROP TABLE IF EXISTS `contributions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contributions` (
  `idcontributions` int NOT NULL AUTO_INCREMENT,
  `amount` varchar(45) NOT NULL,
  `member_id` varchar(45) DEFAULT NULL,
  `group_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idcontributions`),
  KEY `member_id_idx` (`member_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contributions`
--

LOCK TABLES `contributions` WRITE;
/*!40000 ALTER TABLE `contributions` DISABLE KEYS */;
INSERT INTO `contributions` VALUES (10,'1000','09876',NULL),(12,'1000','45678',NULL),(13,'1000','100',NULL),(14,'800','45678',NULL),(15,'1800','234345',NULL),(16,'800','234345',NULL),(17,'200',NULL,'Chacha Foundation');
/*!40000 ALTER TABLE `contributions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_details`
--

DROP TABLE IF EXISTS `group_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `group_details` (
  `group_code` int NOT NULL AUTO_INCREMENT,
  `group_name` varchar(45) NOT NULL,
  `reg_fee` int NOT NULL DEFAULT '5000',
  PRIMARY KEY (`group_code`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_details`
--

LOCK TABLES `group_details` WRITE;
/*!40000 ALTER TABLE `group_details` DISABLE KEYS */;
INSERT INTO `group_details` VALUES (1,'Achievers',5000),(2,'Alliancè',5000),(3,'Junior Group',5000),(10,'Chacha Foundation',5000),(11,'',5000);
/*!40000 ALTER TABLE `group_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loans`
--

DROP TABLE IF EXISTS `loans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loans` (
  `loan_code` int NOT NULL AUTO_INCREMENT,
  `loan_amount` varchar(45) NOT NULL,
  `repay_period` int NOT NULL,
  `id_num` varchar(45) DEFAULT NULL,
  `group_name` varchar(45) DEFAULT NULL,
  `time` date NOT NULL DEFAULT (curdate()),
  PRIMARY KEY (`loan_code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loans`
--

LOCK TABLES `loans` WRITE;
/*!40000 ALTER TABLE `loans` DISABLE KEYS */;
INSERT INTO `loans` VALUES (5,'6000',12,'45678',NULL,'2022-11-07');
/*!40000 ALTER TABLE `loans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `id_number` varchar(45) NOT NULL,
  `FName` varchar(45) NOT NULL,
  `LName` varchar(45) NOT NULL,
  `Gender` varchar(45) NOT NULL,
  `DOB` varchar(45) NOT NULL,
  `group_name` varchar(45) DEFAULT NULL,
  `reg_fee` int DEFAULT NULL,
  PRIMARY KEY (`id_number`),
  UNIQUE KEY `id_number_UNIQUE` (`id_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES ('09876','John','Chacha','Male','1990','',NULL),('100','Angela','Mwise','Female','1990','',NULL),('123456','Kris','nyaga','Female','1991','',NULL),('1789','Sallie','Mwongeli','Female','2004',NULL,2000),('20409242','Faith','Mweng\'e','Female','1990','',NULL),('213456','Fiona','Bundi','Female','2003','',NULL),('21924020','Felicia','Okeyo','Female','1996','',NULL),('234345','Felix','Orwa','Female','1995','Chacha Foundation',NULL),('27496066','Beatrix','katana','Female','2004','',NULL),('30209242','Esther','Nyumu','Female','2019','',NULL),('3345656','Angela','Chacha','Female','1995',NULL,NULL),('3445534','Winnie','Mbusiro','Female','1995','Chacha Foundation',NULL),('40209242','Angela','Chacha','Female','2002','',NULL),('45678','Robi','Chacha','Female','1990','',NULL),('48304272','Susan','Mwise','Male','2003','',NULL),('8486087','Sarah','Boke','Female','1996','',NULL);
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `repayments`
--

DROP TABLE IF EXISTS `repayments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repayments` (
  `id_repay` int NOT NULL AUTO_INCREMENT,
  `loan_id` int NOT NULL,
  `installment` int NOT NULL,
  `amount` int NOT NULL,
  `interest` int NOT NULL,
  `penalties` varchar(45) DEFAULT NULL,
  `time` date NOT NULL DEFAULT (curdate()),
  PRIMARY KEY (`id_repay`),
  KEY `fk_loan_id_idx` (`loan_id`),
  CONSTRAINT `fk_loan_id` FOREIGN KEY (`loan_id`) REFERENCES `loans` (`loan_code`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repayments`
--

LOCK TABLES `repayments` WRITE;
/*!40000 ALTER TABLE `repayments` DISABLE KEYS */;
INSERT INTO `repayments` VALUES (2,5,1,500,600,'0','2022-11-07'),(3,5,2,500,600,'0','2022-11-07');
/*!40000 ALTER TABLE `repayments` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-10  8:52:10
