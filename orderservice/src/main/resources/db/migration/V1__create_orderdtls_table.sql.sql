CREATE TABLE ORDERDTLS (
    order_id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity BIGINT NOT NULL,
    order_date TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    amount BIGINT NOT NULL
);
