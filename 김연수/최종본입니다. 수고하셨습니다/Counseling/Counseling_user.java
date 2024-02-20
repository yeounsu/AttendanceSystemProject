package Counseling;

import javax.swing.*;

import config.Class_Name;
import config.MysqlConnection;
import config.Query;
import config.UserInfo;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

//import java.sql.ResultSetMetaData;

public class Counseling_user extends JPanel {
    JButton btn;
    JTextField  title_tf, send_tf;
   JTextArea ta;
   private Connection conn;
   private String classindexes;
   private JComboBox<String> class_name;
    private Connection connection;
    private final List<String> CLASS_NAMES = Class_Name.CLASS_NAMES;
   
   public Counseling_user() {
      
      try {
         conn = MysqlConnection.getConnection();
         
         setLayout(new BorderLayout());
         
         //수강과목 패널 생성
         JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER,20,18));
         panel1.setBackground(Color.WHITE);
         JLabel subject_Label = new JLabel("수강 과목");
         subject_Label.setFont(new Font("Inter",Font.BOLD,18));
         panel1.add(subject_Label);
         add(panel1,BorderLayout.NORTH);
         
           List<String> classOptions = fetchUserNamesFromDatabase(Query.SELECT_CLASS_NAMEUP, "class_name");
           class_name = new JComboBox<>(classOptions.toArray(new String[0]));
              class_name.setBounds(260, 137, 120, 25);
              panel1.add(class_name);
            
         
         //보낸사람, 제목, 내용 담는 패널 생성
         JPanel panel2 = new JPanel();
         panel2.setBackground(Color.WHITE);
         JLabel title_Label = new JLabel("    제  목    ");
         title_tf =new JTextField(55);
         title_tf.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         title_Label.setFont(new Font("Inter",Font.BOLD,18));
         title_tf.setFont(new Font("Inter",Font.BOLD,18));
         
         String username = UserInfo.getUserID();
         JLabel send_Label = new JLabel(" 보낸 사람");
         send_tf = new JTextField(username, 55);
         send_tf.setEditable(false);
         send_tf.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         send_Label.setFont(new Font("Inter",Font.BOLD,18));
         send_tf.setFont(new Font("Inter",Font.BOLD,18));
         
         JLabel content_Label = new JLabel("    내  용    ");
         ta = new JTextArea(24, 55);
         content_Label.setFont(new Font("Inter",Font.BOLD,18));
         ta.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         ta.setFont(new Font("Inter",Font.BOLD,18));
         
         panel2.add(title_Label);
         panel2.add(title_tf);
         panel2.add(send_Label);
         panel2.add(send_tf);
         panel2.add(content_Label);
         panel2.add(ta);
         add(panel2,BorderLayout.CENTER);
         
         //버튼 패널 생성
         JPanel panel3 = new JPanel();
         panel3.setBackground(Color.WHITE);
         btn=new JButton("신청하기");
         btn.setPreferredSize(new Dimension(180, 30));
         btn.setFont(new Font("Inter", Font.BOLD, 18));
         btn.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         btn.setBackground(Color.WHITE);
         panel3.add(btn);
         add(panel3,BorderLayout.SOUTH);
         
         //신청하기 버튼에 ActionListener  추가
         btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               CounselSaveData();
            }
         });
         setVisible(true);
         
      } catch (Exception e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(this, "데이터베이스 연결을 초기화하는 중 오류가 발생했습니다.");
      }
      
   }
   private List<String> fetchUserNamesFromDatabase(String query, String columnName) {
       List<String> values = new ArrayList<>();

           try {
               this.connection = MysqlConnection.getConnection();
               Statement statement = connection.createStatement();
               ResultSet resultSet = statement.executeQuery(query);

               while (resultSet.next()) {
                   String value = resultSet.getString(columnName);
                   values.add(value);
               }

               resultSet.close();
               statement.close();

           } catch (Exception e) {
               e.printStackTrace();
           }

           return values;
   }
   
   
    
   private void CounselSaveData() {
   //위에 연결된 테이터베이스 사용
      try{
         //counseling 테이블에 삽입
         String sql = "INSERT INTO counseling (counseling_date, counseling_title, counseling_content,user_id,counseling_class)  VALUES(?,?,?,?,?)";
         try (PreparedStatement psmt =conn.prepareStatement(sql)){
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
             String className = (String) class_name.getSelectedItem();
             
            
             
               psmt.setTimestamp(1, currentTimestamp);    //신청버튼 눌렀을때 시간 
            psmt.setString(2, title_tf.getText());               
            psmt.setString(3,ta.getText());                        
            psmt.setString(4, send_tf.getText());       //보낸 사람 정보
            psmt.setString(5, className);                  //선택된 체크박스의 클래스 이름 사용
            
            
            
             // 쿼리 실행
               int rowsAffected = psmt.executeUpdate();
               if (rowsAffected > 0) {
                   JOptionPane.showMessageDialog(this, "신청이 성공적으로 저장되었습니다.");
               } else {
                   JOptionPane.showMessageDialog(this, "신청 저장에 실패했습니다.");
               }  
         }    //2번째 try       
        
      } catch (SQLException e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(this, "데이터 저장 중 오류가 발생했습니다.");
      } // 1번째 try catch
   }   //CounselingSaveData
       
   


   public static void main(String[] args) {
           SwingUtilities.invokeLater(() -> {
               JFrame frame = new JFrame("Counseling Panel");
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.add(new Counseling_user());
               frame.pack();
               frame.setLocationRelativeTo(null);
               frame.setVisible(true);
               
           });
      }
   





}
   
    
   
   

   
   