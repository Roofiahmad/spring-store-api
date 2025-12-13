create table tags
(
    id   bigint not null auto_increment primary key,
    name varchar(255) not null
);

CREATE TABLE user_tag
(
    user_id BIGINT NOT NULL,
    tag_id  BIGINT NOT NULL,

    PRIMARY KEY (user_id, tag_id),

    CONSTRAINT fk_user_tag_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE,

    CONSTRAINT fk_user_tag_tag
        FOREIGN KEY (tag_id)
            REFERENCES tags (id)
            ON DELETE CASCADE
);