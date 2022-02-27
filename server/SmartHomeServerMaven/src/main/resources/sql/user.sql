create table users (
id int(11) not null AUTO_INCREMENT,
uuid varchar(20) not null,
username varchar(10),
userphone varchar(11) not null,
password VARCHAR (10) not null,
sex enum('boy','girl'),
register_time DATETIME not null,
PRIMARY key(id)
);