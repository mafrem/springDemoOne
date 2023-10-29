Drop TABLE customer;
CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    name TEXT not NULL,
    email TEXT not NULL,
    age INT not NULL
);
ALTER TABLE customer ADD CONSTRAINT constraint_name UNIQUE (email);