package Announcement;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import ProgramMgr.AttendanceManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent; // MouseAdapter와 MouseEvent 추가
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import config.*;

public class Announcement_stu extends JPanel{

    private DefaultTableModel model; // model을 필드로 선언
    private JScrollPane scrollPane; // 스크롤 패널도 필드로 선언
    private JTable table;
    private Connection connection;

    public Announcement_stu() {
        this.model = new DefaultTableModel(new String[]{"순번", "게시글", "작성자", "작성일자"}, 0);
        this.table = new JTable(model);
        this.connection = MysqlConnection.getConnection(); // 데이터베이스 연결 초기화
        showData();
    }

    public void showData() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1100, 770));

        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel2.setBackground(Color.WHITE);

        CustomButton refreshButton = new CustomButton("새로 고침");
        refreshButton.addActionListener(e -> {
            // 새로 고침 버튼 클릭 시 모델의 테이블 값을 갱신
            refreshData();
        });
        buttonPanel2.add(refreshButton); // 버튼을 버튼 패널에 추가   

        String[] columns = {"순번", "게시글", "작성자", "작성일자"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(1100, 720));
        Font font = new Font("Inter", Font.BOLD, 18);
        table.setFont(font);
        table.setDefaultEditor(Object.class, null); // Disable editing

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100); // 순번 열의 너비를 100으로 설정
        columnModel.getColumn(1).setPreferredWidth(500); // 게시글 열의 너비를 500으로 설정
        columnModel.getColumn(2).setPreferredWidth(200); // 작성자 열의 너비를 200으로 설정
        columnModel.getColumn(3).setPreferredWidth(200); // 작성일자 열의 너비를 200으로 설정
        table.setRowHeight(30); // 행의 높이를 30으로 설정

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(new MyHeaderRenderer(font));
        }

        // TableCellRenderer를 사용하여 셀의 배경색을 변경
        TableCellRenderer yellowRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                comp.setBackground(Color.WHITE); // 셀의 배경색을 노란색으로 설정
                return comp;
            }
        };
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(231, 222, 248)); // 헤더의 배경색을 흰색으로 설정
        header.setForeground(Color.BLACK); // 헤더의 글자색을 검은색으로 설정
        header.setFont(new Font("Inter", Font.BOLD, 24)); // 헤더의 폰트를 Inter, 평범체, 크기 24로 설정

        // 모든 열에 대해 적용
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(yellowRenderer);
        }

        scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Color.WHITE); // JScrollPane의 배경색을 흰색으로 설정
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel2, BorderLayout.SOUTH);
        mainPanel.setBackground(Color.WHITE); // mainPanel의 배경색을 흰색으로 설정
        add(mainPanel);

        try {
            String username = UserInfo.getUserID();
            String className = AttendanceManager.getClassNameByUsername(username);
            String query = "SELECT announcement_num, announcement_title, announcement_writer, announcement_date " +
                    "FROM announcement " +
                    "WHERE announcement_class = ? " +
                    "ORDER BY announcement_num DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, className); // className 값을 설정
            ResultSet resultSet = statement.executeQuery();
            // 데이터를 JTable 모델에 추가하는 코드
            while (resultSet.next()) {
                int announcementNum = resultSet.getInt("announcement_num");
                String announcementTitle = resultSet.getString("announcement_title");
                String announcementWriter = resultSet.getString("announcement_writer");
                String announcementDate = resultSet.getString("announcement_date");
                model.addRow(new Object[]{announcementNum, announcementTitle, announcementWriter, announcementDate});
            }
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "공지사항이 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static class MyHeaderRenderer implements TableCellRenderer {
        JLabel label;

        public MyHeaderRenderer(Font font) {
            label = new JLabel();
            label.setFont(font);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setBorder(BorderFactory.createEtchedBorder());
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            label.setText(value.toString());
            return label;
        }
    }
    
    public void refreshData() {
        try {
            model.setRowCount(0); // 모델의 행을 모두 제거하여 새로운 데이터로 갱신
            String username = UserInfo.getUserID();
            String className = AttendanceManager.getClassNameByUsername(username);
            String query = "SELECT announcement_num, announcement_title, announcement_writer, announcement_date " +
                    "FROM announcement " +
                    "WHERE announcement_class = ? " +
                    "ORDER BY announcement_num DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, className);
            ResultSet resultSet = statement.executeQuery();
            // 데이터를 JTable 모델에 추가하는 코드
            while (resultSet.next()) {
                int announcementNum = resultSet.getInt("announcement_num");
                String announcementTitle = resultSet.getString("announcement_title");
                String announcementWriter = resultSet.getString("announcement_writer");
                String announcementDate = resultSet.getString("announcement_date");
                model.addRow(new Object[]{announcementNum, announcementTitle, announcementWriter, announcementDate});
            }
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "공지사항이 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Main");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Announcement_stu());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
