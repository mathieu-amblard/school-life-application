create table if not exists users (
    username text not null primary key,
    password text not null,
    email_address text not null
);

create table if not exists authorities (
    username text not null references users(username),
    authority text not null
);