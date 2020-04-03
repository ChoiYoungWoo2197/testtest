create table UWO_PTM_MAP
(
    ID         NUMBER(10) not null
        constraint UWO_PTM_MAP_PK
            primary key,
    TASK_ID    NUMBER(10) not null,
    USER_KEY   NUMBER(10) not null,
    CREATED_AT DATE,
    UPDATED_AT DATE
)
/

comment on table UWO_PTM_MAP is '보호대책 중점과제 담당자'
/

comment on column UWO_PTM_MAP.ID is '식별번호'
/

comment on column UWO_PTM_MAP.TASK_ID is '보호대책 중점과제 식별번호'
/

comment on column UWO_PTM_MAP.USER_KEY is '보호대책 담당자 키(식별번호)'
/

comment on column UWO_PTM_MAP.CREATED_AT is '생성일'
/

comment on column UWO_PTM_MAP.UPDATED_AT is '수정일'
/

create sequence SEQ_UWO_PTM_MAP
    maxvalue 99999999999999999999
/

