-- liquibase formatted sql

-- changeset yosifgenchev:1
-- Initialize tables in database
CREATE TABLE payment_system.transaction
(
    uuid character varying NOT NULL,
    amount double precision,
    customer_email character varying NOT NULL,
    customer_phone character varying,
    status character varying,
    reference_id character varying,
    dtype character varying NOT NULL,
    PRIMARY KEY (uuid),
    CONSTRAINT "fk_transaction_reference_id" FOREIGN KEY ("reference_id") REFERENCES "payment_system"."transaction"("uuid")
);




CREATE TABLE payment_system.merchant
(
    id bigint NOT NULL,
    name character varying NOT NULL,
    description character varying,
    email character varying NOT NULL,
    status character varying NOT NULL,
    total_transaction_sum double precision NOT NULL,
    PRIMARY KEY (id)
);



CREATE TABLE payment_system.merchant_transactions
(
    merchant_id bigint NOT NULL,
    transaction_uuid character varying NOT NULL,
    CONSTRAINT fk_merchant_transactions_merchant FOREIGN KEY (merchant_id)
        REFERENCES payment_system.merchant (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_merchant_transactions_transaction FOREIGN KEY (transaction_uuid)
        REFERENCES payment_system.transaction (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

-- changeset yosifgenchev:2
-- Change transaction - merchant relation

ALTER TABLE IF EXISTS payment_system.transaction
    ADD COLUMN merchant_id bigint NOT NULL;
ALTER TABLE IF EXISTS payment_system.transaction
    ADD CONSTRAINT fk_merchant_id FOREIGN KEY (merchant_id)
        REFERENCES payment_system.merchant (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID;

ALTER TABLE IF EXISTS payment_system.merchant
    ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY;

-- changeset yosifgenchev:3
-- Change dtype column to transaction_type

ALTER TABLE IF EXISTS payment_system.transaction DROP COLUMN IF EXISTS dtype;

ALTER TABLE IF EXISTS payment_system.transaction
    ADD COLUMN transaction_type character varying NOT NULL;

ALTER TABLE IF EXISTS payment_system.transaction
    RENAME transaction_type TO type;