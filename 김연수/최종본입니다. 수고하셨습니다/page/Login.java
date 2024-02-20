package page;
import config.*;
import ProgramMgr.*;
import Profile.*;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

public class Login extends JFrame {

    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost:3306/attendancesystem";
    private final String USER = "root";
    private final String PASS = "1234";
	protected String id;
    private static String userID;
    private static String className;
    private static Time classTime;
    
    
    //////////////////////////////
    private static String loggedInUserID;

    public static void setLoggedInUserID(String userID) {
        loggedInUserID = userID;
    }

    public static String getLoggedInUserID() {
        return loggedInUserID;
    }
    
    public class LoginManager {
        private static String userID;

        public static void setUserID(String id) {
            userID = id;
        }

        public static String getUserID() {
            return userID;
        }
    }
    ///////////////////////////////////////////////////
    
    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    public String getUserID() {
        return userID;
    }
    
    
    public int search_idvalue(String userID) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT user_idValue FROM user WHERE user_id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userID);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int userIDValue = rs.getInt("user_idValue");
                rs.close();
                pstmt.close();
                conn.close();
                return userIDValue;
            } else {
                rs.close();
                pstmt.close();
                conn.close();
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Login() {
        setTitle("어서오세요");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new BorderLayout());

        JLayeredPane layeredPane = new JLayeredPane();

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(133, 91, 221));
        topPanel.setPreferredSize(new Dimension(0, 150));
        topPanel.setLayout(new BorderLayout());
        panel.add(topPanel, BorderLayout.NORTH);
        
        JLabel topLabel = new JLabel("♡ W e l c o m e ♡");
        topLabel.setFont(new Font("Inter", Font.PLAIN, 60));
        topLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topLabel.setForeground(Color.WHITE); // 흰색으로 설정
        topPanel.add(topLabel, BorderLayout.CENTER);
        
        
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(null); // layout을 null로 설정하여 직접 위치 지정 가능하게 함
        panel.add(centerPanel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("학생 출결 관리 시스템");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 35));
        titleLabel.setBounds(865, 150, 360, 35);
        centerPanel.add(titleLabel);
        
        JLabel idLabel = new JLabel("아이디");
        idLabel.setFont(new Font("Inter", Font.BOLD, 20));
        idLabel.setBounds(850, 250, 100, 20);
        centerPanel.add(idLabel);

        
        JTextField idField = new JTextField();
        idField.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
        idField.setFont(new Font("Inter", Font.PLAIN, 28));
        idField.setBounds(850, 280, 400, 35);
        centerPanel.add(idField);


        JLabel passwordLabel = new JLabel("비밀번호");
        passwordLabel.setFont(new Font("Inter", Font.BOLD, 20));
        passwordLabel.setBounds(850, 380, 100, 20);
        centerPanel.add(passwordLabel);

        
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(231, 222, 248), 2));
        passwordField.setFont(new Font("Inter", Font.PLAIN, 28));
        passwordField.setBounds(850, 410, 400, 35);
        centerPanel.add(passwordField);


        JButton loginButton = new JButton("로그인");
        loginButton.setFont(new Font("Inter", Font.BOLD, 30));
        loginButton.setBounds(850, 530, 400, 40);
        centerPanel.add(loginButton);


        JLabel orLabel = new JLabel("또는");
        orLabel.setFont(new Font("Inter", Font.BOLD, 18));
        orLabel.setBounds(1030, 590, 40, 18);
        centerPanel.add(orLabel);

        
        JButton registerButton = new JButton("회원가입");
        registerButton.setFont(new Font("Inter", Font.BOLD, 30));
        registerButton.setBounds(850, 630, 400, 40);
        centerPanel.add(registerButton);

        
        ImageIcon icon = new ImageIcon(Main_admin.class.getResource("dong.PNG"));
        JLabel imgLabel = new JLabel(icon);
        imgLabel.setBounds(200, 150, 450, 450);
        centerPanel.add(imgLabel);
        
        
        JLabel etcLabel = new JLabel("♡ 동 의 대 학 교  평 생 교 육 원 ♡");
        etcLabel.setFont(new Font("Inter", Font.BOLD, 30));
        etcLabel.setBounds(200, 600, 450, 30);
        centerPanel.add(etcLabel);
        
        
        panel.setBackground(Color.WHITE);

        loginButton.setForeground(new Color(255, 255, 255));
        registerButton.setForeground(new Color(255, 255, 255));
        loginButton.setBackground(new Color(133, 91, 221));
        registerButton.setBackground(new Color(133, 91, 221));
        loginButton.setFont(new Font("Inter", Font.BOLD, 30));
        registerButton.setFont(new Font("Inter", Font.BOLD, 30));

        
        
        centerPanel.add(titleLabel);
        centerPanel.add(idLabel);
        centerPanel.add(idField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);
        centerPanel.add(loginButton);
        centerPanel.add(registerButton);
        centerPanel.add(orLabel);
        centerPanel.add(imgLabel);
        centerPanel.add(etcLabel);


        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userID = idField.getText();
                String password = new String(passwordField.getPassword());

                boolean success = authenticate(userID, password);

                if (success) {
                   setLoggedInUserID(userID);
                    int userIDValue = search_idvalue(userID);
                    // 로그인 한 USER_id get
                    String username = idField.getText();
                    UserInfo.setUserID(username);
                    
                    if (userIDValue == 0) {
                       username = UserInfo.getUserID();
                       // 출석 등록
                       AttendanceManager.markAttendance(username);
                                               Main_user mainPage = new Main_user(getConnection(), id);
                        
                        JOptionPane.showMessageDialog(null, "어서오세요 유저님", "알림", JOptionPane.INFORMATION_MESSAGE);
                        mainPage.setVisible(true);
                        dispose();
                    } else if (userIDValue == 1) {
                        Main_admin mainPage = new Main_admin(getConnection(), id);
                        JOptionPane.showMessageDialog(null, "어서오세요 관리자님", "알림", JOptionPane.INFORMATION_MESSAGE);
                        mainPage.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호를 다시 입력해 주세요", "알림", JOptionPane.ERROR_MESSAGE);
                    }


                } else {
                    JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호를 다시 입력해 주세요");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();

                Register registerPage = new Register();
                registerPage.setVisible(false);
            }
        });
    }
    

    private boolean authenticate(String id, String password) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT * FROM user WHERE user_id=? AND user_password=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, hashPassword(password)); // 해싱된 비밀번호 사용

            ResultSet rs = pstmt.executeQuery();
            boolean result = rs.next();

            rs.close();
            pstmt.close();
            conn.close();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Login();
            }
        });
    }
}