/*
 * CreateUser.java
 *
 * Created on April 23, 2006, 9:31 PM
 */

package phs_project;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author  Administrator
 */
public class CreateUser extends javax.swing.JDialog {
    
    /** Creates new form CreateUser */
    public CreateUser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        ViewUser();
        ResetField();
        addType();      
    }
    public CreateUser() {       
        initComponents();
        ViewUser();
        ResetField();
        addType();      
    }
    private void ViewUser(){
        String sql = "SELECT username as 'Tài khoản', FirstName + ' ' + LastName as 'H�? Tên' FROM users";
        new sqlDatabase().addDataTable(sql,table,2);
    }
    private int CheckValues(){
        int result = 0;
        String sql = "SELECT username FROM users WHERE username = N'"+txtUserName.getText()+"'";
        int i = new sqlDatabase().getCount(sql);
        if(i!= 0){
            if(result == 0){
                JOptionPane.showMessageDialog(this,"Tài khoản đã tồn tại");                
                txtUserName.requestFocus();
                txtUserName.selectAll();
                result = 1;
            }
        }                
        if(txtUserName.getText().trim().length() == 0){
            if(result == 0){
                JOptionPane.showMessageDialog(this,"Tài khoản không được để trống");
                txtUserName.requestFocus();
                result = 1;
            }            
        }
        if(txtPass.getText().equals(txtPassReply.getText()) == false){
            if(result == 0){
                JOptionPane.showMessageDialog(this,"Mật khẩu kiểm tra không giống nhau ");
                txtPass.setText("");
                txtPassReply.setText("");
                txtPass.requestFocus();
                result = 1;                
            }
        }
        return result;
    }
    private void ResetField(){
        txtUserName.setText("");
        txtPass.setText("");
        txtPassReply.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtMobilePhone.setText("");
        txtHomePhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
        txtCity.setText("");
        txtUserName.requestFocus();  
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
    }

    private void addType(){
        String sql = "SELECT userTypeName FROM userType";
        new sqlDatabase().addDataCombobox(sql,cboType);
    }
    private void AddUserName(){    
        username = txtUserName.getText().trim();
        pass = txtPass.getText();
        passreply = txtPassReply.getText();
        firtname = txtFirstName.getText();
        lastname = txtLastName.getText();
        mobilephone = txtMobilePhone.getText();
        homephone = txtHomePhone.getText();        
        email = txtEmail.getText();
        address = txtAddress.getText();
        city = txtCity.getText();
        birthday = new UserFormat().getFormat(txtBirthday.getDate(),"ngay");
        contry = txtCountry.getLocale().getDisplayCountry(); 
        
        type = new sqlDatabase().ConverSql("SELECT userTypeId FROM userType WHERE userTypeName = N'"+cboType.getSelectedItem()+"'",1);
        String sql = "INSERT INTO users (username,password,FirstName,LastName,Birthday,Address,City,Country,Email,Mobile,HomePhone,userTypeId) "; 
                sql = sql + "VALUES('"+username+"','"+pass+"',N'"+firtname+"',N'"+lastname+"','"+birthday+"',N'"+address+"',N'"+city+"','"+contry+"','"+email+"','"+mobilephone+"','"+homephone+"',"+type+")";
        if(CheckValues() == 0){
            int re = new sqlDatabase().runSql(sql);
                    //JOptionPane.showMessageDialog(this,txtBirthday.getDate());
            if(true){
                JOptionPane.showMessageDialog(this,"�?ã thêm một ngư�?i quản lý mới");
            }  
        }                     
    }
    private void getValue(int rowSelect){
        usernameNew = table.getValueAt(rowSelect,0).toString();
        String sql = "SELECT FirstName,LastName,Birthday,Address,City,Email,Mobile,HomePhone,userTypeId FROM users WHERE username = '"+usernameNew+"'";
        Connection con = new connectDatabase().getConnection();
        try{
           Statement sttm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); 
           ResultSet rs = sttm.executeQuery(sql);
           while(rs.next()){
               txtUserName.setText(usernameNew);
               txtFirstName.setText( rs.getString(1));
               txtLastName.setText(rs.getString(2));
               txtBirthday.setDate(new SimpleDateFormat("MM/dd/yy").parse(new UserFormat().getFormat(rs.getDate(3),"ngay")));  
               txtAddress.setText(rs.getString(4));
               txtCity.setText(rs.getString(5));
               txtEmail.setText(rs.getString(6));
               txtMobilePhone.setText(rs.getString(7));
               txtHomePhone.setText(rs.getString(8));
               cboType.setSelectedItem(new sqlDatabase().converToString("SELECT userTypeName FROM userType WHERE userTypeId = "+rs.getInt(9),1));
               //cboType.setSelectedItem("Nhân viên");
           }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        btnUpdate.setEnabled(true);
        btnDelete.setEnabled(true);
    }
    
    private void UpdateUser(){    
        username = txtUserName.getText().trim();
        pass = txtPass.getText();
        passreply = txtPassReply.getText();
        firtname = txtFirstName.getText();
        lastname = txtLastName.getText();
        mobilephone = txtMobilePhone.getText();
        homephone = txtHomePhone.getText();        
        email = txtEmail.getText();
        address = txtAddress.getText();
        city = txtCity.getText();
        birthday = new UserFormat().getFormat(txtBirthday.getDate(),"ngay");
        contry = txtCountry.getLocale().getDisplayCountry(); 
        
        type = new sqlDatabase().ConverSql("SELECT userTypeId FROM userType WHERE userTypeName = N'"+cboType.getSelectedItem()+"'",1);
        
        //String sql = "INSERT INTO users (username,password,FirstName,LastName,Birthday,Address,City,Country,Email,Mobile,HomePhone,userTypeId) "; 
          //     sql = sql + "VALUES('"+username+"','"+pass+"',N'"+firtname+"',N'"+lastname+"',"+birthday+",N'"+address+"',N'"+city+"','"+contry+"','"+email+"','"+mobilephone+"','"+homephone+"',"+type+")";
         String sql = "UPDATE users SET ";
                 sql = sql + "username = '"+username+"',";   
                 sql = sql + "FirstName = N'"+firtname+"',";
                 sql = sql + "LastName = N'"+lastname+"',";
                 sql = sql + "Birthday = '"+birthday+"',";
                 sql = sql + "Address = N'"+address+"',";
                 sql = sql + "City = N'"+city+"',";
                 sql = sql + "Country = N'"+contry+"',";
                 sql = sql + "Email = '"+email+"',";
                 sql = sql + "Mobile = '"+mobilephone+"',";
                 sql = sql + "HomePhone = '"+homephone+"',";                 
                 sql = sql + "userTypeId = "+type+" ";
                 sql = sql + "WHERE userName = '"+usernameNew+"'";
         if(CheckValues() == 0){
                 int result = new sqlDatabase().runSql(sql);
                 if(result!=0){
                     JOptionPane.showMessageDialog(this,"Thay đổi thông tin thành công");
                     ResetField();
                     ViewUser();
                 }
         }
                 
    }
    private void DeleteUser(){
        String sql = "DELETE users WHERE username = '"+usernameNew+"'";
        if(table.getSelectedRow()!=-1){
            int result = new sqlDatabase().runSql(sql);
            if(result != 0){
                JOptionPane.showMessageDialog(this,"�?ã xóa thành công");
                ResetField();
                ViewUser();
            }
        }
        else{
            JOptionPane.showMessageDialog(this,"Bạn phải ch�?n tài khoản muốn xóa");
        }
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel9 = new javax.swing.JPanel();
        txtUserName = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        txtPassReply = new javax.swing.JPasswordField();
        jLabel27 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cboType = new javax.swing.JComboBox();
        jPanel10 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        txtFirstName = new javax.swing.JTextField();
        txtLastName = new javax.swing.JTextField();
        txtMobilePhone = new javax.swing.JTextField();
        txtHomePhone = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtBirthday = new com.toedter.calendar.JDateChooser();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtCity = new javax.swing.JTextField();
        txtCountry = new com.toedter.components.JLocaleChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        pane1 = new javax.swing.JPanel();
        btnExit = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("T\u1ea1o t\u00e0i kho\u1ea3n Ng\u01b0\u1eddi s\u1eed d\u1ee5ng");
        setAlwaysOnTop(true);
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "T\u00e0i kho\u1ea3n"));
        txtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        txtPass.setFont(new java.awt.Font("Tahoma", 0, 11));
        txtPass.setText("gffgg");
        txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        txtPassReply.setFont(new java.awt.Font("Tahoma", 0, 11));
        txtPassReply.setText("gfsgs");
        txtPassReply.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        jLabel27.setText("T\u00e0i kho\u1ea3n");

        jLabel29.setText("Nh\u1eadp l\u1ea1i m\u1eadt kh\u1ea9u");

        jLabel28.setText("M\u1eadt kh\u1ea9u");

        jLabel1.setText("Quy\u1ec1n h\u1ea1n");

        cboType.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel27)
                    .add(jLabel29)
                    .add(jLabel28)
                    .add(jLabel1))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(txtUserName)
                        .add(txtPass, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                        .add(txtPassReply, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 210, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(cboType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel27)
                    .add(txtUserName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtPass, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel28))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel29)
                    .add(txtPassReply, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 13, Short.MAX_VALUE)
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(cboType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Th\u00f4ng tin chi ti\u1ebft"));
        jLabel30.setText("T\u00ean \u0111\u1ec7m");

        jLabel31.setText("T\u00ean");

        jLabel32.setText("Ng\u00e0y sinh");

        jLabel33.setText("\u0110/T di \u0111\u1ed9ng");

        jLabel34.setText("\u0110/T gia \u0111\u00ecnh");

        jLabel35.setText("\u0110\u1ecba ch\u1ec9 E - Mail");

        txtFirstName.setText("Ti\u1ebfn");
        txtFirstName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        txtLastName.setText("Hu\u1ef3nh");
        txtLastName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        txtMobilePhone.setText("0912350937");
        txtMobilePhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        txtHomePhone.setText("04853669");
        txtHomePhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        txtEmail.setText("KissFireWall@yahoo.com");
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        txtBirthday.setDateFormatString("MM/dd/yy");
        txtBirthday.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        jLabel36.setText("\u0110\u1ecba ch\u1ec9");

        jLabel37.setText("Th\u00e0nh ph\u1ed1");

        jLabel38.setText("Qu\u1ed1c T\u1ecbch");

        txtCity.setText("H\u00e0 N\u1ed9i");
        txtCity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        txtCountry.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        txtAddress.setColumns(20);
        txtAddress.setRows(5);
        txtAddress.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        jScrollPane2.setViewportView(txtAddress);

        org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel30)
                    .add(jLabel31)
                    .add(jLabel33)
                    .add(jLabel34)
                    .add(jLabel35)
                    .add(jLabel32)
                    .add(jLabel38)
                    .add(jLabel37)
                    .add(jLabel36))
                .add(38, 38, 38)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, txtEmail, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, txtHomePhone, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, txtMobilePhone, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, txtLastName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, txtFirstName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .add(txtBirthday, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtCity, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                    .add(txtCountry, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 145, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createSequentialGroup()
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel30)
                    .add(txtFirstName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(8, 8, 8)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel31)
                    .add(txtLastName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(9, 9, 9)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jLabel32)
                    .add(txtBirthday, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel33)
                    .add(txtMobilePhone, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel34)
                    .add(txtHomePhone, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 19, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel35)
                    .add(txtEmail, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel36)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 49, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(11, 11, 11)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel37)
                    .add(txtCity, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel38)
                    .add(txtCountry, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(22, 22, 22))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Danh s\u00e1ch ng\u01b0\u1eddi s\u1eed d\u1ee5ng"));
        jScrollPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Tài khoản", "Tên", "Trạng thái"
            }
        ));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(table);

        org.jdesktop.layout.GroupLayout jPanel11Layout = new org.jdesktop.layout.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 336, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(71, Short.MAX_VALUE))
        );

        pane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnExit.setMnemonic('H');
        btnExit.setText("Tho\u00e1t");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnAdd.setMnemonic('T');
        btnAdd.setText("Th\u00eam");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        btnAdd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddEvent(evt);
            }
        });

        btnUpdate.setMnemonic('S');
        btnUpdate.setText("S\u1eeda");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setMnemonic('X');
        btnDelete.setText(" X\u00f3a");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout pane1Layout = new org.jdesktop.layout.GroupLayout(pane1);
        pane1.setLayout(pane1Layout);
        pane1Layout.setHorizontalGroup(
            pane1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pane1Layout.createSequentialGroup()
                .addContainerGap(387, Short.MAX_VALUE)
                .add(btnAdd)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnUpdate)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnDelete)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnExit)
                .addContainerGap())
        );
        pane1Layout.setVerticalGroup(
            pane1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pane1Layout.createSequentialGroup()
                .addContainerGap()
                .add(pane1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnExit)
                    .add(btnAdd)
                    .add(btnUpdate)
                    .add(btnDelete))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jPanel9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jPanel10, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(pane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(jPanel9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jPanel10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 297, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
// TODO add your handling code here:
        DeleteUser();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
// TODO add your handling code here:
        UpdateUser();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
// TODO add your handling code here:
        int rowSelect = table.getSelectedRow();
        getValue(rowSelect);        
    }//GEN-LAST:event_tableMouseClicked

    private void AddEvent(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AddEvent
// TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            btnAdd.doClick();
        }
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }//GEN-LAST:event_AddEvent

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
// TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
// TODO add your handling code here:
        AddUserName();  
        ResetField();
        ViewUser();
    }//GEN-LAST:event_btnAddActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateUser().setVisible(true);
            }
        });
    }
    private String username,usernameNew,pass,passreply,firtname,lastname,email,address,city,contry,birthday;
    private String mobilephone,homephone;
    private int type;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox cboType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pane1;
    private javax.swing.JTable table;
    private javax.swing.JTextArea txtAddress;
    private com.toedter.calendar.JDateChooser txtBirthday;
    private javax.swing.JTextField txtCity;
    private com.toedter.components.JLocaleChooser txtCountry;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFirstName;
    private javax.swing.JTextField txtHomePhone;
    private javax.swing.JTextField txtLastName;
    private javax.swing.JTextField txtMobilePhone;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtPassReply;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables
    
}
