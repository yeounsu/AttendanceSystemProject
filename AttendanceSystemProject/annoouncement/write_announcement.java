package annoouncement;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class write_announcement extends JFrame {
    private JTextField title;
    private JComboBox<String> writerComboBox;
    private DatabaseManager databaseManager;

    public write_announcement() {
        setBounds(new Rectangle(600, 0, 450, 280));
        setTitle("공지사항 등록");

        getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("공지사항 제목");
        lblNewLabel.setBounds(12, 25, 80, 15);
        getContentPane().add(lblNewLabel);

        title = new JTextField("공지사항 제목");
        title.setBounds(104, 22, 317, 21);
        getContentPane().add(title);
        title.setColumns(10);

        JLabel label1 = new JLabel("공지사항 내용");
        label1.setBounds(12, 59, 80, 15);
        getContentPane().add(label1);

        JTextArea textArea = new JTextArea("공지사항 내용");
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setBounds(104, 53, 317, 69);
        getContentPane().add(textArea);

        JLabel label2 = new JLabel("작성자");
        label2.setBounds(12, 140, 57, 15);
        getContentPane().add(label2);

        // DatabaseManager 인스턴스 생성
        databaseManager = new DatabaseManager("jdbc:mysql://localhost:3306/attendancesystem?characterEncoding=UTF-8&serverTimezone=UTC", "root", "1234");

        // 데이터베이스에서 사용자 정보를 가져와서 콤보박스에 추가
        writerComboBox = new JComboBox<>();
        writerComboBox.setBounds(104, 137, 200, 21);
        populateUserComboBox(); // 사용자 정보 가져와서 콤보박스에 추가
        getContentPane().add(writerComboBox);

        JButton btnWrite = new JButton("작성완료");
    }

    // 데이터베이스에서 사용자 정보를 가져와서 콤보박스에 추가하는 메서드
    private void populateUserComboBox() {
        try {
            String query = "SELECT user_name FROM user WHERE user_idValue = 1;"; // user_id가 1인 사용자만 가져옴
            ResultSet resultSet = databaseManager.executeQuery(query);
            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                writerComboBox.addItem(userName); // 사용자 이름을 콤보박스에 추가
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

