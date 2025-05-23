CREATE TABLE transaction
(
    transaction_id VARCHAR(36) PRIMARY KEY,
    customer_id    INTEGER        NOT NULL REFERENCES payment.customer (customer_id) ON DELETE CASCADE,
    type           VARCHAR(10)    NOT NULL CHECK (type IN ('CARD2CARD', 'TRANSFER')),
    debit          VARCHAR(20)    NOT NULL,
    credit         VARCHAR(20)    NOT NULL,
    date           DATE           NOT NULL,
    amount         NUMERIC(18, 2) NOT NULL,
    status         VARCHAR(10)    NOT NULL CHECK (status IN ('PENDING', 'SUCCESS'))
);