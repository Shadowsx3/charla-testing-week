-- Add up migration script here

insert into pictures (description,file_path) VALUES ('First picture', '1.png');
insert into pictures (description,file_path) VALUES ('Second picture', '2.png');
insert into pictures (description,file_path) VALUES ('Third picture', '3.png');
insert into pictures (description,file_path) VALUES ('Fourth picture', '4.png');
insert into pictures (description,file_path) VALUES ('Fifth picture', '5.png');
insert into pictures (description,file_path) VALUES ('Six picture', '6.png');
insert into pictures (description,file_path) VALUES ('Seven picture', '7.png');
insert into pictures (description,file_path) VALUES ('Eight picture', '8.png');
insert into pictures (description,file_path) VALUES ('Nine picture', '9.png');

insert into plants (id, name,description,coins_to_collect,file_path) VALUES (1, 'Plantita', 'NaN', 300, '/plants/1.png');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (2, 'Plantita2', 'NN', 1300, '/plants/2.png');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (3, 'Plantita3', 'N3N', 3300, '/plants/3.png');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (4, 'Plantita4', 'N4N', 50, '/plants/4.png');
insert into plants (id, name,description,coins_to_collect,file_path) VALUES (5, 'Goldi', 'NaN5', 9999, '/plants/4.png');

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


