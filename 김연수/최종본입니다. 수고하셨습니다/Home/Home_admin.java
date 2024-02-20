package Home;

import config.*;

import javax.swing.*;

import ProgramMgr.AttendanceManager;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Home_admin extends JPanel {
    private JPanel buttonPanel; // classname 버튼이 들어갈 패널
    private JPanel contentPanel; // 내용을 표시할 패널
    private JPanel statisticsButtonPanel; // 일, 월, 총 버튼이 들어갈 패널
    private JLabel placeholderLabel; // 내용이 표시될 때 사용할 레이블
    private String className;
    private String username;
    

    public Home_admin() {
        // 퇴근 시간 추가
        username = UserInfo.getUserID();
        className = AttendanceManager.getClassNameByUsername(username);
        setLayout(new BorderLayout());

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        statisticsButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

        JPanel buttonContainer = new JPanel(new BorderLayout());
        buttonContainer.add(buttonPanel, BorderLayout.NORTH);
        buttonContainer.add(statisticsButtonPanel, BorderLayout.CENTER);

        add(buttonContainer, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);

        placeholderLabel = new JLabel("여기에 내용을 표시합니다.");
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(placeholderLabel);

        // 생성된 통계 버튼 추가
        createStatisticsButtons(className);
    }

    private void createStatisticsButtons(String className) {
        statisticsButtonPanel.removeAll();

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        JButton dayButton = new JButton("일");
        dayButton.setPreferredSize(new Dimension(366, 82));
        dayButton.setBackground(Color.WHITE);
        dayButton.setFont(new Font("Inter", Font.PLAIN, 18));
        dayButton.setBorderPainted(false);
        dayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(createStaticChartPanel());
                revalidate();
                repaint();
            }
        });

        JButton monthButton = new JButton("월");
        monthButton.setPreferredSize(new Dimension(366, 82));
        monthButton.setBackground(Color.WHITE);
        monthButton.setFont(new Font("Inter", Font.PLAIN, 18));
        monthButton.setBorderPainted(false);
        monthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(createMonthlyBarChartPanel());
                revalidate();
                repaint();
            }
        });

        JButton totalButton = new JButton("총");
        totalButton.setPreferredSize(new Dimension(366, 82));
        totalButton.setBackground(Color.WHITE);
        totalButton.setFont(new Font("Inter", Font.PLAIN, 18));
        totalButton.setBorderPainted(false);
        totalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(createoverallStaticChartPanel());
                revalidate();
                repaint();
            }
        });

        panel.add(dayButton);
        panel.add(monthButton);
        panel.add(totalButton);
        statisticsButtonPanel.add(panel);

        revalidate();
        repaint();
    }

    private JPanel createStaticChartPanel() {
        username = UserInfo.getUserID();
        className = AttendanceManager.getClassNameByUsername(username);
        JPanel StaticChartPanel = new Home_admin_Mgr(MysqlConnection.getConnection()).createStaticChartPanel(className);
        StaticChartPanel.setBorder(BorderFactory.createEmptyBorder());
        return StaticChartPanel;
    }

    private JPanel createMonthlyBarChartPanel() {
        username = UserInfo.getUserID();
        className = AttendanceManager.getClassNameByUsername(username);
        JPanel MonthlyBarChartPanel = new Home_admin_Mgr(MysqlConnection.getConnection()).createMonthlyBarChartPanel(className);
        MonthlyBarChartPanel.setBorder(BorderFactory.createEmptyBorder());
        return MonthlyBarChartPanel;
    }

    private JPanel createoverallStaticChartPanel() {
        username = UserInfo.getUserID();
        className = AttendanceManager.getClassNameByUsername(username);
        JPanel OverallStaticChartPanel = new Home_admin_Mgr(MysqlConnection.getConnection()).createoverallStaticChartPanel(className);
        OverallStaticChartPanel.setBorder(BorderFactory.createEmptyBorder());
        return OverallStaticChartPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Statistics Panel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Home_admin());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
