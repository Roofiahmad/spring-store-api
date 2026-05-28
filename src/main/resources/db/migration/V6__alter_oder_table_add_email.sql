ALTER TABLE orders
    ADD COLUMN customer_email VARCHAR(255) NULL AFTER customer_id,
    ADD COLUMN customer_phone_number VARCHAR(50) NULL AFTER customer_email;

UPDATE orders SET customer_email = 'historical_order@example.com' WHERE customer_email IS NULL;
UPDATE orders SET customer_phone_number = '+620000000000' WHERE customer_phone_number IS NULL;

ALTER TABLE orders
    MODIFY COLUMN customer_email VARCHAR(255) NOT NULL,
    MODIFY COLUMN customer_phone_number VARCHAR(50) NOT NULL;