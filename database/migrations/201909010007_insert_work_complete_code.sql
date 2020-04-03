-- 업무완료 상태코드에 수행중을 뜻하는 "업무 수행중" 코드를 추가한다.

INSERT INTO "ISAMSDBA"."UWO_COM_COD" ("UCC_FIR_COD", "UCC_FIR_NAM", "UCC_SND_COD", "UCC_SND_NAM", "UCC_USE_YN",
                                      "UCC_ETC", "UCC_RGT_ID", "UCC_RGT_MDH", "UCC_COD_ORD")
VALUES ('WKCD_COMP', '업무완료 상태코드', '10', '업무 수행중', 'Y', null, '43',
        TO_DATE('2019-09-01 00:06:31', 'YYYY-MM-DD HH24:MI:SS'), null)
/