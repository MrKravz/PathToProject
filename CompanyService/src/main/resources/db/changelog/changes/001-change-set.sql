--liquibase formatted sql

--changeset change:1
ALTER TABLE companies ADD COLUMN version BIGINT;
ALTER TABLE companies ADD COLUMN created_at TIMESTAMP;
ALTER TABLE companies ADD COLUMN updated_at TIMESTAMP;
ALTER TABLE car_drivers ADD COLUMN created_at TIMESTAMP;
ALTER TABLE car_drivers ADD COLUMN updated_at TIMESTAMP;
ALTER TABLE cars ADD COLUMN created_at TIMESTAMP;
ALTER TABLE cars ADD COLUMN updated_at TIMESTAMP;