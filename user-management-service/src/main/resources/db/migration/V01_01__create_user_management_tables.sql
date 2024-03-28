create table if not exists user_account
(
    username      text not null primary key,
    password      text not null,
    email_address text not null
);

create table if not exists user_account_roles
(
    username text not null references user_account (username),
    role     text not null,
    unique (username, role)
);

create table if not exists user_account_owner
(
    username text not null references user_account (username),
    "owner"  text not null references user_account (username),
    unique (username, "owner")
);

-- admin

insert into user_account(username, password, email_address)
VALUES ('admin', '{bcrypt}$2a$10$f5vCb7/XMrY/tbDTGRwso.TNdxK0mmD41E71zc1F9h1YCfN1gCEA6', 'mathieu.amblard@gmail.com');
insert into user_account_roles(username, role)
VALUES ('admin', 'admin');

-- teacher

insert into user_account(username, password, email_address)
VALUES ('teacher', '{bcrypt}$2a$10$qMdVKFzHAqr4MDv5GfUu/.XOQur8A6c2.rapxkRjh1FaDScf9VOeu', 'mathieu.amblard@gmail.com');
insert into user_account_roles(username, role)
VALUES ('teacher', 'teacher');
insert into user_account_owner(username, owner)
VALUES ('teacher', 'admin');

-- student

insert into user_account(username, password, email_address)
VALUES ('student', '{bcrypt}$2a$10$qPColzcKOFWAV112rLSY..Eo6lzq0dH/PaSNsjRf/MICPEkLFMvxq', 'mathieu.amblard@gmail.com');
insert into user_account_roles(username, role)
VALUES ('student', 'student');
insert into user_account_owner(username, owner)
VALUES ('student', 'teacher');