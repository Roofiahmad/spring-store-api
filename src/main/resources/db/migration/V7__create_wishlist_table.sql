CREATE TABLE wishlist
(
    user_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    CONSTRAINT wishlist_pk
        PRIMARY KEY (product_id, user_id),

    CONSTRAINT wishlist_products_id_fk
        FOREIGN KEY (product_id) REFERENCES products (id)
            ON DELETE CASCADE,

    CONSTRAINT wishlist_users_id_fk
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON DELETE CASCADE
);