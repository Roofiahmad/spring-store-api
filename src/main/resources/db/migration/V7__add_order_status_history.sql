CREATE TABLE `store`.`order_status_history` (
                                                `id`         BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                `order_id`   BIGINT                             NOT NULL,
                                                `status`     VARCHAR(50)                        NOT NULL,
                                                `notes`      VARCHAR(255)                       NULL,
                                                `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,

                                                CONSTRAINT `order_status_history_orders_id_fk`
                                                    FOREIGN KEY (`order_id`) REFERENCES orders (id) ON DELETE CASCADE
);

CREATE INDEX `idx_order_status_history_order_id` ON `order_status_history` (`order_id`);