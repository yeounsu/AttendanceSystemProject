package Counseling;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import ProgramMgr.AttendanceManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import config.*;

public class Counseling_admin extends JPanel{

    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JTable table;
    private Connection connection;

    public Counseling_admin() {
        this.model = new DefaultTableModel(new String[]{"순번", "아이디", "작성자", "작성일자","제 목"}, 0);
        this.table = new JTable(model);
        this.connection = MysqlConnection.getConnection(); // 데이터베이스 연결 초기화
        showData();
    }

    private void showData() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(1100, 770));
        mainPanel.setBackground(Color.WHITE);

     
        
        String[] columns = {"순번", "아이디", "작성자", "작성일자","제 목"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(1100, 720));
        Font font = new Font("Inter", Font.BOLD, 18);
        table.setFont(font);
        table.setDefaultEditor(Object.class, null); // Disable editing
        
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100); // 순번 열의 너비를 100으로 설정
        columnModel.getColumn(1).setPreferredWidth(200); // 게시글 열의 너비를 500으로 설정
        columnModel.getColumn(2).setPreferredWidth(200); // 작성자 열의 너비를 200으로 설정
        columnModel.getColumn(3).setPreferredWidth(200); // 작성일자 열의 너비를 200으로 설정
        columnModel.getColumn(4).setPreferredWidth(400); // 작성일자 열의 너비를 200으로 설정
        table.setRowHeight(30); // 행의 높이를 30으로 설정
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(new MyHeaderRenderer(font));
        }
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(231, 222, 248)); // 헤더의 배경색을 흰색으로 설정
        header.setForeground(Color.BLACK); // 헤더의 글자색을 검은색으로 설정
        header.setFont(new Font("Inter", Font.BOLD, 24)); // 헤더의 폰트를 Inter, 평범체, 크기 24로 설정

        
        scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);
        
        // MouseListener를 추가하여 더블클릭 이벤트 처리
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow(); // 선택된 행 인덱스 가져오기
                if (selectedRow != -1) { // 선택된 행이 있을 경우에만 실행
                    String counselingTitle = (String) table.getValueAt(selectedRow, 4); // 선택된 행의 공지사항 제목 가져오기
                    JFrame frame = new JFrame("Counseling View");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 버튼 동작 설정
                    frame.add(new Counseling_view(counselingTitle)); // Announcement_view 생성자에 공지사항 제목 전달
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Counseling_admin.this, "공지사항을 선택하세요.", "알림", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        
        try {
            model.setRowCount(0);
            String username = UserInfo.getUserID();
            String className = AttendanceManager.getClassNameByUsername(username);
            String query = "SELECT c.counseling_num, c.counseling_date, c.counseling_title, c.user_id, u.user_name " +
                    "FROM counseling c " +
                    "JOIN user u ON c.user_id = u.user_id " +
                    "WHERE counseling_class=?"+
                    "ORDER BY c.counseling_num DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, className); // className 값을 설정
            ResultSet resultSet = statement.executeQuery();
            // 데이터를 JTable 모델에 추가하는 코드
            while (resultSet.next()) {
               String counselingNum = resultSet.getString("counseling_num");
               String userId = resultSet.getString("user_id");
               String userName = resultSet.getString("user_name");
               Timestamp counselingDate = resultSet.getTimestamp("counseling_date");
               String formattedDate = new SimpleDateFormat("yyyy-   MM-dd").format(counselingDate); // 날짜 형식 수정
               String counselingTitle = resultSet.getString("counseling_title");
                model.addRow(new Object[]{counselingNum, userId, userName,  formattedDate, counselingTitle});
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
            frame.add(new Counseling_admin());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }


}