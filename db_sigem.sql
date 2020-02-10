-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: db_sigem
-- ------------------------------------------------------
-- Server version	5.6.17

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
-- Table structure for table `emprestimo`
--

DROP TABLE IF EXISTS `emprestimo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emprestimo` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `num_protocolo` bigint(20) NOT NULL,
  `solicitante_codigo` bigint(20) NOT NULL,
  `usuario_codigo` bigint(20) NOT NULL,
  `is_ativo` bit(1) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UK_9jd4ogprstlg26d9xy8vxcldr` (`num_protocolo`),
  KEY `FK_6r5evg9og14uptidqcshn9rgs` (`solicitante_codigo`),
  KEY `FK_qyxnxv43f4w71cq3c78k17v7m` (`usuario_codigo`),
  CONSTRAINT `FK_6r5evg9og14uptidqcshn9rgs` FOREIGN KEY (`solicitante_codigo`) REFERENCES `solicitante` (`codigo`),
  CONSTRAINT `FK_qyxnxv43f4w71cq3c78k17v7m` FOREIGN KEY (`usuario_codigo`) REFERENCES `usuario` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emprestimo`
--

LOCK TABLES `emprestimo` WRITE;
/*!40000 ALTER TABLE `emprestimo` DISABLE KEYS */;
INSERT INTO `emprestimo` VALUES (1,20171,1,1,''),(2,20172,2,1,''),(3,20173,1,1,''),(4,20174,3,2,''),(5,20175,1,1,''),(6,20176,3,1,''),(7,20177,1,1,''),(8,20178,4,1,''),(9,20179,3,1,''),(10,201710,3,1,''),(11,201711,1,1,''),(12,201712,5,1,''),(13,201713,6,1,''),(14,2017551538,6,1,''),(15,2018168498,1,2,''),(16,2018666393,1,3,''),(17,2018338818,4,5,'\0'),(18,2018735750,4,1,'\0');
/*!40000 ALTER TABLE `emprestimo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emprestimo_has_equipamento`
--

DROP TABLE IF EXISTS `emprestimo_has_equipamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emprestimo_has_equipamento` (
  `data_hora_devolucao_efetiva` datetime DEFAULT NULL,
  `data_hora_devolucao_prevista` datetime DEFAULT NULL,
  `data_hora_entrega` datetime DEFAULT NULL,
  `devolvido` bit(1) NOT NULL,
  `equipamento_codigo` bigint(20) NOT NULL,
  `emprestimo_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`equipamento_codigo`,`emprestimo_codigo`),
  KEY `FK_m66959ay1ghcwds5x1v4c543u` (`emprestimo_codigo`),
  CONSTRAINT `FK_iho8dewj0gnbed0d45ww16vwg` FOREIGN KEY (`equipamento_codigo`) REFERENCES `equipamento` (`codigo`),
  CONSTRAINT `FK_m66959ay1ghcwds5x1v4c543u` FOREIGN KEY (`emprestimo_codigo`) REFERENCES `emprestimo` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emprestimo_has_equipamento`
--

LOCK TABLES `emprestimo_has_equipamento` WRITE;
/*!40000 ALTER TABLE `emprestimo_has_equipamento` DISABLE KEYS */;
INSERT INTO `emprestimo_has_equipamento` VALUES (NULL,'2017-10-26 00:00:00','2017-10-25 14:05:16','',1,3),(NULL,'2017-10-26 00:00:00','2017-10-25 14:07:54','',1,4),('2017-11-09 14:56:28','2017-11-10 00:00:00','2017-11-09 11:14:57','',1,5),('2017-11-09 15:15:30','2017-11-10 00:00:00','2017-11-09 11:30:02','',1,6),(NULL,'2017-11-10 12:28:16','2017-11-09 12:28:25','',1,7),('2017-11-09 15:03:46','2017-11-10 14:57:49','2017-11-09 14:58:00','',1,8),('2017-11-09 16:09:12','2017-11-10 16:06:48','2017-11-09 16:07:21','',1,9),('2017-11-09 22:40:28','2017-11-10 22:38:45','2017-11-09 22:39:01','',1,10),('2017-11-10 15:02:19','2017-11-10 22:40:36','2017-11-09 22:40:45','',1,11),('2017-12-11 10:17:05','2017-11-11 15:13:29','2017-11-10 15:14:08','',1,12),('2017-12-11 15:19:55','2017-12-12 14:56:16','2017-12-11 14:57:18','',1,13),('2018-02-26 10:54:13','2018-02-19 13:05:45','2018-02-19 13:09:46','',1,17),(NULL,'2017-10-25 00:00:00','2017-10-24 17:19:05','',2,1),(NULL,'2017-10-26 00:00:00','2017-10-25 09:29:19','',2,2),('2017-11-09 15:03:46','2017-11-10 14:57:49','2017-11-09 14:58:01','',2,8),('2017-11-09 16:09:12','2017-11-10 16:06:48','2017-11-09 16:07:21','',2,9),('2017-11-09 22:40:28','2017-11-10 22:38:45','2017-11-09 22:39:01','',2,10),('2017-11-10 15:02:19','2017-11-10 22:40:36','2017-11-09 22:40:45','',2,11),('2017-12-11 10:17:05','2017-11-11 15:13:29','2017-11-10 15:14:08','',2,12),('2017-12-11 15:19:55','2017-12-12 14:56:16','2017-12-11 14:57:17','',2,13),('2018-02-17 15:42:41','2017-12-12 15:20:04','2017-12-11 15:20:14','',2,14),(NULL,'2018-02-18 23:45:24','2018-02-17 23:45:42','',2,15),('2018-02-26 10:54:13','2018-02-19 13:05:45','2018-02-19 13:09:46','',2,17),('2017-11-09 16:09:12','2017-11-10 16:06:48','2017-11-09 16:07:21','',3,9),('2017-11-09 22:40:28','2017-11-10 22:38:45','2017-11-09 22:39:02','',3,10),('2017-11-10 15:02:19','2017-11-10 22:40:36','2017-11-09 22:40:46','',3,11),('2017-12-11 10:17:05','2017-11-11 15:13:29','2017-11-10 15:14:08','',3,12),('2017-12-11 15:19:55','2017-12-12 14:56:16','2017-12-11 14:57:17','',3,13),('2018-02-26 10:54:22','2018-02-19 13:25:21','2018-02-19 13:25:28','',4,18),('2018-02-26 10:54:32','2018-02-19 09:43:25','2018-02-18 09:43:57','',8,16);
/*!40000 ALTER TABLE `emprestimo_has_equipamento` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `db_sigem`.`emprestimo_has_equipamento_AFTER_INSERT` AFTER INSERT ON `emprestimo_has_equipamento` FOR EACH ROW
BEGIN
	IF (NEW.equipamento_codigo IS NOT NULL) THEN
		UPDATE `equipamento` SET `equipamento`.`status` = 'Emprestado' WHERE NEW.equipamento_codigo = `equipamento`.`codigo`;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `db_sigem`.`emprestimo_has_equipamento_AFTER_UPDATE` 
AFTER UPDATE ON `emprestimo_has_equipamento` FOR EACH ROW
BEGIN
	IF (NEW.devolvido = 1) THEN
		UPDATE `equipamento` SET `status` = 'Disponível' WHERE `equipamento`.`codigo` = NEW.`equipamento_codigo`;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `equipamento`
--

DROP TABLE IF EXISTS `equipamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipamento` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  `num_tombamento` bigint(20) NOT NULL,
  `status` varchar(15) NOT NULL,
  `tipo` varchar(50) NOT NULL,
  `is_ativo` bit(1) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UK_q4xl1rm581mg0r5m2r2i5b7w7` (`num_tombamento`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipamento`
--

LOCK TABLES `equipamento` WRITE;
/*!40000 ALTER TABLE `equipamento` DISABLE KEYS */;
INSERT INTO `equipamento` VALUES (1,'Caixa de Som',201712345,'Disponível','Caixa de Som',''),(2,'DataShow 01',201754321,'Disponível','DataShow',''),(3,'Microfone 01',201734564,'Manutenção','Microfone',''),(4,'Microfone 02',20173456,'Disponível','Microfone',''),(8,'DataShow 02',201754322,'Disponível','DataShow','');
/*!40000 ALTER TABLE `equipamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `material`
--

DROP TABLE IF EXISTS `material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `material` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(100) NOT NULL,
  `quantidade` bigint(20) NOT NULL,
  `unidade` varchar(20) NOT NULL,
  `valor` decimal(6,2) DEFAULT NULL,
  `is_ativo` bit(1) DEFAULT NULL,
  `quantidade_maxima` bigint(20) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `material`
--

LOCK TABLES `material` WRITE;
/*!40000 ALTER TABLE `material` DISABLE KEYS */;
INSERT INTO `material` VALUES (1,'Caneta esferográfica preta',100,'Un',0.00,'',100),(2,'Caneta esferográfica azul',95,'Un',0.00,'',100),(3,'Bola de Futebol',100,'Un',0.00,'',100),(4,'Bola de Tênis de Mesa',100,'Un',0.00,'',100),(5,'Estilete de Corte',15,'Un',0.00,'',100),(6,'Fita Adesiva Dupla Face',97,'Un',0.00,'',100),(7,'Pincel Atômico Azul',98,'Un',0.00,'',100),(8,'Pincel Atômico Preto',16,'Un',0.00,'',100),(9,'Pincel Atômico Vermelho',100,'Un',0.00,'',100);
/*!40000 ALTER TABLE `material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requisicao`
--

DROP TABLE IF EXISTS `requisicao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requisicao` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `recebedor_codigo` bigint(20) NOT NULL,
  `solicitante_codigo` bigint(20) NOT NULL,
  `is_ativo` bit(1) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  KEY `FK_aej2crstoeleolbmd3wenn7mp` (`recebedor_codigo`),
  KEY `FK_4bd1c9ie6v53xldndg49uexl3` (`solicitante_codigo`),
  CONSTRAINT `FK_4bd1c9ie6v53xldndg49uexl3` FOREIGN KEY (`solicitante_codigo`) REFERENCES `solicitante` (`codigo`),
  CONSTRAINT `FK_aej2crstoeleolbmd3wenn7mp` FOREIGN KEY (`recebedor_codigo`) REFERENCES `solicitante` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requisicao`
--

LOCK TABLES `requisicao` WRITE;
/*!40000 ALTER TABLE `requisicao` DISABLE KEYS */;
INSERT INTO `requisicao` VALUES (23,4,6,''),(24,1,3,''),(25,4,4,''),(26,6,3,''),(27,3,1,''),(28,3,1,''),(29,6,3,''),(30,3,7,''),(31,5,1,''),(32,2,2,''),(34,5,7,''),(35,7,6,'\0'),(36,7,1,'\0');
/*!40000 ALTER TABLE `requisicao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requisicao_has_materiais`
--

DROP TABLE IF EXISTS `requisicao_has_materiais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requisicao_has_materiais` (
  `data_hora_entrega` date NOT NULL,
  `quantidade_entregue` int(11) DEFAULT NULL,
  `quantidade_requisitada` int(11) DEFAULT NULL,
  `requisicao_codigo` bigint(20) NOT NULL,
  `material_codigo` bigint(20) NOT NULL,
  PRIMARY KEY (`requisicao_codigo`,`material_codigo`),
  KEY `FK_aib36qhkx60axfi2cgimfn9dy` (`material_codigo`),
  CONSTRAINT `FK_aib36qhkx60axfi2cgimfn9dy` FOREIGN KEY (`material_codigo`) REFERENCES `material` (`codigo`),
  CONSTRAINT `FK_ojktky9l3i6vta4itllrv0t06` FOREIGN KEY (`requisicao_codigo`) REFERENCES `requisicao` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requisicao_has_materiais`
--

LOCK TABLES `requisicao_has_materiais` WRITE;
/*!40000 ALTER TABLE `requisicao_has_materiais` DISABLE KEYS */;
INSERT INTO `requisicao_has_materiais` VALUES ('2017-12-12',1,1,23,2),('2017-12-12',2,2,23,3),('2017-12-12',1,1,23,6),('2017-12-12',1,1,24,1),('2017-12-12',1,1,24,4),('2017-12-12',1,1,24,6),('2017-12-12',1,1,25,1),('2017-12-12',1,1,25,4),('2017-12-12',1,1,25,5),('2017-12-12',1,1,25,6),('2017-12-12',2,2,26,4),('2017-12-12',1,1,27,3),('2017-12-12',2,5,27,5),('2017-12-12',2,2,27,6),('2017-12-12',3,3,28,1),('2017-12-12',5,5,28,2),('2017-12-12',1,1,28,3),('2017-12-12',1,1,28,4),('2017-12-12',1,1,28,5),('2017-12-12',2,2,28,6),('2018-02-17',5,5,29,5),('2018-02-17',3,3,30,1),('2018-02-17',3,5,30,2),('2018-02-17',1,1,30,6),('2018-02-17',2,2,31,1),('2018-02-17',5,5,31,2),('2018-02-17',1,1,31,6),('2018-02-17',3,3,32,1),('2018-02-17',1,3,32,3),('2018-02-17',1,2,32,4),('2018-02-17',2,4,32,6),('2018-02-18',3,3,34,1),('2018-02-18',5,5,34,2),('2018-02-18',1,1,34,6),('2018-02-18',1,1,34,7),('2018-02-18',2,2,34,8),('2018-02-24',82,82,35,7),('2018-02-26',5,5,36,2),('2018-02-26',3,5,36,6),('2018-02-26',2,2,36,7),('2018-02-26',2,2,36,8);
/*!40000 ALTER TABLE `requisicao_has_materiais` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `db_sigem`.`requisicao_has_materiais_AFTER_INSERT` AFTER INSERT ON `requisicao_has_materiais` FOR EACH ROW
BEGIN
	IF (NEW.requisicao_codigo IS NOT NULL AND NEW.material_codigo IS NOT NULL) THEN
		UPDATE material SET material.quantidade = (material.quantidade - NEW.quantidade_entregue) WHERE material.codigo = NEW.material_codigo;
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `solicitante`
--

DROP TABLE IF EXISTS `solicitante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `solicitante` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `matricula` bigint(20) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `tipo` varchar(25) NOT NULL,
  `unidadeCurso_codigo` bigint(20) NOT NULL,
  `is_ativo` bit(1) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UK_89fa8jxxcyrae2u060pgtnv9m` (`matricula`),
  KEY `FK_4xd6ml5hnf8igv7fyemqdr4oe` (`unidadeCurso_codigo`),
  CONSTRAINT `FK_4xd6ml5hnf8igv7fyemqdr4oe` FOREIGN KEY (`unidadeCurso_codigo`) REFERENCES `unidade_curso` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `solicitante`
--

LOCK TABLES `solicitante` WRITE;
/*!40000 ALTER TABLE `solicitante` DISABLE KEYS */;
INSERT INTO `solicitante` VALUES (1,'correa@gmail.com',201445674356,'Janaína Correa','(99) 9 9999-8888','Aluno',26,''),(2,'jsampaiopa@gmail.com',201418640031,'Janilson Sampaio de Oliveira','(91) 9 8735-6593','Aluno',37,''),(3,'arthur.gabriel@gmail.com',2014324569089,'Arthur Gabriel','(91) 9 8988-7856','Funcionario',34,''),(4,'jean@jean.com',21023456578,'Jean da Silva e Silva','(22) 9 2222-2222','Docente',25,''),(5,'antonio@gmail.com',201210400324,'Antonio Antunes','(99) 9 9999-9999','Docente',25,''),(6,'almeida@almeida.com',2010324345,'Evailton Almeida','(99) 9 9999-9999','Docente',37,''),(7,'diego@diego.com',2014546787,'Diego Leal','(88) 9 8888-8888','Funcionario',18,'');
/*!40000 ALTER TABLE `solicitante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unidade_curso`
--

DROP TABLE IF EXISTS `unidade_curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unidade_curso` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo_unidade` bigint(20) DEFAULT NULL,
  `unidade_curso` varchar(70) NOT NULL,
  `is_ativo` bit(1) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UK_120kcyd10pyngl4gcw6e6kfsq` (`codigo_unidade`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unidade_curso`
--

LOCK TABLES `unidade_curso` WRITE;
/*!40000 ALTER TABLE `unidade_curso` DISABLE KEYS */;
INSERT INTO `unidade_curso` VALUES (1,123456,'FACULDADE DE COMPUTAÇÃO','\0'),(2,487204,'LADES','\0'),(3,45896,'LABORATÓRIO DE 40','\0'),(4,768909,'LABORATÓRIO DE PESQUISA','\0'),(5,459821,'FACULDADE DE MATEMÁTICA','\0'),(6,572165,'FACULDADE DE ED. FÍSICA','\0'),(7,708803,'FAC DE MEDICINA VETERINÁRIA-CASTANHAL',''),(8,938208,'LAB PATOLOGIA ANIMAL-CASTANHAL',''),(9,98435,'DIREÇÃO DO HOSPITAL VETERINÁRIO-CASTANHAL','\0'),(10,956260,'DIV. DE SEGURANÇA E MANUTENÇÃO-CASTANHAL',''),(11,248446,'PPG EM SAÚDE ANIMAL NA AMAZÔNIA-CASTANHAL',''),(12,279097,'LABORATÓRIO DE ANATOMIA-CASTANHAL',''),(13,32860,'LAB. BIOQ. FISIOL E FARMACOLOGIA-CASTANHAL',''),(14,545595,'ALMOXARIFADO CAMPUS1-CASTANHAL',''),(15,512103,'CEBRAM-BIOTEC. DE REP. ANIMAL-CASTANHAL',''),(16,252280,'AUDITÓRIO CENTRAL MARIA DE NAZARÉ SÁ',''),(17,674212,'ALMOXARIFADO CAMPUS2-CASTANHAL',''),(18,510750,'BIBLIOTECA DO CAMPUS DE CASTANHAL',''),(19,535269,'CENTRO MULTIDISCIPLINAR EDUCAÇÃO CONTINUADA CMEC',''),(20,822655,'COPA DOS SERVIDORES-CASTANHAL',''),(21,362719,'COORDENAÇÃO ACADÊMICA CAMPUS DE CASTANHAL',''),(22,427686,'COORDENAÇÃO DE INFRAESTRUTURA CAPUS DE CASTANHAL',''),(23,578090,'DIREÇÃO DO INST DE MEDICINA VETERINÁRIA-CASTANHAL',''),(24,174218,'DIVISÃO DE TECNOLOGIA DA INFORMAÇÃO',''),(25,17313,'LABORATÓRIO DE ACESSIBILIDADE-CASTANHAL',''),(26,958092,'LABORATÓRIO DE DESENVOLVIMENTO DE SISTEMA-CASTANHAL',''),(27,536042,'ASSESSORIA DE COMUNICAÇÃO-CASTANHAL',''),(28,169098,'COORDENAÇÃO DE PLANEJAMENTO-CASTANHAL',''),(29,954343,'SEC. GETI-GRUPO DE EDUCAÇÃO TERCEIRA IDADE',''),(30,677819,'CAMPUS DE CASTANHAL',''),(31,186688,'SECRETARIA EXECUTIVA-CAPUS CASTANHAL',''),(32,839304,'FACULDADE DE PEDAGOGIA-CASTANHAL',''),(33,938923,'PROTOCOLO CAMPUS DE CASTANHAL',''),(34,713498,'LABORATÓRIO DE CONSULTA-CAMPUS 1 DE CASTANHAL',''),(35,817271,'FACULDADE DE LETRAS-CASTANHAL',''),(36,820915,'FACULDADE DE MATEMÁTICA-CASTANHAL',''),(37,973023,'FACULDADE DE COMPUTAÇÃO-CASTANHAL',''),(38,937330,'FACULDADE DE EDUCAÇÃO FÍSICA-CASTANHAL',''),(39,170248,'COPA DO GETI-CASTANHAL',''),(40,859592,'COPA DOS TERCERIZADOS-CASTANHAL','');
/*!40000 ALTER TABLE `unidade_curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `codigo` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `matricula` bigint(20) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `tipo` varchar(25) NOT NULL,
  `login` varchar(50) NOT NULL,
  `senha` varchar(100) NOT NULL,
  `unidadeCurso_codigo` bigint(20) NOT NULL,
  `is_ativo` bit(1) DEFAULT NULL,
  PRIMARY KEY (`codigo`),
  UNIQUE KEY `UK_6r3pgk87k0cmatekasqghpka6` (`matricula`),
  UNIQUE KEY `UK_pm3f4m4fqv89oeeeac4tbe2f4` (`login`),
  KEY `FK_5iok1mj9ux83q8y43t493ldf5` (`unidadeCurso_codigo`),
  CONSTRAINT `FK_5iok1mj9ux83q8y43t493ldf5` FOREIGN KEY (`unidadeCurso_codigo`) REFERENCES `unidade_curso` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'wjua.ps@gmail.com',201400632,'Juan Soares','(91) 9 8488-9550','Usuario','juan','81DC9BDB52D04DC20036DBD8313ED055',1,''),(2,'janilson@gmail.com',2014,'Janilson','(99) 9 9999-8888','Usuario','janilson','81DC9BDB52D04DC20036DBD8313ED055',1,''),(3,'jsampaiopa@gmail.com',201418640031,'Janilson Sampaio de Oliveira','(91) 9 8735-6593','Usuario','jsampaio','65EDE2207B10380CDFA038AFDD8326FF',1,''),(5,'administrador@sigem.com',1519050178668,'Administrador','(91) 9 0000-1111','Administrador','Admin','21232F297A57A5A743894A0E4A801FC3',30,'');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `view_emprestimo_resumo`
--

DROP TABLE IF EXISTS `view_emprestimo_resumo`;
/*!50001 DROP VIEW IF EXISTS `view_emprestimo_resumo`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_emprestimo_resumo` AS SELECT 
 1 AS `Protocolo`,
 1 AS `Nome Solicitante`,
 1 AS `Nome Usuario`,
 1 AS `Data Entrega`,
 1 AS `Data Devolucao`,
 1 AS `Numero Tombamento`,
 1 AS `Tipo`,
 1 AS `Descricao`,
 1 AS `Unidade Curso`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `view_resumo_requisicao`
--

DROP TABLE IF EXISTS `view_resumo_requisicao`;
/*!50001 DROP VIEW IF EXISTS `view_resumo_requisicao`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE VIEW `view_resumo_requisicao` AS SELECT 
 1 AS `Codigo Requisicao`,
 1 AS `Nome Solicitante`,
 1 AS `Nome Recebedor`,
 1 AS `Descricao Material`,
 1 AS `Unidade`,
 1 AS `Codigo Material`,
 1 AS `Data Entrega`,
 1 AS `Quantidade Requisitada`,
 1 AS `Quantidade Entregue`,
 1 AS `Unidade Curso Solicitante`,
 1 AS `Unidade Curso Recebedor`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping events for database 'db_sigem'
--

--
-- Dumping routines for database 'db_sigem'
--
/*!50003 DROP PROCEDURE IF EXISTS `SP_relatorio_emprestimo` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_relatorio_emprestimo`(
	matricula_solicitante BIGINT(20), numero_tombamento BIGINT(20), data_inicial DATETIME, data_final DATETIME)
BEGIN
	CASE 
		WHEN (matricula_solicitante IS NOT NULL AND numero_tombamento IS NOT NULL ) THEN
			SELECT 
				`e`.`num_protocolo` AS `protocolo`, 
				`eq`.`num_tombamento` AS `tombamento`, 
				`eq`.`tipo` AS `tipo`, 
				`eq`.`descricao` AS `descricao`, 
				`s`.`nome` AS `solicitante_nome`, 
				`uc`.`unidade_curso` AS `unidade_curso`, 
				`ehe`.`data_hora_entrega` AS `data_entrega`
			FROM `emprestimo_has_equipamento` AS `ehe` 
				INNER JOIN
					`equipamento` AS `eq` ON (`eq`.`codigo` = `ehe`.`equipamento_codigo`)
				INNER JOIN
					`emprestimo` AS `e` ON (`ehe`.`emprestimo_codigo` = `e`.`codigo`)
				INNER JOIN
					`solicitante` AS `s` ON (`e`.`solicitante_codigo` = `s`.`codigo`)
				INNER JOIN 
					`unidade_curso` AS `uc` ON (`s`.`unidadeCurso_codigo` = `uc`.`codigo`)
			WHERE 
				(`s`.`matricula` = matricula_solicitante AND `eq`.`num_tombamento` = numero_tombamento) AND
				 `ehe`.`data_hora_entrega` BETWEEN data_inicial AND data_final
			ORDER BY `e`.`num_protocolo`;
		WHEN (matricula_solicitante IS NOT NULL) THEN
			SELECT 
				`e`.`num_protocolo` AS `protocolo`, 
				`eq`.`num_tombamento` AS `tombamento`, 
				`eq`.`tipo` AS `tipo`, 
				`eq`.`descricao` AS `descricao`, 
				`s`.`nome` AS `solicitante_nome`, 
				`uc`.`unidade_curso` AS `unidade_curso`, 
				`ehe`.`data_hora_entrega` AS `data_entrega`
			FROM `emprestimo_has_equipamento` AS `ehe` 
				INNER JOIN
					`equipamento` AS `eq` ON (`eq`.`codigo` = `ehe`.`equipamento_codigo`)
				INNER JOIN
					`emprestimo` AS `e` ON (`ehe`.`emprestimo_codigo` = `e`.`codigo`)
				INNER JOIN
					`solicitante` AS `s` ON (`e`.`solicitante_codigo` = `s`.`codigo`)
				INNER JOIN 
					`unidade_curso` AS `uc` ON (`s`.`unidadeCurso_codigo` = `uc`.`codigo`)
			WHERE 
				(`s`.`matricula` = matricula_solicitante) AND
				 `ehe`.`data_hora_entrega` BETWEEN data_inicial AND data_final
			ORDER BY `e`.`num_protocolo`;
		WHEN (numero_tombamento IS NOT NULL) THEN
			SELECT 
				`e`.`num_protocolo` AS `protocolo`, 
				`eq`.`num_tombamento` AS `tombamento`, 
				`eq`.`tipo` AS `tipo`, 
				`eq`.`descricao` AS `descricao`, 
				`s`.`nome` AS `solicitante_nome`, 
				`uc`.`unidade_curso` AS `unidade_curso`, 
				`ehe`.`data_hora_entrega` AS `data_entrega`
			FROM `emprestimo_has_equipamento` AS `ehe` 
				INNER JOIN
					`equipamento` AS `eq` ON (`eq`.`codigo` = `ehe`.`equipamento_codigo`)
				INNER JOIN
					`emprestimo` AS `e` ON (`ehe`.`emprestimo_codigo` = `e`.`codigo`)
				INNER JOIN
					`solicitante` AS `s` ON (`e`.`solicitante_codigo` = `s`.`codigo`)
				INNER JOIN 
					`unidade_curso` AS `uc` ON (`s`.`unidadeCurso_codigo` = `uc`.`codigo`)
			WHERE 
				(`eq`.`num_tombamento` = numero_tombamento) AND
				 `ehe`.`data_hora_entrega` BETWEEN data_inicial AND data_final
			ORDER BY `e`.`num_protocolo`;
		ELSE
			SELECT 
				`e`.`num_protocolo` AS `protocolo`, 
				`eq`.`num_tombamento` AS `tombamento`, 
				`eq`.`tipo` AS `tipo`, 
				`eq`.`descricao` AS `descricao`, 
				`s`.`nome` AS `solicitante_nome`, 
				`uc`.`unidade_curso` AS `unidade_curso`, 
				`ehe`.`data_hora_entrega` AS `data_entrega`
			FROM `emprestimo_has_equipamento` AS `ehe` 
				INNER JOIN
					`equipamento` AS `eq` ON (`eq`.`codigo` = `ehe`.`equipamento_codigo`)
				INNER JOIN
					`emprestimo` AS `e` ON (`ehe`.`emprestimo_codigo` = `e`.`codigo`)
				INNER JOIN
					`solicitante` AS `s` ON (`e`.`solicitante_codigo` = `s`.`codigo`)
				INNER JOIN 
					`unidade_curso` AS `uc` ON (`s`.`unidadeCurso_codigo` = `uc`.`codigo`)
			WHERE 
				`ehe`.`data_hora_entrega` BETWEEN data_inicial AND data_final
			ORDER BY `e`.`num_protocolo`;
    END CASE;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_relatorio_requisicao` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_relatorio_requisicao`(
	nome_requisitante VARCHAR(150), nome_recebedor VARCHAR(150), nome_unidade_curso VARCHAR(250), 
    data_inicial DATE, data_final DATE)
BEGIN
	SELECT DISTINCT 
		`r`.`codigo` AS `requisicao`, 
        `rhm`.`quantidade_entregue` AS `qtd_entregue`,
		`rhm`.`quantidade_requisitada` AS `qtd_requisitada`, 
        `rhm`.`data_hora_entrega` AS `data`,
		`m`.`descricao` AS `material`, 
        `m`.`unidade` AS `unidade`, 
        `requsitante`.`nome` AS `nome_solicitante`,
		`recebedor`.`nome` AS `nome_recebedor`, 
        `ucs`.`unidade_curso` AS `unidade_curso`
	FROM `requisicao` AS `r`
		INNER JOIN 
			`requisicao_has_materiais` AS `rhm` ON (`rhm`.`requisicao_codigo` = `r`.`codigo`)
		INNER JOIN 
			`material` AS `m` ON (`rhm`.`material_codigo` = `m`.`codigo`)
		INNER JOIN 
			`solicitante` AS `requsitante` ON (`r`.`solicitante_codigo` = `requsitante`.`codigo`)
		INNER JOIN 
			`unidade_curso` AS `ucs` ON (`requsitante`.`unidadeCurso_codigo` = `ucs`.`codigo`)
		INNER JOIN 
			`solicitante` AS `recebedor` ON (`r`.`recebedor_codigo` = `recebedor`.`codigo`)
	WHERE
		(`requsitante`.`nome` LIKE nome_requisitante AND `recebedor`.`nome` LIKE nome_recebedor AND
         `ucs`.`unidade_curso` LIKE nome_unidade_curso) AND
		 `rhm`.`data_hora_entrega` BETWEEN data_inicial AND data_final
		ORDER BY `unidade_curso` ASC;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `view_emprestimo_resumo`
--

/*!50001 DROP VIEW IF EXISTS `view_emprestimo_resumo`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_emprestimo_resumo` AS select `emprestimo`.`num_protocolo` AS `Protocolo`,`s`.`nome` AS `Nome Solicitante`,`u`.`nome` AS `Nome Usuario`,`ehp`.`data_hora_entrega` AS `Data Entrega`,`ehp`.`data_hora_devolucao_prevista` AS `Data Devolucao`,`eq`.`num_tombamento` AS `Numero Tombamento`,`eq`.`tipo` AS `Tipo`,`eq`.`descricao` AS `Descricao`,`uc`.`unidade_curso` AS `Unidade Curso` from (((((`emprestimo` join `solicitante` `s` on((`emprestimo`.`solicitante_codigo` = `s`.`codigo`))) join `unidade_curso` `uc` on((`s`.`unidadeCurso_codigo` = `uc`.`codigo`))) join `usuario` `u` on((`emprestimo`.`usuario_codigo` = `u`.`codigo`))) join `emprestimo_has_equipamento` `ehp` on((`ehp`.`emprestimo_codigo` = `emprestimo`.`codigo`))) join `equipamento` `eq` on((`eq`.`codigo` = `ehp`.`equipamento_codigo`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_resumo_requisicao`
--

/*!50001 DROP VIEW IF EXISTS `view_resumo_requisicao`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_resumo_requisicao` AS select `rq`.`codigo` AS `Codigo Requisicao`,`solicitante`.`nome` AS `Nome Solicitante`,`recebedor`.`nome` AS `Nome Recebedor`,`m`.`descricao` AS `Descricao Material`,`m`.`unidade` AS `Unidade`,`m`.`codigo` AS `Codigo Material`,`rhm`.`data_hora_entrega` AS `Data Entrega`,`rhm`.`quantidade_requisitada` AS `Quantidade Requisitada`,`rhm`.`quantidade_entregue` AS `Quantidade Entregue`,`ucsolicitante`.`unidade_curso` AS `Unidade Curso Solicitante`,`ucrecebedor`.`unidade_curso` AS `Unidade Curso Recebedor` from ((((((`requisicao` `rq` join `solicitante` on((`solicitante`.`codigo` = `rq`.`solicitante_codigo`))) join `unidade_curso` `ucsolicitante` on((`ucsolicitante`.`codigo` = `solicitante`.`unidadeCurso_codigo`))) join `solicitante` `recebedor` on((`recebedor`.`codigo` = `rq`.`recebedor_codigo`))) join `unidade_curso` `ucrecebedor` on((`recebedor`.`unidadeCurso_codigo` = `ucrecebedor`.`codigo`))) join `requisicao_has_materiais` `rhm` on((`rq`.`codigo` = `rhm`.`requisicao_codigo`))) join `material` `m` on((`m`.`codigo` = `rhm`.`material_codigo`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-26 15:44:19
