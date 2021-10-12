create table user
(
    id       int primary key auto_increment,
    username varchar(50),
    password varchar(50)
);



insert into user (username, password)
values
       ('zou','zou'),
       ('wen','wen');