-- Updated by データベース設計書_V0.93.xls

CREATE TABLE STR_CAL 
   (PROCESSTIME nvarchar(17),
    DIVISION nvarchar(20), 
    PROFILEID nvarchar(255),
    AVAILABLELICENSECOUNT int, 
    USELICENSECOUNT int, 
    LASTUPDATETIME nvarchar(17),
     PRIMARY KEY (PROCESSTIME)
   );
   
   CREATE TABLE CTL_LOGIN 
   (SESSIONID nvarchar(127) NOT NULL,
    USERID nvarchar(255) NOT NULL, 
    LOGINTIME nvarchar(17),
    LOGOUTTIME nvarchar(17), 
    LASTUPDATETIME nvarchar(17),
     PRIMARY KEY (SESSIONID)
   );
   
   CREATE TABLE CTL_MENU 
   (FUNCTIONID nvarchar(127) NOT NULL,
    USERID nvarchar(127) NOT NULL, 
    ACCESSAUTHORITY int,
    LASTUPDATETIME nvarchar(17),
     PRIMARY KEY (FUNCTIONID,USERID)
   );
   
   CREATE TABLE MGR_MENU_P 
   (FUNCTIONID nvarchar(127) NOT NULL,
    PROFILEID nvarchar(127) NOT NULL, 
    ACCESSAUTHORITY int,
    LASTUPDATETIME nvarchar(17),
     PRIMARY KEY (FUNCTIONID,PROFILEID)
   );
   
   CREATE TABLE MGR_MENU_U 
   (FUNCTIONID nvarchar(127) NOT NULL,
    USERID nvarchar(127) NOT NULL, 
    ACCESSAUTHORITY int,
    LASTUPDATETIME nvarchar(17),
     PRIMARY KEY (FUNCTIONID,USERID)
   );
   
    CREATE TABLE MGR_SITE 
   (SITEID int NOT NULL,
    DATADIRECTORYID int NOT NULL, 
    LASTUPDATETIME nvarchar(17),
     PRIMARY KEY (SITEID,DATADIRECTORYID)
   );
   
    CREATE TABLE MGR_DIRECTORY 
   (DATADIRECTORYID int NOT NULL,
    FOLDERPATH nvarchar(260), 
    NEWCREATEFILE nchar(1),
    RESERVEDDISKVOLSIZE nvarchar(255),
    DISKVOLSIZE nvarchar(255),
    LASTUPDATETIME nvarchar(17),
     PRIMARY KEY (DATADIRECTORYID)
   );
   
   CREATE TABLE MGR_WORKSPACE 
   (WORKSPACEID int NOT NULL,
    WORKSPACENAME nvarchar(255), 
    LASTUPDATETIME nvarchar(17),
     PRIMARY KEY (WORKSPACEID)
   );
   
    CREATE TABLE MGR_WORKSPACE_CONNECT 
   (WORKSPACEID int NOT NULL,
    DBSERVER nvarchar(255),
    SCHEMANAME nvarchar(255), 
    PORT int,
    DBUSER nvarchar(255),
    DBPASSWORD nvarchar(515),
    OWNER nvarchar(255),
    LASTUPDATETIME nvarchar(17),
     PRIMARY KEY (WORKSPACEID)
   );
   
   CREATE TABLE MGR_USER 
   (USERID nvarchar(127) NOT NULL, 
    NAME nvarchar(255), 
    PASSWORD nvarchar(515), 
    LASTUPDATETIME nvarchar(17),
    PRIMARY KEY (USERID)
   ); 
   
   CREATE TABLE MGR_PROFILE 
   (PROFILEID nvarchar(127) NOT NULL, 
    DESCRIPTION nvarchar(255), 
    LDAPIDENTIFIER nvarchar(2048), 
    LASTUPDATETIME nvarchar(17),
    PRIMARY KEY (PROFILEID)
   ); 
   
   CREATE TABLE MGR_USER_PROFILE 
   (PROFILEID nvarchar(127) NOT NULL, 
    USERID nvarchar(127) NOT NULL, 
    LASTUPDATETIME nvarchar(17),
    PRIMARY KEY (PROFILEID,USERID)
   );
   
   CREATE TABLE MGR_MENU 
   (FUNCTIONID nvarchar(127) NOT NULL,
    FUNCTIONNAME nvarchar(255), 
    FUNCTIONCATEGORYID nvarchar(255) NOT NULL,
    FUNCTIONSORTNO int,
    FUNCTIONINFORMATION nvarchar(MAX),
     --CONSTRAINT ENSURE_JSON1 CHECK (FUNCTIONINFORMATION IS JSON) ENABLE,
     PRIMARY KEY (FUNCTIONID)
   );
   
   CREATE TABLE MGR_MENUCATEGORY 
   (FUNCTIONCATEGORYID nvarchar(127) NOT NULL, 
    FUNCTIONCATEGORYNAME nvarchar(255), 
    FUNCTIONSORTNO int,
    PRIMARY KEY (FUNCTIONCATEGORYID)
   );
   
   CREATE TABLE MGR_PROCESS_SERVICE 
   (PROCESSISERVICEID int NOT NULL, 
    WORKSPACEID int, 
    PROCESSISERVICENAME nvarchar(255),
    LASTUPDATETIME nvarchar(17),
    PRIMARY KEY (PROCESSISERVICEID)
   );
   
   CREATE TABLE MGR_INCREMENT 
   (INCREMENTTYPE nvarchar(20) NOT NULL, 
    INCREMENTDATA int NOT NULL, 
    INCREMENTDATETIME nvarchar(17) NOT NULL,
    SESSIONID nvarchar(255) NOT NULL,
    LASTUPDATETIME nvarchar(17),
    PRIMARY KEY (INCREMENTTYPE,INCREMENTDATA)
   );
   
   CREATE TABLE MGR_WORKITEMID 
   (ISSUEDATE nvarchar(8) NOT NULL, 
    COUNT int NOT NULL, 
    INCREMENTDATETIME nvarchar(17) NOT NULL,
    SESSIONID nvarchar(255) NOT NULL,
    LASTUPDATETIME nvarchar(17),
    PRIMARY KEY (ISSUEDATE,COUNT)
   );
   
   CREATE TABLE MGR_EVENTID 
   (ISSUEDATE nvarchar(8) NOT NULL, 
    COUNT int, 
    INCREMENTDATETIME nvarchar(17) NOT NULL,
    SESSIONID nvarchar(255) NOT NULL,
    LASTUPDATETIME nvarchar(17),
    PRIMARY KEY (ISSUEDATE,COUNT)
   );
   
   CREATE TABLE MST_SYSTEM 
   (SECTION nvarchar(127) NOT NULL, 
    VARIABLENAME nvarchar(127)  NOT NULL,
    CONFIGVALUE nvarchar(255),
    LASTUPDATETIME nvarchar(17),
    PRIMARY KEY (SECTION,VARIABLENAME)
   );
   
   CREATE TABLE MST_CODE 
   (CODEID nvarchar(20) NOT NULL, 
    CODEVALUE nvarchar(255) NOT NULL, 
    SECTION nvarchar(20),
    SECTIONVALUE nvarchar(255),
    LASTUPDATETIME nvarchar(17),
    PRIMARY KEY (CODEID,SECTION)
   );
   
   CREATE TABLE MST_CALENDAR 
   (BUSINESSDAY nvarchar(8) NOT NULL, 
    BUSINESSDAYFLAG nchar(1), 
    LASTUPDATETIME nvarchar(17),
    PRIMARY KEY (BUSINESSDAY)
   );
   
   CREATE TABLE MGR_CUSTOM_TASK 
   (CUSTOMTASKID nvarchar(20) NOT NULL, 
    CUSTOMTASKNAME nvarchar(255), 
    CUSTOMTASKTYPE nvarchar(5),
    CLASSNAME nvarchar(255),
    LASTUPDATETIME nvarchar(17),
    PRIMARY KEY (CUSTOMTASKID)
   )
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   