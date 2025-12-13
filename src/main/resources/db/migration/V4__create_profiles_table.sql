CREATE TABLE profiles
(
    id             BIGINT NOT NULL PRIMARY KEY,
    bio            VARCHAR(255) NULL,
    phone_number   VARCHAR(20)  NULL,
    date_of_birth  DATE         NULL,
    loyalty_points INT  UNSIGNED DEFAULT 0,

    CONSTRAINT fk_profiles_users_id
        FOREIGN KEY (id) REFERENCES users (id)
            ON DELETE CASCADE
);