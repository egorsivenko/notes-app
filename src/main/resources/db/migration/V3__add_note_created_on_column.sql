ALTER TABLE notes
    ADD COLUMN created_on TIMESTAMP NOT NULL DEFAULT current_timestamp;