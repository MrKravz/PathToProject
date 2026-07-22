--liquibase formatted sql

--changeset change:1
ALTER TABLE cars ADD COLUMN car_type VARCHAR;