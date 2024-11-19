CREATE TABLE "user" (
    "id" SERIAL PRIMARY KEY,
    "username" VARCHAR(255) NOT NULL UNIQUE,
    "email" VARCHAR(255) NOT NULL UNIQUE,
    "password" VARCHAR(255) NOT NULL
);

CREATE TYPE role_enum AS ENUM ('ROLE_ADMIN', 'ROLE_USER');

CREATE TABLE "user_roles" (
    "user_id" INT NOT NULL,
    "role" role_enum NOT NULL,
    FOREIGN KEY ("user_id") REFERENCES "user"("id") ON DELETE CASCADE
);

INSERT INTO "user" ("username", "email", "password")
VALUES ('john_doe', 'john.doe@example.com', '{noop}Pass@32');

INSERT INTO "user_roles" ("user_id", "role") VALUES (1, 'ROLE_ADMIN');

CREATE TABLE component_details (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    component_name VARCHAR(255),
    component_identifier VARCHAR(255),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX idx_component_identifier ON component_details (component_identifier);


INSERT INTO component_details (component_name, component_identifier, created_date, updated_date)
VALUES
    ('Component A', 'COMP-A', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Component B', 'COMP-B', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Component C', 'COMP-C', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Component D', 'COMP-D', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Component E', 'COMP-E', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
