UPDATE UWO_COM_COD_EXP_STND_KIND
SET KIND = 'default'
WHERE KIND IN ('isms', 'isms_p');

-- ISMS와 ISMS-P는 고정3단계로 통합하기로 한다.