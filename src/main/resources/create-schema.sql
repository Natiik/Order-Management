CREATE TABLE IF NOT EXISTS user_accounts(
                                           id           UUID UNIQUE,
                                           email        VARCHAR(255) NOT NULL UNIQUE,
                                           authority    VARCHAR(255) NOT NULL,
                                           PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS user_accounts_email_index ON user_accounts (email);

CREATE TABLE IF NOT EXISTS user_credentials(
                                            id           UUID,
                                            password     VARCHAR(255) NOT NULL,
                                            PRIMARY KEY (id),
                                            CONSTRAINT fk_user_id FOREIGN KEY (id) REFERENCES user_accounts (id)
);

CREATE TABLE IF NOT EXISTS items_info(
                                            id           UUID UNIQUE,
                                            name         VARCHAR(255) NOT NULL UNIQUE,
                                            price        FLOAT NOT NULL,
                                            PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS item_name_index ON items_info (name);

CREATE TABLE IF NOT EXISTS items_records(
                                         item_id      UUID UNIQUE,
                                         count        INT,
                                         PRIMARY KEY (id),
                                         CONSTRAINT fk_item_id FOREIGN KEY (item_id) REFERENCES items_info (id)
);