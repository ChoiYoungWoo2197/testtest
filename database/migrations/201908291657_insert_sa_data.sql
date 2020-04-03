-- Self-Audit 서비스 코드를 설정한다. SVC_COD 를 적절한 값으로 변경하여야 한다.
-- UWO_SVC_MTR.SVC_COD 값을 넣으면 된다.
insert into UWO_COM_COD (UCC_FIR_COD, UCC_FIR_NAM, UCC_SND_COD, UCC_SND_NAM, UCC_USE_YN, UCC_ETC, UCC_RGT_ID,
                         UCC_RGT_MDH, UCC_COD_ORD)
values ('SVC_SA', 'Self-Audit 서비스 코드', 'SVC_COD', 'SVC_COD 를 변경하세요', 'Y', '', '', sysdate, '')
/

-- Self-Audit 컴플라이언스 코드를 설정한다. COMP_COD 를 적절한 값으로 변경하여야 한다.
-- UWO_CTR_MTR.UCM_CTR_GBN 값 또는 UWO_COM_COD UCC_FIR_COD = 'STND' 인 값들 중 UCC_SND_COD 값을 넣으면 된다.
insert into UWO_COM_COD (UCC_FIR_COD, UCC_FIR_NAM, UCC_SND_COD, UCC_SND_NAM, UCC_USE_YN, UCC_ETC, UCC_RGT_ID,
                         UCC_RGT_MDH, UCC_COD_ORD)
values ('COMP_SA', 'Self-Audit 컴플라이언스 코드', 'COMP_COD', 'COMP_COD 를 변경하세요', 'Y', '', '', sysdate, '')