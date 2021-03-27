import java.sql.*;
import Project.ConnectionProvider;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class addNewQuestion extends javax.swing.JFrame {

    /**
     * Creates new form addNewQuestion
     */
    public addNewQuestion() {
        initComponents();
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE);//Notice, this line may be throw sql exception 
            ResultSet rs = st.executeQuery("select count(id_question) from question");
            if (rs.first()) {
                int id = rs.getInt(1);
                id += 1;
                String str = String.valueOf(id);
                jLabel_id_question.setText(str);
            } else {
                jLabel_id_question.setText("1");
            }
        } catch (Exception e) {
            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(frame, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel_id_question = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField_question = new javax.swing.JTextField();
        jTextField_opt1 = new javax.swing.JTextField();
        jTextField_opt2 = new javax.swing.JTextField();
        jTextField_opt3 = new javax.swing.JTextField();
        jTextField_opt4 = new javax.swing.JTextField();
        jTextField_ans = new javax.swing.JTextField();
        jButton_save = new javax.swing.JButton();
        jButton_clear = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButton_exit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(260, 150));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Algerian", 1, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add new question.png"))); // NOI18N
        jLabel1.setText("Add New Question");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 22, -1, -1));

        jSeparator1.setForeground(new java.awt.Color(0, 153, 51));
        getContentPane().add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 88, 1070, 10));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 51));
        jLabel2.setText("Question ID");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, 120, -1));

        jLabel_id_question.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_id_question.setForeground(new java.awt.Color(0, 153, 51));
        jLabel_id_question.setText("00");
        getContentPane().add(jLabel_id_question, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 110, 30, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 0, 153));
        jLabel4.setText("Option 1:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 0, 153));
        jLabel5.setText("Option 2:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 0, 153));
        jLabel6.setText("Option 3:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 310, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 0, 153));
        jLabel7.setText("Option 4:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 0, 153));
        jLabel8.setText("Answer:");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 410, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 0, 153));
        jLabel10.setText("Question:");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, -1));

        jTextField_question.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jTextField_question.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 255), 2));
        getContentPane().add(jTextField_question, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 810, -1));

        jTextField_opt1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jTextField_opt1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 255), 2));
        getContentPane().add(jTextField_opt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 810, -1));

        jTextField_opt2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jTextField_opt2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 255), 2));
        getContentPane().add(jTextField_opt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, 810, -1));

        jTextField_opt3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jTextField_opt3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 255), 2));
        getContentPane().add(jTextField_opt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 810, -1));

        jTextField_opt4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jTextField_opt4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 255), 2));
        getContentPane().add(jTextField_opt4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, 810, -1));

        jTextField_ans.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jTextField_ans.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 255), 2));
        getContentPane().add(jTextField_ans, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 410, 810, -1));

        jButton_save.setBackground(new java.awt.Color(255, 153, 0));
        jButton_save.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_save.setForeground(new java.awt.Color(255, 255, 255));
        jButton_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/save.png"))); // NOI18N
        jButton_save.setText("Save");
        jButton_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_saveActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_save, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 470, -1, -1));

        jButton_clear.setBackground(new java.awt.Color(255, 153, 0));
        jButton_clear.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jButton_clear.setForeground(new java.awt.Color(255, 255, 255));
        jButton_clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/clear.png"))); // NOI18N
        jButton_clear.setText("Clear");
        jButton_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_clearActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 470, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel1.setForeground(new java.awt.Color(255, 153, 51));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton_exit.setBackground(new java.awt.Color(255, 255, 255));
        jButton_exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/close_32.png"))); // NOI18N
        jButton_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_exitActionPerformed(evt);
            }
        });
        jPanel1.add(jButton_exit, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1070, 540));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_saveActionPerformed
        // TODO add your handling code here:
        String id = jLabel_id_question.getText();
        String name = jTextField_question.getText();
        String opt1 = jTextField_opt1.getText();
        String opt2 = jTextField_opt2.getText();
        String opt3 = jTextField_opt3.getText();
        String opt4 = jTextField_opt4.getText();
        String answer = jTextField_ans.getText();
        try{
            Connection con = ConnectionProvider.getCon();
            PreparedStatement ps = con.prepareStatement("insert into question values (?,?,?,?,?,?,?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, opt1);
            ps.setString(4, opt2);
            ps.setString(5, opt3);
            ps.setString(6, opt4);
            ps.setString(7, answer);
            ps.executeUpdate();
            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(frame, "Successfully Added!");
            setVisible(false);
            new addNewQuestion().setVisible(true);
        }catch(Exception e){
            JFrame frame = new JFrame();
            frame.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(frame, e);
        }
    }//GEN-LAST:event_jButton_saveActionPerformed

    private void jButton_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_clearActionPerformed
        // TODO add your handling code here:
        jTextField_question.setText("");
        jTextField_opt1.setText("");
        jTextField_opt2.setText("");
        jTextField_opt3.setText("");
        jTextField_opt4.setText("");
        jTextField_ans.setText("");
    }//GEN-LAST:event_jButton_clearActionPerformed

    private void jButton_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_exitActionPerformed
        // TODO add your handling code here:
        adminHome.open = 0;
        setVisible(false);
    }//GEN-LAST:event_jButton_exitActionPerformed

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
            java.util.logging.Logger.getLogger(addNewQuestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addNewQuestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addNewQuestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addNewQuestion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addNewQuestion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_clear;
    private javax.swing.JButton jButton_exit;
    private javax.swing.JButton jButton_save;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel_id_question;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField_ans;
    private javax.swing.JTextField jTextField_opt1;
    private javax.swing.JTextField jTextField_opt2;
    private javax.swing.JTextField jTextField_opt3;
    private javax.swing.JTextField jTextField_opt4;
    private javax.swing.JTextField jTextField_question;
    // End of variables declaration//GEN-END:variables
}
