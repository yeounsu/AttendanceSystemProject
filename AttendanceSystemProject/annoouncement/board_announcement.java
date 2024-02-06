package annoouncement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class board_announcement extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public board_announcement() {
        setTitle("Announcement Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 테이블 모델 초기화
        tableModel = new DefaultTableModel();
        tableModel.addColumn("순번");
        tableModel.addColumn("게시글");
        tableModel.addColumn("작성자");
        tableModel.addColumn("작성일자");

        // 테이블 초기
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 데이터베이스에서 데이터 가져와서 테이블에 추가
        DatabaseManager databaseManager = new DatabaseManager("jdbc:mysql://localhost:3306/attendancesystem?characterEncoding=UTF-8&serverTimezone=UTC", "root", "1234");
        loadDataFromDatabase(databaseManager);

        // 화면 갱신 버튼 추가
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadDataFromDatabase(databaseManager));

        // 글쓰기 버튼 추가
        JButton writeButton = new JButton("글쓰기");
        writeButton.addActionListener(e -> {
            write_announcement writeAnnouncementWindow = new write_announcement();
            writeAnnouncementWindow.setVisible(true);
        });

        // 삭제 버튼 추가
        JButton deleteButton = new JButton("삭제");
        deleteButton.addActionListener(e -> {
            // 선택된 행 삭제 기능 구현
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int announcementNum = (int) tableModel.getValueAt(selectedRow, 0);
                // 여기서 해당 순번의 게시글을 삭제하는 코드를 추가하세요
            } else {
                JOptionPane.showMessageDialog(board_announcement.this, "삭제할 행을 선택하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(writeButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private void loadDataFromDatabase(DatabaseManager databaseManager) {
        try {
            // 데이터베이스에서 데이터 가져오기
            String query = "SELECT a.announcement_num, a.announcement_title, u.user_name, a.announcement_date " +
                    "FROM announcement a " +
                    "JOIN user u ON a.user_id = u.user_id";
            ResultSet resultSet = databaseManager.executeQuery(query);

            // 테이블 모델 초기화
            tableModel.setRowCount(0);

            // 결과를 테이블에 추가
            while (resultSet.next()) {
                int announcementNum = resultSet.getInt("announcement_num");
                String title = resultSet.getString("announcement_title");
                String userName = resultSet.getString("user_name");
                Date date = resultSet.getDate("announcement_date");

                Object[] row = {announcementNum, title, userName, date};
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(board_announcement::new);
    }
}
