CREATE TABLE "user"
(
    username          VARCHAR(5) PRIMARY KEY,
    password          TEXT        NOT NULL,
    role              VARCHAR(10) NOT NULL CHECK (role IN ('ADMIN', 'USER')),
    registration_date DATE        NOT NULL,
    status            VARCHAR(10) NOT NULL CHECK (status IN ('ACTIVE', 'DISABLED'))
);