DROP TABLE IF EXISTS internal_comments;
DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS chat_messages;
DROP TABLE IF EXISTS chat_room;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS countries;

CREATE TABLE countries
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         VARCHAR           NOT NULL
);

CREATE TABLE cities
(
    id              INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name            VARCHAR         NOT NULL,
    country_id      INTEGER         NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries (id)
);

CREATE TABLE users
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         VARCHAR                       NOT NULL,
    date         TIMESTAMP                     NOT NULL,
    real_name    VARCHAR                       NULL,
    email        VARCHAR                       NOT NULL,
    password     VARCHAR                       NOT NULL,
    enabled      BOOLEAN                       NOT NULL,
    initial      VARCHAR(1)                    NOT NULL,
    color        VARCHAR                       NOT NULL,
    gender       VARCHAR(1) DEFAULT 'U'        NOT NULL,
    busyness     INTEGER DEFAULT 0             NOT NULL,
    day          INTEGER DEFAULT 0             NOT NULL,
    month        INTEGER DEFAULT 0             NOT NULL,
    year         INTEGER DEFAULT 0             NOT NULL,
    city_id      INTEGER DEFAULT 0             NOT NULL,
    country_id   INTEGER DEFAULT 0             NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries (id),
    FOREIGN KEY (city_id) REFERENCES cities (id)
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id      INTEGER        NOT NULL,
    role         VARCHAR,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE posts
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title        VARCHAR               NOT NULL,
    text         VARCHAR               NOT NULL,
    date         TIMESTAMP             NOT NULL,
    show         INTEGER DEFAULT 0     NOT NULL,
    comments     INTEGER DEFAULT 0     NOT NULL,
    user_id      INTEGER               NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE comments
(
    id           INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text         VARCHAR               NOT NULL,
    date         TIMESTAMP             NOT NULL,
    post_id      INTEGER               NOT NULL,
    user_id      INTEGER               NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE internal_comments
(
    id              INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text            VARCHAR               NOT NULL,
    date            TIMESTAMP             NOT NULL,
    comment_id      INTEGER               NOT NULL,
    user_id         INTEGER               NOT NULL,
    FOREIGN KEY (comment_id) REFERENCES comments (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE chat_room
(
    id              INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    chat_id         VARCHAR               NOT NULL,
    sender_id       INTEGER               NOT NULL,
    recipient_id    INTEGER               NOT NULL,
    last_message    INTEGER               NULL,
    user_read       INTEGER DEFAULT 0     NOT NULL
);
CREATE UNIQUE INDEX chat_room_unique_chat_id_idx ON chat_room (chat_id);

CREATE TABLE chat_messages
(
    id              INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    content         VARCHAR               NOT NULL,
    chat_id         INTEGER               NOT NULL,
    sender_id       INTEGER               NOT NULL,
    recipient_id    INTEGER               NOT NULL,
    date            TIMESTAMP             NOT NULL,
    FOREIGN KEY (chat_id) REFERENCES chat_room (id)
);