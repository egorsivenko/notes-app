CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO users (username, password)
VALUES ('admin', crypt('password', gen_salt('bf')));

INSERT INTO user_roles (user_id, role_id)
VALUES (lastval(), (SELECT id FROM roles WHERE name = 'ADMIN')),
       (lastval(), (SELECT id FROM roles WHERE name = 'USER'));