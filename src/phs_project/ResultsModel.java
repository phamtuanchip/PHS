package phs_project;
import java.sql.*;
import javax.swing.table.*;

class ResultsModel extends DefaultTableModel {
    
    public void setResultSet(ResultSet results) {
        try {
            ResultSetMetaData metadata = results.getMetaData();
            
            int columns =  metadata.getColumnCount();
            
            // Get the column names and set header names
            for (int i = 0; i < columns; i++) {
                addColumn(metadata.getColumnLabel(i+1));
            }
            // Get all rows
            while (results.next()) {
                String[] rowData = new String[columns +1];    // Create array to hold the data                
                for (int i = 0; i <  columns; i++) {         // For each column
                    rowData[i] = results.getString(i+1);   // retrieve the data item
                }
                addRow(rowData);    // Add a row
            }
            fireTableChanged(null);           // Signal the table there is new model data
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
