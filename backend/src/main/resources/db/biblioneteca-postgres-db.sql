CREATE TABLE "authors" (
  "id" UUID PRIMARY KEY NOT NULL,
  "name" varchar2(255) NOT NULL,
  "date_of_birth" DATE NOT NULL,
  "date_of_death" DATE,
  "nationality" varchar2(255)
);

CREATE TABLE "books" (
  "id" UUID PRIMARY KEY NOT NULL,
  "name" varchar2(255) NOT NULL
);

CREATE TABLE "clients" (
  "id" UUID PRIMARY KEY NOT NULL,
  "cpf" "VARCHAR2(11)" UNIQUE NOT NULL,
  "full_name" varchar2(255) NOT NULL,
  "date_of_birth" DATE NOT NULL,
  "email" varchar2(255) UNIQUE,
  "cell_number" varchar2(20) NOT NULL,
  "registration_expiration" DATE NOT NULL
);

CREATE TABLE "publishers" (
  "id" UUID PRIMARY KEY NOT NULL,
  "legal_name" varchar2(255) NOT NULL,
  "trade_name" varchar2(255) NOT NULL,
  "cnpj" varchar2(14) UNIQUE NOT NULL,
  "site_url" varchar2(255)
);

CREATE TABLE "editions" (
  "id" UUID PRIMARY KEY NOT NULL,
  "isbn" "VARCHAR2(17)" UNIQUE,
  "fk_book" UUID NOT NULL,
  "fk_publisher" UUID NOT NULL,
  "edition_number" SMALLINT,
  "year_publication" SMALLINT NOT NULL,
  "language" varchar2(100) NOT NULL,
  "page_number" SMALLINT NOT NULL,
  "cover_url" varchar2(255) NOT NULL,
  "synopsis" "CLOB" NOT NULL
);

CREATE TABLE "librarians" (
  "id" UUID PRIMARY KEY NOT NULL,
  "password" varchar2(255) NOT NULL,
  "full_name" varchar2(255) NOT NULL,
  "date_of_birth" DATE NOT NULL,
  "cpf" varchar2(11) UNIQUE NOT NULL,
  "email" varchar2(255) UNIQUE NOT NULL
);

CREATE TABLE "author_book" (
  "fk_author" UUID NOT NULL,
  "fk_book" UUID NOT NULL,
  PRIMARY KEY ("fk_author", "fk_book")
);

CREATE TABLE "physical_copies" (
  "id" UUID PRIMARY KEY NOT NULL,
  "fk_edition" UUID NOT NULL
);

CREATE TABLE "loans" (
  "id" UUID PRIMARY KEY NOT NULL,
  "fk_librarian" UUID NOT NULL,
  "fk_physical_copy" UUID NOT NULL,
  "fk_client" UUID NOT NULL,
  "date_loan" DATE NOT NULL,
  "due_date" DATE NOT NULL,
  "date_return" DATE,
  "renewal" SMALLINT NOT NULL
);

CREATE INDEX "authors_name_index" ON "authors" ("name");

CREATE INDEX "books_name_index" ON "books" ("name");

CREATE INDEX "editions_fk_book_index" ON "editions" ("fk_book");

CREATE INDEX "editions_fk_publisher_index" ON "editions" ("fk_publisher");

CREATE INDEX "author_book_fk_author_index" ON "author_book" ("fk_author");

CREATE INDEX "author_book_fk_book_index" ON "author_book" ("fk_book");

CREATE INDEX "physical_fk_edition_index" ON "physical_copies" ("fk_edition");

CREATE INDEX "loan_fk_librarian_index" ON "loans" ("fk_librarian");

CREATE INDEX "loan_fk_physical_index" ON "loans" ("fk_physical_copy");

CREATE INDEX "loan_fk_client_index" ON "loans" ("fk_client");

ALTER TABLE "loans" ADD CONSTRAINT "loan_fk_client_foreign" FOREIGN KEY ("fk_client") REFERENCES "clients" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "author_book" ADD CONSTRAINT "author_book_fk_author_foreign" FOREIGN KEY ("fk_author") REFERENCES "authors" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "editions" ADD CONSTRAINT "editions_fk_book_foreign" FOREIGN KEY ("fk_book") REFERENCES "books" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "author_book" ADD CONSTRAINT "author_book_fk_book_foreign" FOREIGN KEY ("fk_book") REFERENCES "books" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "loans" ADD CONSTRAINT "loan_fk_librarian_foreign" FOREIGN KEY ("fk_librarian") REFERENCES "librarians" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "editions" ADD CONSTRAINT "editions_fk_publisher_foreign" FOREIGN KEY ("fk_publisher") REFERENCES "publishers" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "physical_copies" ADD CONSTRAINT "physical_fk_edition_foreign" FOREIGN KEY ("fk_edition") REFERENCES "editions" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "loans" ADD CONSTRAINT "loan_fk_physical_foreign" FOREIGN KEY ("fk_physical_copy") REFERENCES "physical_copies" ("id") DEFERRABLE INITIALLY IMMEDIATE;
