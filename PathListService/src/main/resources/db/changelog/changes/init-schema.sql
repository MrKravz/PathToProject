--liquibase formatted sql

--changeset init:1
CREATE TABLE routes
(
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    mileage             INTEGER     NOT NULL,
    transportation_type VARCHAR(10) NOT NULL,
    communication_type  VARCHAR(10) NOT NULL,
    start_point         VARCHAR(50) NOT NULL,
    end_point           VARCHAR(50) NOT NULL,
    created_at          TIMESTAMP,
    updated_at          TIMESTAMP,
    deleted             BOOLEAN     NOT NULL
);

CREATE TABLE series
(
    id      INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name    VARCHAR(4) NOT NULL,
    deleted BOOLEAN    NOT NULL
);

CREATE TABLE path_lists
(
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    seria_id         INTEGER REFERENCES series (id),
    number           INTEGER     NOT NULL,
    route_id         UUID REFERENCES routes (id),
    car_id           BIGINT      NOT NULL,
    car_driver_id    BIGINT      NOT NULL,
    company_id       BIGINT      NOT NULL,
    reclamation_date DATE        NOT NULL,
    expiration_date  DATE        NOT NULL,
    document_form    VARCHAR(10) NOT NULL,
    created_at       TIMESTAMP,
    updated_at       TIMESTAMP,
    deleted          BOOLEAN     NOT NULL
);

CREATE TABLE outbox_events
(
    id             UUID PRIMARY KEY,
    aggregate_type VARCHAR(255) NOT NULL,
    aggregate_id   VARCHAR(255) NOT NULL,
    type           VARCHAR(255) NOT NULL,
    payload        JSONB        NOT NULL
);

CREATE INDEX path_list_series_idx ON path_lists (seria_id, number);
CREATE INDEX path_list_date_idx ON path_lists (reclamation_date, expiration_date);

