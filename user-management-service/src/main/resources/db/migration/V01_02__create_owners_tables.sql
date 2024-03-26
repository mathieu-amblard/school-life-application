create table if not exists owners
(
    "owner"  text not null references users (username),
    username text not null references users (username)
);
