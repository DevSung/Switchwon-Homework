INSERT INTO users (user_id, name, reg_date)
VALUES ('user1', 'paul doe', CURRENT_TIMESTAMP);
INSERT INTO users (user_id, name, reg_date)
VALUES ('user2', 'Jane Doe', CURRENT_TIMESTAMP);

INSERT INTO currency_code (currency_code, allow_decimal)
VALUES ('USD', TRUE);
INSERT INTO currency_code (currency_code, allow_decimal)
VALUES ('KRW', FALSE);

INSERT INTO merchant (name, fee)
VALUES ('Merchant1', 3.0);
INSERT INTO merchant (name, fee)
VALUES ('Merchant2', 3.12);
INSERT INTO merchant (name, fee)
VALUES ('Merchant3', 3.24);

INSERT INTO account_balance (user_idx, currency_code, balance)
VALUES (1, 'USD', 1000.25),
       (1, 'KRW', 2000);

INSERT INTO account_balance (user_idx, currency_code, balance)
VALUES (2, 'USD', 2000.16),
       (2, 'KRW', 1000);


