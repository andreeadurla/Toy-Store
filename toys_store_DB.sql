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
INSERT INTO `admin` VALUES ('1','george@shop.com','George','Pop','$2a$10$0aISzamI0jBCVTxONzJlHOk7O7QS.XPFIheLVhXultVa9Ju7SarZ6'),('2','mihai@shop.com','Mihai','Anghel','$2a$10$0aISzamI0jBCVTxONzJlHOk7O7QS.XPFIheLVhXultVa9Ju7SarZ6');
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
INSERT INTO `category` VALUES ('00a83398-61e6-486d-ba40-241fcefa75ce',_binary '\0','Lego creeaza produse adresate tuturor varstelor','Lego'),('1',_binary '\0',NULL,'All products'),('67ad066b-f4b6-43b5-a043-661f5fda356d',_binary '\0','','Papusi'),('7782f109-ba6a-4dfb-b7f0-90d4d09c8af2',_binary '\0','Diverse mini figurine','Figurine'),('aa1562be-35c9-46aa-8106-dc2a1606eb76',_binary '\0','','Puzzle');
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
INSERT INTO `client` VALUES ('4ee53d35-fe90-4274-bd34-bf7a2c538697','2021-05-23 14:02:54','ana@email.com','Ana','Popescu','$2a$10$7MqjPxjr7H5ra20WepTcI.mhdWzg7p0gmktvpNv.3OrdcVhUkZ/xS','',0),('cc3d9976-1cbe-4dcd-9879-43fcf96423ae','2021-05-23 13:59:38','alexandru@email.com','Alexandru','Ionescu','$2a$10$dbxjW9QNu8puUr2O7j6Rl.lCln5OdigMmWmgCGHMiPr79ZNlGeTVW','0789657867',0),('fadbc8c6-86fb-4b7e-b7c2-676f809d4e95','2021-05-23 14:04:48','ioana@yahoo.com','Ioana','Manta','$2a$10$KXq8vdgLC2CcSH5cHBelOOpvVaMZtO9AoY/zo/CF1j99taffF3Ja6','',1);
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
  `paid` bit(1) NOT NULL,
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
INSERT INTO `client_order` VALUES ('2cb6de96-3bb9-47f6-98d6-f8da6098260a','2021-05-23 14:14:48',1,'Livrarea sa fie realizata in intervalul orar 10:00 - 16:00. Multumesc pentru intelegere!',_binary '',0,856.45,0,0,'cc3d9976-1cbe-4dcd-9879-43fcf96423ae','7f00623d-cc06-4a12-894c-5a2a4696a315'),('2fb4e2b4-c11a-44c6-b585-6bc55b69c891','2021-05-25 20:56:33',1,'',_binary '',0,183.99,0,0,'4ee53d35-fe90-4274-bd34-bf7a2c538697','6969b672-8847-4650-abeb-f4bb8bacdf6f'),('5839c635-3523-4712-a03a-66cbf2d5e4ab','2021-05-23 14:49:44',1,'',_binary '',0,824.88,0,0,'4ee53d35-fe90-4274-bd34-bf7a2c538697','6969b672-8847-4650-abeb-f4bb8bacdf6f'),('ae24feb5-f043-405e-8a23-1fa22212cb5d','2021-05-23 15:00:49',1,'',_binary '',0,183.99,0,0,'4ee53d35-fe90-4274-bd34-bf7a2c538697','6969b672-8847-4650-abeb-f4bb8bacdf6f'),('d94c8f4e-322f-4ecf-b5f8-aab5d2fd4aa6','2021-05-23 14:47:31',1,'',_binary '\0',1,459.99,0,0,'cc3d9976-1cbe-4dcd-9879-43fcf96423ae','6b35ff9c-74a8-4f70-8ae1-989bb8c0aa56'),('f8a38408-76f1-47bf-a66b-6037c048eaee','2021-05-23 14:20:08',1,'',_binary '',0,2000,0,0,'4ee53d35-fe90-4274-bd34-bf7a2c538697','6969b672-8847-4650-abeb-f4bb8bacdf6f');
/*!40000 ALTER TABLE `client_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit_card`
--

DROP TABLE IF EXISTS `credit_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `credit_card` (
  `id` varchar(255) NOT NULL,
  `cvv` varchar(255) NOT NULL,
  `expiration_date` datetime NOT NULL,
  `name` varchar(255) NOT NULL,
  `no_card` varchar(255) NOT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_t0l4l6q40e5xm3ph0qd10kcw0` (`no_card`),
  KEY `FKhn150y37siyvv3oii4q8mo9q5` (`client_id`),
  CONSTRAINT `FKhn150y37siyvv3oii4q8mo9q5` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_card`
--

LOCK TABLES `credit_card` WRITE;
/*!40000 ALTER TABLE `credit_card` DISABLE KEYS */;
INSERT INTO `credit_card` VALUES ('17cff330-a0b7-49c7-8c7c-4cb23ecf3a5b','189','2021-12-01 00:00:00','Ana Popescu','1987890768976561','4ee53d35-fe90-4274-bd34-bf7a2c538697'),('1a2ac469-a257-499d-ba72-91eb640c5b46','145','2021-12-01 00:00:00','Alexandru Ionescu','1111345678234321','cc3d9976-1cbe-4dcd-9879-43fcf96423ae'),('d61404ae-7818-4740-ad1a-1884cd39c249','198','2021-08-01 00:00:00','Alexandru Ionescu','1435672345342134','cc3d9976-1cbe-4dcd-9879-43fcf96423ae');
/*!40000 ALTER TABLE `credit_card` ENABLE KEYS */;
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
INSERT INTO `delivery_address` VALUES ('6969b672-8847-4650-abeb-f4bb8bacdf6f','Strada Muresului, Bl. 3, Sc. 2, Ap. 56','Craiova','Dolj',_binary '\0','Ana','Popescu','0765895678','4ee53d35-fe90-4274-bd34-bf7a2c538697'),('6b35ff9c-74a8-4f70-8ae1-989bb8c0aa56','Strada Frunzei, Nr. 23','Craiova','Dolj',_binary '\0','Ana','Ionescu','0789654567','cc3d9976-1cbe-4dcd-9879-43fcf96423ae'),('7f00623d-cc06-4a12-894c-5a2a4696a315','Strada Observatorului, Bl. 3A, Sc. 2, Ap. 12','Cluj-Napoca','Cluj',_binary '\0','Alexandru','Ionescu','0765789876','cc3d9976-1cbe-4dcd-9879-43fcf96423ae');
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
INSERT INTO `order_item` VALUES ('2db9d957-1cf6-4c84-b44b-1aaa1ff9f05b',1,459.99,459.99,'d94c8f4e-322f-4ecf-b5f8-aab5d2fd4aa6','6cc77ec9-3ef9-4d2b-9ce6-93f1e0fff772'),('71b88362-2d2a-47e2-b8bd-c13c2bea4c7d',1,183.99,183.99,'2fb4e2b4-c11a-44c6-b585-6bc55b69c891','232b222d-3dc8-413c-8cbc-ac251fbbf782'),('86548d1f-9edb-45f7-a1a9-9aba04278c0f',1,764.89,764.89,'5839c635-3523-4712-a03a-66cbf2d5e4ab','2162a52c-2018-400e-a871-cc56742cb29e'),('9b892996-c2da-4bea-a5b3-19028b7ee356',1,59.99,59.99,'5839c635-3523-4712-a03a-66cbf2d5e4ab','52bdadfd-1329-4d04-8e17-1904a1eb151a'),('c8e1a921-c874-4647-8db7-c5c32652e0f1',1,764.89,764.89,'2cb6de96-3bb9-47f6-98d6-f8da6098260a','2162a52c-2018-400e-a871-cc56742cb29e'),('d4c233cf-d1ca-4862-9d3b-287089ed8cb0',1,2000,2000,'f8a38408-76f1-47bf-a66b-6037c048eaee','6acf2c07-1ff6-4091-853e-135a2fb41f59'),('dce32dfa-ab92-43d1-afe5-608652a3f3a4',2,91.56,45.78,'2cb6de96-3bb9-47f6-98d6-f8da6098260a','e34cf68c-e899-450b-bb5e-e1ebe1d81c23'),('eb5a96a1-eb1d-4315-9ae4-6ae737b25681',1,183.99,183.99,'ae24feb5-f043-405e-8a23-1fa22212cb5d','232b222d-3dc8-413c-8cbc-ac251fbbf782');
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
  `imageurl` varchar(255) DEFAULT NULL,
  `last_sale` datetime DEFAULT NULL,
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
INSERT INTO `product` VALUES ('2162a52c-2018-400e-a871-cc56742cb29e',_binary '\0','Contine 2354 piese. Varsta recomandata 16+.','https://brickdepot.ro/images/10283-LEGO-CREATOR-EXPERT-10.JPG',NULL,'NASA Naveta Spatiala Discovery',764.89,2,'00a83398-61e6-486d-ba40-241fcefa75ce'),('232b222d-3dc8-413c-8cbc-ac251fbbf782',_binary '\0','Maia se pregateste sa te incante cu un uimitor spectacol de balet!','https://noriel.ro/media/catalog/product/cache/c682e6120e627a95f4ce21a5b384118b/i/n/int3244_001w_papusa_maia_balerina_blonda_4_.jpg','2021-05-25 20:56:33','Papusa Maia Balerina',183.99,5,'67ad066b-f4b6-43b5-a043-661f5fda356d'),('52bdadfd-1329-4d04-8e17-1904a1eb151a',_binary '\0','Puzzle tridimensional cu 85 de piese.','https://www.auchan.ro/public/images/h82/hbb/h00/noriel-puzzle-3d-castelul-bran-8872376303646.jpg','2021-05-23 14:49:44','Puzzle 3D - Castelul Bran',59.99,7,'aa1562be-35c9-46aa-8106-dc2a1606eb76'),('6acf2c07-1ff6-4091-853e-135a2fb41f59',_binary '\0','Contine 6020 piese. Varsta recomandata 18+.','https://www.lego.com/cdn/cs/set/assets/blt2c408dc4fb192074/71043.jpg?fit=bounds&format=jpg&quality=80&width=1600&height=1600&dpr=1','2021-05-23 14:20:08','Castelul Hogwarts',2000,4,'00a83398-61e6-486d-ba40-241fcefa75ce'),('6cc77ec9-3ef9-4d2b-9ce6-93f1e0fff772',_binary '\0','Mini elicopter pentru copii','https://s13emagst.akamaized.net/products/27349/27348046/images/res_946f6190c88e946e24d579ad3e72c3f9.jpg','2021-05-23 14:47:00','Elicopter cu telecomanda',479.99,3,'1'),('9c44fe4b-63c7-441e-82ed-e19d0d981689',_binary '\0','Creaza coafuri personalizate cu L.O.L. Surprise #Hairvibes!','https://s13emagst.akamaized.net/products/28338/28337321/images/res_3d72e145d79528ed5c580715a25f719e.jpg',NULL,'Papusa LOL Surprise Hairvibes',99.87,9,'67ad066b-f4b6-43b5-a043-661f5fda356d'),('e34cf68c-e899-450b-bb5e-e1ebe1d81c23',_binary '\0','Fiecare set contine 5 mini-figurine','https://sarahonline.ro/wp-content/uploads/2020/06/image-2381.jpeg','2021-05-23 14:53:00','Star Wars figurine',45.78,13,'7782f109-ba6a-4dfb-b7f0-90d4d09c8af2');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review_product`
--

DROP TABLE IF EXISTS `review_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review_product` (
  `id` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `review` varchar(255) NOT NULL,
  `client_id` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpj3957vf76h7dla3dhgmkp698` (`client_id`),
  KEY `FK2epbpsjll2ucuabh1dn26ivtq` (`product_id`),
  CONSTRAINT `FK2epbpsjll2ucuabh1dn26ivtq` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKpj3957vf76h7dla3dhgmkp698` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review_product`
--

LOCK TABLES `review_product` WRITE;
/*!40000 ALTER TABLE `review_product` DISABLE KEYS */;
INSERT INTO `review_product` VALUES ('26477c71-cfce-4cef-84d3-db914f8f382e','2021-05-23 14:16:15','Cred ca acest set lego merge foarte bine la colecția mea. Imi place seria LEGO NASA.','cc3d9976-1cbe-4dcd-9879-43fcf96423ae','2162a52c-2018-400e-a871-cc56742cb29e'),('33a2ce4d-50e1-46f5-843f-c6c2a05feabc','2021-05-23 14:17:54','Figurine frumoase, insa calitatea materialelor nu este una foarte buna.','cc3d9976-1cbe-4dcd-9879-43fcf96423ae','e34cf68c-e899-450b-bb5e-e1ebe1d81c23'),('a63b40ec-21eb-402f-a9d3-269bfd68ae73','2021-05-25 20:55:15','Un set uimitor','4ee53d35-fe90-4274-bd34-bf7a2c538697','2162a52c-2018-400e-a871-cc56742cb29e'),('c5a34624-9c1c-4e57-abc4-ee075343fd45','2021-05-23 14:48:46','Frumos!\r\nSunt un fan Harry Potter si mi l-am dorit mult.','4ee53d35-fe90-4274-bd34-bf7a2c538697','6acf2c07-1ff6-4091-853e-135a2fb41f59');
/*!40000 ALTER TABLE `review_product` ENABLE KEYS */;
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
INSERT INTO `shopping_cart_item` VALUES ('1b390b13-87c1-4d91-8152-076fc68b6b83',_binary '\0',1,'cc3d9976-1cbe-4dcd-9879-43fcf96423ae','52bdadfd-1329-4d04-8e17-1904a1eb151a');
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

-- Dump completed on 2021-05-25 21:00:12
