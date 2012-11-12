SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

USE s113933;


-- -----------------------------------------------------
-- Table `User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `User` ;

CREATE  TABLE IF NOT EXISTS `User` (
  `UserId` INT NOT NULL AUTO_INCREMENT ,
  `Username` VARCHAR(45) NOT NULL ,
  `Name` VARCHAR(50) NOT NULL ,
  `Email` VARCHAR(100) NOT NULL ,
  `Password` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`UserId`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Group` ;

CREATE  TABLE IF NOT EXISTS `Group` (
  `GroupId` INT NOT NULL AUTO_INCREMENT ,
  `Name` VARCHAR(45) NOT NULL ,
  `Description` TEXT NULL ,
  `Owner_UserId` INT NOT NULL ,
  PRIMARY KEY (`GroupId`) ,
  INDEX `fk_Group_User` (`Owner_UserId` ASC) ,
  CONSTRAINT `fk_Group_User`
    FOREIGN KEY (`Owner_UserId` )
    REFERENCES `User` (`UserId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Project`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Project` ;

CREATE  TABLE IF NOT EXISTS `Project` (
  `ProjectId` INT NOT NULL AUTO_INCREMENT ,
  `ProjectName` VARCHAR(45) NOT NULL ,
  `Description` TEXT NULL ,
  `Owner_UserId` INT NOT NULL ,
  `Group_GroupId` INT NULL ,
  PRIMARY KEY (`ProjectId`) ,
  INDEX `fk_Project_User1_idx` (`Owner_UserId` ASC) ,
  INDEX `fk_Project_Group1_idx` (`Group_GroupId` ASC) ,
  CONSTRAINT `fk_Project_User1`
    FOREIGN KEY (`Owner_UserId` )
    REFERENCES `User` (`UserId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_Group1`
    FOREIGN KEY (`Group_GroupId` )
    REFERENCES `Group` (`GroupId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Task`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Task` ;

CREATE  TABLE IF NOT EXISTS `Task` (
  `TaskId` INT NOT NULL AUTO_INCREMENT ,
  `Title` VARCHAR(45) NOT NULL ,
  `Description` TEXT NULL ,
  `Priority` INT NULL ,
  `Status` INT NULL ,
  `Deadline` TIMESTAMP NULL ,
  `Owner_UserId` INT NOT NULL ,
  `EstimatedTime` INT NULL ,
  `Created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `Updated` TIMESTAMP NULL ,
  `Group_GroupId` INT NULL ,
  `Project_ProjectId` INT NULL ,
  `ParentId` INT NULL ,
  `RootId` INT NULL ,
  PRIMARY KEY (`TaskId`) ,
  INDEX `fk_Task_User` (`Owner_UserId` ASC) ,
  INDEX `fk_Task_Group1` (`Group_GroupId` ASC) ,
  INDEX `fk_Task_Project1_idx` (`Project_ProjectId` ASC) ,
  INDEX `fk_Task_Task1_idx` (`ParentId` ASC) ,
  INDEX `fk_RootId` (`RootId` ASC) ,
  CONSTRAINT `fk_Task_User`
    FOREIGN KEY (`Owner_UserId` )
    REFERENCES `User` (`UserId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_Group1`
    FOREIGN KEY (`Group_GroupId` )
    REFERENCES `Group` (`GroupId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Task_Project1`
    FOREIGN KEY (`Project_ProjectId` )
    REFERENCES `Project` (`ProjectId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ParentId`
    FOREIGN KEY (`ParentId` )
    REFERENCES `Task` (`TaskId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_RootId`
    FOREIGN KEY (`RootId` )
    REFERENCES `Task` (`TaskId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TaskAssignment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `TaskAssignment` ;

CREATE  TABLE IF NOT EXISTS `TaskAssignment` (
  `Task_TaskId` INT NOT NULL ,
  `User_UserId` INT NOT NULL ,
  INDEX `fk_TaskAssignment_Task1` (`Task_TaskId` ASC) ,
  INDEX `fk_TaskAssignment_User1` (`User_UserId` ASC) ,
  PRIMARY KEY (`Task_TaskId`, `User_UserId`) ,
  CONSTRAINT `fk_TaskAssignment_Task1`
    FOREIGN KEY (`Task_TaskId` )
    REFERENCES `Task` (`TaskId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TaskAssignment_User1`
    FOREIGN KEY (`User_UserId` )
    REFERENCES `User` (`UserId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `GroupMembership`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `GroupMembership` ;

CREATE  TABLE IF NOT EXISTS `GroupMembership` (
  `User_UserId` INT NOT NULL ,
  `Group_GroupId` INT NOT NULL ,
  `PermissionLevel` INT NOT NULL ,
  PRIMARY KEY (`Group_GroupId`, `User_UserId`) ,
  INDEX `fk_GroupMembership_User1` (`User_UserId` ASC) ,
  INDEX `fk_GroupMembership_Group1` (`Group_GroupId` ASC) ,
  CONSTRAINT `fk_GroupMembership_User1`
    FOREIGN KEY (`User_UserId` )
    REFERENCES `User` (`UserId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_GroupMembership_Group1`
    FOREIGN KEY (`Group_GroupId` )
    REFERENCES `Group` (`GroupId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TimeEntry`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `TimeEntry` ;

CREATE  TABLE IF NOT EXISTS `TimeEntry` (
  `Duration` INT NOT NULL ,
  `Task_TaskId` INT NOT NULL ,
  `User_UserId` INT NOT NULL ,
  `Date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `TimeEntryId` INT NOT NULL ,
  PRIMARY KEY (`TimeEntryId`) ,
  INDEX `fk_TimeEntry_User1` (`User_UserId` ASC) ,
  CONSTRAINT `fk_TimeEntry_Task1`
    FOREIGN KEY (`Task_TaskId` )
    REFERENCES `Task` (`TaskId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TimeEntry_User1`
    FOREIGN KEY (`User_UserId` )
    REFERENCES `User` (`UserId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ProjectMembership`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ProjectMembership` ;

CREATE  TABLE IF NOT EXISTS `ProjectMembership` (
  `User_UserId` INT NOT NULL ,
  `Project_ProjectId` INT NOT NULL ,
  `PermissionLevel` INT NOT NULL ,
  PRIMARY KEY (`User_UserId`, `Project_ProjectId`) ,
  INDEX `fk_User_has_Project_Project1_idx` (`Project_ProjectId` ASC) ,
  INDEX `fk_User_has_Project_User1_idx` (`User_UserId` ASC) ,
  CONSTRAINT `fk_User_has_Project_User1`
    FOREIGN KEY (`User_UserId` )
    REFERENCES `User` (`UserId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Project_Project1`
    FOREIGN KEY (`Project_ProjectId` )
    REFERENCES `Project` (`ProjectId` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `User`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `User` (`UserId`, `Username`, `Name`, `Email`, `Password`) VALUES (1, 'flemming', 'Flemming Madsen', 'mail@leflings.dk', 'flemming');
INSERT INTO `User` (`UserId`, `Username`, `Name`, `Email`, `Password`) VALUES (2, 'jesper', 'Jesper Plantener', 'jesper@plantener.dk', 'jesper');
INSERT INTO `User` (`UserId`, `Username`, `Name`, `Email`, `Password`) VALUES (3, 'themi', 'Themi Tsiotas von Pfaler', 'themi1234@gmail.com', 'themi');
INSERT INTO `User` (`UserId`, `Username`, `Name`, `Email`, `Password`) VALUES (4, 'mads', 'Mads Hornbeck', 'madshornbeck@gmail.com', 'mads');
INSERT INTO `User` (`UserId`, `Username`, `Name`, `Email`, `Password`) VALUES (5, 'tor', 'Tor Hoset', 'ukendt', 'tor');

COMMIT;

-- -----------------------------------------------------
-- Data for table `Group`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `Group` (`GroupId`, `Name`, `Description`, `Owner_UserId`) VALUES (1, 'Gruppe 10', 'Hej, vi hygger', 1);
INSERT INTO `Group` (`GroupId`, `Name`, `Description`, `Owner_UserId`) VALUES (2, 'Smacky', 'Her ryges smack', 2);

COMMIT;

-- -----------------------------------------------------
-- Data for table `Project`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `Project` (`ProjectId`, `ProjectName`, `Description`, `Owner_UserId`, `Group_GroupId`) VALUES (1, 'TaskManagement', 'Det er faktisk det her projekt vi har gang i', 1, 1);

COMMIT;

-- -----------------------------------------------------
-- Data for table `Task`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `Task` (`TaskId`, `Title`, `Description`, `Priority`, `Status`, `Deadline`, `Owner_UserId`, `EstimatedTime`, `Created`, `Updated`, `Group_GroupId`, `Project_ProjectId`, `ParentId`, `RootId`) VALUES (1, 'Arbejde', NULL, 2, 1, NULL, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `Task` (`TaskId`, `Title`, `Description`, `Priority`, `Status`, `Deadline`, `Owner_UserId`, `EstimatedTime`, `Created`, `Updated`, `Group_GroupId`, `Project_ProjectId`, `ParentId`, `RootId`) VALUES (2, 'Fritid', 'Her holder jeg fri', 1, 1, NULL, 1, 0, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `Task` (`TaskId`, `Title`, `Description`, `Priority`, `Status`, `Deadline`, `Owner_UserId`, `EstimatedTime`, `Created`, `Updated`, `Group_GroupId`, `Project_ProjectId`, `ParentId`, `RootId`) VALUES (3, 'Lav datalayer færdig', 'Det skal laves færdig du', 3, 1, NULL, 1, 0, NULL, NULL, 1, 1, NULL, NULL);

COMMIT;

-- -----------------------------------------------------
-- Data for table `TaskAssignment`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `TaskAssignment` (`Task_TaskId`, `User_UserId`) VALUES (3, 2);

COMMIT;

-- -----------------------------------------------------
-- Data for table `GroupMembership`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `GroupMembership` (`User_UserId`, `Group_GroupId`, `PermissionLevel`) VALUES (1, 2, 1);
INSERT INTO `GroupMembership` (`User_UserId`, `Group_GroupId`, `PermissionLevel`) VALUES (3, 1, 1);
INSERT INTO `GroupMembership` (`User_UserId`, `Group_GroupId`, `PermissionLevel`) VALUES (2, 1, 1);
INSERT INTO `GroupMembership` (`User_UserId`, `Group_GroupId`, `PermissionLevel`) VALUES (4, 1, 1);
INSERT INTO `GroupMembership` (`User_UserId`, `Group_GroupId`, `PermissionLevel`) VALUES (5, 1, 1);

COMMIT;

-- -----------------------------------------------------
-- Data for table `ProjectMembership`
-- -----------------------------------------------------
START TRANSACTION;
INSERT INTO `ProjectMembership` (`User_UserId`, `Project_ProjectId`, `PermissionLevel`) VALUES (3, 1, 1);
INSERT INTO `ProjectMembership` (`User_UserId`, `Project_ProjectId`, `PermissionLevel`) VALUES (4, 1, 1);

COMMIT;

