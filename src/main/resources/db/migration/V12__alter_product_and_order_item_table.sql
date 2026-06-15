ALTER TABLE products
    ADD COLUMN weight INT NOT NULL DEFAULT 0 COMMENT 'Weight in grams',
    ADD COLUMN length INT NOT NULL DEFAULT 0 COMMENT 'Length in centimeters',
    ADD COLUMN width  INT NOT NULL DEFAULT 0 COMMENT 'Width in centimeters',
    ADD COLUMN height INT NOT NULL DEFAULT 0 COMMENT 'Height in centimeters';

ALTER TABLE order_items
    ADD COLUMN weight INT NOT NULL DEFAULT 0 COMMENT 'Snapshotted weight in grams',
    ADD COLUMN length INT NOT NULL DEFAULT 0 COMMENT 'Snapshotted length in cm',
    ADD COLUMN width  INT NOT NULL DEFAULT 0 COMMENT 'Snapshotted width in cm',
    ADD COLUMN height INT NOT NULL DEFAULT 0 COMMENT 'Snapshotted height in cm';