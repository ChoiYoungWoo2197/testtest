DROP FUNCTION "F_GET_USR_INFO"
/

create FUNCTION            "F_GET_USR_INFO" (AS_TYPE     IN VARCHAR2,
                                            AS_USR_KEY  IN VARCHAR2)
  RETURN VARCHAR2
--------------------------------------------------------------------------------
  --    STORAGE FUNC : F_GET_USR_INFO (AS_TYPE, AS_USR_KEY)
  --    DESCRIPTION  : 사업부,서비스,부서,직급을 조회
  --    PARAM1 : 01.사업부 , 02.서비스 , 03.부서 , 04.직급
  --    PARAM2 : 유저코드입력
  --------------------------------------------------------------------------------
  --    작  성  일 : 2014-12-01
  --    작  성  자 : 장철주
  --------------------------------------------------------------------------------
 IS
  LS_DIV_NAM   VARCHAR2(100);
  LS_SVC_NAM   VARCHAR2(100);
  LS_DEP_NAM   VARCHAR2(100);
  LS_POS_NAM   VARCHAR2(100);
  LS_RTN_VALUE VARCHAR2(200);
BEGIN

  -- 사업부명 조회
  IF AS_TYPE = '01' THEN
    SELECT UOM_DIV_NAM
      INTO LS_DIV_NAM
      FROM UWO_ORG_MTR, UWO_USR_MTR, UWO_COM_COD
     WHERE UUM_DEP_COD = UOM_DEP_COD
       AND UUM_POS_COD = UCC_SND_COD
       AND UUM_USR_KEY = AS_USR_KEY
       AND UCC_FIR_COD = 'POS'
       AND ROWNUM = 1;
    LS_RTN_VALUE := LS_DIV_NAM;

  -- 서비스명 조회
  ELSIF AS_TYPE = '02' THEN
    SELECT UOM_SVC_NAM
      INTO LS_SVC_NAM
      FROM UWO_ORG_MTR, UWO_USR_MTR, UWO_COM_COD
     WHERE UUM_DEP_COD = UOM_DEP_COD
       AND UUM_POS_COD = UCC_SND_COD
       AND UUM_USR_KEY = AS_USR_KEY
       AND UCC_FIR_COD = 'POS'
       AND ROWNUM = 1;
    LS_RTN_VALUE := LS_SVC_NAM;

  -- 부서명 조회
  ELSIF AS_TYPE = '03' THEN
    SELECT UOM_DEP_NAM
      INTO LS_DEP_NAM
      FROM UWO_ORG_MTR, UWO_USR_MTR, UWO_COM_COD
     WHERE UUM_DEP_COD = UOM_DEP_COD
       AND UUM_POS_COD = UCC_SND_COD
       AND UUM_USR_KEY = AS_USR_KEY
       AND UCC_FIR_COD = 'POS'
       AND ROWNUM = 1;
    LS_RTN_VALUE := LS_DEP_NAM;

  -- 직급명 조회
  ELSIF AS_TYPE = '04' THEN
    SELECT UCC_SND_NAM
      INTO LS_POS_NAM
      FROM UWO_ORG_MTR, UWO_USR_MTR, UWO_COM_COD
     WHERE UUM_DEP_COD = UOM_DEP_COD
       AND UUM_POS_COD = UCC_SND_COD
       AND UUM_USR_KEY = AS_USR_KEY
       AND UCC_FIR_COD = 'POS'
       AND ROWNUM = 1;
    LS_RTN_VALUE := LS_POS_NAM;
  END IF;

  RETURN LS_RTN_VALUE;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN '명칭없음';
END;

/

