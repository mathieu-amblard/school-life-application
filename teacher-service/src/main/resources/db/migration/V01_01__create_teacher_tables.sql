create table if not exists teachers
(
    id         serial primary key,
    teacher_id text not null unique,
    username   text not null unique,
    lastname   text not null,
    firstname  text not null,
    resume     text not null
);