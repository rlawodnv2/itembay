INSERT INTO item (
	server
	, seller_name
	, item_type
	, title
	, price
	, quantity
	, created_at
	, modified_at
	, version
) VALUES
(
    '라엘08',
    '아리',
    'GAME_MONEY',
    '다이아 1,000,000 판매',
    100000,
    1000000,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0
),
(
    '오르페',
    '도레미',
    'ITEM',
    '전설 아이템 판매',
    50000,
    1,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    0
);