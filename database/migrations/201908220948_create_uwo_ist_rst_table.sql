create table UWO_IST_RST
(
    UIR_SVC_COD VARCHAR2(10) not null,
    UIR_CTR_GBN VARCHAR2(10) not null,
    UIR_CTR_KEY NUMBER(10)   not null,
    UIR_BCY_COD VARCHAR2(10) not null,
    UIR_ASW_ID  VARCHAR2(10) not null,
    UIR_ASW_VAL VARCHAR2(10) not null,
    UIR_WRK_KEY NUMBER(10)   not null,
    CREATED_AT  DATE,
    UPDATED_AT  DATE,
    constraint UWO_IST_RST_PK
        primary key (UIR_SVC_COD, UIR_CTR_KEY, UIR_ASW_ID)
)
/

comment on column UWO_IST_RST.UIR_SVC_COD is '서비스 코드'
/

comment on table UWO_IST_RST is '기반시설 평가 결과'
/

comment on column UWO_IST_RST.UIR_CTR_GBN is '컴플라이언스 코드'
/

comment on column UWO_IST_RST.UIR_CTR_KEY is '통제항목 식별번호'
/

comment on column UWO_IST_RST.UIR_BCY_COD is '대주기 코드'
/

comment on column UWO_IST_RST.UIR_ASW_ID is '선택된 결과 식별값'
/

comment on column UWO_IST_RST.UIR_ASW_VAL is '선택된 결과 평가 값'
/

comment on column UWO_IST_RST.UIR_WRK_KEY is '작업자 식별번호'
/

comment on column UWO_IST_RST.CREATED_AT is '생성일'
/

comment on column UWO_IST_RST.UPDATED_AT is '수정일'
/