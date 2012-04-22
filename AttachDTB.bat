CLS
ECHO OFF
@net start mssqlserver
@isql -S . -U sa -P sa -Q "USE MASTER IF EXISTS (SELECT NAME FROM SYSDATABASES WHERE NAME ='phs_data') BEGIN EXEC sp_detach_db 'phs_data', 'true' END EXEC sp_attach_db @dbname = N'phs_data',  @filename1 = N'%~1',  @filename2 = N'%~2' "

