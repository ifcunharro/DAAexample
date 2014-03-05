DROP TABLE IF EXISTS `people`;
CREATE TABLE `people` (
	`id` int NOT NULL AUTO_INCREMENT,
	`name` varchar(50) DEFAULT NULL,
	`surname` varchar(100) DEFAULT NULL,
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `state`;
CREATE TABLE `state` (
	`id` int NOT NULL AUTO_INCREMENT,
	`street` varchar(100) DEFAULT NULL,
	`number` int DEFAULT NULL,
	`locality` varchar(100) DEFAULT NULL,
	`province` varchar(60) DEFAULT NULL,
	PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
	`login` varchar(100) NOT NULL,
	`password` varbinary(64) DEFAULT NULL,
	PRIMARY KEY (`login`)
);

INSERT INTO `people` (`id`,`name`,`surname`) VALUES (0, 'Anton', 'Álvarez');
INSERT INTO `people` (`id`,`name`,`surname`) VALUES (0, 'Ana', 'Amargo');
INSERT INTO `people` (`id`,`name`,`surname`) VALUES (0, 'Manuel', 'Martínez');
INSERT INTO `people` (`id`,`name`,`surname`) VALUES (0, 'Mara', 'Márquez');
INSERT INTO `people` (`id`,`name`,`surname`) VALUES (0, 'Lorenzo', 'López');
INSERT INTO `people` (`id`,`name`,`surname`) VALUES (0, 'Laura', 'Laredo');
INSERT INTO `people` (`id`,`name`,`surname`) VALUES (0, 'Perico', 'Palotes');
INSERT INTO `people` (`id`,`name`,`surname`) VALUES (0, 'Patricia', 'Pérez');
INSERT INTO `people` (`id`,`name`,`surname`) VALUES (0, 'Juan', 'Jiménez');
INSERT INTO `people` (`id`,`name`,`surname`) VALUES (0, 'Julia', 'Justa');


INSERT INTO `users` (`login`,`password`) VALUES ('mrjato', '59189332a4abf8ddf66fde068cad09eb563b4bd974f7663d97ff6852a7910a73');

INSERT INTO `state` (`id`,`street`,`number`,`locality`,`province`) VALUES (0,'Principal',21,'Lalin','Pontevedra');
INSERT INTO `state` (`id`,`street`,`number`,`locality`,`province`) VALUES (0,'Joaquin Loriga',5,'Lalin','Pontevedra');
INSERT INTO `state` (`id`,`street`,`number`,`locality`,`province`) VALUES (0,'Perico',200,'Lol','Milán');
