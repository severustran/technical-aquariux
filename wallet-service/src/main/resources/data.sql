INSERT INTO coin_pair(coin_pair, is_active, created_at) VALUES ('BTCUSDT', true, NOW());
INSERT INTO coin_pair(coin_pair, is_active, created_at) VALUES ('ETHUSDT', true, NOW());

INSERT INTO user_wallet(user_id, coin_pair_id, balance, blocked_balance, available_balance, address, status, created_at)
VALUES (1, 1, 50000, 0, 0, 'address-userid1-btcusdt', 'ACTIVE', NOW());
INSERT INTO user_wallet(user_id, coin_pair_id, balance, blocked_balance, available_balance, address, status, created_at)
VALUES (1, 2, 50000, 0, 0, 'address-userid1-ethusdt', 'ACTIVE', NOW());