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
(1, 'Lets save money', 'Obtained by discovering how to save money while nurturing Bamboo in your garden.', 'B4NK', 'bank', 1),
(2, 'Wow, a gift', 'Unlocked by learning the art of gift-giving through Cactus gardening. What surprises await?', 'B0X', 'box', 2),
(3, 'I LOVE COFFEE', 'Earned by nurturing Coffee beans and diving deep into the world of coffee. Discover its secrets.', 'C0FFEE', 'coffee', 3),
(4, 'RainBow', 'Achieved by growing Leaf and exploring the vibrant world of colors in your garden. Seek the rainbow!', 'C0LOR', 'color', 4),
(5, 'Perfect, right?', 'Attained by nurturing Mushroom and unveiling the mysteries hidden within this enchanting plant.', 'C0MPASS', 'compass', 5),
(6, 'Another money', 'Secured by growing Bamboo and following the path to riches and secrets paved with diamonds.', 'DI4MOND', 'diamond', 6),
(7, 'Que dice?', 'Earned by rolling the dice of fate while nurturing Bamboo. Discover garden secrets with each roll.', 'D1CE', 'dice', 7),
(8, 'Lets play', 'Unlocked through the playful growth of Cactus. Find secret game elements as you nurture your Cactus.', 'G4ME', 'game', 11),
(9, 'Science!', 'Attained by nurturing Coffee beans and exploring the scientific wonders hidden in your garden.', 'M1CRO', 'microscope', 9),
(10, 'Coooiiins', 'Earned by following the trail of coins through Leaf cultivation. Unearth the secrets of prosperity.', 'M0NEY', 'money', 10),
(11, 'The tribe', 'Joined the tribe of Mushroom growers and uncovered the ancient secrets of this unique plant.', 'TR1BAL', 'tribal', 8);



insert into events (id,name,start_date,end_date) VALUES (1, 'ALWAYS', '2016-06-22 00:00:00-03', '2099-06-22 19:10:25-07');
insert into events (id,name,start_date,end_date) VALUES (2, 'HallOWin', '2022-06-22 00:00:00-03', '2031-06-22 19:10:25-07');


-- Insert store items
INSERT INTO store_items
(id, cost, name, description, event_id, plant_id) VALUES
(1, 10000, 'Small Plant Bundle', 'A bundle of small plants to enhance your garden.', 1, 1),
(2, 1502, 'Leafy Delight', 'A collection of beautiful leafy plants for your garden.', 1, 4),
(3, 5000, 'Halloween Special Pack', 'A special pack of spooky plants for Halloween.', 1, 5),
(4, 200, 'Tiny Succulents', 'A set of cute and tiny succulent plants.', 1, 2),
(5, 120, 'Desert Oasis', 'A variety of desert plants to add some flair to your garden.', 2, 3),
(6, 750, 'Colorful Garden Kit', 'A kit with vibrant and colorful plants for your garden.', 1, 8),
(7, 220, 'Bamboo Charm', 'A bundle of lucky bamboo plants for your home.', 2, 1),
(8, 1609, 'Sunny Collection', 'A collection of sun-loving plants to brighten up your garden.', 1, 9),
(9, 300, 'Exotic Blooms', 'Rare and exotic flowers to make your garden stand out.', 1, 10),
(10, 3150, 'Mystical Mushrooms', 'Enchanting mushrooms to add a touch of magic to your garden.', 1, 5),
(11, 10300, 'Deluxe Rose Garden', 'A luxurious collection of the most beautiful roses.', 1, 4),
(12, 2820, 'Cactus Paradise', 'A paradise of unique cactus plants for enthusiasts.', 2, 2),
(13, 40, 'Tulip Elegance', 'Elegant tulip flowers to bring sophistication to your garden.', 1, 10),
(14, 450, 'Waterlily Serenity', 'Tranquil waterlilies for a serene garden experience.', 1, 11),
(15, 3333, 'Wheat Harvest', 'A bundle of wheat plants, perfect for a countryside feel.', 2, 12);

-- Ensure the cost falls within the specified range (100 - 10000)
UPDATE store_items SET cost = 100 WHERE cost < 100;
UPDATE store_items SET cost = 10000 WHERE cost > 10000;


INSERT INTO users (id,email,name,role,password,next_claim_energy_time) VALUES ('d114ae53-b841-4053-9d2c-cfcffbfaadd4', 'a', 'a', 'User', '$argon2id$v=19$m=19456,t=2,p=1$ISVS9jnSynRhBOXdegt61Q$N4+TsK8Y3EVwuSjryiGK/gILTakS+5o7sZiNZnZK3sY', now());

