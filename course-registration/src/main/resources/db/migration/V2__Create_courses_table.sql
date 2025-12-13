CREATE TABLE courses (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         code VARCHAR(50) NOT NULL UNIQUE,
                         capacity INTEGER NOT NULL,
                         enrolled INTEGER NOT NULL DEFAULT 0,
                         instructor VARCHAR(255)
);