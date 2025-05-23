CREATE TABLE customer
(
    customer_id       SERIAL PRIMARY KEY,
    first_name        VARCHAR(100)      NOT NULL,
    last_name         VARCHAR(100)      NOT NULL,
    birth_date        DATE              NOT NULL,
    fin_code          VARCHAR(7) UNIQUE NOT NULL,
    phone_number      VARCHAR(20)       NOT NULL CHECK (phone_number ~ '^\+994\d{9}$'),
    email             VARCHAR(150)      NOT NULL CHECK (email LIKE '%@%'),
    registration_date DATE              NOT NULL,
    status            VARCHAR(10)       NOT NULL CHECK (status IN ('REGULAR', 'SUSPECTED'))
);