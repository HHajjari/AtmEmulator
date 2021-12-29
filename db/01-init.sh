#!/bin/bash
set -e
export PGPASSWORD=$POSTGRES_PASSWORD;
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
  CREATE USER $APP_DB_USER WITH PASSWORD '$APP_DB_PASS';
  CREATE DATABASE $APP_DB_NAME;
  GRANT ALL PRIVILEGES ON DATABASE $APP_DB_NAME TO $APP_DB_USER;
  \connect $APP_DB_NAME $APP_DB_USER
  BEGIN;
    CREATE TABLE IF NOT EXISTS card (
	  card_number varchar(255) NOT NULL,
	  balance numeric(19,2) NULL,
	  finger_print varchar(255) NULL,
	  invalid_auth_attempt_count int4 NULL,
	  is_active bool NULL,
	  pin int4 NULL,
	  PRIMARY KEY (card_number)
	);
	INSERT INTO CARD (card_number, balance, finger_print, invalid_auth_attempt_count,is_active, pin) VALUES('4111111111111111', 0, 'AAABBBCCCDDDEEEFFF', 0, 1, 1234)
  COMMIT;
EOSQL