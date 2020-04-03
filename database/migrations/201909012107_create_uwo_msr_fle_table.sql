create table UWO_MSR_FLE
(
	UMF_SVC_COD varchar2(10) not null,
	UMF_CTR_KEY number not null,
	UMF_FLE_KEY number not null,
	constraint UWO_MSR_FLE_pk
		primary key (UMF_SVC_COD, UMF_CTR_KEY, UMF_FLE_KEY)
)
/

comment on table UWO_MSR_FLE is '대책 명세서에 포함될 SA 파일 정보를 담고 있는 테이블'
/

comment on column UWO_MSR_FLE.UMF_SVC_COD is '서비스 코드'
/

comment on column UWO_MSR_FLE.UMF_CTR_KEY is '통제항목 식별번호'
/

comment on column UWO_MSR_FLE.UMF_FLE_KEY is '파일 식별번호'
/

