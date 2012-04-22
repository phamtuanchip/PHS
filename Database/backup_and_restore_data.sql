--backup lan dau tien --
backup database pcs_data 
to Disk = 'F:\PCS_Project\Database\backup\pcs_databackup' 
--Backup cac lan sau --
backup database pcs_data 
to Disk = 'F:\PCS_Project\Database\backup\pcs_databackup'with DIFFERENTIAL

-- Xem thong tin ve du lieu da backup --
restore filelistonly
from disk ='F:\PCS_Project\Database\backup\pcs_databackup' 
-- Restore co so du lieu --
RESTORE DATABASE pcs_data
   FROM DISK = 'f:\PCS_Project\Database\backup\pcs_databackup'
   WITH MOVE 'pcs_data_data' TO 'F:\PCS_Project\Database\pcs_data.mdf',
   MOVE 'pcs_data_log' TO 'F:\PCS_Project\Database\pcs_data.ldf'

use pcs_data
use master
drop  database pcs_data