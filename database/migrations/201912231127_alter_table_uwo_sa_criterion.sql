ALTER TABLE "ISAMSDBA"."UWO_SA_CRITERION" 
ADD ("USC_KIND_TYPE" VARCHAR2(30) );

COMMENT ON COLUMN "ISAMSDBA"."UWO_SA_CRITERION"."USC_KIND_TYPE" IS '구분';

UPDATE "ISAMSDBA"."UWO_SA_CRITERION" SET USC_KIND_TYPE='개수' WHERE USC_KIND_COD='na';
UPDATE "ISAMSDBA"."UWO_SA_CRITERION" SET USC_KIND_TYPE='퍼센트' WHERE USC_KIND_COD='inc' OR USC_KIND_COD='prog';