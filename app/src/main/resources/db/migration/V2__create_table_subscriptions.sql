create table subscriptions
(
    id                     bigserial primary key,
    subscription_date_time timestamp(6),
    subscription_name      varchar(255),
    user_id                bigint constraint fkhro52ohfqfbay9774bev0qinr references users
);