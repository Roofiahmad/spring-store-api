INSERT INTO categories (id, name)
VALUES (1, 'Electronics'),
       (2, 'Home'),
       (3, 'Fashion'),
       (4, 'Sports'),
       (5, 'Books')
    ON DUPLICATE KEY UPDATE name=VALUES(name);