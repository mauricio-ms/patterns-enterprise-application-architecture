CREATE TABLE products
(
    id   int primary key,
    name varchar,
    type varchar
);

CREATE TABLE contracts
(
    id         int primary key,
    product    int,
    revenue    decimal,
    dateSigned date
);

CREATE TABLE revenue_recognitions
(
    contract      int,
    amount        decimal,
    recognized_on date,
    PRIMARY KEY (contract, recognized_on)
);