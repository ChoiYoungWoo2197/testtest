create table UWO_PTM_TSK
(
    ID                    NUMBER(10)     not null
        constraint PROTECTION_MEASURE_TASK_PK
            primary key,
    PROTECTION_MEASURE_ID NUMBER(10)     not null,
    PARENT_NODE_ID        NUMBER(10),
    NODE_TYPE             VARCHAR2(20)   not null,
    TITLE                 VARCHAR2(1000) not null,
    TASK_CONTENT          VARCHAR2(4000),
    STARTED_AT            DATE,
    ENDED_AT              DATE,
    PURPOSE               VARCHAR2(1000),
    EVALUATION_INDEX      VARCHAR2(1000),
    BUDGET                VARCHAR2(20) default null,
    TARGET_FACILITY       VARCHAR2(1000),
    STATUS                VARCHAR2(20) default 'INITIATED',
    USER_KEY              NUMBER(10)     not null,
    CREATED_AT            DATE,
    UPDATED_AT            DATE,
    DELETED_AT            DATE
)
/

comment on table UWO_PTM_TSK is '보호대책 중점과제'
/

comment on column UWO_PTM_TSK.ID is '식별번호'
/

comment on column UWO_PTM_TSK.PROTECTION_MEASURE_ID is '보호대책 식별번호'
/

comment on column UWO_PTM_TSK.PARENT_NODE_ID is '부모 노드 식별번호'
/

comment on column UWO_PTM_TSK.NODE_TYPE is '구조상 해당 노드의 타입'
/

comment on column UWO_PTM_TSK.TITLE is '제목'
/

comment on column UWO_PTM_TSK.TASK_CONTENT is '내용'
/

comment on column UWO_PTM_TSK.STARTED_AT is '과제 시작일'
/

comment on column UWO_PTM_TSK.ENDED_AT is '과제 종료일'
/

comment on column UWO_PTM_TSK.PURPOSE is '목적'
/

comment on column UWO_PTM_TSK.EVALUATION_INDEX is '성과 평가지표'
/

comment on column UWO_PTM_TSK.BUDGET is '예산'
/

comment on column UWO_PTM_TSK.TARGET_FACILITY is '대상시설'
/

comment on column UWO_PTM_TSK.STATUS is '과제 상태'
/

comment on column UWO_PTM_TSK.USER_KEY is '과제 생성자'
/

comment on column UWO_PTM_TSK.CREATED_AT is '생성일'
/

comment on column UWO_PTM_TSK.UPDATED_AT is '수정일'
/

create sequence SEQ_UWO_PTM_TSK
    maxvalue 99999999999999999999
/

