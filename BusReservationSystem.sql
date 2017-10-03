CREATE DATABASE  IF NOT EXISTS `brs` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `brs`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: brs
-- ------------------------------------------------------
-- Server version	5.7.17-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bus`
--

DROP TABLE IF EXISTS `bus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `routeid` int(11) NOT NULL,
  `ac` tinyint(1) NOT NULL,
  `fare` int(11) NOT NULL,
  `availableseats` int(11) NOT NULL,
  `departuretime` varchar(6) NOT NULL,
  `arrivaltime` varchar(6) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `routeid` (`routeid`),
  CONSTRAINT `bus_ibfk_1` FOREIGN KEY (`routeid`) REFERENCES `route` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bus`
--

LOCK TABLES `bus` WRITE;
/*!40000 ALTER TABLE `bus` DISABLE KEYS */;
INSERT INTO `bus` VALUES (100,1,1,60,40,'8AM','4 PM'),(101,2,1,90,35,'8AM','8 PM'),(102,1,0,40,40,'8AM','6 PM'),(103,1,0,30,30,'5AM','12 PM'),(104,2,0,60,40,'8AM','4 PM'),(105,3,1,60,40,'8AM','4 PM'),(106,3,0,50,40,'8AM','4 PM'),(107,4,1,60,40,'8AM','4 PM'),(108,4,0,50,40,'8AM','4 PM'),(109,5,1,60,40,'8AM','4 PM'),(110,5,0,50,40,'8AM','4 PM'),(111,6,1,60,40,'8AM','4 PM'),(112,6,0,50,40,'8AM','4 PM'),(113,7,1,60,40,'8AM','4 PM'),(114,7,0,50,40,'8AM','4 PM'),(115,8,0,50,40,'8AM','4 PM'),(116,8,1,60,40,'8AM','4 PM'),(117,1,1,25,40,'2AM','10 AM'),(118,9,1,60,40,'8AM','4 PM');
/*!40000 ALTER TABLE `bus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `passenger` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `email` varchar(32) NOT NULL,
  `mobile` int(11) NOT NULL,
  `tickets` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=500 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger`
--

LOCK TABLES `passenger` WRITE;
/*!40000 ALTER TABLE `passenger` DISABLE KEYS */;
/*!40000 ALTER TABLE `passenger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reserve`
--

DROP TABLE IF EXISTS `reserve`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reserve` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `passengerid` int(11) NOT NULL,
  `busid` int(11) NOT NULL,
  `dt` date NOT NULL,
  `tstamp` time NOT NULL,
  `seat` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `SEAT_UNIQUE` (`busid`,`dt`,`seat`),
  KEY `passengerid` (`passengerid`),
  CONSTRAINT `reserve_ibfk_1` FOREIGN KEY (`passengerid`) REFERENCES `passenger` (`id`),
  CONSTRAINT `reserve_ibfk_2` FOREIGN KEY (`busid`) REFERENCES `bus` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reserve`
--

LOCK TABLES `reserve` WRITE;
/*!40000 ALTER TABLE `reserve` DISABLE KEYS */;
/*!40000 ALTER TABLE `reserve` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `route` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `origin` varchar(20) NOT NULL,
  `destination` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ROUTE_UNIQUE` (`origin`,`destination`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
INSERT INTO `route` VALUES (9,'austin','dallas'),(6,'boston','rochester'),(10,'chicago','new york city'),(4,'chicago','rochester'),(8,'dallas','austin'),(7,'miami','orlando'),(3,'new york city','rochester'),(2,'rochester','boston'),(5,'rochester','chicago'),(1,'rochester','new york city');
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'brs'
--

--
-- Dumping routines for database 'brs'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-04-02 21:15:41
