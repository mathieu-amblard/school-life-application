create table if not exists workshops
(
    id          serial primary key,
    workshop_id text not null unique,
    teacher_id  text not null,
    title       text not null,
    description text,
    location    text,
    date        date,
    fromAge     integer,
    toAge       integer,
    minCapacity integer,
    maxCapacity integer
);

create table if not exists registrations
(
    workshop_id integer not null references workshops (id),
    student_id  text    not null,
    unique (workshop_id, student_id)
);