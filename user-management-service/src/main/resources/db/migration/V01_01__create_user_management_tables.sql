create table if not exists user_account
(
    username      text not null primary key,
    password      text not null,
    email_address text not null
);

create table if not exists user_account_roles
(
    username text not null references user_account (username),
    role     text not null
);

insert into user_account(username, password, email_address)
VALUES ('admin', '{bcrypt}$2a$10$f5vCb7/XMrY/tbDTGRwso.TNdxK0mmD41E71zc1F9h1YCfN1gCEA6', 'mathieu.amblard@gmail.com');

insert into user_account_roles(username, role)
VALUES ('admin', 'admin');