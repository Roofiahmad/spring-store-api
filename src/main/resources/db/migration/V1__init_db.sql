CREATE TABLE users (
                       id       BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name     VARCHAR(255) NOT NULL,
                       email    VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role     VARCHAR(20)  NOT NULL DEFAULT 'USER'
);

CREATE TABLE tags (
                      id   BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL
);

CREATE TABLE categories (
                            id   TINYINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL
);

CREATE TABLE profiles (
                          id             BIGINT NOT NULL PRIMARY KEY,
                          bio            VARCHAR(255) NULL,
                          phone_number   VARCHAR(20)  NULL,
                          date_of_birth  DATE         NULL,
                          loyalty_points INT UNSIGNED DEFAULT 0,
                          CONSTRAINT fk_profiles_users_id
                              FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE addresses (
                           id      BIGINT AUTO_INCREMENT PRIMARY KEY,
                           street  VARCHAR(255) NOT NULL,
                           city    VARCHAR(255) NOT NULL,
                           state   VARCHAR(255) NOT NULL,
                           zip     VARCHAR(255) NOT NULL,
                           user_id BIGINT       NOT NULL,
                           CONSTRAINT addresses_users_id_fk
                               FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE products (
                          id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name        VARCHAR(255)   NOT NULL,
                          description VARCHAR(255)            NULL,
                          price       DECIMAL(10, 2) NOT NULL,
                          stock       INT DEFAULT 0  NOT NULL,
                          main_image  VARCHAR(255)   NULL,
                          category_id TINYINT        NOT NULL,
                          created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          CONSTRAINT products_categories_id_fk
                              FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT
);

CREATE TABLE user_tag (
                          user_id BIGINT NOT NULL,
                          tag_id  BIGINT NOT NULL,
                          PRIMARY KEY (user_id, tag_id),
                          CONSTRAINT fk_user_tag_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
                          CONSTRAINT fk_user_tag_tag  FOREIGN KEY (tag_id)  REFERENCES tags (id)  ON DELETE CASCADE
);

CREATE TABLE product_galleries (
                                   id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   product_id BIGINT       NOT NULL,
                                   url        VARCHAR(255) NOT NULL,
                                   CONSTRAINT product_galleries_products_id_fk
                                       FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

CREATE TABLE wishlist (
                          user_id    BIGINT NOT NULL,
                          product_id BIGINT NOT NULL,
                          CONSTRAINT wishlist_pk PRIMARY KEY (product_id, user_id),
                          CONSTRAINT wishlist_products_id_fk FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE,
                          CONSTRAINT wishlist_users_id_fk    FOREIGN KEY (user_id)    REFERENCES users (id)    ON DELETE CASCADE
);

CREATE TABLE carts (
                       id           BINARY(16) DEFAULT (UUID_TO_BIN(UUID())) PRIMARY KEY,
                       user_id    BIGINT         NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                       CONSTRAINT carts_users_id_fk
                           FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE cart_items (
                            id         BIGINT AUTO_INCREMENT PRIMARY KEY,
                            cart_id    BINARY(16)    NOT NULL,
                            product_id BIGINT        NOT NULL,
                            quantity   INT DEFAULT 1 NOT NULL,
                            CONSTRAINT cart_items_cart_product_unique UNIQUE (cart_id, product_id),
                            CONSTRAINT cart_items_cart_id_fk     FOREIGN KEY (cart_id)    REFERENCES carts (id)    ON DELETE CASCADE,
                            CONSTRAINT cart_items_products_id_fk FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);

CREATE TABLE orders (
                        id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                        customer_id BIGINT                             NOT NULL,
                        status      VARCHAR(20)                        NOT NULL,
                        created_at  DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
                        total_price DECIMAL(10, 2)                     NOT NULL,
                        CONSTRAINT orders_users_id_fk FOREIGN KEY (customer_id) REFERENCES users (id)
);

CREATE TABLE order_items (
                             id          BIGINT AUTO_INCREMENT PRIMARY KEY,
                             order_id    BIGINT         NOT NULL,
                             product_id  BIGINT         NOT NULL,
                             unit_price  DECIMAL(10, 2) NOT NULL,
                             quantity    INT            NULL,
                             total_price DECIMAL(10, 2) NOT NULL,
                             CONSTRAINT order_items_orders_id_fk   FOREIGN KEY (order_id)   REFERENCES orders (id),
                             CONSTRAINT order_items_products_id_fk FOREIGN KEY (product_id) REFERENCES products (id)
);

DELIMITER $$

CREATE PROCEDURE findProductsByPrice(
    minPrice DECIMAL(10,2),
    maxPrice DECIMAL(10,2)
)
BEGIN
    SELECT id, name, price, category_id
    FROM products
    WHERE price BETWEEN minPrice AND maxPrice
    ORDER BY name;
END $$

DELIMITER ;