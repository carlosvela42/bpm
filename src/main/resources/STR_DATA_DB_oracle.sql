CREATE TABLE "##"."STR_DATA_DB"
   ("WORKITEMID" nvarchar2(20) NOT NULL,
    "FOLDERITEMNO" nvarchar2(20) NOT NULL,
    "DOCUMENTNO" nvarchar2(20) NOT NULL,
    "DOCUMENTDATA" nvarchar2(2000),
    "LASTUPDATETIME" nvarchar2(17),
    PRIMARY KEY (WORKITEMID,FOLDERITEMNO,DOCUMENTNO)
)