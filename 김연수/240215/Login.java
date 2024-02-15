package attendancesystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
	
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;

public class Login extends JFrame {

    private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String DB_URL = "jdbc:mysql://localhost:3306/attendancesystem";
    private final String USER = "root";
    private final String PASS = "1234";
    private String userID;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    private static String loggedInUserID;

    public static void setLoggedInUserID(String userID) {
        loggedInUserID = userID;
    }

    public static String getLoggedInUserID() {
        return loggedInUserID;
    }

    public int getUserIDValueFromDatabase(String id) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = getConnection();

            String sql = "SELECT user_idValue FROM user WHERE user_id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

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

    public class LoginManager {
        private static String userID;

        public static void setUserID(String id) {
            userID = id;
        }

        public static String getUserID() {
            return userID;
        }
    }

    public Login() {
        setTitle("�������");
        setSize(1440, 1024);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(null);

        JLabel idLabel = new JLabel("���̵�");
        JTextField idField = new JTextField(20);
        idLabel.setBounds(810, 350, 425, 30);
        idField.setBounds(810, 400, 425, 30);

        JLabel passwordLabel = new JLabel("��й�ȣ");
        JPasswordField passwordField = new JPasswordField(100);
        passwordLabel.setBounds(810, 500, 425, 30);
        passwordField.setBounds(810, 550, 425, 30);

        JButton loginButton = new JButton("�α���");
        JButton registerButton = new JButton("ȸ������");
        loginButton.setBounds(810, 670, 425, 50);
        registerButton.setBounds(810, 780, 425, 50);

        panel.setBackground(Color.WHITE);

        loginButton.setForeground(new Color(255, 255, 255));
        registerButton.setForeground(new Color(255, 255, 255));
        loginButton.setBackground(new Color(133, 91, 221));
        registerButton.setBackground(new Color(133, 91, 221));
        loginButton.setFont(new Font("Inter", Font.BOLD, 30));
        registerButton.setFont(new Font("Inter", Font.BOLD, 30));

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String password = new String(passwordField.getPassword());

                boolean success = authenticate(id, password);

                if (success) {
                    setLoggedInUserID(id);
                    int userIDValue = getUserIDValueFromDatabase(id);
                    if (userIDValue == 0) {
                        ImageViewerUser mainPage = new ImageViewerUser(getConnection(), id);
                        JOptionPane.showMessageDialog(null, "������� ������", "�˸�", JOptionPane.INFORMATION_MESSAGE);
                        mainPage.setVisible(true);
                        dispose();
                    } else if (userIDValue == 1) {
                        ImageViewer mainPage = new ImageViewer();
                        JOptionPane.showMessageDialog(null, "������� �����ڴ�", "�˸�", JOptionPane.INFORMATION_MESSAGE);
                        mainPage.setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "���̵� Ȥ�� ��й�ȣ�� �ٽ� �Է��� �ּ���", "�˸�", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "���̵� Ȥ�� ��й�ȣ�� �ٽ� �Է��� �ּ���");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();

                // RegisterPage registerPage = new RegisterPage();
                // registerPage.setVisible(false);
            }
        });
    }


    private boolean authenticate(String id, String password) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = getConnection();

            String sql = "SELECT * FROM user WHERE user_id=? AND user_password=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, password);

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
                new Login().setVisible(true);
            }
        });
    }
}
