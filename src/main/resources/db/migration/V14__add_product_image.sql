alter table products
    add main_image  VARCHAR(255)  NULL;

CREATE TABLE product_galleries (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT       NOT NULL,
    url        VARCHAR(255) NOT NULL,
    CONSTRAINT product_galleries_products_id_fk
    FOREIGN KEY (product_id) REFERENCES products (id) ON DELETE CASCADE
);