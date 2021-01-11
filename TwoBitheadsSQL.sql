CREATE SCHEMA `twobitheadsbnb` ;
CREATE TABLE `reservations` (
  `idreservations` int NOT NULL,
  `guestname` varchar(45) NOT NULL,
  `email` varchar(60) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `arrival` date NOT NULL,
  `departure` date NOT NULL,
  `rooms` int NOT NULL,
  `price` int NOT NULL,
  PRIMARY KEY (`idreservations`)
);

Insert into reservations( guestname, email, phone, arrival, departure, rooms, price)
values( 'Garrett','my@email.com', '123-456-7890', '2021-01-05', '2021-01-12', 2, 200);

select * from reservations;
select guestName from reservations;

ALTER TABLE `twobitheadsbnb`.`reservations` 
CHANGE COLUMN `idreservations` `idreservations` INT NOT NULL AUTO_INCREMENT;
alter table `twobitheadsbnb`.`reservations` auto_increment=1;
