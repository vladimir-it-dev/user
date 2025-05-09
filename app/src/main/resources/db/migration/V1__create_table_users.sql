create schema if not exists public;

create table users
(
    id         bigserial primary key,
    user_email varchar(255),
    user_name  varchar(255)
);