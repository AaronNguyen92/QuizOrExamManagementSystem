import Project.ConnectionProvider;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class quizExamStudent extends javax.swing.JFrame {
    public String questionId = "1";
    public int questionNo = 1, totalQuestion = 0;
    public String answer;
    public int min = 0, sec = 0, marks = 0;
    public Timer time;//dùng để hiển thị tính giờ làm bài
    public ArrayList<String> selectedQuestions = new ArrayList<String>();
    String contentOfQuestion = "";
    /**
     * Creates new form quizExamStudent
     */
    
    public void answerCheck(){
        String studentAnswer = "";
        
        if(jRadioButton_opt1.isSelected()){
            studentAnswer = jRadioButton_opt1.getText();
        }
        else if(jRadioButton_opt2.isSelected()){
            studentAnswer = jRadioButton_opt2.getText();
        }
        else if(jRadioButton_opt3.isSelected()){
            studentAnswer = jRadioButton_opt3.getText();
        }
        else if(jRadioButton_opt4.isSelected()){
            studentAnswer = jRadioButton_opt4.getText();
        }

        
        if(studentAnswer.equalsIgnoreCase(answer)){
            marks = marks + 1;
            String marks1 = String.valueOf(marks);
            jLabel_marks.setText(marks1);
            
        }
        
        
        //question number change and display next question
        questionNo += 1;
        Random rd = new Random();
        int questionId1 = rd.nextInt(totalQuestion);
        questionId = String.valueOf(questionId1);
        
        while(selectedQuestions.indexOf(questionId) != -1){
            questionId1 = rd.nextInt(totalQuestion);
            questionId = String.valueOf(questionId1);
        }
        
        selectedQuestions.add(questionId);
        
        //clear JRadioButton
        jRadioButton_opt1.setSelected(false);
        jRadioButton_opt2.setSelected(false);
        jRadioButton_opt3.setSelected(false);
        jRadioButton_opt4.setSelected(false);
        
        //Last question hide next button
        if(questionNo == 10){
            jButton_next.setVisible(false);
        }
    }
    
    //Tính toán điểm số và lưu vào CSDL
    public void submit(){
        String rollNo = jLabel_rollNo.getText();
        String name = jLabel_name.getText();
        answerCheck();
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            st.executeUpdate("update user set marks = '"+marks+"' where rollNo = '" + rollNo +"'");

            String marks1 = String.valueOf(marks);
            setVisible(false);
            new successfullySubmited(marks1, rollNo, name).setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public void question(){
        try { 
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from question where id_question = '"+questionId+"'");
            jLabel_question_number.setText(String.valueOf(questionNo));
            while(rs.next()){
                contentOfQuestion = rs.getString(2);
                if(contentOfQuestion.length() >= 90){
                    contentOfQuestion = "<html><p style=\"font-size:15px;width:750px;\"><b>" 
                            + contentOfQuestion + "</b></p></html>";
                }
                jLabel_content_question.setText(contentOfQuestion);
                
                //Lưu 4 lựa chọn của mỗi cầu hỏi vào 1 danh sách và hiển thị chúng ra màn hình
                List<String> stringListQuestions = new ArrayList<String>();
                stringListQuestions.add(rs.getString(3));
                stringListQuestions.add(rs.getString(4));
                stringListQuestions.add(rs.getString(5));
                stringListQuestions.add(rs.getString(6));
		Collections.shuffle(stringListQuestions);

                jRadioButton_opt1.setText(stringListQuestions.get(0));
                jRadioButton_opt2.setText(stringListQuestions.get(1));
                jRadioButton_opt3.setText(stringListQuestions.get(2));
                jRadioButton_opt4.setText(stringListQuestions.get(3));
                answer = rs.getString(7);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    public quizExamStudent() {
        initComponents();
    }
   
    
    public quizExamStudent(String rollNo) {
        initComponents();
        jLabel_rollNo.setText(rollNo);
        byte[] image = null;
              
        //date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        jLabel_time.setText(sdf.format(date));
        
        //first question and student details
        try {
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 
                        ResultSet.CONCUR_UPDATABLE);//Notice, this line may be throw sql exception 
            ResultSet rs = st.executeQuery("select * from user where rollNo = '"+rollNo+"'");
            while(rs.next()){
                jLabel_name.setText(rs.getString(2));
                image = rs.getBytes(11);
                Image img = Toolkit.getDefaultToolkit().createImage(image).getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                ImageIcon icon = new ImageIcon(img);
                jLabel_avatar.setIcon(icon);
            }
            
            //Lấy số tổng số câu hỏi có trong CSDL
            ResultSet rs1 = st.executeQuery("select count(*) from question");
            while(rs1.next()){
                totalQuestion = rs1.getInt(1);
            }
            
            //Hiển thị câu hỏi đầu tiên
            Random rd = new Random();
            int questionId1 = rd.nextInt(totalQuestion);
            questionId = String.valueOf(questionId1);
            selectedQuestions.add(questionId);
            
            ResultSet rs2 = st.executeQuery("select * from question where id_question = '"+questionId+"'");
            while(rs2.next()){
                jLabel_question_number.setText(String.valueOf(questionNo));
                contentOfQuestion = rs2.getString(2);
                if(contentOfQuestion.length() >= 90){
                    contentOfQuestion = "<html><p style=\"font-size:15px;width:750px;\"><b>" 
                            + contentOfQuestion + "</b></p></html>";
                }
                jLabel_content_question.setText(contentOfQuestion);
                jRadioButton_opt1.setText(rs2.getString(3));
                jRadioButton_opt2.setText(rs2.getString(4));
                jRadioButton_opt3.setText(rs2.getString(5));
                jRadioButton_opt4.setText(rs2.getString(6));
                answer = rs2.getString(7);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
        //Đoạn chương trình tính thời gian thi
        setLocationRelativeTo(this);//hiển thị cửa sổ lên vị trí giữa màn hình
        time = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jLabel_sec.setText(String.valueOf(sec));
                jLabel_min.setText(String.valueOf(min));
                
                if (sec==60) {
                    sec = 0;
                    min++;
                    if(min == 10){
                        time.stop();
                        answerCheck();
                        submit();
                    }
                }
                sec++;
            }
        });
        time.start();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel_rollNo = new javax.swing.JLabel();
        jLabel_Total_question = new javax.swing.JLabel();
        jLabel_marks = new javax.swing.JLabel();
        jLabel_name = new javax.swing.JLabel();
        jLabel_avatar = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel_time = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel_min = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();
        jLabel_sec = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel_content_question = new javax.swing.JLabel();
        jRadioButton_opt1 = new javax.swing.JRadioButton();
        jRadioButton_opt2 = new javax.swing.JRadioButton();
        jRadioButton_opt3 = new javax.swing.JRadioButton();
        jRadioButton_opt4 = new javax.swing.JRadioButton();
        jButton_next = new javax.swing.JButton();
        jButton_submit = new javax.swing.JButton();
        jLabel_question_number = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 0, 51));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Roll Number:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Total Question:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Your Marks:");

        jLabel_rollNo.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_rollNo.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_rollNo.setText("10001");

        jLabel_Total_question.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_Total_question.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_Total_question.setText("10");

        jLabel_marks.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel_marks.setForeground(new java.awt.Color(255, 0, 0));
        jLabel_marks.setText("00");

        jLabel_name.setFont(new java.awt.Font("Comic Sans MS", 1, 28)); // NOI18N
        jLabel_name.setForeground(new java.awt.Color(0, 255, 0));
        jLabel_name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_name.setText("Henry");

        jLabel_avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/user_icon_150.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel_marks)
                            .addComponent(jLabel_Total_question)
                            .addComponent(jLabel_rollNo)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jLabel_name, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel_avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel_avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel_name)
                .addGap(81, 81, 81)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel_rollNo))
                .addGap(43, 43, 43)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel_Total_question))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel_marks))
                .addContainerGap(284, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 770));

        jPanel1.setBackground(new java.awt.Color(0, 51, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Algerian", 1, 60)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Welcome!");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Date:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 70, -1, -1));

        jLabel_time.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_time.setForeground(new java.awt.Color(255, 255, 255));
        jLabel_time.setText("jLabel4");
        jPanel1.add(jLabel_time, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 70, -1, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Total Time:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 20, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Time Taken:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 70, -1, -1));

        jLabel_min.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_min.setForeground(new java.awt.Color(51, 255, 0));
        jLabel_min.setText("00");
        jPanel1.add(jLabel_min, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 70, -1, -1));

        jLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel.setForeground(new java.awt.Color(51, 255, 0));
        jLabel.setText(":");
        jPanel1.add(jLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(979, 60, 10, 40));

        jLabel_sec.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_sec.setForeground(new java.awt.Color(51, 255, 0));
        jLabel_sec.setText("00");
        jPanel1.add(jLabel_sec, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 70, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("10 Min");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 20, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 0, 1090, 120));

        jLabel_content_question.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_content_question.setText("Question demo?");
        getContentPane().add(jLabel_content_question, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, -1, -1));

        jRadioButton_opt1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jRadioButton_opt1.setText("jRadioButton1");
        jRadioButton_opt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_opt1ActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton_opt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 271, -1, -1));

        jRadioButton_opt2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jRadioButton_opt2.setText("jRadioButton2");
        jRadioButton_opt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_opt2ActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton_opt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 335, -1, -1));

        jRadioButton_opt3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jRadioButton_opt3.setText("jRadioButton3");
        jRadioButton_opt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_opt3ActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton_opt3, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 403, -1, -1));

        jRadioButton_opt4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jRadioButton_opt4.setText("jRadioButton4");
        jRadioButton_opt4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_opt4ActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton_opt4, new org.netbeans.lib.awtextra.AbsoluteConstraints(361, 477, -1, -1));

        jButton_next.setBackground(new java.awt.Color(255, 153, 0));
        jButton_next.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton_next.setForeground(new java.awt.Color(255, 255, 255));
        jButton_next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Next.png"))); // NOI18N
        jButton_next.setText("Next");
        jButton_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_nextActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_next, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 583, 130, 50));

        jButton_submit.setBackground(new java.awt.Color(255, 153, 0));
        jButton_submit.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButton_submit.setForeground(new java.awt.Color(255, 255, 255));
        jButton_submit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/save.png"))); // NOI18N
        jButton_submit.setText("Submit");
        jButton_submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_submitActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_submit, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 583, 150, 50));

        jLabel_question_number.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel_question_number.setText("00");
        getContentPane().add(jLabel_question_number, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 170, -1, -1));

        pack();
    }// </editor-fold>                        

    private void jButton_nextActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
        answerCheck();
        question();
    }                                            

    private void jButton_submitActionPerformed(java.awt.event.ActionEvent evt) {                                               
        // TODO add your handling code here:
        int a = JOptionPane.showConfirmDialog(null, "Do you really want to submit?","Select", JOptionPane.YES_NO_OPTION);
        if(a == JOptionPane.YES_OPTION){
            submit();
            time.stop();
        }
    }                                              

    private void jRadioButton_opt1ActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        if(jRadioButton_opt1.isSelected()){
            jRadioButton_opt2.setSelected(false);
            jRadioButton_opt3.setSelected(false);
            jRadioButton_opt4.setSelected(false);
        }
    }                                                 

    private void jRadioButton_opt2ActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        if(jRadioButton_opt2.isSelected()){
            jRadioButton_opt1.setSelected(false);
            jRadioButton_opt3.setSelected(false);
            jRadioButton_opt4.setSelected(false);
        }
    }                                                 

    private void jRadioButton_opt3ActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        if(jRadioButton_opt3.isSelected()){
            jRadioButton_opt1.setSelected(false);
            jRadioButton_opt2.setSelected(false);
            jRadioButton_opt4.setSelected(false);
        }
    }                                                 

    private void jRadioButton_opt4ActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        // TODO add your handling code here:
        if(jRadioButton_opt4.isSelected()){
            jRadioButton_opt1.setSelected(false);
            jRadioButton_opt2.setSelected(false);
            jRadioButton_opt3.setSelected(false);
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
            java.util.logging.Logger.getLogger(quizExamStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(quizExamStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(quizExamStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(quizExamStudent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new quizExamStudent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton_next;
    private javax.swing.JButton jButton_submit;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel_Total_question;
    private javax.swing.JLabel jLabel_avatar;
    private javax.swing.JLabel jLabel_content_question;
    private javax.swing.JLabel jLabel_marks;
    private javax.swing.JLabel jLabel_min;
    private javax.swing.JLabel jLabel_name;
    private javax.swing.JLabel jLabel_question_number;
    private javax.swing.JLabel jLabel_rollNo;
    private javax.swing.JLabel jLabel_sec;
    private javax.swing.JLabel jLabel_time;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JRadioButton jRadioButton_opt1;
    private javax.swing.JRadioButton jRadioButton_opt2;
    private javax.swing.JRadioButton jRadioButton_opt3;
    private javax.swing.JRadioButton jRadioButton_opt4;
    // End of variables declaration                   
}
