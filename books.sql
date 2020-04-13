create database if not exists ebookstore;
use ebookstore;
drop table if exists books;
create table books (
id int,
title varchar(50),
author varchar(50),
qty int,
primary key (id));
insert into books values (3001, 'A Tale of Two Cities', 'Charles Dickens', 30);
insert into books values (3002, 'Harry Potter and the Philosophers Stone ', 'J.K Rowling', 40);
insert into books values (3003, 'The Lion, the Witch and the Wardrobe', 'C.S Lewis', 25);
insert into books values (3004, 'The Lord of the Rings', 'J.R.R Tolkien', 37);
insert into books values (3005, 'Alice in Wonderland', 'Lewis Carrol', 12);