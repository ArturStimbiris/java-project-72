CREATE TABLE IF NOT EXISTS urls (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS url_checks (
    id BIGSERIAL PRIMARY KEY,
    url_id BIGINT NOT NULL REFERENCES urls(id),
    status_code INT NOT NULL,
    title VARCHAR(255),
    h1 VARCHAR(255),
    description TEXT,
    created_at TIMESTAMP NOT NULL
);