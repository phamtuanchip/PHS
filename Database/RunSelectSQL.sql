--use master
--EXEC sp_detach_db 'pubs', 'true'
EXEC sp_attach_db @dbname = N'pubs', 
   @filename1 = N'c:\Program Files\Microsoft SQL Server\MSSQL\Data\pubs.mdf', 
   @filename2 = N'c:\Program Files\Microsoft SQL Server\MSSQL\Data\pubs_log.ldf'
go
WAITFOR DELAY '00:00:05'
go