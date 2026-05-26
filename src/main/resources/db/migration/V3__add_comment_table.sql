CREATE TABLE product_reviews (
                                 id BIGINT PRIMARY KEY,
                                 product_id BIGINT NOT NULL,
                                 user_id BIGINT NOT NULL,
                                 rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5), -- ◄— Locked strictly 1 to 5
                                 comment TEXT,
                                 is_verified_purchase BOOLEAN DEFAULT FALSE,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                 CONSTRAINT fk_reviews_product FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                                 CONSTRAINT fk_reviews_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                                 CONSTRAINT uk_user_product_review UNIQUE (user_id, product_id)
);


CREATE INDEX idx_reviews_product_id_created ON product_reviews (product_id, created_at DESC);