
CREATE TABLE IF NOT EXISTS CJ_PRODUCT
(
    PRODUCT_ID   BIGINT       NOT NULL COMMENT '상품 ID',
    PRODUCT_NAME VARCHAR(200) NOT NULL COMMENT '상품명',
    QUANTITY     BIGINT       NOT NULL COMMENT '상품 수량',
    MAX_ORDER_QUANTITY BIGINT  NOT NULL COMMENT '최대 주문 수량',
    REG_USER     VARCHAR(50)  NOT NULL COMMENT '등록자',
    UPD_USER     VARCHAR(50)  NOT NULL COMMENT '수정자',
    REG_DATE     DATETIME     NOT NULL COMMENT '등록일시',
    UPD_DATE     DATETIME     COMMENT '수정일시',
    PRIMARY KEY (PRODUCT_ID)
);