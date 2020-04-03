alter table UWO_ORG_MTR drop primary key
/

drop index UWO_ORG_MTR_PK
/

alter table UWO_ORG_MTR
	add UOM_BCY_COD varchar2(10)
/

