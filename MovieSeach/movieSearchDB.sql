DROP DATABASE IF EXISTS movieSearchDB;

CREATE DATABASE movieSearchDB;

USE movieSearchDB;

CREATE TABLE movie (
	title VARCHAR(50) PRIMARY KEY,
    director VARCHAR(50),
    plot TEXT,
    mpaa_rating VARCHAR(5),
    year_released VARCHAR(20),
    bookmarked BOOL,
    rating VARCHAR(1)
);
	