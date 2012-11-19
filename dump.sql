SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

USE s113933;


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
  PRIMARY KEY (`u_UserId`) )
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
  INDEX `fk_Group_User` (`g_Owner_UserId` ASC) ,
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
  INDEX `fk_Task_User` (`t_Owner_UserId` ASC) ,
  INDEX `fk_Task_Group1` (`t_GroupId` ASC) ,
  INDEX `fk_Task_Project1_idx` (`t_ProjectId` ASC) ,
  INDEX `fk_Parent_TaskId` (`t_Parent_TaskId` ASC) ,
  INDEX `fk_Root_TaskId` (`t_Root_TaskId` ASC) ,
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
  INDEX `fk_TaskAssignment_Task1` (`ta_TaskId` ASC) ,
  INDEX `fk_TaskAssignment_User1` (`ta_UserId` ASC) ,
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
  INDEX `fk_GroupMembership_User1` (`gm_UserId` ASC) ,
  INDEX `fk_GroupMembership_Group1` (`gm_GroupId` ASC) ,
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
  INDEX `fk_TimeEntry_User1` (`te_UserId` ASC) ,
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



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `User`
-- -----------------------------------------------------
START TRANSACTION;
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
INSERT INTO `Group` (`g_GroupId`, `g_Title`, `g_Description`, `g_Owner_UserId`) VALUES (1, 'Gruppe 10', 'Hej, vi hygger', 1);
INSERT INTO `Group` (`g_GroupId`, `g_Title`, `g_Description`, `g_Owner_UserId`) VALUES (2, 'Enhedslisten', 'Parti', 2);
INSERT INTO `Group` (`g_GroupId`, `g_Title`, `g_Description`, `g_Owner_UserId`) VALUES (3, 'NetGroup', 'Company', 5);

COMMIT;

-- -----------------------------------------------------
-- Data for table `Project`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `Project` (`p_ProjectId`, `p_Title`, `P_Description`, `p_Owner_UserId`, `p_Group_GroupId`) VALUES (1, 'TaskManagement', 'Det er faktisk det her projekt vi har gang i', 1, 1);
INSERT INTO `Project` (`p_ProjectId`, `p_Title`, `P_Description`, `p_Owner_UserId`, `p_Group_GroupId`) VALUES (2, 'Kampagne', 'parti kampagne', 3, 2);
INSERT INTO `Project` (`p_ProjectId`, `p_Title`, `P_Description`, `p_Owner_UserId`, `p_Group_GroupId`) VALUES (3, 'Afstemning', 'Valg', 4, 2);

COMMIT;

-- -----------------------------------------------------
-- Data for table `Task`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `Task` (`t_TaskId`, `t_Title`, `t_Description`, `t_Priority`, `t_Status`, `t_Deadline`, `t_Owner_UserId`, `t_EstimatedTime`, `t_Created`, `t_Updated`, `t_GroupId`, `t_ProjectId`, `t_Parent_TaskId`, `t_Root_TaskId`) VALUES (1, 'Arbejde', NULL, 2, 1, NULL, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `Task` (`t_TaskId`, `t_Title`, `t_Description`, `t_Priority`, `t_Status`, `t_Deadline`, `t_Owner_UserId`, `t_EstimatedTime`, `t_Created`, `t_Updated`, `t_GroupId`, `t_ProjectId`, `t_Parent_TaskId`, `t_Root_TaskId`) VALUES (2, 'Fritid', 'Her holder jeg fri', 1, 1, NULL, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `Task` (`t_TaskId`, `t_Title`, `t_Description`, `t_Priority`, `t_Status`, `t_Deadline`, `t_Owner_UserId`, `t_EstimatedTime`, `t_Created`, `t_Updated`, `t_GroupId`, `t_ProjectId`, `t_Parent_TaskId`, `t_Root_TaskId`) VALUES (3, 'Lav datalayer færdig', 'Det skal laves færdig du', 3, 1, NULL, 1, 0, NULL, NULL, 1, 1, NULL, NULL);
INSERT INTO `Task` (`t_TaskId`, `t_Title`, `t_Description`, `t_Priority`, `t_Status`, `t_Deadline`, `t_Owner_UserId`, `t_EstimatedTime`, `t_Created`, `t_Updated`, `t_GroupId`, `t_ProjectId`, `t_Parent_TaskId`, `t_Root_TaskId`) VALUES (4, 'ParentTask', 'test', 1, 1, NULL, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `Task` (`t_TaskId`, `t_Title`, `t_Description`, `t_Priority`, `t_Status`, `t_Deadline`, `t_Owner_UserId`, `t_EstimatedTime`, `t_Created`, `t_Updated`, `t_GroupId`, `t_ProjectId`, `t_Parent_TaskId`, `t_Root_TaskId`) VALUES (5, 'ChildTask', NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, 4, 4);
INSERT INTO `Task` (`t_TaskId`, `t_Title`, `t_Description`, `t_Priority`, `t_Status`, `t_Deadline`, `t_Owner_UserId`, `t_EstimatedTime`, `t_Created`, `t_Updated`, `t_GroupId`, `t_ProjectId`, `t_Parent_TaskId`, `t_Root_TaskId`) VALUES (6, 'SubChildTask', 'underundertask', 3, 2, NULL, 2, 4, NULL, NULL, 2, 1, 5, 4);
INSERT INTO `Task` (`t_TaskId`, `t_Title`, `t_Description`, `t_Priority`, `t_Status`, `t_Deadline`, `t_Owner_UserId`, `t_EstimatedTime`, `t_Created`, `t_Updated`, `t_GroupId`, `t_ProjectId`, `t_Parent_TaskId`, `t_Root_TaskId`) VALUES (7, 'AnotherChildTask', 'hej', NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, 4, 4);
INSERT INTO `Task` (`t_TaskId`, `t_Title`, `t_Description`, `t_Priority`, `t_Status`, `t_Deadline`, `t_Owner_UserId`, `t_EstimatedTime`, `t_Created`, `t_Updated`, `t_GroupId`, `t_ProjectId`, `t_Parent_TaskId`, `t_Root_TaskId`) VALUES (8, 'AnyChild', 'godag', NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, 4, 4);
INSERT INTO `Task` (`t_TaskId`, `t_Title`, `t_Description`, `t_Priority`, `t_Status`, `t_Deadline`, `t_Owner_UserId`, `t_EstimatedTime`, `t_Created`, `t_Updated`, `t_GroupId`, `t_ProjectId`, `t_Parent_TaskId`, `t_Root_TaskId`) VALUES (9, 'SubChild', NULL, NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, 8, 4);

COMMIT;

-- -----------------------------------------------------
-- Data for table `TaskAssignment`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `TaskAssignment` (`ta_TaskId`, `ta_UserId`) VALUES (3, 2);
INSERT INTO `TaskAssignment` (`ta_TaskId`, `ta_UserId`) VALUES (4, 1);
INSERT INTO `TaskAssignment` (`ta_TaskId`, `ta_UserId`) VALUES (4, 2);
INSERT INTO `TaskAssignment` (`ta_TaskId`, `ta_UserId`) VALUES (5, 3);

COMMIT;

-- -----------------------------------------------------
-- Data for table `GroupMembership`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `GroupMembership` (`gm_UserId`, `gm_GroupId`, `gm_PermissionLevel`) VALUES (1, 2, 1);
INSERT INTO `GroupMembership` (`gm_UserId`, `gm_GroupId`, `gm_PermissionLevel`) VALUES (3, 1, 1);
INSERT INTO `GroupMembership` (`gm_UserId`, `gm_GroupId`, `gm_PermissionLevel`) VALUES (2, 1, 1);
INSERT INTO `GroupMembership` (`gm_UserId`, `gm_GroupId`, `gm_PermissionLevel`) VALUES (4, 1, 1);
INSERT INTO `GroupMembership` (`gm_UserId`, `gm_GroupId`, `gm_PermissionLevel`) VALUES (5, 1, 1);

COMMIT;

-- -----------------------------------------------------
-- Data for table `TimeEntry`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `TimeEntry` (`te_Duration`, `te_TaskId`, `te_UserId`, `te_Date`, `te_TimeEntryId`) VALUES (2, 4, 1, 'CURRENT_DATE', 1);
INSERT INTO `TimeEntry` (`te_Duration`, `te_TaskId`, `te_UserId`, `te_Date`, `te_TimeEntryId`) VALUES (217, 4, 2, 'CURRENT_DATE', 2);

COMMIT;

-- -----------------------------------------------------
-- Data for table `ProjectMembership`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `ProjectMembership` (`pm_UserId`, `pm_ProjectId`, `pm_PermissionLevel`) VALUES (3, 1, 1);
INSERT INTO `ProjectMembership` (`pm_UserId`, `pm_ProjectId`, `pm_PermissionLevel`) VALUES (4, 1, 1);
INSERT INTO `ProjectMembership` (`pm_UserId`, `pm_ProjectId`, `pm_PermissionLevel`) VALUES (5, 3, 2);
INSERT INTO `ProjectMembership` (`pm_UserId`, `pm_ProjectId`, `pm_PermissionLevel`) VALUES (5, 2, 1);
INSERT INTO `ProjectMembership` (`pm_UserId`, `pm_ProjectId`, `pm_PermissionLevel`) VALUES (3, 3, 1);
INSERT INTO `ProjectMembership` (`pm_UserId`, `pm_ProjectId`, `pm_PermissionLevel`) VALUES (1, 2, 3);

COMMIT;

