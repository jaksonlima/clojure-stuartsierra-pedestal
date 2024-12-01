CREATE TABLE url
(
    id         varchar(36) PRIMARY KEY,
    name       varchar(55) NOT NULL,
    origin     TEXT        NOT NULL,
    hash       TEXT        NOT NULL,
    active     boolean     default true,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW()
);