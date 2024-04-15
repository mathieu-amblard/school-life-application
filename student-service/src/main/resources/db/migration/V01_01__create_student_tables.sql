create table if not exists students
(
    id         serial primary key,
    student_id text not null unique,
    username   text not null unique,
    lastname   text not null,
    firstname  text not null,
    birthdate  date not null
);