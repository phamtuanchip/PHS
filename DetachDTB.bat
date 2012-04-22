CLS
ECHO OFF
@net start mssqlserver
@isql -S . -U sa -P sa -Q "USE MASTER IF EXISTS (SELECT NAME FROM SYSDATABASES WHERE NAME ='phs_data')  EXEC sp_detach_db 'phs_data', 'true' "