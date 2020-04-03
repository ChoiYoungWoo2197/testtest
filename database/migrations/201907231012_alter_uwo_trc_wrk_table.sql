alter table UWO_TRC_WRK
    add UTW_WRK_PRG number(3) default 0
/

comment on column UWO_TRC_WRK.UTW_WRK_PRG is '업무가 진행된 정도를 정수형태의 숫자로 나타낸다. 0~100 범위를 가진다.
현업 사용자는 숫자 대신 Y, UP, LP, N, N/A 값 중 하나를 선택하게되고 해당 값들은 각각 정수로 치환되어 저장된다.'
/