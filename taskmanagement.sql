SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `TaskManagement` ;
CREATE SCHEMA IF NOT EXISTS `TaskManagement` DEFAULT CHARACTER SET utf8 ;
USE `TaskManagement` ;

-- -----------------------------------------------------
-- Table `User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `User` ;

CREATE  TABLE IF NOT EXISTS `User` (
  `u_UserId` INT NOT NULL AUTO_INCREMENT ,
  `u_Username` VARCHAR(45) NOT NULL ,
  `u_Name` VARCHAR(50) NOT NULL ,
  `u_Email` VARCHAR(100) NOT NULL ,
  `u_Password` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`u_UserId`) ,
  UNIQUE INDEX `u_Username_UNIQUE` (`u_Username` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Group` ;

CREATE  TABLE IF NOT EXISTS `Group` (
  `g_GroupId` INT NOT NULL AUTO_INCREMENT ,
  `g_Title` VARCHAR(45) NOT NULL ,
  `g_Description` TEXT NULL ,
  `g_Owner_UserId` INT NOT NULL ,
  PRIMARY KEY (`g_GroupId`) ,
  INDEX `fk_Group_User_idx` (`g_Owner_UserId` ASC) ,
  CONSTRAINT `fk_Group_User`
    FOREIGN KEY (`g_Owner_UserId` )
    REFERENCES `User` (`u_UserId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project` ;

CREATE  TABLE IF NOT EXISTS `Project` (
  `p_ProjectId` INT NOT NULL AUTO_INCREMENT ,
  `p_Title` VARCHAR(45) NOT NULL ,
  `P_Description` TEXT NULL ,
  `p_Owner_UserId` INT NOT NULL ,
  `p_Group_GroupId` INT NULL ,
  PRIMARY KEY (`p_ProjectId`) ,
  INDEX `fk_Project_User1_idx` (`p_Owner_UserId` ASC) ,
  INDEX `fk_Project_Group1_idx` (`p_Group_GroupId` ASC) ,
  CONSTRAINT `fk_Project_User1`
    FOREIGN KEY (`p_Owner_UserId` )
    REFERENCES `User` (`u_UserId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_Group1`
    FOREIGN KEY (`p_Group_GroupId` )
    REFERENCES `Group` (`g_GroupId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Task` ;

CREATE  TABLE IF NOT EXISTS `Task` (
  `t_TaskId` INT NOT NULL AUTO_INCREMENT ,
  `t_Title` VARCHAR(45) NOT NULL ,
  `t_Description` TEXT NULL ,
  `t_Priority` INT NULL ,
  `t_Status` INT NULL ,
  `t_Deadline` TIMESTAMP NULL ,
  `t_Owner_UserId` INT NOT NULL ,
  `t_EstimatedTime` INT NULL ,
  `t_Created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `t_Updated` TIMESTAMP NULL ,
  `t_GroupId` INT NULL ,
  `t_ProjectId` INT NULL ,
  `t_Parent_TaskId` INT NULL ,
  `t_Root_TaskId` INT NULL ,
  PRIMARY KEY (`t_TaskId`) ,
  INDEX `fk_Task_User_idx` (`t_Owner_UserId` ASC) ,
  INDEX `fk_Task_Group1_idx` (`t_GroupId` ASC) ,
  INDEX `fk_Task_Project1_idx` (`t_ProjectId` ASC) ,
  INDEX `fk_Parent_TaskId_idx` (`t_Parent_TaskId` ASC) ,
  INDEX `fk_Root_TaskId_idx` (`t_Root_TaskId` ASC) ,
  CONSTRAINT `fk_Task_User`
    FOREIGN KEY (`t_Owner_UserId` )
    REFERENCES `User` (`u_UserId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_Group1`
    FOREIGN KEY (`t_GroupId` )
    REFERENCES `Group` (`g_GroupId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_Project1`
    FOREIGN KEY (`t_ProjectId` )
    REFERENCES `Project` (`p_ProjectId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Parent_TaskId`
    FOREIGN KEY (`t_Parent_TaskId` )
    REFERENCES `Task` (`t_TaskId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Root_TaskId`
    FOREIGN KEY (`t_Root_TaskId` )
    REFERENCES `Task` (`t_TaskId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TaskAssignment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `TaskAssignment` ;

CREATE  TABLE IF NOT EXISTS `TaskAssignment` (
  `ta_TaskId` INT NOT NULL ,
  `ta_UserId` INT NOT NULL ,
  INDEX `fk_TaskAssignment_Task1_idx` (`ta_TaskId` ASC) ,
  INDEX `fk_TaskAssignment_User1_idx` (`ta_UserId` ASC) ,
  PRIMARY KEY (`ta_TaskId`, `ta_UserId`) ,
  CONSTRAINT `fk_TaskAssignment_Task1`
    FOREIGN KEY (`ta_TaskId` )
    REFERENCES `Task` (`t_TaskId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TaskAssignment_User1`
    FOREIGN KEY (`ta_UserId` )
    REFERENCES `User` (`u_UserId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GroupMembership`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GroupMembership` ;

CREATE  TABLE IF NOT EXISTS `GroupMembership` (
  `gm_UserId` INT NOT NULL ,
  `gm_GroupId` INT NOT NULL ,
  `gm_PermissionLevel` INT NOT NULL ,
  PRIMARY KEY (`gm_GroupId`, `gm_UserId`) ,
  INDEX `fk_GroupMembership_User1_idx` (`gm_UserId` ASC) ,
  INDEX `fk_GroupMembership_Group1_idx` (`gm_GroupId` ASC) ,
  CONSTRAINT `fk_GroupMembership_User1`
    FOREIGN KEY (`gm_UserId` )
    REFERENCES `User` (`u_UserId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_GroupMembership_Group1`
    FOREIGN KEY (`gm_GroupId` )
    REFERENCES `Group` (`g_GroupId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TimeEntry`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `TimeEntry` ;

CREATE  TABLE IF NOT EXISTS `TimeEntry` (
  `te_Duration` INT NOT NULL ,
  `te_TaskId` INT NOT NULL ,
  `te_UserId` INT NOT NULL ,
  `te_Date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `te_TimeEntryId` INT NOT NULL AUTO_INCREMENT ,
  PRIMARY KEY (`te_TimeEntryId`) ,
  INDEX `fk_TimeEntry_User1_idx` (`te_UserId` ASC) ,
  CONSTRAINT `fk_TimeEntry_Task1`
    FOREIGN KEY (`te_TaskId` )
    REFERENCES `Task` (`t_TaskId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TimeEntry_User1`
    FOREIGN KEY (`te_UserId` )
    REFERENCES `User` (`u_UserId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ProjectMembership`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ProjectMembership` ;

CREATE  TABLE IF NOT EXISTS `ProjectMembership` (
  `pm_UserId` INT NOT NULL ,
  `pm_ProjectId` INT NOT NULL ,
  `pm_PermissionLevel` INT NOT NULL ,
  PRIMARY KEY (`pm_UserId`, `pm_ProjectId`) ,
  INDEX `fk_User_has_Project_Project1_idx` (`pm_ProjectId` ASC) ,
  INDEX `fk_User_has_Project_User1_idx` (`pm_UserId` ASC) ,
  CONSTRAINT `fk_User_has_Project_User1`
    FOREIGN KEY (`pm_UserId` )
    REFERENCES `User` (`u_UserId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Project_Project1`
    FOREIGN KEY (`pm_ProjectId` )
    REFERENCES `Project` (`p_ProjectId` )
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Task_log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Task_log` ;

CREATE  TABLE IF NOT EXISTS `Task_log` (
  `Action` ENUM('create','update','delete') NULL ,
  `TaskId` VARCHAR(45) NOT NULL ,
  `Logged` TIMESTAMP NULL ,
  `Title` VARCHAR(45) NOT NULL ,
  `Description` TEXT NULL ,
  `Priority` INT NULL ,
  `Status` INT NULL ,
  `Deadline` TIMESTAMP NULL ,
  `Owner_UserId` INT NOT NULL ,
  `EstimatedTime` INT NULL ,
  `Created` TIMESTAMP NULL ,
  `Updated` TIMESTAMP NULL ,
  `GroupId` INT NULL ,
  `ProjectId` INT NULL ,
  `Parent_TaskId` INT NULL ,
  `Root_TaskId` INT NULL ,
  INDEX `index1` (`TaskId` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- procedure Login
-- -----------------------------------------------------

USE `TaskManagement`;
DROP procedure IF EXISTS `Login`;
DELIMITER $$
USE `TaskManagement`$$

CREATE PROCEDURE Login(IN userName VARCHAR(45), IN password VARCHAR(100))
BEGIN
SELECT * FROM User WHERE u_Username = userName AND u_Password = password;
END
$$

$$
DELIMITER ;

USE `TaskManagement`;

DELIMITER $$

USE `TaskManagement`$$
DROP TRIGGER IF EXISTS `ai_task` $$
USE `TaskManagement`$$
CREATE TRIGGER ai_task AFTER INSERT ON Task
FOR EACH ROW
BEGIN
INSERT INTO Task_log (Action,TaskId,Logged,Title,Description,Priority,Status,Deadline,Owner_UserId,EstimatedTime,Created,Updated,GroupId,ProjectId,Parent_TaskId,Root_TaskId)

VALUES('create',NEW.t_TaskId,NOW(),NEW.t_Title,NEW.t_Description,NEW.t_Priority,NEW.t_Status,NEW.t_Deadline,NEW.t_Owner_UserId,NEW.t_EstimatedTime,NEW.t_Created,NEW.t_Updated,NEW.t_GroupId,NEW.t_ProjectId,NEW.t_Parent_TaskId,NEW.t_Root_TaskId);

END;
$$


USE `TaskManagement`$$
DROP TRIGGER IF EXISTS `au_task` $$
USE `TaskManagement`$$
CREATE TRIGGER au_task AFTER UPDATE ON Task
FOR EACH ROW
BEGIN
INSERT INTO Task_log (Action,TaskId,Logged,Title,Description,Priority,Status,Deadline,Owner_UserId,EstimatedTime,Created,Updated,GroupId,ProjectId,Parent_TaskId,Root_TaskId)

VALUES ('update',NEW.t_TaskId,NOW(),NEW.t_Title,NEW.t_Description,NEW.t_Priority,NEW.t_Status,NEW.t_Deadline,NEW.t_Owner_UserId,NEW.t_EstimatedTime,NEW.t_Created,NEW.t_Updated,NEW.t_GroupId,NEW.t_ProjectId,NEW.t_Parent_TaskId,NEW.t_Root_TaskId);

END;
$$


USE `TaskManagement`$$
DROP TRIGGER IF EXISTS `ad_task` $$
USE `TaskManagement`$$
CREATE TRIGGER ad_task AFTER DELETE ON Task
FOR EACH ROW
BEGIN
INSERT INTO Task_log (Action,TaskId,Logged,Title,Description,Priority,Status,Deadline,Owner_UserId,EstimatedTime,Created,Updated,GroupId,ProjectId,Parent_TaskId,Root_TaskId)

VALUES ('delete',OLD.t_TaskId,NOW(),OLD.t_Title,OLD.t_Description,OLD.t_Priority,OLD.t_Status,OLD.t_Deadline,OLD.t_Owner_UserId,OLD.t_EstimatedTime,OLD.t_Created,OLD.t_Updated,OLD.t_GroupId,OLD.t_ProjectId,OLD.t_Parent_TaskId,OLD.t_Root_TaskId);

END;
$$


DELIMITER ;


-- -----------------------------------------------------
-- Data for table `User`
-- -----------------------------------------------------
START TRANSACTION;
USE `TaskManagement`;
INSERT INTO `User` (`u_UserId`, `u_Username`, `u_Name`, `u_Email`, `u_Password`) VALUES (1, 'flemming', 'Flemming Madsen', 'mail@leflings.dk', 'flemming');
INSERT INTO `User` (`u_UserId`, `u_Username`, `u_Name`, `u_Email`, `u_Password`) VALUES (2, 'jesper', 'Jesper Plantener', 'jesper@plantener.dk', 'jesper');
INSERT INTO `User` (`u_UserId`, `u_Username`, `u_Name`, `u_Email`, `u_Password`) VALUES (3, 'themi', 'Themi Tsiotas von Pfaler', 'themi1234@gmail.com', 'themi');
INSERT INTO `User` (`u_UserId`, `u_Username`, `u_Name`, `u_Email`, `u_Password`) VALUES (4, 'mads', 'Mads Hornbeck', 'madshornbeck@gmail.com', 'mads');
INSERT INTO `User` (`u_UserId`, `u_Username`, `u_Name`, `u_Email`, `u_Password`) VALUES (5, 'tor', 'Tor Hoset', 'ukendt', 'tor');

COMMIT;

-- -----------------------------------------------------
-- Data for table `Group`
-- -----------------------------------------------------
START TRANSACTION;
USE `TaskManagement`;
INSERT INTO `Group` (`g_GroupId`, `g_Title`, `g_Description`, `g_Owner_UserId`) VALUES (1, 'Gruppe 10', 'Hej, vi hygger', 1);
INSERT INTO `Group` (`g_GroupId`, `g_Title`, `g_Description`, `g_Owner_UserId`) VALUES (2, 'Enhedslisten', 'Parti', 2);
INSERT INTO `Group` (`g_GroupId`, `g_Title`, `g_Description`, `g_Owner_UserId`) VALUES (3, 'NetGroup', 'Company', 5);

COMMIT;

-- -----------------------------------------------------
-- Data for table `Project`
-- -----------------------------------------------------
START TRANSACTION;
USE `TaskManagement`;
INSERT INTO `Project` (`p_ProjectId`, `p_Title`, `P_Description`, `p_Owner_UserId`, `p_Group_GroupId`) VALUES (1, 'TaskManagement', 'Det er faktisk det her projekt vi har gang i', 1, 1);
INSERT INTO `Project` (`p_ProjectId`, `p_Title`, `P_Description`, `p_Owner_UserId`, `p_Group_GroupId`) VALUES (2, 'Kampagne', 'parti kampagne', 3, 2);
INSERT INTO `Project` (`p_ProjectId`, `p_Title`, `P_Description`, `p_Owner_UserId`, `p_Group_GroupId`) VALUES (3, 'Afstemning', 'Valg', 4, 2);

COMMIT;

-- -----------------------------------------------------
-- Data for table `TaskAssignment`
-- -----------------------------------------------------
START TRANSACTION;
USE `TaskManagement`;
INSERT INTO `TaskAssignment` (`ta_TaskId`, `ta_UserId`) VALUES (3, 2);
INSERT INTO `TaskAssignment` (`ta_TaskId`, `ta_UserId`) VALUES (4, 1);
INSERT INTO `TaskAssignment` (`ta_TaskId`, `ta_UserId`) VALUES (4, 2);
INSERT INTO `TaskAssignment` (`ta_TaskId`, `ta_UserId`) VALUES (5, 3);

COMMIT;

-- -----------------------------------------------------
-- Data for table `GroupMembership`
-- -----------------------------------------------------
START TRANSACTION;
USE `TaskManagement`;
INSERT INTO `GroupMembership` (`gm_UserId`, `gm_GroupId`, `gm_PermissionLevel`) VALUES (1, 2, 1);
INSERT INTO `GroupMembership` (`gm_UserId`, `gm_GroupId`, `gm_PermissionLevel`) VALUES (3, 1, 1);
INSERT INTO `GroupMembership` (`gm_UserId`, `gm_GroupId`, `gm_PermissionLevel`) VALUES (2, 1, 1);
INSERT INTO `GroupMembership` (`gm_UserId`, `gm_GroupId`, `gm_PermissionLevel`) VALUES (4, 1, 1);
INSERT INTO `GroupMembership` (`gm_UserId`, `gm_GroupId`, `gm_PermissionLevel`) VALUES (5, 1, 1);

COMMIT;

-- -----------------------------------------------------
-- Data for table `ProjectMembership`
-- -----------------------------------------------------
START TRANSACTION;
USE `TaskManagement`;
INSERT INTO `ProjectMembership` (`pm_UserId`, `pm_ProjectId`, `pm_PermissionLevel`) VALUES (3, 1, 1);
INSERT INTO `ProjectMembership` (`pm_UserId`, `pm_ProjectId`, `pm_PermissionLevel`) VALUES (4, 1, 1);
INSERT INTO `ProjectMembership` (`pm_UserId`, `pm_ProjectId`, `pm_PermissionLevel`) VALUES (5, 3, 2);
INSERT INTO `ProjectMembership` (`pm_UserId`, `pm_ProjectId`, `pm_PermissionLevel`) VALUES (5, 2, 1);
INSERT INTO `ProjectMembership` (`pm_UserId`, `pm_ProjectId`, `pm_PermissionLevel`) VALUES (3, 3, 1);
INSERT INTO `ProjectMembership` (`pm_UserId`, `pm_ProjectId`, `pm_PermissionLevel`) VALUES (1, 2, 3);

COMMIT;

LOCK TABLES `Task` WRITE;
/*!40000 ALTER TABLE `Task` DISABLE KEYS */;
INSERT INTO `Task` VALUES (1,'Arbejde',NULL,2,1,NULL,1,0,'2012-11-27 10:18:48',NULL,NULL,NULL,NULL,NULL),(2,'Fritid','Her holder jeg fri',1,1,NULL,1,0,'2012-11-27 10:18:48',NULL,NULL,NULL,NULL,NULL),(3,'Lav datalayer færdig','Det skal laves færdig du',3,1,NULL,1,0,'2012-11-27 10:18:48',NULL,1,1,NULL,NULL),(4,'ParentTask','test',1,1,NULL,1,0,'2012-11-27 10:18:48',NULL,NULL,NULL,NULL,NULL),(5,'ChildTask',NULL,NULL,NULL,NULL,1,NULL,'2012-11-27 10:18:48',NULL,NULL,NULL,4,4),(6,'SubChildTask','underundertask',3,2,NULL,2,4,'2012-11-27 10:18:48',NULL,2,1,5,4),(7,'AnotherChildTask','hej',NULL,NULL,NULL,1,NULL,'2012-11-27 10:18:48',NULL,NULL,NULL,4,4),(8,'AnyChild','godag',NULL,NULL,NULL,1,NULL,'2012-11-27 10:18:48',NULL,NULL,NULL,4,4),(9,'SubChild',NULL,NULL,NULL,NULL,1,NULL,'2012-11-27 10:18:48',NULL,NULL,NULL,8,4),(10,'koordinering ledersynsvinklerne',NULL,3,3,'2012-12-22 10:19:03',5,0,'2012-11-27 10:19:03',NULL,NULL,1,NULL,NULL),(11,'forskningsprojekterne som',NULL,3,3,'2013-01-16 10:19:03',2,0,'2012-11-27 10:19:03',NULL,NULL,1,NULL,NULL),(12,'og kompetent',NULL,0,3,'2013-01-19 10:19:03',4,0,'2012-11-27 10:19:03',NULL,NULL,NULL,NULL,NULL),(13,'mindst foregangslandet',NULL,1,4,'2013-01-22 10:19:03',2,0,'2012-11-27 10:19:03',NULL,NULL,NULL,NULL,NULL),(14,'Ikke modsvarer',NULL,0,0,'2013-01-11 10:19:03',4,0,'2012-11-27 10:19:03',NULL,2,NULL,NULL,NULL),(15,'private at',NULL,3,3,'2013-01-21 10:19:03',1,0,'2012-11-27 10:19:03',NULL,NULL,NULL,NULL,NULL),(16,'svækker og',NULL,2,2,'2013-01-09 10:19:03',4,0,'2012-11-27 10:19:03',NULL,NULL,NULL,NULL,NULL),(17,'problemområde ressourcen',NULL,0,3,'2012-12-09 10:19:03',2,0,'2012-11-27 10:19:03',NULL,3,NULL,NULL,NULL),(18,'besværliggør at',NULL,2,1,'2012-12-25 10:19:03',2,0,'2012-11-27 10:19:03',NULL,NULL,NULL,NULL,NULL),(19,'anvendt samfundsmæssige',NULL,0,4,'2012-12-09 10:19:03',5,0,'2012-11-27 10:19:03',NULL,NULL,1,NULL,NULL),(20,'særlig er',NULL,0,2,'2012-12-20 10:19:03',2,0,'2012-11-27 10:19:03',NULL,NULL,2,NULL,NULL),(21,'slutter ledersynsvinklerne',NULL,2,3,'2013-01-16 10:19:03',2,0,'2012-11-27 10:19:03',NULL,NULL,2,NULL,NULL),(22,'måske dette',NULL,0,0,'2013-01-18 10:19:03',2,0,'2012-11-27 10:19:03',NULL,NULL,NULL,NULL,NULL),(23,'det understreges',NULL,0,1,'2012-12-14 10:19:03',2,0,'2012-11-27 10:19:03',NULL,NULL,NULL,NULL,NULL),(24,'bør resultater',NULL,2,2,'2013-01-17 10:19:03',3,0,'2012-11-27 10:19:03',NULL,NULL,2,NULL,NULL),(25,'mindst trods',NULL,0,4,'2012-12-23 10:19:03',5,0,'2012-11-27 10:19:03',NULL,NULL,1,NULL,NULL),(26,'Organisationerne basal',NULL,1,4,'2012-12-24 10:19:03',1,0,'2012-11-27 10:19:03',NULL,NULL,1,NULL,NULL),(27,'beklage virkninger',NULL,2,2,'2012-12-19 10:19:03',2,0,'2012-11-27 10:19:03',NULL,NULL,NULL,NULL,NULL),(28,'beklage velkendt',NULL,3,3,'2013-01-06 10:19:03',5,0,'2012-11-27 10:19:03',NULL,NULL,1,NULL,NULL),(29,'sjældent og',NULL,1,2,'2012-12-29 10:19:03',4,0,'2012-11-27 10:19:03',NULL,2,NULL,NULL,NULL),(30,'anvendt samfundsmæssige',NULL,3,0,'2013-01-13 10:19:03',3,0,'2012-11-27 10:19:03',NULL,NULL,2,NULL,NULL),(31,'accentuerer Det',NULL,2,3,'2012-12-03 10:19:03',1,0,'2012-11-27 10:19:03',NULL,NULL,3,NULL,NULL),(32,'Ikke ressourcen',NULL,0,0,'2013-01-14 10:19:03',3,0,'2012-11-27 10:19:03',NULL,NULL,2,NULL,NULL),(33,'samfundsmæssige velkendt',NULL,3,2,'2013-01-03 10:19:03',2,0,'2012-11-27 10:19:03',NULL,NULL,NULL,NULL,NULL),(34,'måske særlig',NULL,2,3,'2012-12-02 10:19:03',2,0,'2012-11-27 10:19:03',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `Task` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES `TimeEntry` WRITE;
/*!40000 ALTER TABLE `TimeEntry` DISABLE KEYS */;
INSERT INTO `TimeEntry` VALUES (142,6,5,'2012-10-15 10:19:40',1),(141,28,2,'2012-10-13 10:19:40',2),(171,13,5,'2012-11-07 10:19:40',3),(45,7,5,'2012-10-04 10:19:40',4),(82,27,4,'2012-09-29 10:19:40',5),(119,8,5,'2012-11-07 10:19:40',6),(43,2,5,'2012-09-30 10:19:40',7),(156,16,5,'2012-09-30 10:19:40',8),(152,3,4,'2012-09-29 10:19:40',9),(40,32,3,'2012-11-11 10:19:40',10),(39,25,4,'2012-10-17 10:19:40',11),(192,13,1,'2012-11-03 10:19:40',12),(101,18,4,'2012-11-25 10:19:40',13),(124,18,5,'2012-10-14 10:19:40',14),(196,8,2,'2012-09-29 10:19:40',15),(149,31,5,'2012-11-07 10:19:40',16),(189,22,4,'2012-11-02 10:19:40',17),(103,14,3,'2012-10-12 10:19:40',18),(110,2,5,'2012-10-15 10:19:40',19),(40,33,2,'2012-11-11 10:19:40',20),(94,8,5,'2012-10-15 10:19:40',21),(47,34,4,'2012-10-13 10:19:40',22),(123,4,5,'2012-10-22 10:19:40',23),(197,14,3,'2012-10-29 10:19:40',24),(146,26,4,'2012-11-08 10:19:40',25),(15,11,5,'2012-10-30 10:19:40',26),(151,9,1,'2012-10-18 10:19:40',27),(169,12,2,'2012-10-07 10:19:40',28),(184,1,4,'2012-11-12 10:19:40',29),(44,26,1,'2012-11-23 10:19:40',30),(5,2,4,'2012-10-05 10:19:41',31),(120,25,3,'2012-11-06 10:19:41',32),(27,28,3,'2012-10-20 10:19:41',33),(50,33,2,'2012-10-09 10:19:41',34),(172,27,5,'2012-11-01 10:19:41',35),(23,16,2,'2012-11-08 10:19:41',36),(39,3,2,'2012-09-30 10:19:41',37),(182,3,2,'2012-11-11 10:19:41',38),(94,7,5,'2012-11-09 10:19:41',39),(154,4,5,'2012-11-25 10:19:41',40),(34,8,4,'2012-10-08 10:19:41',41),(133,8,2,'2012-11-22 10:19:41',42),(177,15,2,'2012-10-23 10:19:41',43),(78,15,2,'2012-10-22 10:19:41',44),(48,16,1,'2012-10-30 10:19:41',45),(171,22,3,'2012-11-01 10:19:41',46),(17,26,3,'2012-10-15 10:19:41',47),(151,16,4,'2012-10-03 10:19:41',48),(198,8,3,'2012-10-19 10:19:41',49),(125,6,2,'2012-11-14 10:19:41',50),(43,9,5,'2012-11-10 10:19:41',51),(154,15,4,'2012-11-22 10:19:41',52),(87,11,3,'2012-10-17 10:19:41',53),(110,23,3,'2012-11-22 10:19:41',54),(75,6,1,'2012-11-07 10:19:41',55),(107,25,4,'2012-11-02 10:19:41',56),(65,15,3,'2012-10-01 10:19:41',57),(108,24,4,'2012-10-29 10:19:41',58),(181,33,2,'2012-10-25 10:19:41',59),(103,4,3,'2012-11-25 10:19:41',60),(55,20,3,'2012-10-29 10:19:41',61),(187,32,5,'2012-10-08 10:19:41',62),(135,9,5,'2012-11-23 10:19:41',63),(194,31,4,'2012-10-08 10:19:41',64),(183,22,4,'2012-11-20 10:19:41',65),(40,17,1,'2012-11-12 10:19:41',66),(172,20,2,'2012-11-20 10:19:41',67),(25,20,3,'2012-11-23 10:19:41',68),(125,14,5,'2012-10-27 10:19:41',69),(22,2,4,'2012-10-02 10:19:41',70),(10,29,1,'2012-10-21 10:19:41',71),(0,18,1,'2012-11-13 10:19:41',72),(125,21,5,'2012-11-04 10:19:41',73),(99,1,5,'2012-10-05 10:19:41',74),(188,7,1,'2012-11-20 10:19:41',75),(124,3,4,'2012-11-17 10:19:41',76),(4,24,2,'2012-10-29 10:19:41',77),(131,27,5,'2012-10-27 10:19:41',78),(183,13,2,'2012-10-25 10:19:41',79),(89,10,3,'2012-10-03 10:19:41',80),(175,13,1,'2012-10-19 10:19:41',81),(83,2,5,'2012-10-15 10:19:41',82),(40,21,3,'2012-11-14 10:19:41',83),(148,27,1,'2012-10-10 10:19:41',84),(97,18,4,'2012-10-09 10:19:41',85),(6,8,5,'2012-11-27 10:19:41',86),(123,30,5,'2012-10-01 10:19:41',87),(177,23,5,'2012-10-06 10:19:41',88),(0,13,2,'2012-10-09 10:19:41',89),(50,23,3,'2012-10-10 10:19:41',90),(108,30,3,'2012-11-22 10:19:41',91),(157,10,5,'2012-10-27 10:19:41',92),(143,9,3,'2012-10-21 10:19:41',93),(51,4,5,'2012-10-12 10:19:41',94),(97,8,1,'2012-09-30 10:19:41',95),(197,2,1,'2012-10-12 10:19:41',96),(84,33,3,'2012-10-23 10:19:41',97),(86,1,1,'2012-11-19 10:19:41',98),(198,24,1,'2012-11-20 10:19:41',99),(76,24,5,'2012-10-11 10:19:41',100);
/*!40000 ALTER TABLE `TimeEntry` ENABLE KEYS */;
UNLOCK TABLES;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

