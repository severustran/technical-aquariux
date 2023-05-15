INSERT INTO coin_pair(coin_pair, is_active, created_at, rate_in_usdt) VALUES ('BTCUSDT', true, NOW(), 0.000037);
INSERT INTO coin_pair(coin_pair, is_active, created_at, rate_in_usdt) VALUES ('ETHUSDT', true, NOW(), 0.00055);

INSERT INTO user_wallet(user_id, coin_pair_id, balance, blocked_balance, available_balance, address, status, created_at)
VALUES (1, 1, 50000, 0, 0, 'address-userid1-btcusdt', 'ACTIVE', NOW());
INSERT INTO user_wallet(user_id, coin_pair_id, balance, blocked_balance, available_balance, address, status, created_at)
VALUES (1, 2, 50000, 0, 0, 'address-userid1-ethusdt', 'ACTIVE', NOW());