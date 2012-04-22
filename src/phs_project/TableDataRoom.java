package phs_project;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.*;


class TableDataRoom extends AbstractTableModel{
	private boolean DEBUG = true;
	protected String[] title;
	protected String beginDate,endDate;
        protected String select;
	public String[] columnNames;
	public Object[][] data;


	public TableDataRoom(String beginDate,String endDate,String select,String[] title){
	//	this.begin_Date = beginDate;
	//	this.end_Date = endDate;
		columnNames = title;
		System.out.println (columnNames.length);
		getData getdata = new getData();
                data = getdata.getDataTable(beginDate,endDate,select);
	}
        public TableDataRoom(int OrderID,String[] title){
            columnNames = title;
            data = new getData().getDataRoomOfOrderTable(OrderID);
        }

    public int getColumnCount() {
    	return columnNames.length;
    }

    public int getRowCount() {

        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {

        return data[row][col];
    }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
    public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
        if (col == 0) {
            return true;
        } else {
            return false;
        }
    }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
	public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

    }

