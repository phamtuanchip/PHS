/*
 * sqlDatabase.java
 *
 * Created on March 15, 2006, 7:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package phs_project;
import javax.swing.*;
import java.sql.*;
import javax.swing.table.TableCellRenderer;
/**
 *
 * @author Administrator
 */
public class sqlDatabase {
    
    /** Creates a new instance of sqlDatabase */
    public sqlDatabase() {
    }
    public void addDataTable(String sql,JTable tablename){ 
        if(con == null){        
            con = new connectDatabase().getConnection();
        }
        try{            
            Statement st = con.createStatement();        
            ResultSet rs = st.executeQuery(sql);
                
            ResultsModel model = new ResultsModel();     // Create a table model
            model.setResultSet(rs);      
            tablename.setModel(model);            
          
            st.close();
            rs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    //
    public void addDataTable(String sql,JTable tablename, int type){ 
        ResultSet rs;
        Statement st;
        tablename.setFocusable(false);
        
        if(con == null){        
            con = new connectDatabase().getConnection();
        }        
        if(type == 3){
            try{
                CallableStatement cs;
                cs = con.prepareCall(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);                
                rs = cs.executeQuery();
                ResultsTableModel model = new ResultsTableModel();     // Create a table model        
                model.setResultSet(rs);               
                tablename.setModel(model);
                TableCellRenderer renderer = new CustomTableCellRenderer(); 
                tablename.setDefaultRenderer( Class.forName( "java.lang.String" ), renderer );                  
                rs.close();
            }
            catch(Exception e){
                System.out.println(e);
            }
        }
        if(type == 2){
            try{
                CallableStatement cs;
                cs = con.prepareCall(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
                rs = cs.executeQuery();
                ResultsTableModel model = new ResultsTableModel();     // Create a table model        
                model.setResultSet(rs);               
                tablename.setModel(model);
                //TableCellRenderer renderer = new CustomTableCellRenderer(); 
                //tablename.setDefaultRenderer( Class.forName( "java.lang.String" ), renderer );                  
                rs.close();
            }
            catch(Exception e){
                System.out.println(e);
            }            
        }
        else{
            try{                   
                st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);        
                rs = st.executeQuery(sql);
                ResultsTableModel model = new ResultsTableModel();     // Create a table model        
                model.setResultSet(rs);               
                tablename.setModel(model);
                TableCellRenderer renderer = new CustomTableCellRenderer(); 
                tablename.setDefaultRenderer( Class.forName( "java.lang.String" ), renderer );  
                st.close();
                rs.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public int ConverSql(String sql,int count){
        int result = 0 ;
        if(con == null){        
            con = new connectDatabase().getConnection();      
        }
        try{ 
            Statement sttm = con.createStatement();        
            ResultSet rs = sttm.executeQuery(sql);            
        while(rs.next()){
            result =  rs.getInt(count);
	}
        sttm.close(); 
        rs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        } 
        return result;
    }
    //
       
    
    public void addDataCombobox(String sql,JComboBox comboboxname){
        if(con == null){        
            con = new connectDatabase().getConnection();      
        }
        try{ 
            Statement sttm = con.createStatement();        
            ResultSet rs = sttm.executeQuery(sql);            
        while(rs.next()){
            comboboxname.addItem(rs.getString(1));
	}
        sttm.close(); 
        rs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addDataCombobox(String sql,JComboBox comboboxname,String title){//overwrite method
        if(con == null){        
            con = new connectDatabase().getConnection();      
        }
        if(!title.equals("")){
            comboboxname.addItem(title);
        }
        try{ 
            Statement sttm = con.createStatement();        
            ResultSet rs = sttm.executeQuery(sql);            
        while(rs.next()){
            comboboxname.addItem(rs.getString(1));
	}
        sttm.close(); 
        rs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
//  
    public String converToString(String sql,int count){
        String result = "" ;
        if(con == null){        
            con = new connectDatabase().getConnection();      
        }
        try{ 
            Statement sttm = con.createStatement();        
            ResultSet rs = sttm.executeQuery(sql);            
            while(rs.next()){
                result =  rs.getString(count);
            }
            sttm.close(); 
            rs.close();
            }
        catch(Exception e){
            e.printStackTrace();
        } 
        return result;
    }
    //////////////////
    public int runSql(String sql){
        int result = 0;
        if(con == null){        
            con = new connectDatabase().getConnection();      
        }
        try{
            Statement sttm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);        
            result = sttm.executeUpdate(sql);                      
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }    
    
      public int getCount(String sql){
        int result = 0;
        if(con == null){        
            con = new connectDatabase().getConnection();      
        }
        try{
            Statement sttm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);        
            ResultSet rs = sttm.executeQuery(sql);                      
            while(rs.next()){
                result++;
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }    
    
    public void addDataToTextField(String sql,JTextField comboboxname){
        if(con == null){        
            con = new connectDatabase().getConnection();      
        }
        try{ 
            Statement sttm = con.createStatement();        
            ResultSet rs = sttm.executeQuery(sql);            
        while(rs.next()){
            comboboxname.setText(rs.getString(1));
	}
        sttm.close(); 
        rs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
private Connection con;
}
