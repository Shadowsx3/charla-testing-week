-- Add up migration script here

-- Insert plant data
INSERT INTO plants (id, name, description, coins_to_collect, file_path) VALUES
(1, 'Bamboo', 'Tall and fast-growing plant.', 300, 'bamboo'),
(2, 'Cactus', 'Succulent desert plant with spines.', 1300, 'cactus'),
(3, 'Coffee beans', 'Produces beans used for coffee.', 50, 'coffee_beans'),
(4, 'Leaf', 'Simple and common leafy plant.', 9999, 'leaf'),
(5, 'Mushroom', 'Fungi that often grows in damp places.', 1300, 'mushroom'),
(6, 'Plant', 'Basic and versatile flowering plant.', 3300, 'plant_alone'),
(7, 'Pumpkin', 'Vine crop known for large orange fruit.', 50, 'pumpkin'),
(8, 'Rose', 'Classic flowering plant with thorns.', 9999, 'rose'),
(9, 'Sunflower', 'Tall plant with large, sunny flowers.', 300, 'sunflower'),
(10, 'Tulip', 'Colorful bulbous flower in various hues.', 1300, 'tulip'),
(11, 'Waterlily', 'Aquatic plant with floating leaves.', 3300, 'waterlily'),
(12, 'Wheat', 'Common cereal crop used for food.', 50, 'wheat');

-- Insert achievement data
INSERT INTO achievements (id, name, description, code, file_path, plants_id) VALUES
(1, 'Lets save money', 'Achieve this by growing Bamboo.', 'B4NK', 'bank', 1),
(2, 'Wow, a gift', 'Achieve this by growing Cactus.', 'B0X', 'box', 2),
(3, 'I LOVE COFFE', 'Achieve this by growing Coffee beans.', 'C0FFE', 'coffee', 3),
(4, 'RainBow', 'Achieve this by growing Leaf.', 'C0LOR', 'color', 4),
(5, 'Perfect, right?', 'Achieve this by growing Mushroom.', 'C0MPASS', 'compass', 5),
(6, 'Another money', 'Achieve this by growing Bamboo.', 'DI4MOND', 'diamond', 6),
(7, 'Que dice?', 'Achieve this by growing Bamboo.', 'D1CE', 'dice', 7),
(8, 'Lets play', 'Achieve this by growing Cactus.', 'G4ME', 'game', 8),
(9, 'Science!', 'Achieve this by growing Coffee beans.', 'M1CRO', 'microscope', 9),
(10, 'Coooiiins', 'Achieve this by growing Leaf.', 'M0NEY', 'money', 10),
(11, 'The tribe', 'Achieve this by growing Mushroom.', 'TR1BAL', 'tribal', 11);


insert into events (id,name,start_date,end_date) VALUES (1, 'ALWAYS', '2016-06-22 00:00:00-03', '2099-06-22 19:10:25-07');
insert into events (id,name,start_date,end_date) VALUES (2, 'HallOWin', '2022-06-22 00:00:00-03', '2031-06-22 19:10:25-07');


insert into store_items (id,cost,name,description,event_id,plant_id) VALUES (1, 100, 'Plantitas con cosas','Woooow, sirve', 1,1);
insert into store_items (id,cost,name,description,event_id,plant_id) VALUES (2, 100, 'Caro','Woooow, sirve', 1,4);
insert into store_items (id,cost,name,description,event_id,plant_id) VALUES (3, 1000, 'PackHall','Woooow, sirve', 2,5);
insert into store_items (id,cost,name,description,event_id,plant_id) VALUES (4, 1000, 'Caro2','Woooow, sirve', 1,4);

INSERT INTO users (id,email,name,role,password,next_claim_energy_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 'a', 'a', 'User', '$argon2id$v=19$m=19456,t=2,p=1$ISVS9jnSynRhBOXdegt61Q$N4+TsK8Y3EVwuSjryiGK/gILTakS+5o7sZiNZnZK3sY', now());

