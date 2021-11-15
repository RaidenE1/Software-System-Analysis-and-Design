drop table if exists paper;

create table paper
(
    id             varchar(24) primary key,
    title          varchar(50),
    author         varchar(20),
    published_year int,
    pdf_url        varchar(200),
    abstraction    text,
    type           varchar(20)
);

insert into paper (id, title, author, published_year, pdf_url, abstraction, type)
values ('123412345678123456781234', '期刊', 'zou', 2021,
        'https://www.cv-foundation.org/openaccess/content_iccv_2013/papers/Baktashmotlagh_Unsupervised_Domain_Adaptation_2013_ICCV_paper.pdf',
        'abstraction', '期刊'),
       ('123412345678123456781235', '会议', 'zou', 2020,
        'https://www.cv-foundation.org/openaccess/content_iccv_2013/papers/Baktashmotlagh_Unsupervised_Domain_Adaptation_2013_ICCV_paper.pdf',
        'abstraction', '会议');

select *
from paper;