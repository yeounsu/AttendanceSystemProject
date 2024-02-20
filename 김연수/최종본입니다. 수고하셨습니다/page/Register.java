package page;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import config.Query;
import Profile.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends JFrame {

    private Blob profile_image;
    JLabel img_Label = new JLabel();
    private String profile_name;
    private String profile_uploadName;
    private String profile_extension;
    private long profile_Size;
    private JButton backButton;

    public Register() {

        JLabel titleLabel = new JLabel("환 영 합 니 다 .");
        titleLabel.setBounds(360, 80, 720, 50);
        titleLabel.setFont(new Font("SanSerif", Font.BOLD, 40));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel title2Label = new JLabel("학생 출결 관리 지금 바로 시작하세요 !");
        title2Label.setBounds(360, 150, 720, 20);
        title2Label.setFont(new Font("SanSerif", Font.BOLD, 20));
        title2Label.setHorizontalAlignment(SwingConstants.CENTER);

        JFrame otherFrame = new JFrame("회원가입 페이지");
        otherFrame.setSize(1440, 1024);
        otherFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(null);

        JLabel User_Name_Label = new JLabel("이름");
        JTextField User_Name_Field = new JTextField(20);
        User_Name_Label.setBounds(360, 240, 50, 30);
        User_Name_Field.setBounds(360, 270, 330, 30);

        JLabel User_Id_Label = new JLabel("아이디");
        JTextField User_Id_Field = new JTextField(20);
        User_Id_Label.setBounds(750, 240, 50, 30);
        User_Id_Field.setBounds(750, 270, 330, 30);

        JLabel User_Password_Label = new JLabel("비밀번호");
        JPasswordField User_Password_Field = new JPasswordField(100);
        User_Password_Label.setBounds(360, 330, 50, 30);
        User_Password_Field.setBounds(360, 360, 330, 30);

        JLabel Password_C_Label = new JLabel("비밀번호 확인");
        JPasswordField Password_C_Field = new JPasswordField(100);
        Password_C_Label.setBounds(750, 330, 100, 30);
        Password_C_Field.setBounds(750, 360, 330, 30);

        JLabel User_Birth_Label = new JLabel("생년월일");
        JTextField User_Birth_Field = new JTextField(20);
        User_Birth_Label.setBounds(360, 420, 50, 30);
        User_Birth_Field.setBounds(360, 450, 330, 30);

        JLabel User_Gender_Label = new JLabel("성별");
        String[] genderOptions = { "---성별을 선택하세요---", "남자", "여자" };
        JComboBox<String> genderComboBox = new JComboBox<>(genderOptions);
        User_Gender_Label.setBounds(750, 420, 50, 30);
        genderComboBox.setBounds(750, 450, 330, 30);
        String selectedgender = (String) genderComboBox.getSelectedItem();
        if ("---성별을 선택하세요---".equals(selectedgender)) {
            selectedgender = null;
        }

        JLabel User_Address_Label = new JLabel("주소");
        JTextField User_Address_Field = new JTextField(100);
        User_Address_Label.setBounds(360, 510, 50, 30);
        User_Address_Field.setBounds(360, 540, 720, 30);

        JLabel User_phone_Label = new JLabel("전화번호");
        JTextField User_phone_Field = new JTextField(20);
        User_phone_Label.setBounds(360, 600, 50, 30);
        User_phone_Field.setBounds(360, 630, 330, 30);

        JLabel User_major_Label = new JLabel("전공");
        JTextField User_major_Field = new JTextField(20);
        User_major_Label.setBounds(750, 600, 50, 30);
        User_major_Field.setBounds(750, 630, 330, 30);

        JLabel Class_name_Label = new JLabel("수강과목");
        String[] Class_name_Options = { "---수강과목을 선택하세요---", "java반", "cad1급반", "cad2급반", "컴활반" };
        JComboBox<String> ClassComboBox = new JComboBox<>(Class_name_Options);
        Class_name_Label.setBounds(360, 690, 80, 30);
        ClassComboBox.setBounds(360, 720, 330, 30);
        String selectedclass = (String) ClassComboBox.getSelectedItem();
        if ("---수강과목을 선택하세요---".equals(selectedclass)) {
            selectedclass = null;
        }

        JLabel profile_img_Label = new JLabel("사진을 선택하세요");
        profile_img_Label.setBounds(750, 690, 150, 30);

        JLabel profile_img_1Label = new JLabel("사진 규격 (132 * 170)");
        profile_img_1Label.setFont(new Font("SanSerif", Font.BOLD, 10));
        profile_img_1Label.setBounds(750, 713, 150, 10);

        JButton profile_img_button = new JButton("파일선택");
        img_Label.setBounds(880, 700, 132, 170);
        img_Label.setBorder(new LineBorder(Color.BLACK, 1, true));
        profile_img_button.setBounds(750, 730, 90, 25);
        profile_img_button.setFont(new Font("Inter", Font.ITALIC, 12));

        JButton ComButton = new JButton("가입하기");
        ComButton.setBounds(400, 900, 500, 40);
		ComButton.setForeground(new Color(255, 255, 255));
		ComButton.setBackground(new Color(133, 91, 221));
		ComButton.setFont(new Font("Inter", Font.BOLD, 30));
        
        backButton = new JButton("로그인");
        backButton.setBounds(910, 900, 100, 40);
        backButton.setForeground(new Color(255, 255, 255));
        backButton.setBackground(new Color(133, 91, 221));
        backButton.setFont(new Font("Inter", Font.BOLD, 20));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 현재 창 닫기
                setVisible(false);
                
                // Login 페이지 보여주기
                Login loginPage = new Login();
                loginPage.setVisible(true);
                
                // 현재 창 완전히 제거하기
                dispose();
            }
        });
        panel.add(backButton); // 패널에 뒤로가기 버튼 추가

        JLabel adminLabel = new JLabel("관리자 체크");
        adminLabel.setBounds(360, 770, 100, 30);

        JCheckBox adminCheckBox = new JCheckBox();
        adminCheckBox.setBounds(470, 770, 30, 30);

        JLabel adminPasswordLabel = new JLabel("관리자 비밀번호");
        adminPasswordLabel.setBounds(360, 810, 100, 30);

        JPasswordField adminPasswordField = new JPasswordField(100);
        adminPasswordField.setBounds(470, 810, 330, 30);
        adminPasswordField.setEnabled(false);

        backButton = new JButton("로그인");
        backButton.setBounds(910, 900, 100, 40);
        backButton.setForeground(new Color(255, 255, 255));
        backButton.setBackground(new Color(133, 91, 221));
        backButton.setFont(new Font("Inter", Font.BOLD, 20));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Login loginPage = new Login();
                loginPage.setVisible(true);
                dispose();
            }
        });

        panel.add(profile_img_Label);
        panel.add(profile_img_1Label);
        panel.add(img_Label);
        panel.add(profile_img_button);

        panel.add(titleLabel);
        panel.add(title2Label);
        panel.add(User_Name_Label);
        panel.add(User_Name_Field);
        panel.add(User_Id_Label);
        panel.add(User_Id_Field);
        panel.add(User_Password_Label);
        panel.add(User_Password_Field);
        panel.add(Password_C_Label);
        panel.add(Password_C_Field);
        panel.add(User_Birth_Label);
        panel.add(User_Birth_Field);
        panel.add(User_Gender_Label);
        panel.add(genderComboBox);
        panel.add(User_Address_Label);
        panel.add(User_Address_Field);
        panel.add(User_phone_Label);
        panel.add(User_phone_Field);
        panel.add(User_major_Label);
        panel.add(User_major_Field);
        panel.add(Class_name_Label);
        panel.add(ClassComboBox);
        panel.add(ComButton);
        panel.add(adminLabel);
        panel.add(adminCheckBox);
        panel.add(adminPasswordLabel);
        panel.add(adminPasswordField);

        profile_img_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "1234");
                        File selectedFile = fileChooser.getSelectedFile();
                        byte[] fileContent = Files.readAllBytes(selectedFile.toPath());
                        profile_image = conn.createBlob();
                        profile_image.setBytes(1, fileContent);
                        profile_name = selectedFile.getName();
                        profile_uploadName = selectedFile.getName();
                        profile_extension = getFileExtension(selectedFile);
                        profile_Size = selectedFile.length();
                        img_Label.setIcon(new ImageIcon(new ImageIcon(selectedFile.getAbsolutePath()).getImage().getScaledInstance(132, 170, java.awt.Image.SCALE_SMOOTH)));
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }

                    img_Label.setBounds(880, 700, 132, 170);
                    panel.add(img_Label);
                    img_Label.setVisible(true);
                    panel.revalidate();
                    panel.repaint();
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

        adminCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (adminCheckBox.isSelected()) {
                    adminPasswordField.setEnabled(true);
                } else {
                    adminPasswordField.setEnabled(false);
                }
            }
        });

        ComButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String password = new String(User_Password_Field.getPassword());
                String hashedPassword = ProfileMgr.hashPassword(password);
                dispose();

                String username = User_Name_Field.getText();
                String Id = User_Id_Field.getText();
                String password1 = new String(User_Password_Field.getPassword());
                String confirmPassword = new String(Password_C_Field.getPassword());
                String birth = User_Birth_Field.getText();
                String selectedgender = (String) genderComboBox.getSelectedItem();
                if ("---성별을 선택하세요---".equals(selectedgender)) {
                    JOptionPane.showMessageDialog(null, "성별을 선택하세요.", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String gender = selectedgender != null && !selectedgender.equals("---성별을 선택하세요---") ? selectedgender.toString() : "";
                String address = User_Address_Field.getText();
                String phone = User_phone_Field.getText();
                String major = User_major_Field.getText();
                String selectedclass = (String) ClassComboBox.getSelectedItem();
                if ("---수강과목을 선택하세요---".equals(selectedclass)) {
                    JOptionPane.showMessageDialog(null, "수강과목을 선택하세요.", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String class_name = selectedclass != null && !selectedclass.equals("---수강과목을 선택하세요---") ? selectedclass.toString() : "";
                String adminPassword = new String(adminPasswordField.getPassword());
                
                int idValue = 0;
                if (adminCheckBox.isSelected() && adminPassword.equals("1234")) {
                    idValue = 1;
                }

                if (adminCheckBox.isSelected() && (adminPassword.isEmpty())) {
                    JOptionPane.showMessageDialog(null, "관리자 비밀번호를 입력하세요.", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (adminCheckBox.isSelected() && (!adminPassword.equals("1234"))) {
                    JOptionPane.showMessageDialog(null, "관리자 비밀번호가 옳지않습니다.", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "이름을 입력하세요.", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (Id.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "아이디를 입력하세요.", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (password1.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password1.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "비밀번호확인을 위해 입력해주세요", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (birth.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "생년월일을 입력해 주세요.", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (gender == null || gender.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "성별을 선택하세요", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (address.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "주소를 입력하세요.", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "전화번호를 입력하세요.", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (major.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "전공을 입력하세요.", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (class_name == null || class_name.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "수강과목을 선택하세", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "1234");
                    PreparedStatement pstmt = conn.prepareStatement(Query.SELECT_REGID_TABLE);
                    pstmt.setString(1, Id);


                    if (pstmt.executeQuery().next()) {
                        JOptionPane.showMessageDialog(null, "이미 가입된 아이디 입니다.", "알림", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        String sql = Query.INSERT_REINFO_TABLE;
                        PreparedStatement pstmt1 = conn.prepareStatement(sql);
                        pstmt1.setString(1, Id);
                        pstmt1.setInt(2, idValue);
                        pstmt1.setString(3, hashedPassword);
                        pstmt1.setString(4, username);
                        pstmt1.setString(5, birth);
                        pstmt1.setString(6, address);
                        pstmt1.setString(7, gender);
                        pstmt1.setString(8, phone);
                        pstmt1.setString(9, major);
                        pstmt1.setString(10, class_name);
                        pstmt1.setBlob(11, profile_image);
                        pstmt1.setString(12, profile_name);
                        pstmt1.setString(13, profile_uploadName);
                        pstmt1.setString(14, profile_extension);
                        pstmt1.setLong(15, profile_Size);


                        int rowsAffected = pstmt1.executeUpdate();
                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(null, "회원가입 성공", "알림", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "회원가입 실패", "알림", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "회원가입에 실패하였습니다.", "알림", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Login loginPage = new Login();
                loginPage.setVisible(true);
            }

        });

        otherFrame.add(panel);
        otherFrame.setLocationRelativeTo(null);
        otherFrame.setVisible(true);
        panel.setBackground(Color.WHITE);

    }

    protected String getFileExtension(File selectedFile) {
        String fileName = selectedFile.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        } else {
            return "";
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Register();
            }
        });
    }

    public ImageIcon getImgLabelIcon() {
        return (ImageIcon) img_Label.getIcon();
    }
}