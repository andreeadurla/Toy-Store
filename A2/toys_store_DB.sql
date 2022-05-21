-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: toys_store
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `id` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_c0r9atamxvbhjjvy5j8da1kam` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('1','george@shop.com','George','Pop','George123!'),('2','mihai@shop.com','Mihai','Anghel','Mihai321@');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` varchar(255) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('1',_binary '\0',NULL,'All products'),('2ed3facf-28d9-4d86-838c-486da4998f47',_binary '\0','Puzzle pentru toate varstele','Puzzle'),('df6c33e3-6763-49e4-bd02-e6e3cc0bdf03',_binary '\0','Lego creativ','Lego'),('fbc388f7-0ac9-40e4-824c-801012208f8f',_binary '\0','Diverse mini figurine','Figurine');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `id` varchar(255) NOT NULL,
  `creation_date` datetime NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bfgjs3fem0hmjhvih80158x29` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES ('11a59269-be12-472b-9a34-fc7a0f292410','2021-04-28 21:54:33','ana@email.com','Ana','Popescu','Anapopescu1@','0756453987',0),('299e6ead-830c-437b-b888-adb1efd48f95','2021-04-28 22:02:52','ioana@yahoo.com','Ioana','Manta','Ioana11@','0789546789',1);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_order`
--

DROP TABLE IF EXISTS `client_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client_order` (
  `id` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `delivery_method` int NOT NULL,
  `observation` varchar(255) DEFAULT NULL,
  `payment_method` int NOT NULL,
  `price` float NOT NULL,
  `shipping_cost` float DEFAULT NULL,
  `status` int NOT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `delivery_address_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcggjtc9f3otuprl7takbttwuk` (`client_id`),
  KEY `FKt9gomm5yc0xgsq87o8g86hsa` (`delivery_address_id`),
  CONSTRAINT `FKcggjtc9f3otuprl7takbttwuk` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  CONSTRAINT `FKt9gomm5yc0xgsq87o8g86hsa` FOREIGN KEY (`delivery_address_id`) REFERENCES `delivery_address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_order`
--

LOCK TABLES `client_order` WRITE;
/*!40000 ALTER TABLE `client_order` DISABLE KEYS */;
INSERT INTO `client_order` VALUES ('682617e9-3983-4171-aa36-a780389a6730','2021-04-29 09:19:39',1,'',1,47.21,0,0,'299e6ead-830c-437b-b888-adb1efd48f95','cab244f6-e300-4708-a2ba-5cb778885997'),('81a450cd-01dc-4c83-a464-ee8fff0719e7','2021-04-28 22:46:32',0,'',0,614.3,0,0,'11a59269-be12-472b-9a34-fc7a0f292410','a4694510-1626-4064-86e3-a55b7cde9d10'),('a093e333-695e-43f4-9d21-c7307a7b0b0e','2021-04-28 22:23:11',1,'Doresc livrare comenzii in intervalul orar 10:00 - 16:00. Multumesc!',0,2889.91,0,0,'299e6ead-830c-437b-b888-adb1efd48f95','cab244f6-e300-4708-a2ba-5cb778885997');
/*!40000 ALTER TABLE `client_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_address`
--

DROP TABLE IF EXISTS `delivery_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `delivery_address` (
  `id` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `county` varchar(255) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6wpj8dnfkasr93prrfsfwtl5r` (`client_id`),
  CONSTRAINT `FK6wpj8dnfkasr93prrfsfwtl5r` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_address`
--

LOCK TABLES `delivery_address` WRITE;
/*!40000 ALTER TABLE `delivery_address` DISABLE KEYS */;
INSERT INTO `delivery_address` VALUES ('90021c49-c018-4922-b4b3-21959ac4d1b9','Strada Frunzei, Nr. 23','Craiova','Dolj',_binary '\0','Ioana','Manta','0796785678','299e6ead-830c-437b-b888-adb1efd48f95'),('99beb5c5-9774-47c6-a346-204d0d11135e','Strada Merelor, Nr. 64','Craiova','Dolj',_binary '\0','Ioana','Manta','0796785678','299e6ead-830c-437b-b888-adb1efd48f95'),('a4694510-1626-4064-86e3-a55b7cde9d10','Strada Observatorului, Bl. 3A, Sc. 2, Ap. 12','Cluj-Napoca','Cluj',_binary '\0','Ana','Popescu','0756987612','11a59269-be12-472b-9a34-fc7a0f292410'),('cab244f6-e300-4708-a2ba-5cb778885997','Strada Muresului, Bl. 3, Sc. 2, Ap. 56','Targu-Jiu','Gorj',_binary '\0','George','Manta','0756439876','299e6ead-830c-437b-b888-adb1efd48f95');
/*!40000 ALTER TABLE `delivery_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` varchar(255) NOT NULL,
  `quantity` int NOT NULL,
  `total_price` float NOT NULL,
  `unit_price` float NOT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK551losx9j75ss5d6bfsqvijna` (`product_id`),
  KEY `FK9dm854t3ybtcsv86ro5lrin21` (`order_id`),
  CONSTRAINT `FK551losx9j75ss5d6bfsqvijna` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FK9dm854t3ybtcsv86ro5lrin21` FOREIGN KEY (`order_id`) REFERENCES `client_order` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES ('3cea40da-671f-4c71-93c3-40cb439474c7',1,459.99,459.99,'81a450cd-01dc-4c83-a464-ee8fff0719e7','62de7524-e4e2-41a4-8c2d-e1bba0c103c0'),('49422e97-6665-4c76-8a9f-1aecbecd9fd3',3,2603.97,867.99,'a093e333-695e-43f4-9d21-c7307a7b0b0e','f4055fee-1cf6-41d6-b447-404f027d617b'),('494de607-27bd-43fa-9aa8-7a7428961f31',5,226.05,45.21,'a093e333-695e-43f4-9d21-c7307a7b0b0e','4c757f4d-493a-4289-a955-d83149d8df15'),('60f39cbb-f328-4bb5-a8d1-dcd907bf7861',1,59.89,59.89,'81a450cd-01dc-4c83-a464-ee8fff0719e7','6b236c2f-a2d3-47f2-bab9-adeaeacc2953'),('a7924a23-4565-4a8a-a6cf-9d34d4c4fda2',1,59.89,59.89,'a093e333-695e-43f4-9d21-c7307a7b0b0e','6b236c2f-a2d3-47f2-bab9-adeaeacc2953'),('b058c07d-0481-4f85-9d75-88c002cf1e78',1,47.21,47.21,'682617e9-3983-4171-aa36-a780389a6730','4c757f4d-493a-4289-a955-d83149d8df15'),('f9993283-44ab-4b0a-81cf-ab28cf99b72f',2,94.42,47.21,'81a450cd-01dc-4c83-a464-ee8fff0719e7','4c757f4d-493a-4289-a955-d83149d8df15');
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` varchar(255) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `price` float NOT NULL,
  `quantity` int NOT NULL,
  `category_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jmivyxk9rmgysrmsqw15lqr5b` (`name`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES ('4c757f4d-493a-4289-a955-d83149d8df15',_binary '\0','Fiecare set contine 2 mini-figurine','Star Wars figurine',47.21,3,'1'),('62de7524-e4e2-41a4-8c2d-e1bba0c103c0',_binary '\0','Mini elicopter pentru copii','Elicopter cu telecomanda',459.99,13,'1'),('6b236c2f-a2d3-47f2-bab9-adeaeacc2953',_binary '\0','','Puzzle 3D - Castelul Peles',59.89,14,'2ed3facf-28d9-4d86-838c-486da4998f47'),('f4055fee-1cf6-41d6-b447-404f027d617b',_binary '\0','Contine 12452 piese','NASA Nava spatiala',867.99,7,'df6c33e3-6763-49e4-bd02-e6e3cc0bdf03');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shopping_cart_item`
--

DROP TABLE IF EXISTS `shopping_cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `shopping_cart_item` (
  `id` varchar(255) NOT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `quantity` int NOT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4bmjhb2g0ex5p19e8g0kajaau` (`client_id`),
  KEY `FKnqunkunvnryn6k78joe6n7yxr` (`product_id`),
  CONSTRAINT `FK4bmjhb2g0ex5p19e8g0kajaau` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  CONSTRAINT `FKnqunkunvnryn6k78joe6n7yxr` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shopping_cart_item`
--

LOCK TABLES `shopping_cart_item` WRITE;
/*!40000 ALTER TABLE `shopping_cart_item` DISABLE KEYS */;
INSERT INTO `shopping_cart_item` VALUES ('0524339a-2013-4d1c-b478-bfcd8902da8f',_binary '\0',2,'11a59269-be12-472b-9a34-fc7a0f292410','6b236c2f-a2d3-47f2-bab9-adeaeacc2953'),('246ee0bc-3ba1-4ce9-b8a6-3369e57895a9',_binary '\0',1,'11a59269-be12-472b-9a34-fc7a0f292410','62de7524-e4e2-41a4-8c2d-e1bba0c103c0'),('bf9a99a6-a427-4d42-bdcc-39c8a504af6b',_binary '\0',1,'11a59269-be12-472b-9a34-fc7a0f292410','4c757f4d-493a-4289-a955-d83149d8df15');
/*!40000 ALTER TABLE `shopping_cart_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-29  9:26:09
