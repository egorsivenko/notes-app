CREATE TABLE categories
(
    id         UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    name       VARCHAR(255) NOT NULL UNIQUE,
    created_on TIMESTAMP    NOT NULL DEFAULT current_timestamp,
    user_id    INT REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE
);