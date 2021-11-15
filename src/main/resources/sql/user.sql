drop table if exists user;

create table user
(
    id       int primary key auto_increment,
    username varchar(50),
    password varchar(50),
    email    varchar(50)
);



insert into user (username, password, email)
values ('zou', 'zou', 'zou@qq.com'),
       ('wen', 'wen', 'wen@qq.com');

select *
from user;