CREATE TABLE IF NOT EXISTS user_accounts(
                                           id           UUID,
                                           email        VARCHAR(255) NOT NULL,
                                           password     VARCHAR(255),
                                           authority    VARCHAR(255) NOT NULL
);

CREATE INDEX IF NOT EXISTS user_accounts_email_index ON user_accounts (email);
