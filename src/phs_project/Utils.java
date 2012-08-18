/*
 * publicClass.java
 *
 * Created on April 16, 2006, 7:05 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


package phs_project;
    import java.sql.Connection;
    import java.sql.ResultSet;
    import java.sql.Statement;
    import java.util.Vector;
    import javax.swing.JTable;
    import javax.swing.JTextField;
    import javax.swing.JComboBox;



public class Utils {
    public static void hiddencol(JTable tblName, int colNum )
    {
        tblName.getColumnModel().getColumn(colNum).setMaxWidth(0);
        tblName.getColumnModel().getColumn(colNum).setMinWidth(0); 
        tblName.getColumnModel().getColumn(colNum).setPreferredWidth(0);
    }
    
     
    public static void SQLRUN(String SQLTEXT) // Ham de chay cau truy van
    {
        try{
            Connection   conn = getConnection();
            Statement stm = conn.createStatement();
            stm.execute(SQLTEXT);
            conn.close();
            stm.close();
             
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static String  SelectedRowToString(JTable TableName, int CollNumb ) // Lay va In ten truong thu CollNumb ra  bietn ReturnValua, lay 1 bien
    {

        int  RowSelected;
        String ReturnValue;
        RowSelected = TableName.getSelectedRow();
        ReturnValue=TableName.getValueAt(RowSelected,CollNumb).toString();

        return ReturnValue;

        
    }
    public static void addItemTooCombobox(JComboBox ComboboxName,String sqlcb,String Firstchoice) // Dua du lieu tu cau truy van vao combobox, chi co 1 truong trong du lieu
    {

        if (!Firstchoice.equals("")){
            ComboboxName.addItem(Firstchoice);
        }
        new sqlDatabase().addDataCombobox(sqlcb,ComboboxName);
    }
    
    public static void addItemToTable(JTable TableName,String sqltb )// Lay du lieu tu cau truy van dua vao tabe, nhieu ten bang
    {
        
        new sqlDatabase().addDataTable(sqltb,TableName);
        
    }
    
    public static void addDataToTextField(String sql,String rsName,JTextField TexFiedName) // dua du lieu tu trong cau truy van vao bang
    {

        try{
            Statement sttm = getConnection().createStatement();
            ResultSet rs = sttm.executeQuery(sql);
            while(rs.next()){
                TexFiedName.setText(rs.getString(rsName));
            }
            sttm.close();
            rs.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    

    public static String selectDateToString(String sql,String rsName)  
    {
        String returnvl ="" ;

        try{
            Statement sttm = getConnection().createStatement();
            ResultSet rs = sttm.executeQuery(sql);
            while(rs.next()){
                System.err.println(rs.getString(rsName));
                returnvl = rs.getString(rsName);
            }
            sttm.close();
            rs.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return returnvl;
    }
    
      public static float selectDataToFloat(String sql,String rsName) {
        float returnvl =0 ;

        try{
            Statement sttm = getConnection().createStatement();
            ResultSet rs = sttm.executeQuery(sql);
            while(rs.next()){
                System.err.println(rs.getString(rsName));
                returnvl = rs.getFloat(rsName);
            }
            sttm.close();
            rs.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return returnvl;
    }
    public Vector getDataToVector(String sql,String rsName){
        Vector vt = new Vector();
        try{
            Statement sttm = getConnection().createStatement();
            ResultSet rs = sttm.executeQuery(sql);
            while(rs.next()){
                System.err.println(rs.getString(rsName));
                vt.addElement(rs.getString(rsName));
            }
            sttm.close();
            rs.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return vt;
    }
     
    
    /** Creates a new instance of publicClass */
    public Utils() {
    }
    public static Connection getConnection() {
        try {
        if(con == null || con.isClosed()){
            con = new connectDatabase().getConnection();
        }
        } catch (Exception e) {
        e.printStackTrace();
        }
        return con;
    }
    private static Connection con;
}
