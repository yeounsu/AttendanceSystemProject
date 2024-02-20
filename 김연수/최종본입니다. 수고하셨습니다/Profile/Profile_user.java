package Profile;
import page.*;
import config.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Profile_user extends JPanel {

      private Connection connection;
      String username = UserInfo.getUserID();
      private JTextField nameField;
      private JTextField majorField;
      private JTextField birthField;
      private JTextField genderField;
      private JTextField addressField;
      private JTextField idField;
      private JPasswordField passwordField;
      private JPasswordField newpassField;
      private JPasswordField newpasscomField;
      private JButton saveButton;
      private JButton imgButton;
      private String profile_image;
       private String profile_name;
       private String profile_uploadName;
       private String profile_extension;
       private long profile_Size;
       private ProfileMgr profileMgr;
       

       
       
      public Profile_user() {
         setPreferredSize(new Dimension(1100, 800));
         setLayout(null);
         setBackground(Color.WHITE);

         JPanel panel = new JPanel();
         panel.setLayout(null);
         panel.setBounds(0, 0, 1200, 950);

         JLabel imgLabel = new JLabel();
         imgLabel.setBounds(200, 90, 130, 165);
         imgLabel.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         imgLabel.setBorder(new LineBorder(Color.BLACK, 1, true));
         panel.add(imgLabel);
         panel.setBackground(Color.WHITE);
         
         
         
         imgButton = new JButton("사진 수정");
         imgButton.setBounds(200, 257, 130, 24);
         imgButton.setBackground(new Color(231, 222, 248));
         imgButton.setForeground(Color.BLACK);
         imgButton.setFont(new Font("Inter", Font.BOLD, 15));
         panel.add(imgButton);
         

         
         JLabel nameLabel = new JLabel("이름");
         nameLabel.setBounds(380, 80, 50, 40);
         nameLabel.setFont(new Font("Inter", Font.BOLD, 15));
         panel.add(nameLabel);
         nameField = new JTextField(20);
         nameField.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         nameField.setBounds(380, 115, 250, 40);
         nameField.setEditable(false);
         panel.add(nameField);

         
         
         JLabel majorLabel = new JLabel("전공");
         majorLabel.setBounds(700, 80, 50, 40);
         majorLabel.setFont(new Font("Inter", Font.BOLD, 15));
         panel.add(majorLabel);
         majorField = new JTextField(20);
         majorField.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         majorField.setBounds(700, 115, 250, 40);
         majorField.setEditable(false);
         panel.add(majorField);

         
         
         JLabel birthLabel = new JLabel("생년월일");
         birthLabel.setBounds(380, 205, 70, 40);
         birthLabel.setFont(new Font("Inter", Font.BOLD, 15));
         panel.add(birthLabel);
         birthField = new JTextField(20);
         birthField.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         birthField.setBounds(380, 240, 250, 40);
         birthField.setEditable(false);
         panel.add(birthField);

         
         
         JLabel genderLabel = new JLabel("성별");
         genderLabel.setBounds(700, 205, 50, 40);
         genderLabel.setFont(new Font("Inter", Font.BOLD, 15));
         panel.add(genderLabel);
         genderField = new JTextField(20);
         genderField.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         genderField.setBounds(700, 240, 250, 40);
         genderField.setEditable(false);
         panel.add(genderField);

         
         
         JLabel addressLabel = new JLabel("주소");
         addressLabel.setBounds(200, 330, 50, 40);
         addressLabel.setFont(new Font("Inter", Font.BOLD, 15));
         panel.add(addressLabel);
         addressField = new JTextField(20);
         addressField.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         addressField.setBounds(200, 365, 750, 40);
         panel.add(addressField);

         
         
         JLabel idLabel = new JLabel("아이디");
         idLabel.setBounds(200, 455, 50, 40);
         idLabel.setFont(new Font("Inter", Font.BOLD, 15));
         panel.add(idLabel);
         idField = new JTextField(20);
         idField.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         idField.setBounds(200, 490, 350, 40);
         idField.setEditable(false);
         panel.add(idField);

         
         
         JLabel passwordLabel = new JLabel("비밀번호");
         passwordLabel.setBounds(600, 455, 70, 40);
         passwordLabel.setFont(new Font("Inter", Font.BOLD, 15));
         panel.add(passwordLabel);
         passwordField = new JPasswordField(20);
         passwordField.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         passwordField.setBounds(600, 490, 350, 40);
         panel.add(passwordField);

         
         
         JLabel newpassLabel = new JLabel("새 비밀번호");
         newpassLabel.setFont(new Font("Inter", Font.BOLD, 15));
         newpassLabel.setBounds(200, 580, 100, 40);
         panel.add(newpassLabel);
         newpassField = new JPasswordField(20);
         newpassField.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         newpassField.setBounds(200, 615, 350, 40);
         panel.add(newpassField);

         
         
         JLabel newpasscomLabel = new JLabel("새 비밀번호 확인");
         newpasscomLabel.setFont(new Font("Inter", Font.BOLD, 15));
         newpasscomLabel.setBounds(600, 580, 130, 40);
         panel.add(newpasscomLabel);
         newpasscomField = new JPasswordField(20);
         newpasscomField.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
         newpasscomField.setBounds(600, 615, 350, 40);
         panel.add(newpasscomField);

         
         
         saveButton = new JButton("정보수정");
         saveButton.setBounds(850, 690, 100, 30);
         saveButton.setBackground(new Color(231, 222, 248));
         saveButton.setForeground(Color.BLACK);
         saveButton.setFont(new Font("Inter", Font.BOLD, 15));
         panel.add(saveButton);

         
         
         panel.add(birthField);

         add(panel);

         setVisible(true);

         try {
            // MySQL 연결 설정
            String url = "jdbc:mysql://localhost:3306/attendancesystem";
            String user = "root";
            String password = "1234";
            connection = DriverManager.getConnection(url, user, password);


            ResultSet resultSet = QueryExecutor.executeQuery(Query.SELECT_STUPRO_TABLE);

            if (resultSet != null && resultSet.next()) {
                Blob imageBlob = resultSet.getBlob("profile_image");
                if (imageBlob != null) {
                    byte[] imageData = imageBlob.getBytes(1, (int) imageBlob.length());
                    ImageIcon imageIcon = new ImageIcon(imageData);
                    Image image = imageIcon.getImage().getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH);
                    imgLabel.setIcon(new ImageIcon(image));
                }
               String userName = resultSet.getString("user_name");
               String userMajor = resultSet.getString("user_major");
               String userBirth = resultSet.getString("user_birth");
               String userGender = resultSet.getString("user_gender");
               String userAddress = resultSet.getString("user_address");
               String userId = resultSet.getString("user_id");
               nameField.setText(userName);
               majorField.setText(userMajor);
               birthField.setText(userBirth);
               genderField.setText(userGender);
               addressField.setText(userAddress);
               idField.setText(userId);
            }

         } catch (SQLException e) {
            e.printStackTrace();
         }
         
         
         
         
         imgButton.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 JFileChooser fileChooser = new JFileChooser();
                 int result = fileChooser.showOpenDialog(null);
                 if (result == JFileChooser.APPROVE_OPTION) {
                     File selectedFile = fileChooser.getSelectedFile();
                     try {
                         // 이미지 파일을 Blob으로 변환
                         FileInputStream fis = new FileInputStream(selectedFile);
                         ByteArrayOutputStream bos = new ByteArrayOutputStream();
                         byte[] buf = new byte[1024];
                         for (int readNum; (readNum = fis.read(buf)) != -1;) {
                             bos.write(buf, 0, readNum);
                         }
                         byte[] imageBytes = bos.toByteArray();
                         fis.close();
                         bos.close();
                         
                         String profile_uploadName = selectedFile.getName();
                         String profile_extension = getFileExtension(selectedFile);
                         long profile_Size = selectedFile.length();
                         
                         String updateQuery = Query.UPDATE_STUIMG_TABLE;
                         PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                         updateStatement.setBytes(1, imageBytes);
                         updateStatement.setString(2, selectedFile.getName());
                         updateStatement.setString(3, profile_uploadName);
                         updateStatement.setString(4, profile_extension);
                         updateStatement.setLong(5, profile_Size);
                         updateStatement.setString(6, username);

                     
                         int rowsUpdated = updateStatement.executeUpdate();
                         if (rowsUpdated > 0) {
                             ImageIcon imageIcon = new ImageIcon(imageBytes);
                             Image image = imageIcon.getImage().getScaledInstance(imgLabel.getWidth(), imgLabel.getHeight(), Image.SCALE_SMOOTH);
                             imgLabel.setIcon(new ImageIcon(image));
                             JOptionPane.showMessageDialog(null, "프로필 사진을 업데이트하였습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                         } else {
                             JOptionPane.showMessageDialog(null, "프로필 사진을 업데이트하지 못하였습니다.", "알림", JOptionPane.ERROR_MESSAGE);
                         }
                     } catch (IOException | SQLException ex) {
                         ex.printStackTrace();
                         JOptionPane.showMessageDialog(null, "데이터베이스 오류가 발생했습니다.", "알림", JOptionPane.ERROR_MESSAGE);
                     }
                 }
             }

             private String getFileExtension(File file) {
                 String fileName = file.getName();
                 int dotIndex = fileName.lastIndexOf('.');
                 if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                     return fileName.substring(dotIndex + 1).toLowerCase();
                 } else {
                     return "";
                 }
             }
         });

         saveButton.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  String address = addressField.getText();
                  String password1 = new String(passwordField.getPassword());
                  String newPass = new String(newpassField.getPassword());
                  String newPassCom = new String(newpasscomField.getPassword());
                  String hashedPassword1 = ProfileMgr.hashPassword(password1);

                  if (address.isEmpty()) {
                      JOptionPane.showMessageDialog(null, "주소를 입력하세요.", "알림", JOptionPane.ERROR_MESSAGE);
                      return;
                  }

                 try {
                     String selectQuery = Query.SELECT_STUPASS_TABLE;
                     PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
                     selectStatement.setString(1, username);

                     ResultSet resultSet = selectStatement.executeQuery();
                     if (resultSet.next()) {
                         String myPassword = resultSet.getString("user_password");
                         if (password1.isEmpty()) {
                             JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.", "알림", JOptionPane.ERROR_MESSAGE);
                             return;
                         } else if (!hashedPassword1.equals(myPassword)) {
                             JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "알림", JOptionPane.ERROR_MESSAGE);
                             return;
                         }
                     }

                     if (newPass.isEmpty() && newPassCom.isEmpty()) {
                         String updateQuery = Query.UPDATE_STUADPAS_TABLE;
                         PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                         updateStatement.setString(1, address);
                         updateStatement.setString(2, hashedPassword1); // 현재 비밀번호를 그대로 저장
                         updateStatement.setString(3, username);

                         int rowsUpdated = updateStatement.executeUpdate();
                         if (rowsUpdated > 0) {
                             JOptionPane.showMessageDialog(null, "회원정보를 수정하였습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                         } else {
                             JOptionPane.showMessageDialog(null, "회원정보를 수정하지 못하였습니다.", "알림", JOptionPane.ERROR_MESSAGE);
                         }
                     } else if (newPass.equals(newPassCom)) {
                         String hashedPasswordcom = ProfileMgr.hashPassword(newPassCom);
                         String updateQuery = Query.UPDATE_STUADPAS_TABLE;
                         PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                         updateStatement.setString(1, address);
                         updateStatement.setString(2, hashedPasswordcom); // 변경된 비밀번호로 업데이트
                         updateStatement.setString(3, username);

                         int rowsUpdated = updateStatement.executeUpdate();
                         if (rowsUpdated > 0) {
                             JOptionPane.showMessageDialog(null, "회원정보를 수정하였습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                         } else {
                             JOptionPane.showMessageDialog(null, "회원정보를 수정하지 못하였습니다.", "알림", JOptionPane.ERROR_MESSAGE);
                         }
                     } else {
                         JOptionPane.showMessageDialog(null, "새 비밀번호가 일치하지 않습니다.", "알림", JOptionPane.ERROR_MESSAGE);
                     }

                 } catch (SQLException ex) {
                     ex.printStackTrace();
                     JOptionPane.showMessageDialog(null, "데이터베이스 오류가 발생했습니다.", "알림", JOptionPane.ERROR_MESSAGE);
                 }
             }
         });
      }   
   }