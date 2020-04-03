CREATE TABLE "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_LA"
(   "IL_ID" NUMBER NOT NULL ,
    "CTR_KEY" VARCHAR2(255) ,
    "LVL_COD" VARCHAR2(255) ,
    "TITLE" VARCHAR2(255) ,
    "NOTE" VARCHAR2(2000) ,
    "SEQ" NUMBER,
    PRIMARY KEY ("IL_ID")
);

COMMENT ON TABLE "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_LA" IS '기반시설 수준평가 확장테이블';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_LA"."IL_ID" IS '기반시설 수준평가 질의 ID';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_LA"."CTR_KEY" IS '통제항목코드';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_LA"."LVL_COD" IS '통제항목 최종레벨코드';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_LA"."TITLE" IS '착안사항 설명';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_LA"."NOTE" IS '증빙자료 예시';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_LA"."SEQ" IS '순서';

CREATE SEQUENCE SEQ_INFRA_LA START WITH 1 INCREMENT BY 1 MAXVALUE 99999999999999999999;
