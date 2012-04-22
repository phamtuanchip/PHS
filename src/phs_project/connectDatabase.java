package phs_project;
import java.sql.*;

public class connectDatabase{    
    public Connection getConnection(){
		try{
                    String dsn = "jdbc:microsoft:sqlserver://"+loginForm.server+":1433;databaseName =phs_data;User=sa;Password=sa";
                    //load driver
                    Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
                    //make connection
                    con = java.sql.DriverManager.getConnection(dsn);
                    if(con!=null){
                    	System.out.println ("Connection Success !!!");
                    }
		}
		catch(Exception e){
			System.out.println ("khong connect duoc");
		}
		return con;
	}
        public static Connection con;
}
