drop database if exists stocks;
create database stocks;
use stocks;

create table candle (id integer not null, date datetime, open decimal(19,2), close decimal(19,2), low decimal(19,2), high decimal(19,2), volume decimal(19,2), primary key (id));
create table company (name varchar(255) not null, primary key (name));
create table company_candle (company_name varchar(255) not null, candles_id integer not null);
create table hibernate_sequence (next_val bigint);
insert into hibernate_sequence values ( 1 );
alter table company_candle add constraint UK_w9lod5p3bo2hycop2jhf7pug unique (candles_id);
alter table company_candle add constraint FKakepcreu06scv2lc6qpysg9a7 foreign key (candles_id) references candle (id);
alter table company_candle add constraint FKiwospl5vc9ukqgshpicrdropd foreign key (company_name) references company (name);
alter table company add column stock_currency varchar(255);
alter table company add column business_type varchar(255);