create table if not exists users
(
    id            serial primary key,
    username      text not null unique,
    password      text not null,
    email_address text not null,
    owner         text
);

create table if not exists user_roles
(
    user_id  integer not null references users (id),
    username text    not null references users (username),
    role     text    not null,
    unique (username, role)
);

-- admin

insert into users(username, password, email_address, owner)
VALUES ('admin', '{bcrypt}$2a$10$f5vCb7/XMrY/tbDTGRwso.TNdxK0mmD41E71zc1F9h1YCfN1gCEA6', 'mathieu.amblard@gmail.com', 'admin');
insert into user_roles(user_id, username, role)
VALUES ((select lastval() from users limit 1), 'admin', 'admin');

-- teacher

insert into users(username, password, email_address, owner)
VALUES ('teacher', '{bcrypt}$2a$10$qMdVKFzHAqr4MDv5GfUu/.XOQur8A6c2.rapxkRjh1FaDScf9VOeu', 'mathieu.amblard@gmail.com', 'admin');
insert into user_roles(user_id, username, role)
VALUES ((select lastval() from users limit 1), 'teacher', 'teacher');

-- student

insert into users(username, password, email_address, owner)
VALUES ('student', '{bcrypt}$2a$10$qPColzcKOFWAV112rLSY..Eo6lzq0dH/PaSNsjRf/MICPEkLFMvxq', 'mathieu.amblard@gmail.com', 'admin');
insert into user_roles(user_id, username, role)
VALUES ((select lastval() from users limit 1), 'student', 'student');