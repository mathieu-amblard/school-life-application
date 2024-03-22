create table if not exists users
(
    username      text not null primary key,
    password      text not null,
    email_address text not null
);

create table if not exists authorities
(
    username  text not null references users (username),
    authority text not null
);

insert into users(username, password, email_address)
VALUES ('admin', '{bcrypt}$2a$10$f5vCb7/XMrY/tbDTGRwso.TNdxK0mmD41E71zc1F9h1YCfN1gCEA6', 'mathieu.amblard@gmail.com');

insert into authorities(username, authority)
VALUES ('admin', 'admin');