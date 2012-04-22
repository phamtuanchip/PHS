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
    import java.sql.ResultSetMetaData;
    import java.sql.Statement;
import java.util.Vector;
    import javax.swing.JOptionPane;
    import javax.swing.JTable;
    import javax.swing.JTextField;
    import javax.swing.JComboBox;



/**
 *
 * @author Administrator
 */
public class publicClass {
    public  void hiddencol(JTable tblName, int colNum )
    {
        tblName.getColumnModel().getColumn(colNum).setMaxWidth(0);
        tblName.getColumnModel().getColumn(colNum).setMinWidth(0); 
        tblName.getColumnModel().getColumn(colNum).setPreferredWidth(0);
    }
    
     // COPY Tu day di tat ca cac form
                                  /* import cac goi nay
                                    import java.sql.Connection;
                                    import java.sql.ResultSet;
                                    import java.sql.ResultSetMetaData;
                                    import java.sql.Statement;
                                    import javax.swing.JOptionPane;
                                    import javax.swing.JTable;
                                    import javax.swing.JTextField;
                                   
                                   */
    public void SQLRUN(String SQLTEXT) // Ham de chay cau truy van
    {
        try{
            Connection   conn = new connectDatabase().getConnection();
            Statement stm = conn.createStatement();
            stm.execute(SQLTEXT);
            conn.close();
            stm.close();
            System.out.println(SQLTEXT);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public String  SelectedRowToString(JTable TableName, int CollNumb ) // Lay va In ten truong thu CollNumb ra  bietn ReturnValua, lay 1 bien
    {
        //int CollNumb ;
        //CollNumb=0;
        int  RowSelected;
        String ReturnValue;
        RowSelected = TableName.getSelectedRow();
        ReturnValue=TableName.getValueAt(RowSelected,CollNumb).toString();
        System.out.println(ReturnValue+" ");
        return ReturnValue;
        //System.out.println(TableName.getValueAt(RowSelected,CollNumb));
        
    }
    public void addItemTooCombobox(JComboBox ComboboxName,String sqlcb,String Firstchoice) // Dua du lieu tu cau truy van vao combobox, chi co 1 truong trong du lieu
    {
        //String sqlcb = "select TenTruogn from TenBang";
        //ComboboxName.addItem("Chon 1");
        if (Firstchoice !=""){
            ComboboxName.addItem(Firstchoice);
        }
        new sqlDatabase().addDataCombobox(sqlcb,ComboboxName);
    }
    
    public void addItemToTable(JTable TableName,String sqltb )// Lay du lieu tu cau truy van dua vao tabe, nhieu ten bang
    {
        
        //String sqltb=  "select * from Tenbang";
        new sqlDatabase().addDataTable(sqltb,TableName);
        
    }
    
    public void addDataToTextField(String sql,String rsName,JTextField TexFiedName) // dua du lieu tu trong cau truy van vao bang
    {
        //Connection con =null;
        if(con == null){
            con = new connectDatabase().getConnection();
        }
        try{
            Statement sttm = con.createStatement();
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
    public String selectDateToString(String sql,String rsName) // dua du lieu tu trong cau truy van ra bien string
    {
        String returnvl ="" ;
        //Connection con =null;
        if(con == null){
            con = new connectDatabase().getConnection();
        }
        try{
            Statement sttm = con.createStatement();
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
   // TungBV Code
      public float selectDataToFloat(String sql,String rsName) // dua du lieu tu trong cau truy van ra bien float
    {
        float returnvl =0 ;
        //Connection con =null;
        if(con == null){
            con = new connectDatabase().getConnection();
        }
        try{
            Statement sttm = con.createStatement();
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
        if(con == null){
            con = new connectDatabase().getConnection();
        }
        try{
            Statement sttm = con.createStatement();
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
    // End TungBV code
   
    
    
    //  Copy tu day di tat ca cac form
    
    /** Creates a new instance of publicClass */
    public publicClass() {
    }
    
    private Connection con;
}
