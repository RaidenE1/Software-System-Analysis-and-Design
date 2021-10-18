drop table user;

create table user
(
    id       int primary key auto_increment,
    username varchar(50),
    password varchar(50),
    email varchar(50)
);



insert into user (username, password,email)
values
       ('zou','zou','2238761683@qq.com'),
       ('wen','wen','2238761683@qq.com');

select * from user;