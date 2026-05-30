CREATE INDEX `idx_products_category_id`
    ON `products` (`category_id`);

CREATE INDEX `idx_products_created_at`
    ON `products` (`created_at` DESC);

CREATE INDEX `idx_products_badge`
    ON `products` (`badge`);