INSERT INTO categories (id, name)
VALUES (1, 'Electronics'),
       (2, 'Home & Kitchen'),
       (3, 'Fashion'),
       (4, 'Sports & Outdoors'),
       (5, 'Books')
    ON DUPLICATE KEY UPDATE name=VALUES(name);

INSERT INTO products (name, description, price, stock, main_image, category_id)
VALUES ('Sony WH-1000XM5', 'Industry leading noise canceling headphones with 30-hour battery life.', 348.00, 25, 'https://images.unsplash.com/photo-1675243027103-6893693e5069?q=80&w=1000', 1),
       ('Apple MacBook Air M3', '13-inch laptop with the powerful M3 chip, 8GB Unified Memory, and 256GB SSD.', 1099.00, 15, 'https://images.unsplash.com/photo-1517336714467-d23784a38b47?q=80&w=1000', 1),
       ('Instant Pot Duo Plus', '9-in-1 Electric Pressure Cooker, Slow Cooker, Rice Cooker, and Steamer.', 129.95, 50, 'https://images.unsplash.com/photo-1584990344610-52db3bc9b0d1?q=80&w=1000', 2),
       ('Keurig K-Elite Coffee Maker', 'Single serve K-Cup pod coffee brewer with iced coffee capability.', 189.99, 30, 'https://images.unsplash.com/photo-1520970014086-2208d157c9e2?q=80&w=1000', 2),
       ('Levi''s 501 Original Jeans', 'The original button fly jeans with a straight fit and all-cotton denim.', 79.50, 100, 'https://images.unsplash.com/photo-1542272604-787c3835535d?q=80&w=1000', 3),
       ('Nike Air Force 1 ''07', 'Legendary basketball original with crisp leather and bold details.', 115.00, 45, 'https://images.unsplash.com/photo-1600185365483-26d7a4cc7519?q=80&w=1000', 3),
       ('Hydro Flask 32 oz', 'Wide mouth water bottle with TempShield insulation to keep drinks cold.', 44.95, 150, 'https://images.unsplash.com/photo-1602143307185-844cb1b2c5a2?q=80&w=1000', 4),
       ('Kindle Paperwhite (16 GB)', '6.8 inch display with adjustable warm light and up to 10 weeks of battery.', 149.99, 40, 'https://images.unsplash.com/photo-1592492159418-39f319320569?q=80&w=1000', 1),
       ('Wilson Evolution Basketball', 'The #1 indoor game basketball in America with a microfiber composite cover.', 79.95, 60, 'https://images.unsplash.com/photo-1519861531473-9200262188bf?q=80&w=1000', 4),
       ('Yeti Tundra 45 Cooler', 'Heavy-duty cooler built to be indestructible and keep ice for days.', 325.00, 10, 'https://images.unsplash.com/photo-1628150654412-58e1a74204d8?q=80&w=1000', 4);