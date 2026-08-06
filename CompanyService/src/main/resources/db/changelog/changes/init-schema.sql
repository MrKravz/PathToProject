--liquibase formatted sql

--changeset init:1
CREATE TABLE car_drivers
(
    id                    BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name                  VARCHAR(15) NOT NULL,
    surname               VARCHAR(20) NOT NULL,
    lastname              VARCHAR(20) NOT NULL,
    driver_license_number VARCHAR(12) NOT NULL,
    created_at            TIMESTAMP   NOT NULL,
    updated_at            TIMESTAMP   NOT NULL,
    deleted               BOOLEAN     NOT NULL
);

CREATE TABLE cars
(
    id                      BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    car_name                VARCHAR(35) NOT NULL,
    car_type                VARCHAR(20) NOT NULL,
    resident_number         VARCHAR(10) NOT NULL,
    mileage                 INTEGER     NOT NULL,
    fuel_consumption        INTEGER,
    action_fuel_consumption INTEGER,
    oil_consumption         INTEGER,
    created_at              TIMESTAMP   NOT NULL,
    updated_at              TIMESTAMP   NOT NULL,
    deleted                 BOOLEAN     NOT NULL
);

CREATE TABLE companies
(
    id           BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    identifier   VARCHAR(15) NOT NULL,
    company_name VARCHAR(30),
    address      VARCHAR(50) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    created_at   TIMESTAMP   NOT NULL,
    updated_at   TIMESTAMP   NOT NULL,
    version      BIGINT      NOT NULL,
    deleted      BOOLEAN     NOT NULL
);

CREATE TABLE company_cars
(
    company_id BIGINT REFERENCES companies (id),
    car_id     BIGINT REFERENCES cars (id),
    deleted    BOOLEAN NOT NULL
);

CREATE TABLE company_drivers
(
    company_id BIGINT REFERENCES companies (id),
    driver_id  BIGINT REFERENCES car_drivers (id),
    deleted    BOOLEAN NOT NULL
);


CREATE UNIQUE INDEX car_drivers_license_idx ON car_drivers (driver_license_number);
CREATE UNIQUE INDEX cars_resident_number_idx ON cars (resident_number);
CREATE INDEX company_cars_idx ON company_cars (company_id, car_id);
CREATE INDEX company_drivers_idx ON company_drivers (company_id, driver_id);