DROP DATABASE IF EXISTS airworthDB;

CREATE DATABASE airworthDB;

USE airworthDB;

CREATE TABLE directives (
	id INT AUTO_INCREMENT PRIMARY KEY,
    tail_number VARCHAR(10) NOT NULL,
	make VARCHAR (10) NOT NULL,
	model VARCHAR(10) NOT NULL,
    compliance VARCHAR(1000) NOT NULL,
	ad_number VARCHAR(25) NOT NULL,
    effective_date DATE NULL,
    `subject` VARCHAR(100) NOT NULL,
    not_complied_with BOOL NOT NULL
);

CREATE TABLE `admin`(
	username VARCHAR(50) PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(50) NOT NULL
);

CREATE TABLE maintainer (
	username VARCHAR(50) PRIMARY KEY,
	first_name VARCHAR(30) NOT NULL,
	last_name VARCHAR(50) NOT NULL
);

CREATE TABLE plane (
	tail_number VARCHAR(10) PRIMARY KEY,
    `year` INT NULL,
    make VARCHAR (10) NOT NULL,
	model VARCHAR(20) NOT NULL,
    last_serviced_date DATE NOT NULL,
    next_service_date DATE NOT NULL
);

CREATE TABLE directives_plane (
	id INT NOT NULL,
	tail_number VARCHAR(10) NOT NULL,
	PRIMARY KEY (id, tail_number),
		FOREIGN KEY (id)
			REFERENCES directives(id),
		FOREIGN KEY (tail_number)
			REFERENCES plane(tail_number)
);


