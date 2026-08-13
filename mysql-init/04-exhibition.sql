CREATE DATABASE exhibition_db;
USE exhibition_db;

CREATE TABLE venues (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        address VARCHAR(200)
);

CREATE TABLE exhibitions (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             title VARCHAR(150) NOT NULL,
                             venue_id BIGINT,
                             start_date DATE NOT NULL,
                             end_date DATE NOT NULL,
                             description TEXT,
                             FOREIGN KEY (venue_id) REFERENCES venues(id)
);

CREATE TABLE exhibition_paintings (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      exhibition_id BIGINT NOT NULL,
                                      painting_id BIGINT NOT NULL,
                                      FOREIGN KEY (exhibition_id) REFERENCES exhibitions(id)
);