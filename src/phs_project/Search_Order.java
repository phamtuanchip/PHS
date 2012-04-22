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
public class Search_Order extends javax.swing.JFrame {
    public int OrderID;
    public static int size = 0;
    /** Creates new form Search_Order */
    public Search_Order(int Order_ID) {
        this.OrderID = Order_ID;        
        initComponents();
        hiddenComponent();
        int result = fillTableRoomofOrder();
             
    }      
    private void hiddenComponent(){
        //lblNgaytra.setVisible(false);
        txtEdate.setVisible(false);
    }
    public int fillTableRoomofOrder(){
        //fill data lien quan den 1 order vao table         
        txtOrderID.setText(""+OrderID);
        String cusName = this.getCusName();
        txtCusName.setText(cusName);
        String [] title = {" ","Tên phòng","Ngày bắt đầu","Ngày kết thúc","Trạng thái"};
        TableDataRoom tabledataroom = new TableDataRoom(OrderID,title);
        tableSearchOrder.setModel(tabledataroom);
        size = tableSearchOrder.getRowCount();
        if(size == 0){
            //JOptionPane.showMessageDialog(this,"don hang da duoc thanh toan");            
        }
        return size;
    }
    public String getCusName(){
        //Lay ra ten nguoi chiu trach nhiem thanh toan don hang        
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
        df2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String EndDate = df2.format(txtEdate.getDate());
        
        String checkbox = "";
        if(chbxTratruoc.isSelected() == true){
            checkbox = "Yes";
        }
        else{
            checkbox = "No";
        }
        JOptionPane.showMessageDialog(this,"" + EndDate + "" + checkbox);
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
                JOptionPane.showMessageDialog(this," "+ OrderID + " " + vector.get(i).toString() +" " + Edate + " " + check);
                System.out.println("exec checkoutProcess "+ OrderID + ",'" + vector.get(i).toString() +"', '" + Edate + "', " + check + "'");
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
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtOrderID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCusName = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSearchOrder = new javax.swing.JTable();
        chbxCheckOutAll = new javax.swing.JCheckBox();
        btnChon = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        btnHuybo = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        chbxTratruoc = new javax.swing.JCheckBox();
        txtEdate = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Th\u00f4ng tin \u0111\u01a1n h\u00e0ng"));
        jLabel3.setText("M\u00e3 \u0111\u01a1n h\u00e0ng");

        txtOrderID.setEditable(false);

        jLabel4.setText("Ng\u01b0\u1eddi ch\u1ecbu ch\u00e1ch nhi\u1ec7m");

        txtCusName.setEditable(false);

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(jLabel4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 21, Short.MAX_VALUE)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(txtOrderID)
                    .add(txtCusName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel3))
                    .add(txtOrderID, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(11, 11, 11)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel4)
                    .add(txtCusName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), "Danh s\u00e1ch c\u00e1c ph\u00f2ng \u0111\u00e3 \u1edf"));
        jScrollPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jScrollPane1.setViewportView(tableSearchOrder);

        chbxCheckOutAll.setText("Ch\u1ecdn h\u1ebft \u0111\u01a1n h\u00e0ng");
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
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
            .add(jPanel3Layout.createSequentialGroup()
                .add(chbxCheckOutAll)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(chbxCheckOutAll)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 122, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(63, 63, 63))
        );

        btnChon.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jButton3.setText("Tr\u1ea3 ph\u00f2ng");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btnHuybo.setText("H\u1ee7y b\u1ecf");
        btnHuybo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyboActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout btnChonLayout = new org.jdesktop.layout.GroupLayout(btnChon);
        btnChon.setLayout(btnChonLayout);
        btnChonLayout.setHorizontalGroup(
            btnChonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, btnChonLayout.createSequentialGroup()
                .addContainerGap(400, Short.MAX_VALUE)
                .add(jButton3)
                .add(16, 16, 16)
                .add(btnHuybo)
                .addContainerGap())
        );
        btnChonLayout.setVerticalGroup(
            btnChonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnChonLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(btnChonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnHuybo)
                    .add(jButton3))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "T\u00f9y ch\u1ecdn"));
        chbxTratruoc.setText("Tr\u1ea3 ch\u01b0\u1edbc th\u1eddi h\u1ea1n");
        chbxTratruoc.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        chbxTratruoc.setMargin(new java.awt.Insets(0, 0, 0, 0));
        chbxTratruoc.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chbxTratruocItemStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(txtEdate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(chbxTratruoc))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(chbxTratruoc)
                .add(17, 17, 17)
                .add(txtEdate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, btnChon, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 190, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnChon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chbxTratruocItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chbxTratruocItemStateChanged
// TODO add your handling code here:
        if(chbxTratruoc.isSelected() == true){
            //lblNgaytra.setVisible(true);
            txtEdate.setVisible(true);
        }
        else{
            hiddenComponent();
        }
    }//GEN-LAST:event_chbxTratruocItemStateChanged

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
        checkOut1 checkOu = new checkOut1(this,true,vector,OrderID);
        checkOu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Search_Order(0).setVisible(true);
            }
        });
    }
    private SimpleDateFormat df1,df2;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnChon;
    private javax.swing.JButton btnHuybo;
    private javax.swing.JCheckBox chbxCheckOutAll;
    private javax.swing.JCheckBox chbxTratruoc;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableSearchOrder;
    private javax.swing.JTextField txtCusName;
    private com.toedter.calendar.JDateChooser txtEdate;
    private javax.swing.JTextField txtOrderID;
    // End of variables declaration//GEN-END:variables
    
}
