ALTER TABLE addresses ADD COLUMN label VARCHAR(255) NOT NULL DEFAULT 'Home';
ALTER TABLE addresses ADD COLUMN is_primary BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE addresses ADD COLUMN primary_owner_identity BIGINT
    AS (CASE WHEN is_primary = TRUE THEN user_id ELSE NULL END) VIRTUAL;

CREATE UNIQUE INDEX uk_user_single_active_primary
    ON addresses (primary_owner_identity);