CREATE TABLE users
(
    idx     BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL UNIQUE,
    name    VARCHAR(50) NOT NULL
);

CREATE TABLE currency_code
(
    currency_code VARCHAR(3) NOT NULL,
    allow_decimal BOOLEAN    NOT NULL,
    PRIMARY KEY (currency_code)
);

CREATE TABLE merchant
(
    idx  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    fee  DECIMAL(5, 2)
);

CREATE TABLE payments
(
    idx           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_idx      BIGINT,
    merchant_idx  BIGINT,
    currency_code VARCHAR(3),
    total_amount  DECIMAL(15, 2),
    amount        DECIMAL(15, 2),
    fee           DECIMAL(5, 2),
    status        VARCHAR(3),
    payment_date  TIMESTAMP,
    FOREIGN KEY (user_idx) REFERENCES users (idx)
);

CREATE TABLE account_balance
(
    idx           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_idx      BIGINT,
    currency_code VARCHAR(3),
    balance       DECIMAL(15, 2),
    FOREIGN KEY (user_idx) REFERENCES users (idx)
);

CREATE TABLE balance_recharge
(
    idx           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_idx      BIGINT,
    currency_code VARCHAR(3),
    amount        DECIMAL(15, 2),
    recharge_type VARCHAR(3),
    status        VARCHAR(3),
    recharge_date TIMESTAMP,
    FOREIGN KEY (user_idx) REFERENCES users (idx)
);

CREATE TABLE card_recharge_detail
(
    idx          BIGINT AUTO_INCREMENT PRIMARY KEY,
    recharge_idx BIGINT,
    card_company VARCHAR(20),
    card_number  VARCHAR(20),
    expiry_date  VARCHAR(5),
    cvs          VARCHAR(3),
    FOREIGN KEY (recharge_idx) REFERENCES balance_recharge (idx)
);

CREATE TABLE cash_recharge_detail
(
    idx            BIGINT AUTO_INCREMENT PRIMARY KEY,
    recharge_idx   BIGINT,
    receipt_number VARCHAR(20),
    FOREIGN KEY (recharge_idx) REFERENCES balance_recharge (idx)
);
