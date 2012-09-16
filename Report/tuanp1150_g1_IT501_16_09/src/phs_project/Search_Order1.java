/*
 * Search_Order.java
 *
 * Created on April 7, 2006, 4:10 PM
 */

package phs_project;

import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.*;
import javax.swing.JOptionPane;
import java.util.*;
import java.util.Vector.*;
import java.text.Format;
import java.text.SimpleDateFormat;
/**
 *
 * @author  TUNG
 */
public class Search_Order1 extends javax.swing.JFrame {
    
    /** Creates new form Search_Order */
    public Search_Order1() {
        initComponents();
        AdddatatoCombobox();
        
    }
            
    private void AdddatatoCombobox(){
        String sql = "Select RoomNumb from rooms";
       cbxRoomName.addItem("Chon Phong thuoc don hang");
        new sqlDatabase().addDataCombobox(sql,cbxRoomName);
        
    }
    private int SearchOrder(String roomName,String bdate){
        int orderID = 0;
        Connection conn = new connectDatabase().getConnection();
        CallableStatement cs;
        try{
            cs = conn.prepareCall("{call search_order(?,?)}");
            cs.setString(1,roomName);
            cs.setString(2,bdate);
            ResultSet  rs =  cs.executeQuery();   
            if(rs.wasNull()){
                JOptionPane.showMessageDialog(this,"Khong tim thay don hang nay!!!");
            }
            else{
                while(rs.next()){
                   orderID  = rs.getInt(1);
                }
            }
            
            cs.close();
            conn.close();
        }
        catch(SQLException se){
            System.out.println("loi search_o");
            System.err.println(se);
        }
         return orderID;
    }
    private void fillTableRoomofOrder(){
        //fill data lien quan den 1 order vao table 
        df1 = new SimpleDateFormat("MM/dd/yyyy");
        String beginDate = df1.format(txtEndDate.getDate());
        System.out.println(beginDate);
        int OrderID_value = this.SearchOrder(cbxRoomName.getSelectedItem().toString(),beginDate);
        txtOrderID.setText(""+OrderID_value);
        String cusName = this.getCusName();
        txtCusName.setText(cusName);
        String [] title = {" ","Ten phong","Ngay bat dau","Ngay ket thuc","Thanh toan"};
        TableDataRoom tabledataroom = new TableDataRoom(OrderID_value,title);
        tableSearchOrder.setModel(tabledataroom);
    }
    private String getCusName(){
        //Lay ra ten nguoi chiu trach nhiem thanh toan don hang
        df1 = new SimpleDateFormat("MM/dd/yyyy");
        String beginDate = df1.format(txtEndDate.getDate());
        int OrderID = this.SearchOrder(cbxRoomName.getSelectedItem().toString(),beginDate);
        String Cus_Name = "";
        Connection conn = new connectDatabase().getConnection();
        String sql = "select firstName + lastName As CustomerName from customers ";
        sql = sql + " where customerId = (Select customerId from orders where orderId = " + OrderID + ")";
        try{
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stmt.executeQuery(sql);
            while(resultSet.next()){
                Cus_Name = resultSet.getString(1);
            }
            stmt.close();
            conn.close();
        }
        catch(SQLException se){
            System.err.println(se);
        }
        return Cus_Name;
    }
    private Vector getSizeVector(){
        Vector roomInOrder = new Vector();
        df1 = new SimpleDateFormat("MM/dd/yyyy");
        String beginDate = df1.format(txtEndDate.getDate());
        int OrderID = this.SearchOrder(cbxRoomName.getSelectedItem().toString(),beginDate);
        String sql ="";
        sql = sql + "SELECT dbo.rooms.roomNumb FROM dbo.orderDetail INNER JOIN dbo.rooms ON dbo.orderDetail.roomId = dbo.rooms.roomId where orderid = " + OrderID;

                      
        try{
            Connection conn = new connectDatabase().getConnection();
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                roomInOrder.addElement(rs.getString(1));
                return roomInOrder;
            }
            st.close();
            conn.close();
        }
        catch(SQLException se){
            System.out.println(se);
        }
        return roomInOrder;
    }
    private Vector getVectorRoom(){
        //Lay ra so phong muon check out
        Vector vt = new Vector();
        int ro = tableSearchOrder.getSelectedRow();
        if(ro == -1){
            JOptionPane.showMessageDialog(this,"Bạn phải chọn ít nhất một phòng!");
        }
        else{
            for(int i=0; i<getData.RowCount1;i++) {
                if(tableSearchOrder.getValueAt(i,0).equals(true)) {
                    vt.addElement(tableSearchOrder.getValueAt(i,1));                        
                }
                else{                        
                    vt.removeElement(tableSearchOrder.getValueAt(i,1));
                }                   
            }          
            if   (vt.size()==0 ) {
                JOptionPane.showMessageDialog(this,"Bạn phải chọn ít nhất một phòng");
            }
            else {
                 return vt;
            }
        } // else cua phan chua chon    
        return vt;
    }                              
    private void UpdateOrderDetail(){
    //UPdate lai ngay tra phong va tinh tong tien cua orderDetail 
        Vector vector = this.getVectorRoom();
        df1 = new SimpleDateFormat("MM/dd/yyyy");
        String beginDate = df1.format(txtEndDate.getDate());
        int OrderID = this.SearchOrder(cbxRoomName.getSelectedItem().toString(),beginDate);
        df2 = new SimpleDateFormat("MM/dd/yyyy");
        String EndDate = df2.format(txtEdate.getDate());
        String checkbox = "";
        if(chbxTratruoc.isSelected() == true){
            checkbox = "Yes";
        }
        else{
            checkbox = "No";
        }
        execUpdateOrderDetail(OrderID,vector,EndDate,checkbox);
    }
    private void execUpdateOrderDetail(int OrderID,Vector vector,String Edate,String check){
    // exec proc checkoutProcess
        Connection conn = new connectDatabase().getConnection();
        CallableStatement cs;
        try{
            for(int i = 0;i<vector.size();i++){
                cs = conn.prepareCall("{call checkoutProcess(?,?,?,?)}",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                cs.setInt(1,OrderID);
                cs.setString(2,vector.get(i).toString());
                cs.setString(3,Edate);
                cs.setString(4,check);
                cs.execute();                
            }         
        conn.close();   
        }      
        catch(SQLException se){        
            
        }       
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cbxRoomName = new javax.swing.JComboBox();
        jButton2 = new javax.swing.JButton();
        txtEndDate = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtOrderID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCusName = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSearchOrder = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        chbxTratruoc = new javax.swing.JCheckBox();
        txtEdate = new com.toedter.calendar.JDateChooser();
        chbxCheckOutAll = new javax.swing.JCheckBox();
        btnChon = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        btnHuybo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tim don hang"));
        jLabel1.setText("Ten phong");

        jLabel2.setText("Ngay bat dau");

        jButton2.setText("Tim");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .add(22, 22, 22)
                .add(cbxRoomName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 139, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(55, 55, 55)
                .add(jLabel2)
                .add(25, 25, 25)
                .add(txtEndDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(35, 35, 35)
                .add(jButton2)
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel1)
                        .add(cbxRoomName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel2)
                        .add(jButton2))
                    .add(txtEndDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jLabel3.setText("Don hang so");

        jLabel4.setText("Nguoi giao dich");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel3)
                .add(12, 12, 12)
                .add(txtOrderID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 49, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(146, 146, 146)
                .add(jLabel4)
                .add(12, 12, 12)
                .add(txtCusName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 138, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(170, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(txtCusName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(txtOrderID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sach cac phong da o"));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setViewportView(tableSearchOrder);

        jLabel5.setText("Ngay tra phong");

        chbxTratruoc.setText("Tra phong truoc thoi han");
        chbxTratruoc.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        chbxTratruoc.setMargin(new java.awt.Insets(0, 0, 0, 0));

        chbxCheckOutAll.setText("Tra tat ca cac phong thuoc don hang");
        chbxCheckOutAll.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        chbxCheckOutAll.setMargin(new java.awt.Insets(0, 0, 0, 0));
        chbxCheckOutAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbxCheckOutAllActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(chbxTratruoc)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 139, Short.MAX_VALUE)
                        .add(jLabel5)
                        .add(14, 14, 14)
                        .add(txtEdate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(158, 158, 158))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(chbxCheckOutAll)
                        .addContainerGap(467, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(chbxCheckOutAll)
                .add(15, 15, 15)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                .add(17, 17, 17)
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel5)
                        .add(chbxTratruoc))
                    .add(txtEdate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnChon.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jButton3.setText("Chon");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnHuybo.setText("Huy bo");
        btnHuybo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyboActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout btnChonLayout = new org.jdesktop.layout.GroupLayout(btnChon);
        btnChon.setLayout(btnChonLayout);
        btnChonLayout.setHorizontalGroup(
            btnChonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnChonLayout.createSequentialGroup()
                .add(222, 222, 222)
                .add(jButton3)
                .add(64, 64, 64)
                .add(btnHuybo)
                .addContainerGap(262, Short.MAX_VALUE))
        );
        btnChonLayout.setVerticalGroup(
            btnChonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnChonLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btnChonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnHuybo)
                    .add(jButton3)))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, btnChon, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 14, Short.MAX_VALUE)
                .add(btnChon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chbxCheckOutAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbxCheckOutAllActionPerformed
        if(chbxCheckOutAll.isSelected() == true){
            for(int i=0; i<getData.RowCount1;i++) {
                tableSearchOrder.setValueAt(true,i,0);                        
            }
        }
        else{
            for(int i=0; i<getData.RowCount1;i++) {
                tableSearchOrder.setValueAt(false,i,0);                        
            }
        }
// TODO add your handling code here:
    }//GEN-LAST:event_chbxCheckOutAllActionPerformed

    private void btnHuyboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyboActionPerformed
// TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnHuyboActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
// TODO add your handling code here:
        UpdateOrderDetail();   
        Vector vector = this.getVectorRoom();
        df1 = new SimpleDateFormat("MM/dd/yyyy");
        String beginDate = df1.format(txtEndDate.getDate());
        int OrderID = this.SearchOrder(cbxRoomName.getSelectedItem().toString(),beginDate);
        checkOut1 checkOu = new checkOut1(this,true,vector,OrderID);
        checkOu.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
        df1 = new SimpleDateFormat("MM/dd/yyyy");
        String beginDate = df1.format(txtEndDate.getDate());
         //JOptionPane.showMessageDialog(this,beginDate);
        if(cbxRoomName.getSelectedItem().toString() == "Chon Phong thuoc don hang" || beginDate.length() == 0){
            JOptionPane.showMessageDialog(this,"Phai chon phong va ngay bat dau!!!");
        }
        else{
            fillTableRoomofOrder();                      
        }
    }//GEN-LAST:event_jButton2ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Search_Order1().setVisible(true);
            }
        });
    }
    private SimpleDateFormat df1,df2;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnChon;
    private javax.swing.JButton btnHuybo;
    private javax.swing.JComboBox cbxRoomName;
    private javax.swing.JCheckBox chbxCheckOutAll;
    private javax.swing.JCheckBox chbxTratruoc;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableSearchOrder;
    private javax.swing.JTextField txtCusName;
    private com.toedter.calendar.JDateChooser txtEdate;
    private com.toedter.calendar.JDateChooser txtEndDate;
    private javax.swing.JTextField txtOrderID;
    // End of variables declaration//GEN-END:variables
    
}
