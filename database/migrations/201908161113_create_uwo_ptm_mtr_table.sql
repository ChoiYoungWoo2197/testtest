create table UWO_PTM_MTR
(
    ID         NUMBER(10) not null,
    SEASON     NUMBER(4)  not null,
    TITLE      VARCHAR2(1000),
    STATUS     VARCHAR2(20) default 'STAGED',
    CREATED_AT DATE,
    UPDATED_AT DATE
)
/

comment on table UWO_PTM_MTR is '보호대책'
/

comment on column UWO_PTM_MTR.ID is '식별번호'
/

comment on column UWO_PTM_MTR.SEASON is '보호대책 수립 작성 연도'
/

comment on column UWO_PTM_MTR.TITLE is '보호대책 수립 별칭'
/

comment on column UWO_PTM_MTR.STATUS is '보호대책 수립 업무 진행 상태'
/

comment on column UWO_PTM_MTR.CREATED_AT is '생성일'
/

comment on column UWO_PTM_MTR.UPDATED_AT is '수정일'
/

create sequence SEQ_UWO_PTM_MTR
    maxvalue 99999999999999999999
/