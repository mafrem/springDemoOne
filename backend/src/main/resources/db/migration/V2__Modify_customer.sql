DROP Table customer;
CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    name TEXT not NULL,
    email TEXT not NULL
);