CREATE ROLE money_transfer_sa WITH LOGIN PASSWORD 'sa123';
ALTER ROLE money_transfer_sa WITH SUPERUSER;
ALTER ROLE money_transfer_sa WITH CREATEDB;

CREATE DATABASE "bank_money_transfer" WITH OWNER money_transfer_sa;