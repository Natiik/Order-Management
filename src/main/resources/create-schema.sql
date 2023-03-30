CREATE TABLE IF NOT EXISTS user_accounts(
                                           id           UUID UNIQUE,
                                           email        VARCHAR(255) NOT NULL UNIQUE,
                                           authority    VARCHAR(255) NOT NULL
);

CREATE INDEX IF NOT EXISTS user_accounts_email_index ON user_accounts (email);

CREATE TABLE IF NOT EXISTS user_credentials(
                                            id           UUID,
                                            password     VARCHAR(255) NOT NULL
);

