CREATE DATABASE order_db;
USE order_db;

CREATE TABLE users (
                       id BIGINT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL
);

CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        status ENUM('PENDING','CONFIRMED','CANCELLED') NOT NULL DEFAULT 'PENDING',
                        total_price DECIMAL(10,2) NOT NULL,
                        created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE order_items (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             order_id BIGINT NOT NULL,
                             painting_id BIGINT NOT NULL,
                             price_at_order_time DECIMAL(10,2) NOT NULL,
                             FOREIGN KEY (order_id) REFERENCES orders(id)
);