create table UWO_IST_DTL
(
	UIR_SVC_COD VARCHAR2(10) not null,
	UIR_CTR_GBN VARCHAR2(10) not null,
	UIR_BCY_COD VARCHAR2(10) not null,
	UIR_CTR_KEY NUMBER(10) not null,
	UID_RST_DTL VARCHAR2(4000),
	UID_RLT_DOC VARCHAR2(4000),
	constraint UWO_IST_DTL_PK
		primary key (UIR_SVC_COD, UIR_CTR_GBN, UIR_BCY_COD, UIR_CTR_KEY)
)
/

comment on table UWO_IST_DTL is '기반시설 평가 항목별 결과 추가 정보'
/

comment on column UWO_IST_DTL.UIR_SVC_COD is '서비스 코드'
/

comment on column UWO_IST_DTL.UIR_CTR_GBN is '컴플라이언스 코드'
/

comment on column UWO_IST_DTL.UIR_BCY_COD is '대주기 코드'
/

comment on column UWO_IST_DTL.UIR_CTR_KEY is '통제항목 식별번호'
/

comment on column UWO_IST_DTL.UID_RST_DTL is '서술형 점검결과'
/

comment on column UWO_IST_DTL.UID_RLT_DOC is '서술형 관련 문서'
/

