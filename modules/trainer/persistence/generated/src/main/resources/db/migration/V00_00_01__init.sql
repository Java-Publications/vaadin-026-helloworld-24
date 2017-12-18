CREATE SEQUENCE core_user_id_seq;

CREATE TABLE core_user (
  ID         BIGINT PRIMARY KEY DEFAULT nextval('core_user_id_seq'),
  LOGIN      VARCHAR(15) NOT NULL UNIQUE,
  FORENAME   VARCHAR(15) NOT NULL,
  FAMILYNAME VARCHAR(15) NOT NULL
);
COMMIT;

CREATE SEQUENCE comp_math_basic_id_seq;
CREATE TABLE comp_math_basic (
  ID             BIGINT PRIMARY KEY DEFAULT nextval('comp_math_basic_id_seq'),
  OP_A           FLOAT       NOT NULL,
  OP             VARCHAR(15) NOT NULL,
  OP_B           FLOAT       NOT NULL,
  RESULT_MACHINE FLOAT       NOT NULL,
  RESULT_HUMAN   VARCHAR(15),
  RESULT_OK      BOOLEAN     NOT NULL,
  CREATED        TIMESTAMP   NOT NULL,
  ID_USER        BIGINT REFERENCES core_user (ID)
);
COMMIT;