CREATE DATABASE catalog_db;
USE catalog_db;

CREATE TABLE artists (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         first_name VARCHAR(50) NOT NULL,
                         last_name VARCHAR(50) NOT NULL,
                         bio TEXT,
                         birth_year INT
);

CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE paintings (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           title VARCHAR(150) NOT NULL,
                           artist_id BIGINT NOT NULL,
                           category_id BIGINT,
                           price DECIMAL(10,2) NOT NULL,
                           year_created INT,
                           status ENUM('AVAILABLE','RESERVED','ON_EXHIBITION','SOLD') NOT NULL DEFAULT 'AVAILABLE',
                           created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (artist_id) REFERENCES artists(id),
                           FOREIGN KEY (category_id) REFERENCES categories(id)
);