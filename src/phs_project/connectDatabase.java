package phs_project;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;


public class connectDatabase {

    public String readConfig() {

        try {
            // Open the file that is the first
            URL url = getClass().getResource("Image/connect.txt");
            File file = new File(url.getPath());

            FileInputStream fstream = new FileInputStream(file);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                return strLine;
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            e.printStackTrace();
            //System.err.println("Error: " + e.getMessage());

        }
        return null;

    }

    public Connection getConnection() {
        try {

            String dsn = readConfig();
            if (dsn == null) {
                dsn = "jdbc:microsoft:sqlserver://" + loginForm.server + ":1433;databaseName =phs_data;User=sa;Password=1234567";
            }
            //load driver
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
            //make connection
            con = java.sql.DriverManager.getConnection(dsn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    public static Connection con;
}
