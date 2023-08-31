-- Add up migration script here

insert into plants (id, name,description,coins_to_collect,file_path) VALUES (1, 'Bamboo', 'NaN', 300, 'bamboo');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (2, 'Cactus', 'NN', 1300, 'cactus');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (4, 'Coffee beans', 'N4N', 50, 'coffee_beans');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (5, 'Leaf', 'NaN5', 9999, 'leaf');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (7, 'Mushroom', 'NN', 1300, 'mushroom');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (8, 'Plant', 'N3N', 3300, 'plant_alone');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (9, 'Pumpkin', 'N4N', 50, 'pumpkin');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (10, 'Rose', 'NaN5', 9999, 'rose');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (11, 'Sunflower', 'NaN', 300, 'sunflower');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (12, 'Tulip', 'NN', 1300, 'tulip');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (3, 'Waterlily', 'N3N', 3300, 'waterlily');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (6, 'Wheat', 'N4N', 50, 'wheat');


insert into achievements (id, name,description,code,file_path,plants_id) VALUES (1, 'Wow, that is a session', 'Idk, just it, thanks', 'LOG1N', '/achievements/1.png',1);
insert into achievements (id, name,description,code,file_path,plants_id) VALUES (2, 'Click on profile', 'Idk, just it, thanks', 'PROF1LE', '/achievements/2.png',2);
insert into achievements (id, name,description,code,file_path,plants_id) VALUES (3, 'Get an incredible plant', 'Idk, just it, thanks', 'PL4NT', '/achievements/3.png',3);
insert into achievements (id, name,description,code,file_path,plants_id) VALUES (4, 'Secret1', 'Idk, just it, thanks', 'S3CRET', '/achievements/4.png',4);
insert into achievements (id, name,description,code,file_path,plants_id) VALUES (5, 'Secret2', 'Idk, just it, thanks', 'SECR3T', '/achievements/5.png',5);
insert into achievements (id, name,description,code,file_path,plants_id) VALUES (6, 'Secret3', 'Idk, just it, thanks', '5ECRET', '/achievements/6.png',1);


insert into events (id,name,start_date,end_date) VALUES (1, 'ALWAYS', '2016-06-22 00:00:00-03', '2099-06-22 19:10:25-07');
insert into events (id,name,start_date,end_date) VALUES (2, 'HallOWin', '2022-06-22 00:00:00-03', '2031-06-22 19:10:25-07');


insert into store_items (id,cost,name,description,event_id,plant_id) VALUES (1, 100, 'Plantitas con cosas','Woooow, sirve', 1,1);
insert into store_items (id,cost,name,description,event_id,plant_id) VALUES (2, 100, 'Caro','Woooow, sirve', 1,4);
insert into store_items (id,cost,name,description,event_id,plant_id) VALUES (3, 1000, 'PackHall','Woooow, sirve', 2,5);
insert into store_items (id,cost,name,description,event_id,plant_id) VALUES (4, 1000, 'Caro2','Woooow, sirve', 1,4);

INSERT INTO users (id,email,name,role,password,next_claim_energy_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 'a', 'a', 'User', '$argon2id$v=19$m=19456,t=2,p=1$ISVS9jnSynRhBOXdegt61Q$N4+TsK8Y3EVwuSjryiGK/gILTakS+5o7sZiNZnZK3sY', now());

