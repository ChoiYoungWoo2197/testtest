CREATE FUNCTION "F_GET_CONFIG"(AS_CODE IN VARCHAR2)
    RETURN VARCHAR2
    IS
    LS_RTN_VALUE VARCHAR2(200);
BEGIN
    SELECT UCC_ETC INTO LS_RTN_VALUE
    FROM UWO_COM_COD
    WHERE UCC_FIR_COD = 'CONFIG'
      AND UCC_SND_COD = AS_CODE;
    RETURN LS_RTN_VALUE;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN '';
END;

/