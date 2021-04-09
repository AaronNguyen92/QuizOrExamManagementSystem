import Project.ConnectionProvider;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.*;
import javax.swing.table.TableModel;

public class allStudentResult extends javax.swing.JFrame {

    /**
     * Creates new form allStudentResult
     */
    public allStudentResult() {
        initComponents();
        //Đăng ký sự kiện và xử lý sự kiện đó trên jTable
        jTable_result.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                table.setEnabled(false);
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    // your valueChanged overridden method 
                    int numberOfRows = jTable_result.getSelectedRowCount();
                    String id = "", name = "";
                    TableModel model = jTable_result.getModel();
                    JFrame frame = new JFrame();
                    frame.setAlwaysOnTop(true);
                    if (numberOfRows > 0) {
                        int[] arr = jTable_result.getSelectedRows();
                        for (int i = 0; i < arr.length; i++) { // xóa từng đối tượng được lựa chọn
                            id = String.valueOf(model.getValueAt(arr[i], 0));
                            name = String.valueOf(model.getValueAt(arr[i], 1));
                            int a = JOptionPane.showConfirmDialog(frame, "Do you really want to delete this \"" + name + "\" user?", "Select", JOptionPane.YES_NO_OPTION);
                            if (a == 0) {
                                try {
                                    Connection con = ConnectionProvider.getCon();
                                    PreparedStatement ps = con.prepareStatement("delete from user where rollNo = ?");
                                    ps.setString(1, id);
                                    ps.executeUpdate();
                                    JOptionPane.showMessageDialog(frame, "Successfully Removed \"" + name + "\" user!");

                                    Statement st = con.createStatement();
                                    ResultSet rs = st.executeQuery("select rollNo, name, dob, gender, contactNo, email, address, marks, avatar from user");
                                    jTable_result.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

                                    //Create new table model
                                    DefaultTableModel tableModel = new DefaultTableModel() {
                                        @Override
                                        public Class<?> getColumnClass(int columnIndex) {
                                            if (columnIndex == 8) {
                                                return Icon.class;
                                            }
                                            return Object.class;
                                        }
                                    };

                                    //Retrieve meta data from ResultSet
                                    ResultSetMetaData metaData = rs.getMetaData();

                                    //Get number of columns from meta data
                                    int columnCount = metaData.getColumnCount();

                                    //Get all column names from meta data and add columns to table model
                                    for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                                        tableModel.addColumn(metaData.getColumnLabel(columnIndex));
                                    }

                                    //Create array of Objects with size of column count from meta data
                                    Object[] row1 = new Object[columnCount];

                                    //Scroll through result set
                                    while (rs.next()) {
                                        //Get object from column with specific index of result set to array of objects
                                        for (int j = 0; j < columnCount; j++) {
                                            if (j == 8) {
                                                byte[] pic = rs.getBytes(j + 1);
                                                Image img = Toolkit.getDefaultToolkit().createImage(pic).getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                                                ImageIcon icon = new ImageIcon(img);
                                                row1[j] = icon;
                                            } else {
                                                row1[j] = rs.getObject(j + 1);
                                            }

                                        }
                                        //Now add row to table model with that array of objects as an argument
                                        tableModel.addRow(row1);
                                    }

                                    //Now add that table model to your table and you are done 
                                    jTable_result.setModel(tableModel);
                                    jTable_result.setRowHeight(80);

                                    JTableHeader header = jTable_result.getTableHeader();
                                    DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
                                    headerRenderer.setBackground(new Color(0, 153, 51));
                                    headerRenderer.setForeground(new Color(255, 255, 255));

                                    for (int l = 0; l < jTable_result.getModel().getColumnCount(); l++) {
                                        jTable_result.getColumnModel().getColumn(l).setHeaderRenderer(headerRenderer);
                                    }

                                    TableColumnModel colMod = header.getColumnModel();
                                    TableColumn tabCol_rollNo = colMod.getColumn(0);
                                    TableColumn tabCol_name = colMod.getColumn(1);
                                    TableColumn tabCol_dob = colMod.getColumn(2);
                                    TableColumn tabCol_gender = colMod.getColumn(3);
                                    TableColumn tabCol_contactNo = colMod.getColumn(4);
                                    TableColumn tabCol_email = colMod.getColumn(5);
                                    TableColumn tabCol_address = colMod.getColumn(6);
                                    TableColumn tabCol_marks = colMod.getColumn(7);
                                    tabCol_rollNo.setHeaderValue("Registration Code");
                                    tabCol_name.setHeaderValue("Username");
                                    tabCol_dob.setHeaderValue("Date of Birth");
                                    tabCol_gender.setHeaderValue("Gender");
                                    tabCol_contactNo.setHeaderValue("Contact Number");
                                    tabCol_email.setHeaderValue("Email");
                                    tabCol_address.setHeaderValue("Adsress");
                                    tabCol_marks.setHeaderValue("Score");
                                    header.repaint();
                                } catch (Exception e) {
                                    JOptionPane.showMessageDialog(frame, e);
                                }
                            }
                        }

                    }
                    table.setEnabled(true);
                }
            }
        });
        
        //Hiển thị tất cả người dùng dưới dạng bảng
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select rollNo, name, dob, gender, contactNo, email, address, marks, avatar from user");
            
            jTable_result.setAutoResizeMode(jTable_result.AUTO_RESIZE_ALL_COLUMNS);
            
            //Create new table model
            DefaultTableModel tableModel = new DefaultTableModel(){
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 8) {
                        return Icon.class;
                    }
                    return Object.class;
                }
            };

            //Retrieve meta data from ResultSet
            ResultSetMetaData metaData = rs.getMetaData();

            //Get number of columns from meta data
            int columnCount = metaData.getColumnCount();

            //Get all column names from meta data and add columns to table model
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tableModel.addColumn(metaData.getColumnLabel(columnIndex));
            }

            //Create array of Objects with size of column count from meta data
            Object[] row = new Object[columnCount];

            //Scroll through result set
            while (rs.next()) {
                //Get object from column with specific index of result set to array of objects
                for (int i = 0; i < columnCount; i++) {
                    if(i == 8){
                        byte[] pic = rs.getBytes(i + 1);
                        Image img = Toolkit.getDefaultToolkit().createImage(pic).getScaledInstance(70, 70, Image.SCALE_SMOOTH); 
                        ImageIcon icon = new ImageIcon(img);
                        row[i] = icon;   
                    }else{
                        row[i] = rs.getObject(i + 1);
                    }
                    
                }
                //Now add row to table model with that array of objects as an argument
                tableModel.addRow(row);
            }
            

            //Now add that table model to your table and you are done 
            jTable_result.setModel(tableModel);
            jTable_result.setRowHeight(80);
            
            JTableHeader header = jTable_result.getTableHeader();

            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(new Color(0, 153, 51));
            headerRenderer.setForeground(new Color(255, 255, 255));

            for (int i = 0; i < jTable_result.getModel().getColumnCount(); i++) {
                jTable_result.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            }
            
            TableColumnModel colMod = header.getColumnModel();
            TableColumn tabCol_rollNo = colMod.getColumn(0);
            TableColumn tabCol_name = colMod.getColumn(1);
            TableColumn tabCol_avatar = colMod.getColumn(8);//
            TableColumn tabCol_dob = colMod.getColumn(2);
            TableColumn tabCol_gender = colMod.getColumn(3);
            TableColumn tabCol_contactNo = colMod.getColumn(4);
            TableColumn tabCol_email = colMod.getColumn(5);
            TableColumn tabCol_address = colMod.getColumn(6);
            TableColumn tabCol_marks = colMod.getColumn(7);
            
            tabCol_rollNo.setHeaderValue("Registration Code");
            tabCol_name.setHeaderValue("Username");
            tabCol_avatar.setHeaderValue("Avatar");//
            tabCol_dob.setHeaderValue("Date of Birth");
            tabCol_gender.setHeaderValue("Gender");
            tabCol_contactNo.setHeaderValue("Contact Number");
            tabCol_email.setHeaderValue("Email");
            tabCol_address.setHeaderValue("Adsress");
            tabCol_marks.setHeaderValue("Score");
            header.repaint();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jTextField_mark_search = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_result = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton_exit = new javax.swing.JButton();
        jCheckBox_rank = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setLocation(new java.awt.Point(260, 150));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/all student result.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Algerian", 1, 40)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 153));
        jLabel2.setText("All Student Result");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 11, -1, -1));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 86, 1066, 10));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(153, 0, 153));
        jLabel3.setText("Filter Student By Marks:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 105, -1, -1));

        jTextField_mark_search.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jTextField_mark_search.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 153), 2));
        jTextField_mark_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField_mark_searchKeyReleased(evt);
            }
        });
        getContentPane().add(jTextField_mark_search, new org.netbeans.lib.awtextra.AbsoluteConstraints(298, 102, 113, -1));

        jTable_result.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTable_result.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        jTable_result.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_resultMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_result);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 151, 1046, 373));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton_exit.setBackground(new java.awt.Color(255, 255, 255));
        jButton_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/close_32.png"))); // NOI18N
        jButton_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_exitActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, -1, -1));

        jCheckBox_rank.setBackground(new java.awt.Color(255, 255, 255));
        jCheckBox_rank.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jCheckBox_rank.setForeground(new java.awt.Color(255, 153, 51));
        jCheckBox_rank.setText("Ranking");
        jCheckBox_rank.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCheckBox_rankMouseClicked(evt);
            }
        });
        jPanel1.add(jCheckBox_rank, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 100, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 540));

        pack();
    }// </editor-fold>                        

    private void jButton_exitActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        adminHome.open = 0;
        setVisible(false);
    }                                            

    //Phương thức xử lý sự kiện lọc dữ liệu thành viên dựa trên điểm số người dùng
    private void jTextField_mark_searchKeyReleased(java.awt.event.KeyEvent evt) {                                                   
        // TODO add your handling code here:
        int marks;
        if(jTextField_mark_search.getText().equalsIgnoreCase("")){
            marks = 0;
        }
        else{
            marks = Integer.parseInt(jTextField_mark_search.getText());
        }
        
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select rollNo, name, dob, gender, contactNo, email, address, marks, avatar from user where marks >= "+marks+"");
            jTable_result.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            
            //Create new table model
            DefaultTableModel tableModel = new DefaultTableModel(){
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 8) {
                        return Icon.class;
                    }
                    return Object.class;
                }
            };

            //Retrieve meta data from ResultSet
            ResultSetMetaData metaData = rs.getMetaData();

            //Get number of columns from meta data
            int columnCount = metaData.getColumnCount();

            //Get all column names from meta data and add columns to table model
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tableModel.addColumn(metaData.getColumnLabel(columnIndex));
            }

            //Create array of Objects with size of column count from meta data
            Object[] row = new Object[columnCount];

            //Scroll through result set
            while (rs.next()) {
                //Get object from column with specific index of result set to array of objects
                for (int i = 0; i < columnCount; i++) {
                    if(i == 8){
                        byte[] pic = rs.getBytes(i + 1);
                        Image img = Toolkit.getDefaultToolkit().createImage(pic).getScaledInstance(70, 70, Image.SCALE_SMOOTH); 
                        ImageIcon icon = new ImageIcon(img);
                        row[i] = icon;   
                    }else{
                        row[i] = rs.getObject(i + 1);
                    }
                    
                }
                //Now add row to table model with that array of objects as an argument
                tableModel.addRow(row);
            }
            
            //Now add that table model to your table and you are done 
            jTable_result.setModel(tableModel);
            jTable_result.setRowHeight(80);
          
            JTableHeader header = jTable_result.getTableHeader();
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(new Color(0, 153, 51));
            headerRenderer.setForeground(new Color(255, 255, 255));

            for (int i = 0; i < jTable_result.getModel().getColumnCount(); i++) {
                jTable_result.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            }
            
            TableColumnModel colMod = header.getColumnModel();
            TableColumn tabCol_rollNo = colMod.getColumn(0);
            TableColumn tabCol_name = colMod.getColumn(1);
            TableColumn tabCol_dob = colMod.getColumn(2);
            TableColumn tabCol_gender = colMod.getColumn(3);
            TableColumn tabCol_contactNo = colMod.getColumn(4);
            TableColumn tabCol_email = colMod.getColumn(5);
            TableColumn tabCol_address = colMod.getColumn(6);
            TableColumn tabCol_marks = colMod.getColumn(7);
            tabCol_rollNo.setHeaderValue("Registration Code");
            tabCol_name.setHeaderValue("Username");
            tabCol_dob.setHeaderValue("Date of Birth");
            tabCol_gender.setHeaderValue("Gender");
            tabCol_contactNo.setHeaderValue("Contact Number");
            tabCol_email.setHeaderValue("Email");
            tabCol_address.setHeaderValue("Adsress");
            tabCol_marks.setHeaderValue("Score");
            header.repaint();
        } catch (Exception e) {
            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(frame, e);
        }
    }                                                  

    private void jTable_resultMouseClicked(java.awt.event.MouseEvent evt) {                                           
  
    }                                          

    //Xếp hạng thành viên có điểm số người cao đến thấp
    private void jCheckBox_rankMouseClicked(java.awt.event.MouseEvent evt) {                                            
        // TODO add your handling code here:
        String sorting = "";
        if(jCheckBox_rank.isSelected()){
            sorting = "order by marks DESC";
        }
        
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select rollNo, name, dob, gender, contactNo, email, address, marks, avatar from user "+ sorting);
            jTable_result.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

            //Create new table model
            DefaultTableModel tableModel = new DefaultTableModel() {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    if (columnIndex == 8) {
                        return Icon.class;
                    }
                    return Object.class;
                }
            };

            //Retrieve meta data from ResultSet
            ResultSetMetaData metaData = rs.getMetaData();

            //Get number of columns from meta data
            int columnCount = metaData.getColumnCount();

            //Get all column names from meta data and add columns to table model
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tableModel.addColumn(metaData.getColumnLabel(columnIndex));
            }

            //Create array of Objects with size of column count from meta data
            Object[] row = new Object[columnCount];

            //Scroll through result set
            while (rs.next()) {
                //Get object from column with specific index of result set to array of objects
                for (int i = 0; i < columnCount; i++) {
                    if (i == 8) {
                        byte[] pic = rs.getBytes(i + 1);
                        Image img = Toolkit.getDefaultToolkit().createImage(pic).getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                        ImageIcon icon = new ImageIcon(img);
                        row[i] = icon;
                    } else {
                        row[i] = rs.getObject(i + 1);
                    }

                }
                //Now add row to table model with that array of objects as an argument
                tableModel.addRow(row);
            }

            //Now add that table model to your table and you are done 
            jTable_result.setModel(tableModel);
            jTable_result.setRowHeight(80);

            JTableHeader header = jTable_result.getTableHeader();
            DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
            headerRenderer.setBackground(new Color(0, 153, 51));
            headerRenderer.setForeground(new Color(255, 255, 255));

            for (int i = 0; i < jTable_result.getModel().getColumnCount(); i++) {
                jTable_result.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
            }

            TableColumnModel colMod = header.getColumnModel();
            TableColumn tabCol_rollNo = colMod.getColumn(0);
            TableColumn tabCol_name = colMod.getColumn(1);
            TableColumn tabCol_dob = colMod.getColumn(2);
            TableColumn tabCol_gender = colMod.getColumn(3);
            TableColumn tabCol_contactNo = colMod.getColumn(4);
            TableColumn tabCol_email = colMod.getColumn(5);
            TableColumn tabCol_address = colMod.getColumn(6);
            TableColumn tabCol_marks = colMod.getColumn(7);
            tabCol_rollNo.setHeaderValue("Registration Code");
            tabCol_name.setHeaderValue("Username");
            tabCol_dob.setHeaderValue("Date of Birth");
            tabCol_gender.setHeaderValue("Gender");
            tabCol_contactNo.setHeaderValue("Contact Number");
            tabCol_email.setHeaderValue("Email");
            tabCol_address.setHeaderValue("Adsress");
            tabCol_marks.setHeaderValue("Score");
            header.repaint();
        } catch (Exception e) {
            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(frame, e);
        }
    }                                           

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(allStudentResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(allStudentResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(allStudentResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(allStudentResult.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new allStudentResult().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton_exit;
    private javax.swing.JCheckBox jCheckBox_rank;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable_result;
    private javax.swing.JTextField jTextField_mark_search;
    // End of variables declaration                   
}
