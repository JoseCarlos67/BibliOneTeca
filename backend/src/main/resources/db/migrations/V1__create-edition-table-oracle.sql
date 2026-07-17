CREATE TABLE "EDITIONS"
(
    "id"               UUID PRIMARY KEY NOT NULL,
    "isbn"             "VARCHAR2(17)" UNIQUE,
    "fk_book"          UUID             NOT NULL,
    "fk_publisher"     UUID             NOT NULL,
    "edition_number"   SMALLINT,
    "year_publication" SMALLINT         NOT NULL,
    "language"         varchar2(100) NOT NULL,
    "page_number"      SMALLINT         NOT NULL,
    "cover_url"        varchar2(255) NOT NULL,
    "synopsis"         "CLOB"           NOT NULL
);
