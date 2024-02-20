package Attendance;

import config.*;
import page.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import ProgramMgr.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class Attendance_admin extends JPanel {

    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JTable table;
    private JButton selectedButton;
    private String classindex;

    public Attendance_admin() {
        showData();
    }

    private void showData() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        String[] columnNames = {"이름", "전공", "아이디", "출근시간", "퇴근시간", "출석현황"};
        model = new DefaultTableModel(columnNames, 0);

        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(1080, 570));
        Font font = new Font("Inter", Font.PLAIN, 18);
        table.setFont(font);
        table.setDefaultEditor(Object.class, null); // Disable editing

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(500);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(200);
        table.setRowHeight(30);
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
        header.setFont(new Font("Inter", Font.BOLD, 20)); // 헤더의 폰트를 Inter, 평범체, 크기 24로 설정

        // 모든 열에 대해 적용
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(yellowRenderer);
        }

        scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Color.WHITE); // JScrollPane의 배경색을 흰색으로 설정

        try {
            String username = UserInfo.getUserID();
            String className = AttendanceManager.getClassNameByUsername(username);
            // 현재 날짜 가져오기
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(new Date());

            // 데이터베이스에서 데이터를 가져오는 쿼리 실행
            String query = Query.SELECT_ATTENDANCE;
            PreparedStatement pstmt = MysqlConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, className); // className을 PreparedStatement에 설정
            pstmt.setString(2, currentDate);
            ResultSet rs = pstmt.executeQuery();

            // 결과를 dataList에 추가하기 전에 dataList을 초기화
            List<Object[]> dataList = new ArrayList<>();

            // 결과를 dataList에 추가
            while (rs.next()) {
                String userName = rs.getString("user_name");
                String major = rs.getString("user_major");
                String userId = rs.getString("user_id");
                String attendanceIn = rs.getString("attendance_in");
                String attendanceOut = rs.getString("attendance_out");
                String attendanceStatus = rs.getString("attendance_status");
                Object[] rowData = {userName, major, userId, attendanceIn, attendanceOut, attendanceStatus};
                dataList.add(rowData);
            }

            rs.close();
            pstmt.close();

            if (dataList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "출결 데이터가 없습니다.");
            }

            // 데이터를 모델에 반영
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setDataVector(dataList.toArray(new Object[0][0]), new Object[]{"이름", "전공", "아이디", "출근시간", "퇴근시간", "출석현황"});
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JPanel editButtonPanel = new JPanel();
        editButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        CustomButton detailButton = new CustomButton("수정");
        CustomButton refreshButton = new CustomButton("새로 고침");
        
        refreshButton.addActionListener(e -> {
            // 새로 고침 버튼 클릭 시 모델의 테이블 값을 갱신
            refreshData();
        });
        
        
        detailButton.setPreferredSize(new Dimension(100, 30));
        detailButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String userId = (String) table.getValueAt(selectedRow, 2);
                    JFrame frame = new JFrame("Attendance view");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.add(new Attendance_view(userId));
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Attendance_admin.this, "학생을 선택하세요.", "알림", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        editButtonPanel.add(refreshButton);
        editButtonPanel.add(detailButton);

        UtilDateModel dateModel = new UtilDateModel();
        dateModel.setValue(new java.util.Date());

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);

        JButton confirmButton = new JButton("확인");
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
                if (selectedDate != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String formattedDate = sdf.format(selectedDate);
                    System.out.println("선택된 날짜: " + formattedDate);

                    // 정적 메서드 호출
                    AttendanceMgr.selectData(classindex, formattedDate, table);
                    System.out.println("눌러진 버튼의 텍스트: " + classindex);
                } else {
                    JOptionPane.showMessageDialog(null, "날짜를 먼저 선택해주세요.");
                }
            }
        });

        JPanel dateAndButtonPanel = new JPanel();
        dateAndButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        dateAndButtonPanel.add(datePicker);
        dateAndButtonPanel.add(confirmButton);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(editButtonPanel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainPanel.add(dateAndButtonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }
    
    public void refreshData() {
        try {
            String username = UserInfo.getUserID();
            String className = AttendanceManager.getClassNameByUsername(username);
            // 현재 날짜 가져오기
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sdf.format(new Date());

            // 데이터베이스에서 데이터를 가져오는 쿼리 실행
            String query = Query.SELECT_ATTENDANCE;
            PreparedStatement pstmt = MysqlConnection.getConnection().prepareStatement(query);
            pstmt.setString(1, className); // className을 PreparedStatement에 설정
            pstmt.setString(2, currentDate);
            ResultSet rs = pstmt.executeQuery();

            // 결과를 dataList에 추가하기 전에 dataList을 초기화
            List<Object[]> dataList = new ArrayList<>();

            // 결과를 dataList에 추가
            while (rs.next()) {
                String userName = rs.getString("user_name");
                String major = rs.getString("user_major");
                String userId = rs.getString("user_id");
                String attendanceIn = rs.getString("attendance_in");
                String attendanceOut = rs.getString("attendance_out");
                String attendanceStatus = rs.getString("attendance_status");
                Object[] rowData = {userName, major, userId, attendanceIn, attendanceOut, attendanceStatus};
                dataList.add(rowData);
            }

            rs.close();
            pstmt.close();

            if (dataList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "출결 데이터가 없습니다.");
            }

            // 데이터를 모델에 반영
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setDataVector(dataList.toArray(new Object[0][0]), new Object[]{"이름", "전공", "아이디", "출근시간", "퇴근시간", "출석현황"});
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Main");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Attendance_admin());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}