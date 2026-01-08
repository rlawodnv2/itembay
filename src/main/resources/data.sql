INSERT INTO ITEM (
	ID
	, SERVER
	, SELLER_NAME
	, ITEM_TYPE
	, TITLE
	, PRICE
	, QUANTITY
	, CREATE_AT
	, MODIFIED_AT
	, VERSION
) VALUES
(
    1,
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
    2,
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