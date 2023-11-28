CREATE TABLE article
(
    id  BIGSERIAL   PRIMARY KEY,
    name    VARCHAR(100)    UNIQUE NOT NULL,
    trending    BOOLEAN NOT NULL,
    tags    TEXT NOT NULL
);
CREATE TABLE comment
(
    id  BIGSERIAL   PRIMARY KEY,
    article_id BIGINT REFERENCES article (id) NOT NULL,
    text    TEXT    NOT NULL
);