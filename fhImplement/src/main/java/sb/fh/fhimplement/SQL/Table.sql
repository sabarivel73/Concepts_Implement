CREATE TABLE table_1 (
    id SERIAL PRIMARY KEY,
    name TEXT,
    file OID --Stores a reference to a PostgreSQL Large Object and OID pointing to pg_largeobject where PostgreSQL stores the actual bytes.
);

CREATE TABLE table_2 (
    id SERIAL PRIMARY KEY,
    fileName TEXT,
    fileURL TEXT,
    fileType TEXT,
    fileKey TEXT
);

CREATE TABLE table_3 (
    id SERIAL PRIMARY KEY,
    originalName TEXT,
    storedName TEXT,
    filePath TEXT,
    fileType TEXT
);