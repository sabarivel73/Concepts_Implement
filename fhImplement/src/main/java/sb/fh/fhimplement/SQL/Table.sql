CREATE TABLE table_1 (
    id serial primary key,
    name text,
    file oid --Stores a reference to a PostgreSQL Large Object and OID pointing to pg_largeobject where PostgreSQL stores the actual bytes.
);