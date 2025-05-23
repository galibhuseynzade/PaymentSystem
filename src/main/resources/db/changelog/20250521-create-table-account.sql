CREATE TABLE account
(
    account_number VARCHAR(20) PRIMARY KEY,
    customer_id    INTEGER        NOT NULL REFERENCES payment.customer (customer_id) ON DELETE CASCADE,
    balance        NUMERIC(18, 2) NOT NULL DEFAULT 0.00,
    opening_date   DATE           NOT NULL,
    expire_date    DATE           NOT NULL,
    status         VARCHAR(10)    NOT NULL CHECK (status IN ('NEW', 'ACTIVE', 'EXPIRED'))
);