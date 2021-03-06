CREATE DATABASE `daaexample`;

CREATE TABLE `daaexample`.`people` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(50) DEFAULT NULL,
	`surname` varchar(100) DEFAULT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `daaexample`.`users` (
	`login` varchar(100) NOT NULL,
	`password` varbinary(64) DEFAULT NULL,
	PRIMARY KEY (`login`)
);

CREATE TABLE `daaexample`.`state` (
	`id` int NOT NULL AUTO_INCREMENT,
	`street` varchar(100) DEFAULT NULL,
	`number` int DEFAULT NULL,
	`locality` varchar(100) DEFAULT NULL,
	`province` varchar(60) DEFAULT NULL,
	PRIMARY KEY (`id`)
);

GRANT ALL ON `daaexample`.* TO 'daa'@'localhost' IDENTIFIED BY 'daa';

INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Antón','Pérez');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Manuel','Martínez');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Laura','Reboredo');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Perico','Palotes');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Ana','María');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'María','Nuevo');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Alba','Fernández');
INSERT INTO `daaexample`.`people` (`id`,`name`,`surname`) VALUES (0,'Asunción','Jiménez');

INSERT INTO `daaexample`.`users` (`login`,`password`) VALUES ('mrjato', '59189332a4abf8ddf66fde068cad09eb563b4bd974f7663d97ff6852a7910a73');

INSERT INTO `daaexample`.`state` (`id`,`street`,`number`,`locality`,`province`) VALUES (0,'Principal',21,'Lalin','Pontevedra');
INSERT INTO `daaexample`.`state` (`id`,`street`,`number`,`locality`,`province`) VALUES (0,'Joaquin Loriga',5,'Lalin','Pontevedra');
INSERT INTO `daaexample`.`state` (`id`,`street`,`number`,`locality`,`province`) VALUES (0,'Perico',200,'Lol','Milán');
