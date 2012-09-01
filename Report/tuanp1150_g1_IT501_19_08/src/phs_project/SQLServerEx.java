package phs_project;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.awt.Font;

/**
 * Modify and query SQL Server 2000 database
 *
 */
public class SQLServerEx {
    
    /** Creates a new instance of SQLServerEx */
    public SQLServerEx() {
        try {
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
//            Class.forName("net.sourceforge.jtds.jdbc.Driver");            
            String url = "jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=northwind";
//            String url = "jdbc:jtds:sqlserver://localhost:1433/northwind";           
            Properties props = new Properties();
            props.put("user", "sa");
            props.put("password", "root");
            Connection con = DriverManager.getConnection(url, props);
            Statement stmt = con.createStatement();
            String update = "UPDATE Customers SET City=N'Hà Nội' WHERE City=N'London'";
            stmt.execute(update);
            update = "UPDATE Customers SET CompanyName=N'Café Thượng Uyển', Address=N'Phố Lương Văn Can', Country=N'Việt Nam' WHERE City=N'Hà Nội'";
            stmt.execute(update);
            
            String updateString = "UPDATE Customers SET CompanyName=?, Address=?, City=?, Country=? WHERE City=?";
            PreparedStatement preStmt = con.prepareStatement(updateString);
            preStmt.setString(1, "Lục Huyền Cầm");
            preStmt.setString(2, "Trần Quốc Toản");
            preStmt.setString(3, "Đà Nẵng");
            preStmt.setString(4, "Việt Nam"); 
            preStmt.setString(5, "México D.F.");            
            preStmt.executeUpdate();
                
            String query = "SELECT CompanyName AS 'Tên tiệm', Address AS 'Địa chỉ', City AS 'Thành phố', Phone AS 'Điện thoại', Country AS 'Quốc gia' FROM Customers";
            ResultSet rs = stmt.executeQuery(query);
            displayResult(rs, "Khách hàng");
            
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    
    /** 
     * Shows resultset 
     */
    void displayResult(ResultSet rs, String tableName) {
        ResultsModel model = new ResultsModel();     // Create a table model
        model.setResultSet(rs);      
        JTable table = new JTable(model);            // Create a table from the model
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);   // Use scrollbars
        Font font = table.getTableHeader().getFont().deriveFont(Font.BOLD);
        table.getTableHeader().setFont(font); // Bold header font
        
        JFrame jf = new JFrame(tableName);
        jf.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jf.setSize(800, 400);
        JTextArea jt = new JTextArea();
        jf.getContentPane().add(new JScrollPane(table));        
        jf.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new SQLServerEx();
    }
}
