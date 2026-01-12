CREATE DATABASE IF NOT EXISTS appdb;
USE appdb;

CREATE TABLE IF NOT EXISTS users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50)
);

INSERT INTO users (name) VALUES
('AWS'),
('Java'),
('Tomcat');

-- ✅ New table added (correct place)
CREATE TABLE IF NOT EXISTS employees (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  department VARCHAR(50)
);

-- 🔐 Create app user ONCE, correctly
CREATE USER IF NOT EXISTS 'appuser'@'%' 
IDENTIFIED WITH mysql_native_password BY 'app@123';

GRANT ALL PRIVILEGES ON appdb.* TO 'appuser'@'%';
FLUSH PRIVILEGES;
