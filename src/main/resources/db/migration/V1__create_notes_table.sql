CREATE TABLE notes
(
    id              UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    title           VARCHAR(100) NOT NULL,
    content         VARCHAR(500),
    last_updated_on TIMESTAMP    NOT NULL DEFAULT current_timestamp
);