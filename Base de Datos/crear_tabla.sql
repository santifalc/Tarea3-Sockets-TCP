CREATE TABLE nis (
    id bigserial NOT NULL,
    estado BOOLEAN DEFAULT FALSE,
    consumo INTEGER DEFAULT 0,
    CONSTRAINT nis_primary_key PRIMARY KEY(id)
);