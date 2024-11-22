CREATE TABLE IF NOT EXISTS users (
                                     id SERIAL PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
    balance NUMERIC(12, 2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );



CREATE TABLE IF NOT EXISTS cashouts (
                                        id SERIAL PRIMARY KEY,
                                        user_id INT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    amount NUMERIC(12, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );



ALTER TABLE users
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS balance NUMERIC(12, 2) NOT NULL DEFAULT 0.00;

ALTER TABLE cashouts
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;


select *
from cashouts;

select *
from users;


INSERT INTO users (name, email, balance) VALUES
    ('John Doe', 'johndoe@example.com', 100.00);


INSERT INTO cashouts (user_id, amount) VALUES
                                           (1, 50.00),
                                           (2, 30.00);
