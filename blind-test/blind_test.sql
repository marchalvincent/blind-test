SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `blind_test` ;
CREATE SCHEMA IF NOT EXISTS `blind_test` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `blind_test` ;

-- -----------------------------------------------------
-- Table `blind_test`.`banque`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `blind_test`.`banque` ;

CREATE TABLE IF NOT EXISTS `blind_test`.`banque` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `answer` VARCHAR(60) NOT NULL ,
  `name` CHAR(37) NOT NULL ,
  `directory` VARCHAR(100) NOT NULL ,
  `version` INT NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `index_banque_answer` ON `blind_test`.`banque` (`answer` ASC) ;

CREATE INDEX `index_banque_name` ON `blind_test`.`banque` (`name` ASC) ;
CREATE INDEX `index_banque_directory` ON `blind_test`.`banque` (`directory` ASC) ;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
