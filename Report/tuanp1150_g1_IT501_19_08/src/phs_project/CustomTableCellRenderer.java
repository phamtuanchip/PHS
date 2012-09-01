package phs_project;
import java.awt.Component;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class CustomTableCellRenderer extends DefaultTableCellRenderer 
{
    public Component getTableCellRendererComponent
       (JTable table, Object value, boolean isSelected,
       boolean hasFocus, int row, int column) 
    {
        Component cell = super.getTableCellRendererComponent
           (table, value, isSelected, hasFocus, row, column);
               
        if(table.getValueAt(row,1).toString().equals("Phòng đang trống")){        	
        	cell.setBackground( Color.white );
                cell.setForeground(Color.black);               
        }
        if(table.getValueAt(row,1).toString().equals("Phòng đang bận")){
        	cell.setBackground(new Color(182,182,212));
                cell.setForeground(Color.white);
        }
        if(table.getValueAt(row,1).toString().equals("Phòng đang đặt")){
        	cell.setBackground( Color.cyan);
                cell.setForeground(Color.black);                
        }
        if(isSelected){            
            cell.setForeground(Color.RED);
        }
        return cell;

    }
}


