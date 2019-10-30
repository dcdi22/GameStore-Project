create schema if not exists game_store;
create schema if not exists game_store_test;
use game_store;
-- use game_store_test;


create table if not exists game (
    game_id int(11) not null auto_increment primary key,
    title varchar(50) not null,
    esrb_rating varchar(50) not null,
    description varchar(255) not null,
    price decimal(5, 2) not null,
    studio varchar(50) not null,
    quantity int(11)
);

create table if not exists console (
    console_id int(11) not null auto_increment primary key,
    model varchar(50) not null,
    manufacturer varchar(50) not null,
    memory_amount varchar(20),
    processor varchar(20),
    price decimal(5, 2) not null,
    quantity int(11) not null
);

create table if not exists t_shirt (
    t_shirt_id int(11) not null auto_increment primary key,
    size varchar(20) not null,
    color varchar(20) not null,
    description varchar(255) not null,
    price decimal(5,2) not null,
    quantity int(11) not null
);

create table if not exists sales_tax_rate (
    state char(2) not null,
    rate decimal(3,2) not null
);

create unique index ix_state_rate on sales_tax_rate (state, rate);

create table if not exists processing_fee (
    product_type varchar(20) not null,
    fee decimal (4,2)
);

create unique index ix_product_type_fee on processing_fee (product_type, fee);

create table if not exists invoice (
    invoice_id int(11) not null auto_increment primary key,
    name varchar(80) not null,
    street varchar(30) not null,
    city varchar(30) not null,
    state varchar(30) not null,
    zipcode varchar(5) not null,
    item_type varchar(20) not null,
    item_id int(11) not null,
    unit_price decimal(5,2) not null,
    quantity int(11) not null,
    subtotal decimal(5,2) not null,
    tax decimal(5,2) not null,
    processing_fee decimal (5,2) not null,
    total decimal(5,2) not null
);

create table if not exists users(
	username varchar(50) not null primary key,
	password varchar(100) not null,
	enabled boolean not null
);

create table if not exists authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username));
	create unique index ix_auth_username on authorities (username,authority
);

insert into users (username, password, enabled) values ('staff', '$2a$10$MjQyv.dx4EzbYtalp2Ff8O/R50clejbGSeWlaDloyEd3RjICubmcq', true);
insert into users (username, password, enabled) values ('manager', '$2a$10$t6s91GZyRGb9bav05Q1HSOcBGH0.sHDGOmtc2zm4z32ojWc6q0Vum', true);
insert into users (username, password, enabled) values ('admin', '$2a$10$cKuY9hZblmOkRg8MTBWkZudJ7SL6JBPSufN66xro78PHe17H8bFbm', true);

insert into authorities (username, authority) values ('staff', 'ROLE_STAFF');
insert into authorities (username, authority) values ('manager', 'ROLE_STAFF');
insert into authorities (username, authority) values ('manager', 'ROLE_MANAGER');
insert into authorities (username, authority) values ('admin', 'ROLE_STAFF');
insert into authorities (username, authority) values ('admin', 'ROLE_MANAGER');
insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');