-- Make a table column nullable
alter table table_name modify (column_name NULL);
-- Create table with a default not null column
create table table_name (column_name varchar(100) default '' not null enable);
-- Create table with primary key
create table table_name (column_name varchar(100) not null enable, CONSTRAINT table_name_pk PRIMARY KEY (column_name));
-- Create table with foreign key
create table table_name (column_name varchar(100) not null enable, CONSTRAINT table_name_fk FOREIGN KEY (column_name) REFERENCES rf_table_name (rf_column_name));