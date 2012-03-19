CREATE DATABASE `spac-stats`;


DROP TABLE IF EXISTS `category_old`;

CREATE TABLE `category_old` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `prefix` varchar(8) NOT NULL,
  `name` varchar(64) NOT NULL,
  `coefficient` decimal(5,2) NOT NULL DEFAULT '1.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `race_old`;

CREATE TABLE `race_old` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `date` date DEFAULT NULL,
  `place` varchar(64) DEFAULT NULL,
  `championship` int(11) NOT NULL DEFAULT '0',
  `season` int(11) NOT NULL DEFAULT '2010',
  `notice` varchar(256) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `racer_old`;

CREATE TABLE `racer_old` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_category` int(11) NOT NULL,
  `id_team` int(11) DEFAULT NULL,
  `first_name` varchar(64) NOT NULL,
  `surname` varchar(64) NOT NULL,
  `year_born` int(11) DEFAULT NULL,
  `spac_number` int(11) NOT NULL,
  `season` int(11) NOT NULL DEFAULT '2010',
  `team_name` varchar(64) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `result_old`;

CREATE TABLE `result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_race` int(11) NOT NULL,
  `id_racer` int(11) NOT NULL,
  `place` int(11) NOT NULL,
  `points` decimal(10,2) NOT NULL,
  `finished` int(11) NOT NULL DEFAULT '1',
  `length` decimal(5,2) DEFAULT NULL,
  `total_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `result_team_old`;

CREATE TABLE `result_team_old` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_race` int(11) NOT NULL,
  `id_team` int(11) NOT NULL,
  `points` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `team_old`;

CREATE TABLE `team_old` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `count_racers` int(11) DEFAULT '0',
  `season` int(11) NOT NULL DEFAULT '2010',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

