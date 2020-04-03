DROP FUNCTION "F_FIND_NAME"
/

create FUNCTION            "F_FIND_NAME" (AS_TYPE IN VARCHAR2,
                                         AS_CODE IN VARCHAR2,
                                         AS_COM  IN VARCHAR2)
  RETURN VARCHAR2
--------------------------------------------------------------------------------
  --    STORAGE FUNC : F_FIND_NAME (AS_TYPE, AS_CODE, AS_COM)
  --    DESCRIPTION  : 사업부,서비스,부서명을 조회
  --    PARAM1 : 조직구분 (필수)
  --    PARAM2 : 찾고자 하는 조직명의 코드 (필수)
  --    PARAM3 : 업체명 (필수)
  --------------------------------------------------------------------------------
 IS
  LS_DIV_NAM   VARCHAR2(100);
  LS_SVC_NAM   VARCHAR2(100);
  LS_DEP_NAM   VARCHAR2(100);
  LS_RTN_VALUE VARCHAR2(200);
BEGIN
  --사업부코드 입력했을때
  IF AS_TYPE = '01' THEN
    --사업부명
    SELECT UOM_DIV_NAM
      INTO LS_DIV_NAM
      FROM UWO_ORG_MTR
     WHERE UOM_CSR_COD = AS_COM
       AND UOM_DIV_COD = AS_CODE
       AND ROWNUM = 1;
    LS_RTN_VALUE := LS_DIV_NAM;
    --------------------------------------------------------
    --서비스코드 입력했을때
  ELSIF AS_TYPE = '11' THEN
    --사업부명
    SELECT UOM_DIV_NAM
      INTO LS_DIV_NAM
      FROM UWO_ORG_MTR
     WHERE UOM_CSR_COD = AS_COM
       AND UOM_SVC_COD = AS_CODE
       AND ROWNUM = 1;
    LS_RTN_VALUE := LS_DIV_NAM;
  ELSIF AS_TYPE = '12' THEN
    --서비스명
    SELECT UOM_SVC_NAM
      INTO LS_SVC_NAM
      FROM UWO_ORG_MTR
     WHERE UOM_CSR_COD = AS_COM
       AND UOM_SVC_COD = AS_CODE
       AND ROWNUM = 1;
    LS_RTN_VALUE := LS_SVC_NAM;
    --------------------------------------------------------------
    --부서코드 입력했을때
  ELSIF AS_TYPE = '21' THEN
    --사업부명
    SELECT UOM_DIV_NAM
      INTO LS_DIV_NAM
      FROM UWO_ORG_MTR
     WHERE UOM_CSR_COD = AS_COM
       AND UOM_DEP_COD = AS_CODE
       AND ROWNUM = 1;
    LS_RTN_VALUE := LS_DIV_NAM;
  ELSIF AS_TYPE = '22' THEN
    --서비스명
    SELECT UOM_SVC_NAM
      INTO LS_SVC_NAM
      FROM UWO_ORG_MTR
     WHERE UOM_CSR_COD = AS_COM
       AND UOM_DEP_COD = AS_CODE
       AND ROWNUM = 1;
    LS_RTN_VALUE := LS_SVC_NAM;
  ELSIF AS_TYPE = '23' THEN
    --부서명
    SELECT UOM_DEP_NAM
      INTO LS_DEP_NAM
      FROM UWO_ORG_MTR
     WHERE UOM_CSR_COD = AS_COM
       AND UOM_DEP_COD = AS_CODE
       AND ROWNUM = 1;
    LS_RTN_VALUE := LS_DEP_NAM;

  ---------------------------------------
  ELSIF AS_TYPE = '31' THEN
    --부서명 NEW
    select UCC_SND_NAM
      INTO LS_DEP_NAM
      from uwo_Com_Cod
     where ucc_fir_Cod = 'DEPT'
       AND UCC_SND_COD = AS_CODE;
    LS_RTN_VALUE := LS_DEP_NAM;

  ELSIF AS_TYPE = '32' THEN
    --서비스명 NEW
    select UCC_SND_NAM
      INTO LS_DEP_NAM
      from uwo_Com_Cod
     where ucc_fir_Cod = 'SERVICE'
       AND UCC_SND_COD = AS_CODE;
    LS_RTN_VALUE := LS_DEP_NAM;

  ELSIF AS_TYPE = 'DEPTNM' THEN
    SELECT UDM_DEP_NAM
      INTO LS_DEP_NAM
      FROM UWO_DEP_MTR
     WHERE UDM_DEP_COD = AS_CODE;
     LS_RTN_VALUE := LS_DEP_NAM;
  END IF;
  RETURN LS_RTN_VALUE;
EXCEPTION
  WHEN NO_DATA_FOUND THEN
    RETURN '명칭없음';
END;

/

