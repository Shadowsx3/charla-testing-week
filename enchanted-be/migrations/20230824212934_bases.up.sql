-- Add up migration script here

insert into plants (id, name,description,coins_to_collect,file_path) VALUES (1, 'Plantita', 'NaN', 300, 'rosa');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (2, 'Plantita2', 'NN', 1300, 'rosa');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (3, 'Plantita3', 'N3N', 3300, 'rosa');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (4, 'Plantita4', 'N4N', 50, 'rosa');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (5, 'Goldi', 'NaN5', 9999, 'rosa');

insert into achievements (id, name,description,code,file_path,plants_id) VALUES (1, 'Wow, that is a session', 'Idk, just it, thanks', 'LOG1N', '/achievements/1.png',1);
insert into achievements (id, name,description,code,file_path,plants_id) VALUES (2, 'Click on profile', 'Idk, just it, thanks', 'PROF1LE', '/achievements/2.png',2);
insert into achievements (id, name,description,code,file_path,plants_id) VALUES (3, 'Get an incredible plant', 'Idk, just it, thanks', 'PL4NT', '/achievements/3.png',3);
insert into achievements (id, name,description,code,file_path,plants_id) VALUES (4, 'Secret1', 'Idk, just it, thanks', 'S3CRET', '/achievements/4.png',4);
insert into achievements (id, name,description,code,file_path,plants_id) VALUES (5, 'Secret2', 'Idk, just it, thanks', 'SECR3T', '/achievements/5.png',5);
insert into achievements (id, name,description,code,file_path,plants_id) VALUES (6, 'Secret3', 'Idk, just it, thanks', '5ECRET', '/achievements/6.png',1);


insert into events (id,name,start_date,end_date) VALUES (1, 'ALWAYS', '2016-06-22 00:00:00-03', '2099-06-22 19:10:25-07');
insert into events (id,name,start_date,end_date) VALUES (2, 'HallOWin', '2022-06-22 00:00:00-03', '2031-06-22 19:10:25-07');


insert into store_items (id,cost,description,event_id,plant_id) VALUES (1, 100, 'Plantitas con cosas', 1,1);
insert into store_items (id,cost,description,event_id,plant_id) VALUES (2, 100, 'Caro', 1,4);
insert into store_items (id,cost,description,event_id,plant_id) VALUES (3, 1000, 'PackHall', 2,5);
insert into store_items (id,cost,description,event_id,plant_id) VALUES (4, 1000, 'Caro2', 1,4);

INSERT INTO users (id,email,name,role,password,next_claim_energy_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 'a', 'a', 'User', '$argon2id$v=19$m=19456,t=2,p=1$ISVS9jnSynRhBOXdegt61Q$N4+TsK8Y3EVwuSjryiGK/gILTakS+5o7sZiNZnZK3sY', now());

insert into users_plants (user_id,plants_id,next_collect_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 1, now());
insert into users_plants (user_id,plants_id,next_collect_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 2, now());
insert into users_plants (user_id,plants_id,next_collect_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 3, now());
insert into users_plants (user_id,plants_id,next_collect_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 4, now());
insert into users_plants (user_id,plants_id,next_collect_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 5, now());