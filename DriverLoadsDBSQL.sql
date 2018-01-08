-- MySQL Script to create and populate the DriverLoads Database 
-- Author: R. LaChance

-- -----------------------------------------------------
-- Schema DRIVERLOADS
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS DRIVERLOADS;

CREATE SCHEMA IF NOT EXISTS DRIVERLOADS DEFAULT CHARACTER SET utf8 ;
USE DRIVERLOADS;

-- -----------------------------------------------------
-- Table TRACTORS
-- -----------------------------------------------------
DROP TABLE IF EXISTS TRACTORS;

CREATE TABLE IF NOT EXISTS TRACTORS(
  UNIT_NUMBER INT(4) NOT NULL,
  MODEL_YEAR INT(4) NOT NULL,
  MAKE CHAR(15) NOT NULL,
  PRIMARY KEY (UNIT_NUMBER))
  ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table TRAILERS
-- -----------------------------------------------------
DROP TABLE IF EXISTS TRAILERS;

CREATE TABLE IF NOT EXISTS TRAILERS(
  UNIT_NUMBER INT(8) NOT NULL,
  MODEL_YEAR INT(4) NULL DEFAULT NULL,
  MAKE CHAR(15) NULL DEFAULT NULL,
  PRIMARY KEY (UNIT_NUMBER))
  ENGINE=InnoDB;

-- -----------------------------------------------------
-- Table COMPANY_LOCATIONS
-- -----------------------------------------------------
DROP TABLE IF EXISTS COMPANY_LOCATIONS;

CREATE TABLE IF NOT EXISTS COMPANY_LOCATIONS(
  LOCATION_ID INT NOT NULL AUTO_INCREMENT,
  LOCATION_NAME CHAR(25) NOT NULL,
  STREET CHAR(30),
  CITY CHAR(20),
  STATE CHAR(2),
  ZIP INT(5),
  PRIMARY KEY (LOCATION_ID))
  ENGINE=InnoDB;

ALTER TABLE COMPANY_LOCATIONS AUTO_INCREMENT = 200;

-- -----------------------------------------------------
-- Table CUSTOMER_LOCATIONS
-- -----------------------------------------------------
DROP TABLE IF EXISTS CUSTOMER_LOCATIONS;

CREATE TABLE IF NOT EXISTS CUSTOMER_LOCATIONS(
  LOCATION_ID INT NOT NULL AUTO_INCREMENT,
  LOCATION_NAME CHAR(25) NOT NULL,
  STREET CHAR(30),
  CITY CHAR(20),
  STATE CHAR(2),
  ZIP INT(5),
  PRIMARY KEY (LOCATION_ID))
  ENGINE=InnoDB;

ALTER TABLE CUSTOMER_LOCATIONS AUTO_INCREMENT = 300;

-- -----------------------------------------------------
-- Table DRIVERS
-- -----------------------------------------------------
DROP TABLE IF EXISTS DRIVERS;

CREATE TABLE IF NOT EXISTS DRIVERS(
  DRIVER_ID INT NOT NULL AUTO_INCREMENT,
  LAST_NAME CHAR(20) NOT NULL,
  FIRST_NAME CHAR(20) NOT NULL,
  STREET CHAR(30),
  CITY CHAR(20),
  STATE CHAR(2),
  ZIP INT(5),
  PRIMARY KEY (DRIVER_ID))
  ENGINE=InnoDB;

ALTER TABLE DRIVERS AUTO_INCREMENT = 100;

-- -----------------------------------------------------
-- Table LOADS
-- -----------------------------------------------------
DROP TABLE IF EXISTS LOADS;

CREATE TABLE IF NOT EXISTS LOADS(
  LOAD_ID INT NOT NULL AUTO_INCREMENT,
  LOAD_NUMBER CHAR(7),
  LOAD_DATE DATE NOT NULL,
  DRIVER_ID INT(3) NOT NULL,
  TRACTOR_ID INT(4) NOT NULL,
  TRAILER_ID INT(8) NOT NULL,
  PICKUP_ID INT(3) NOT NULL,
  DESTINATION_ID INT(3) NOT NULL,
  DELIVERY_ID INT(3) NOT NULL,
  PRIMARY KEY (LOAD_ID),

  FOREIGN KEY (DRIVER_ID)
    REFERENCES DRIVERS(DRIVER_ID),
  
  FOREIGN KEY (TRACTOR_ID)
    REFERENCES TRACTORS(UNIT_NUMBER),

  FOREIGN KEY (TRAILER_ID)
    REFERENCES TRAILERS(UNIT_NUMBER))
  ENGINE=InnoDB;

ALTER TABLE LOADS AUTO_INCREMENT = 1000;

-- -----------------------------------------------------
-- INSERT INTO TRACTORS
-- -----------------------------------------------------
INSERT INTO TRACTORS
VALUES
('3681','2014','Peterbilt');

-- -----------------------------------------------------
-- INSERT INTO TRAILERS
-- -----------------------------------------------------
INSERT INTO TRAILERS 
VALUES
('53454',NULL,NULL),('11696',NULL,NULL);

-- -----------------------------------------------------
-- INSERT INTO COMPANY_LOCATIONS
-- -----------------------------------------------------
INSERT INTO COMPANY_LOCATIONS (LOCATION_NAME,STREET,CITY,STATE,ZIP)
VALUES
('Bolingbrook','W. Crossroads Parkway','Bolingbrook','IL','60440'),
('West Chester',NULL,NULL,'OH',NULL),
('Jam','W. Hill Rd','Flint','MI','48507'),
('Ryder','Dort Hwy','Flint','MI','48506'),
('Mich City',NULL,'Michigan City','IN',NULL);

-- -----------------------------------------------------
-- INSERT INTO CUSTOMER_LOCATIONS
-- -----------------------------------------------------
INSERT INTO CUSTOMER_LOCATIONS (LOCATION_NAME,STREET,CITY,STATE,ZIP)
VALUES
('YPSI',NULL,'Ypsilanti','MI',NULL),
('Swartz Creek 01',NULL,'Swartz Creek','MI',NULL),
('Davison 77','Davison Rd','Burton','MI',NULL),
('Pontiac 75',NULL,'Pontiac','MI',NULL),
('Lansing',NULL,'Lansing','MI',NULL),
('MCD','Torrey Rd','Flint','MI',NULL),
('CCRC','Dort Hwy','Flint','MI',NULL),
('Northgate','Corunna Rd','Flint','MI',NULL),
('Focus Hope','Joy Rd','Detroit','MI',NULL),
('Hardware Short',NULL,NULL,NULL,NULL),
('Hardware Long',NULL,NULL,NULL,NULL),
('Shaheen','American Rd','Lansing','MI',NULL);

-- -----------------------------------------------------
-- INSERT INTO DRIVERS
-- -----------------------------------------------------
INSERT INTO DRIVERS (LAST_NAME,FIRST_NAME)
VALUES
('Shells','Gavin');