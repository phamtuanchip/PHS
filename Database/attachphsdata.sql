USE MASTER
IF EXISTS (SELECT NAME FROM SYSDATABASES WHERE NAME ='PHS_DATA')
  BEGIN
   print 'DA CO DATABASE NAY DANG DETACHT va ATTACHT lai '
   EXEC sp_detach_db 'phs_data', 'true'
   EXEC sp_attach_db @dbname = N'phs_data', 
   @filename1 = N'C:\PHS\Database\phs_data_Data.MDF', 
   @filename2 = N'C:\PHS\Database\phs_data_Log.LDF'
   
  EXEC sp_defaultdb 'sa','phs_data'
  END
ELSE
  BEGIN 
   print 'CHUA CO DATABASE NAY BAT DAU ATTACHT'
   CREATE DATABASE phs_data
   EXEC sp_attach_db @dbname = N'phs_data', 
   @filename1 = N'C:\PHS\Database\phs_data_Data.MDF', 
   @filename2 = N'C:\PHS\Database\phs_data_Log.LDF'
  EXEC sp_defaultdb 'sa','phs_data'
  END 