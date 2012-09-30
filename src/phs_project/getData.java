package phs_project;

import java.sql.*;

public class getData {

    public Object[][] data1;
    public Object[][] dataRoomOfOrder;
    protected String endDate, beginDate;
    protected String select;
    public Connection con;
    public static int RowCount = 0;
    public static int RowCount1 = 0;

    public Object[][] getDataTable(String beginDate, String endDate, String select) {

        CallableStatement cs;
        try {
            con = new connectDatabase().getConnection();
            cs = con.prepareCall("{call searchroom(?,?,?)}", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            cs.setString(1, select);
            cs.setString(2, beginDate);
            cs.setString(3, endDate);
            ResultSet resultSet = cs.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int column_count = resultSetMetaData.getColumnCount();
            int c_row;
            int row_count = 0;
            resultSet.last();
            row_count = resultSet.getRow();
            RowCount = row_count;
            data1 = new Object[row_count][column_count];
            resultSet.first();
            resultSet.previous();
            while (resultSet.next()) {
                c_row = resultSet.getRow();
                data1[c_row - 1][0] = new Boolean(false);
                for (int i = 2; i <= column_count; i++) {
                    data1[c_row - 1][i - 1] = resultSet.getString(i);
                }
            }
            resultSet.close();
            cs.close();
        } catch (SQLException ce) {
            ce.printStackTrace();
        }

        return data1;
    }

    public Object[][] getDataRoomOfOrderTable(int OrderId) {
        Connection conn = new connectDatabase().getConnection();
        CallableStatement fillcs;
        try {
            fillcs = conn.prepareCall("{call fillTable(?)}", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            fillcs.setInt(1, OrderId);
            ResultSet resultSet = fillcs.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int column_count = resultSetMetaData.getColumnCount();
            int c_row;
            int row_count = 0;
            resultSet.last();
            row_count = resultSet.getRow();
            RowCount1 = row_count;
            dataRoomOfOrder = new Object[row_count][column_count];
            resultSet.first();
            resultSet.previous();
            while (resultSet.next()) {
                c_row = resultSet.getRow();
                dataRoomOfOrder[c_row - 1][0] = new Boolean(false);
                for (int i = 2; i <= column_count; i++) {
                    dataRoomOfOrder[c_row - 1][i - 1] = resultSet.getString(i);
                }
            }
            resultSet.close();
            fillcs.close();

        } catch (SQLException se) {
           se.printStackTrace();
        }
        return dataRoomOfOrder;
    }
}
