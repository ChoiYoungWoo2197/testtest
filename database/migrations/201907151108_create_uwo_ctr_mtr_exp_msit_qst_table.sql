CREATE TABLE "ISAMSDBA"."UWO_CTR_MTR_EXP_MSIT_QST"
(   "MQ_ID" NUMBER NOT NULL ,
    "CTR_KEY" VARCHAR2(255) ,
    "LVL_COD" VARCHAR2(255) ,
    "TITLE" VARCHAR2(255) ,
    "POINT" VARCHAR2(255) ,
    "SEQ" NUMBER ,
    PRIMARY KEY ("MQ_ID")
);

COMMENT ON TABLE "ISAMSDBA"."UWO_CTR_MTR_EXP_MSIT_QST" IS '과기정통부 확장테이블';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_MSIT_QST"."MQ_ID" IS '과기정통부 질의 ID';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_MSIT_QST"."CTR_KEY" IS '통제항목코드';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_MSIT_QST"."LVL_COD" IS '통제항목 최종레벨코드';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_MSIT_QST"."TITLE" IS '문항';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_MSIT_QST"."POINT" IS '배점';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_MSIT_QST"."SEQ" IS '순서';

CREATE SEQUENCE SEQ_MSIT_QST START WITH 1 INCREMENT BY 1 MAXVALUE 99999999999999999999;
