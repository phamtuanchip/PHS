--EXEC sp_attach_db @dbname = N'tuan', 
  -- @filename1 = N'C:\Design\Data\tuan_data.mdf', 
   --@filename2 = N'C:\Design\Data\tuan_log.ldf'


EXEC sp_attach_db @dbname = N'pcs_data', 
   @filename1 = N'C:\PCS\Database\pcs_data.mdf', 
   @filename2 = N'C:\PCS\Database\pcs_log.ldf'

