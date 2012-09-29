package phs_project;

import java.sql.*;
import javax.swing.table.*;

class ResultsTableModel extends AbstractTableModel {

    private String[] columnNames = {"Name", "Birthday", "Trang thai"};
    private Object[][] data = {
        {"TienHuynh", "100"},
        {"Hello", "100"}
    };

    public void setResultSet(ResultSet results) {
        try {
            ResultSetMetaData metadata = results.getMetaData();
            int column_count = metadata.getColumnCount();
            int c_row;
            int row_count = 0;
            results.last();
            row_count = results.getRow();
            columnNames = new String[column_count];
            for (int i = 0; i < column_count; i++) {
                columnNames[i] = metadata.getColumnLabel(i + 1);
            }
            data = new Object[row_count][column_count];
            results.first();
            results.previous();
            while (results.next()) {
                c_row = results.getRow();
                for (int i = 0; i < column_count; i++) {
                    data[c_row - 1][i] = results.getString(i + 1);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //

    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public String getColumnName(int column) {
        return columnNames[column];
    }

    public int getRowCount() {
        return data.length;
    }

    public Object getValueAt(int row, int column) {
        return data[row][column];
    }
}
