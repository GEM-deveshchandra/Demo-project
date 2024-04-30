CREATE TABLE IF NOT EXISTS meter (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    load_amount INT NOT NULL,
    min_bill_amount DOUBLE NOT NULL
) ;

CREATE TABLE IF NOT EXISTS supplier (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    supplier_type VARCHAR(255)
) ;

CREATE TABLE IF NOT EXISTS customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    connection_date DATE NOT NULL,
    last_reading DOUBLE NOT NULL,
    current_reading DOUBLE NOT NULL,
    bill_amount DOUBLE,
    meter_id BIGINT NOT NULL,
    supplier_id BIGINT NOT NULL,
    FOREIGN KEY (meter_id) REFERENCES meter(id),
    FOREIGN KEY (supplier_id) REFERENCES supplier(id)
);
CREATE TABLE IF NOT EXISTS price_per_unit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    unit_range_lower INT NOT NULL,
    unit_range_upper INT NOT NULL,
    price DOUBLE NOT NULL
);
