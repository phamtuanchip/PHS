/*
 * purchard.java
 *
 * Created on April 28, 2006, 12:47 AM
 */
package phs_project;

import java.sql.*;
import java.sql.Connection;

/**
 *
 * @author  TUNG
 */
public class purchard extends javax.swing.JDialog {

    public String Order_ID;
    public Connection conn;

    /** Creates new form purchard */
    public purchard(javax.swing.JDialog parent, boolean modal, String OrderID) {
        super(parent, modal);
        this.Order_ID = OrderID;
        this.conn = new connectDatabase().getConnection();
        initComponents();
        OrderInfor(Order_ID);
        fillTableRoomDetail(Order_ID);
        fillTableServiceDetail(Order_ID);
        tinhtongtienphong(Order_ID);
        tinhtongtiendichvu(Order_ID);
        cong(sumRoomPrice, sumServicesPrice);
        thanhtien(Order_ID);
    }

    public purchard(java.awt.Frame parent, boolean modal, String OrderID) {
        super(parent, modal);
        this.Order_ID = OrderID;
        this.conn = new connectDatabase().getConnection();
        initComponents();
        OrderInfor(Order_ID);
        fillTableRoomDetail(Order_ID);
        fillTableServiceDetail(Order_ID);
        tinhtongtienphong(Order_ID);
        tinhtongtiendichvu(Order_ID);
        cong(sumRoomPrice, sumServicesPrice);
        thanhtien(Order_ID);
    }

    private void OrderInfor(String OrderID) {
        String sql = "";
        sql = sql + "select orderid,orderDate,name,idCardNumb,address,email from orderInfo where orderid ='" + OrderID + "'";
        try {
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lblOrderID.setText(rs.getString(1));
                lblBegindate.setText(rs.getString(2));
                lblCusName.setText(rs.getString(3));
                lblCMND.setText(rs.getString(4));
                lblAddress.setText(rs.getString(5));
                lblEmail.setText(rs.getString(6));
            }
            rs.close();
            st.close();
        } catch (SQLException se) {
            System.out.println("loi ORderInfo");
            System.out.println(se);
        }

    }

    private void fillTableRoomDetail(String OrderID) {
        String sql = "";
        sql = sql + "select [Mã đơn hàng],[Tên phòng],[Ngày bắt đầu],[Ngày kết thúc],[Số ngày ],[�?ơn giá phòng],[Phụ phí], ";
        sql = sql + "[Giảm giá],[Thành ti�?n] from Tinhtienphong where [Mã đơn hàng] = '" + OrderID + "'";
        new Utils().addItemToTable(tblRoomPrice, sql);
        new Utils().hiddencol(tblRoomPrice, 0);
    }

    private void fillTableServiceDetail(String OrderID) {
        String sql = "";
        sql = sql + "select Mã,[Của phòng] as [Phòng], Loại, [Tên dịch vụ],[Giá ti�?n], [Giảm giá], [Phụ phí], [Tổng ti�?n] ";
        sql = sql + "from costofservices where Mã = '" + OrderID + "'";
        new Utils().addItemToTable(tblServicesPrice, sql);
        new Utils().hiddencol(tblServicesPrice, 0);
    }

    private void tinhtongtienphong(String OrderID) {
        String sql = "";
        sql = sql + "Select sum([Thành ti�?n]) as tongtienphong from Tinhtienphong where [Mã đơn hàng] = '" + OrderID + "'";
        sql = sql + " group by [Mã đơn hàng]";
        sumRoomPrice = new Utils().selectDateToString(sql, "tongtienphong");
        lblSumPriceRoom.setText(sumRoomPrice);
    }

    private void tinhtongtiendichvu(String OrderID) {
        String sql = "";
        sql = sql + "select sum([Tổng ti�?n]) as tongtiendichvu from costofservices where Mã = '" + OrderID + "'";
        sql = sql + " group by Mã";
        sumServicesPrice = new Utils().selectDateToString(sql, "tongtiendichvu");
        lblSumPriceServices.setText(sumServicesPrice);
    }

    private void cong(String str1, String str2) {
        /// if (str2.toString().equals(null))    {
        //    JOptionPane.showMessageDialog(this,"NUll");
        // }
        if (str2.toString().equals("")) {
            // JOptionPane.showMessageDialog(this,"..");
            str2 = "0";
        }
        double sum = new Double(str1) + new Double(str2);
        lblSum.setText("" + sum);
    }

    private void thanhtien(String OrderID) {
        String sql = "select totalfee,addition,discount from orders where orderid ='" + OrderID + "'";
        String tt = new Utils().selectDateToString(sql, "totalfee");
        lblSumOrder.setText(tt);
        String add = new Utils().selectDateToString(sql, "addition");
        lblAdd.setText(add);
        String discount = new Utils().selectDateToString(sql, "discount");
        lbldiscount.setText(discount);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRoomPrice = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        lblSumPriceRoom = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblServicesPrice = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        lblSumPriceServices = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        lblSum = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lbldiscount = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblAdd = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lblSumOrder = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblCusName = new javax.swing.JLabel();
        lblOrderID = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblCMND = new javax.swing.JLabel();
        lblBegindate = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jLabel1.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14));
        jLabel1.setText("H\u00d3A \u0110\u01a0N THANH TO\u00c1N");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ti\u1ec1n ph\u00f2ng"));
        tblRoomPrice.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        tblRoomPrice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblRoomPrice.setAutoscrolls(false);
        jScrollPane1.setViewportView(tblRoomPrice);

        jLabel16.setText("T\u1ed5ng ti\u1ec1n ph\u00f2ng:");

        lblSumPriceRoom.setText("0");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(jLabel16)
                        .add(34, 34, 34)
                        .add(lblSumPriceRoom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(15, 15, 15))
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 91, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel16)
                    .add(lblSumPriceRoom)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("D\u1ecbch v\u1ee5"));
        tblServicesPrice.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        tblServicesPrice.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblServicesPrice);

        jLabel18.setText("T\u1ed5ng ti\u1ec1n d\u1ecbch v\u1ee5:");

        lblSumPriceServices.setText("0");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
                        .addContainerGap())
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                        .add(jLabel18)
                        .add(37, 37, 37)
                        .add(lblSumPriceServices)
                        .add(74, 74, 74))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 87, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblSumPriceServices)
                    .add(jLabel18))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Th\u00e0nh ti\u1ec1n"));
        jLabel20.setText("T\u1ed5ng ti\u1ec1n:");

        lblSum.setText("0");

        jLabel22.setText("Khuy\u1ebfn m\u1ea1i:");

        lbldiscount.setText("0");

        jLabel24.setText("Vat:");

        lblAdd.setText("0");

        jLabel26.setText("Ti\u1ec1n ph\u1ea3i tr\u1ea3:");

        lblSumOrder.setText("0");

        jLabel3.setText("H\u00e0 n\u1ed9i:....../....../.......");

        jLabel5.setText("Ng\u01b0\u1eddi l\u1eadp");

        jLabel7.setText("Ng\u01b0\u1eddi n\u1ed9p ti\u1ec1n");

        jLabel9.setText("Ng\u01b0\u1eddi nh\u1eadn");

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .add(jLabel7)
                .add(58, 58, 58)
                .add(jLabel9)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 88, Short.MAX_VALUE)
                .add(jLabel5)
                .add(57, 57, 57))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(226, Short.MAX_VALUE)
                .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 137, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel3)
                .add(21, 21, 21)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel7)
                    .add(jLabel5)
                    .add(jLabel9))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel20)
                            .add(jLabel24))
                        .add(64, 64, 64)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lblAdd)
                            .add(lblSum)))
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel22)
                            .add(jLabel26))
                        .add(49, 49, 49)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lblSumOrder)
                            .add(lbldiscount))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 116, Short.MAX_VALUE)
                .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3Layout.createSequentialGroup()
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel20)
                            .add(lblSum))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel24)
                            .add(lblAdd))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel22)
                            .add(lbldiscount))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel26)
                            .add(lblSumOrder)))
                    .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("\u0110\u01a1n h\u00e0ng s\u1ed1:");

        jLabel4.setText("Kh\u00e1ch h\u00e0ng:");

        jLabel6.setText("\u0110\u1ecba ch\u1ec9:");

        lblAddress.setText("Address");

        lblCusName.setText("Cusname");

        lblOrderID.setText("Order Id");

        jLabel12.setText("Ng\u00e0y l\u1eadp \u0111\u01a1n h\u00e0ng:");

        jLabel8.setText("S\u1ed1 CMT:");

        jLabel10.setText("Email:");

        lblEmail.setText("Email");

        lblCMND.setText("CMND");

        lblBegindate.setText("Date");

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(jLabel4)
                    .add(jLabel6))
                .add(21, 21, 21)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblCusName)
                    .add(lblOrderID)
                    .add(lblAddress))
                .add(158, 158, 158)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel12)
                    .add(jLabel10)
                    .add(jLabel8))
                .add(44, 44, 44)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblEmail)
                    .add(lblCMND)
                    .add(lblBegindate))
                .addContainerGap(179, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2)
                            .add(lblOrderID))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel4)
                            .add(lblCusName))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel6)
                            .add(lblAddress)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4Layout.createSequentialGroup()
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel12)
                            .add(lblBegindate))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(lblCMND)
                            .add(jLabel8))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel10)
                            .add(lblEmail)))))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(206, 206, 206)
                .add(jLabel1)
                .addContainerGap())
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(14, 14, 14)
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // java.awt.EventQueue.invokeLater(new Runnable() {
        //   public void run() {
        //     new purchard(new javax.swing.JFrame(), true,100).setVisible(true);
        //  }
        // });
    }
    private String sumRoomPrice;
    private String sumServicesPrice;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAdd;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblBegindate;
    private javax.swing.JLabel lblCMND;
    private javax.swing.JLabel lblCusName;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblOrderID;
    private javax.swing.JLabel lblSum;
    private javax.swing.JLabel lblSumOrder;
    private javax.swing.JLabel lblSumPriceRoom;
    private javax.swing.JLabel lblSumPriceServices;
    private javax.swing.JLabel lbldiscount;
    private javax.swing.JTable tblRoomPrice;
    private javax.swing.JTable tblServicesPrice;
    // End of variables declaration//GEN-END:variables
}
