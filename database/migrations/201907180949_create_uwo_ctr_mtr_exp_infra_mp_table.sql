CREATE TABLE "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_MP"
(   "IM_ID" NUMBER NOT NULL ,
    "CTR_KEY" VARCHAR2(255) ,
    "LVL_COD" VARCHAR2(255) ,
    "REF_NO" VARCHAR2(255) ,
    "GRADE" VARCHAR2(255) ,
    "POINT" NUMBER ,
    PRIMARY KEY ("IM_ID")
);

COMMENT ON TABLE "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_MP" IS '기반시설 관리물리 확장테이블';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_MP"."IM_ID" IS '기반시설 관리물리 질의 ID';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_MP"."CTR_KEY" IS '통제항목코드';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_MP"."LVL_COD" IS '통제항목 최종레벨코드';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_MP"."REF_NO" IS '레퍼런스 코드';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_MP"."GRADE" IS '등급';
COMMENT ON COLUMN "ISAMSDBA"."UWO_CTR_MTR_EXP_INFRA_MP"."POINT" IS '점수';

CREATE SEQUENCE SEQ_INFRA_MP START WITH 1 INCREMENT BY 1 MAXVALUE 99999999999999999999;
