drop table if exists commentshare;

create table commentshare
(
    commentshareid int primary key auto_increment,
    shareid        int,
    commentsharecreatetime        DATE ,
    commentcontent        varchar(200)
);

insert into commentshare (shareid, commentsharecreatetime, commentcontent)
values (1, Date'2021-10-11','真不错'),(1, Date'2021-12-11','太扯了');


select *
from commentshare;