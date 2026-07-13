--liquibase formatted sql

--changeset change:1
ALTER TABLE companies DROP COLUMN full_name;