-- Add down migration script here

delete from pictures;
delete from achievements;
delete from plants;
insert into users_plants (user_id,plants_id,next_collect_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 1, now());
insert into users_plants (user_id,plants_id,next_collect_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 2, now());
insert into users_plants (user_id,plants_id,next_collect_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 3, now());
insert into users_plants (user_id,plants_id,next_collect_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 4, now());
insert into users_plants (user_id,plants_id,next_collect_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 5, now());