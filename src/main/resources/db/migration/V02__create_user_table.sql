CREATE TABLE IF NOT EXISTS user (
    user_id VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    is_active TINYINT,
    password VARCHAR(255),
    PRIMARY KEY (user_id)
    );