CREATE TABLE IF NOT EXISTS product (
   product_id SERIAL PRIMARY KEY,
   product_name VARCHAR(255),
    product_description TEXT,
    product_quantity BIGINT,
    product_price DOUBLE PRECISION,
    product_image VARCHAR(255),
    product_category VARCHAR(255),
    product_status VARCHAR(50),
    local_date_time TIMESTAMP
    );
