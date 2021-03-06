-- Self-Audit 서비스 코드를 조회한다.

CREATE FUNCTION "F_SA_SVC_COD"
    RETURN VARCHAR2
    IS
    LS_SVC_SOD   VARCHAR2(100);
    LS_RTN_VALUE VARCHAR2(200);
BEGIN
    SELECT UCC_SND_COD
    INTO LS_SVC_SOD
    FROM UWO_COM_COD
    WHERE UCC_FIR_COD = 'SVC_SA' AND ROWNUM = 1;
    LS_RTN_VALUE := LS_SVC_SOD;
    RETURN LS_RTN_VALUE;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN '';
END;
/