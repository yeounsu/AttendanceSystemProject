package Clean;

import config.*;

import javax.swing.*;

import ProgramMgr.AttendanceManager;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Clean_admin extends JPanel {

    private JPanel buttonPanel; // classname 버튼이 들어갈 패널
    private JPanel contentPanel; // 내용을 표시할 패널

    private CleanMgr cleanMgr;

    public Clean_admin() {
        try {
            setLayout(new BorderLayout());

            // 반 버튼 패널 생성
            buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            add(buttonPanel, BorderLayout.NORTH);

            // 내용 content 패널 생성 (top+center)
            contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            JScrollPane scrollPane = new JScrollPane(contentPanel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Changed to VERTICAL_SCROLLBAR_AS_NEEDED for better usability
            add(scrollPane, BorderLayout.CENTER);

            // Fetching class name based on admin's username
            String adminname = UserInfo.getUserID();
            String className = AttendanceManager.getClassNameByUsername(adminname);
            contentPanel.add(createCleanPanel(className));

        } catch (Exception ex) {
            ex.printStackTrace();
            // Handle exceptions here
        }
    }

    // Method to create the clean panel
    private JPanel createCleanPanel(String className) {
        JPanel createCleanPanel = new CleanMgr(MysqlConnection.getConnection()).createCleanPanel(className);
        createCleanPanel.setBorder(BorderFactory.createEmptyBorder());
        return createCleanPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("managerCleanPanel");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Clean_admin());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}