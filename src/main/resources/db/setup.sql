create database phoenix_db;

create user 'phoenix_user'@'localhost' identified by "password";

grant all privileges on phoenix_db.* to 'phoenix_user'@'localhost';

flush privileges;