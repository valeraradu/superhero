-- see also: https://github.com/spring-projects/spring-session/blob/master/spring-session/src/main/resources/org/springframework/session/jdbc/schema-mysql.sql
CREATE TABLE SPRING_SESSION (
	SESSION_ID CHAR(36),
	CREATION_TIME BIGINT NOT NULL,
	LAST_ACCESS_TIME BIGINT NOT NULL,
	MAX_INACTIVE_INTERVAL INT NOT NULL,
	PRINCIPAL_NAME VARCHAR(100),
	CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (SESSION_ID)
) ENGINE=InnoDB;

CREATE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (LAST_ACCESS_TIME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
	SESSION_ID CHAR(36),
	ATTRIBUTE_NAME VARCHAR(200),
	ATTRIBUTE_BYTES BLOB,
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_ID, ATTRIBUTE_NAME),
	CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_ID) REFERENCES SPRING_SESSION(SESSION_ID) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE INDEX SPRING_SESSION_ATTRIBUTES_IX1 ON SPRING_SESSION_ATTRIBUTES (SESSION_ID);

CREATE TABLE `Superhero_allies` (
  `Superhero_id` bigint(20) NOT NULL,
  `allies` varchar(255) DEFAULT NULL,
  KEY `FK7rwbp7ks8w9w3ll5wwv9bmq0n` (`Superhero_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `Superhero_skills` (
  `Superhero_id` bigint(20) NOT NULL,
  `skills` varchar(255) DEFAULT NULL,
  KEY `FKpcx281h52674jupg0rqdir1x5` (`Superhero_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


CREATE TABLE `superhero` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_appearance` datetime NOT NULL,
  `name` varchar(80) NOT NULL,
  `pseudonym` varchar(80) NOT NULL,
  `publisher` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pdqx7vmcftags8txbs5cubg0l` (`name`),
  UNIQUE KEY `UK_8hyfj9eoest56hi11vacp3hy5` (`pseudonym`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;