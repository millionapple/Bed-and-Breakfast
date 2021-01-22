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
CREATE TABLE `twobitheadsbnb`.`rooms` (
  `idrooms` INT NOT NULL,
  `occupied` VARCHAR(6) NOT NULL,
  PRIMARY KEY (`idrooms`));
CREATE TABLE `twobitheadsbnb`.`reserv_rooms` (
  `reservid` INT NOT NULL,
  `roomid` INT NOT NULL);

Insert into reservations( guestname, email, phone, arrival, departure, rooms, price)
values( 'Garrett','my@email.com', '123-456-7890', '2021-01-05', '2021-01-12', 2, 200);

INSERT INTO `twobitheadsbnb`.`rooms` (`idrooms`, `occupied`) VALUES ('1', 'false');
INSERT INTO `twobitheadsbnb`.`rooms` (`idrooms`, `occupied`) VALUES ('2', 'false');
INSERT INTO `twobitheadsbnb`.`rooms` (`idrooms`, `occupied`) VALUES ('3', 'false');
INSERT INTO `twobitheadsbnb`.`rooms` (`idrooms`, `occupied`) VALUES ('4', 'false');
INSERT INTO `twobitheadsbnb`.`rooms` (`idrooms`, `occupied`) VALUES ('5', 'false');
INSERT INTO `twobitheadsbnb`.`rooms` (`idrooms`, `occupied`) VALUES ('6', 'false');
INSERT INTO `twobitheadsbnb`.`rooms` (`idrooms`, `occupied`) VALUES ('7', 'false');
INSERT INTO `twobitheadsbnb`.`rooms` (`idrooms`, `occupied`) VALUES ('8', 'false');

insert into `twobitheadsbnb`.`reserv_rooms` (`reservid`, `roomid`) 
values ('11','1');
insert into `twobitheadsbnb`.`reserv_rooms` (`reservid`, `roomid`) 
values ('11','2');
insert into `twobitheadsbnb`.`reserv_rooms` (`reservid`, `roomid`) 
values ('11','4');

select idreservations from reservations
where guestname='Garrett' and email='' and phone='' and arrival='2021-01-18' and departure = '2021-01-20' and rooms = '1' and price='200';
select * from reservations;
select * from rooms;
select * from reserv_rooms;
select guestName from reservations;

ALTER TABLE `twobitheadsbnb`.`reservations` 
CHANGE COLUMN `idreservations` `idreservations` INT NOT NULL AUTO_INCREMENT;
alter table `twobitheadsbnb`.`reservations` auto_increment=1;
ALTER TABLE reservations ADD days int;
alter table reservations add idrooms int;
alter table reservations add foreign key (idrooms) references rooms(idrooms);
alter table rooms drop column occupied;
alter table reserv_rooms add foreign key (reservid) references reservations(idreservations);
alter table reserv_rooms add foreign key (roomid) references rooms(idrooms);

  delete from reserv_rooms where reservid = 11;
