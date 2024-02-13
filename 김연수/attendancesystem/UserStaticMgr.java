package attendancesystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserStaticMgr {
    private Connection connection;
    private String loggedInUserID;

    public UserStaticMgr(Connection connection, String loggedInUserID) {
        this.connection = connection;
        this.loggedInUserID = loggedInUserID;
    }

    // Method to create the panel for overall attendance
    public JPanel createOverallAttendancePanel() {
        JPanel overallAttendancePanel = new JPanel();
        overallAttendancePanel.setBackground(Color.WHITE);
        overallAttendancePanel.setLayout(new BorderLayout());
        overallAttendancePanel.setPreferredSize(new Dimension(200, 150));

        // Label for "나의 출결 상황 (전체)"
        JLabel overallAttendanceLabel = new JLabel("나의 출결 상황 (전체)");
        overallAttendanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        overallAttendanceLabel.setFont(new Font("Inter", Font.BOLD, 16));
        overallAttendancePanel.add(overallAttendanceLabel, BorderLayout.NORTH);

        // Fetch overall attendance data for the logged-in user
        String overallAttendanceData = fetchOverallAttendanceData(loggedInUserID);

        // Label to display overall attendance data
        JLabel overallAttendanceDataLabel = new JLabel(overallAttendanceData);
        overallAttendanceDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        overallAttendancePanel.add(overallAttendanceDataLabel, BorderLayout.CENTER);

        return overallAttendancePanel;
    }

    // Method to create the panel for this month's attendance
    public JPanel createThisMonthAttendancePanel() {
        JPanel thisMonthAttendancePanel = new JPanel();
        thisMonthAttendancePanel.setBackground(Color.WHITE);
        thisMonthAttendancePanel.setLayout(new BorderLayout());
        thisMonthAttendancePanel.setPreferredSize(new Dimension(200, 150));

        // Label for "나의 출결 상황 (이번 달)"
        JLabel thisMonthAttendanceLabel = new JLabel("나의 출결 상황 (이번 달)");
        thisMonthAttendanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        thisMonthAttendanceLabel.setFont(new Font("Inter", Font.BOLD, 16));
        thisMonthAttendancePanel.add(thisMonthAttendanceLabel, BorderLayout.NORTH);

        // Fetch this month's attendance data for the logged-in user
        String thisMonthAttendanceData = fetchThisMonthAttendanceData(loggedInUserID);

        // Label to display this month's attendance data
        JLabel thisMonthAttendanceDataLabel = new JLabel(thisMonthAttendanceData);
        thisMonthAttendanceDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        thisMonthAttendancePanel.add(thisMonthAttendanceDataLabel, BorderLayout.CENTER);

        return thisMonthAttendancePanel;
    }

    // Method to fetch overall attendance data for the logged-in user
    private String fetchOverallAttendanceData(String userID) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(Query.FETCH_OVERALL_ATTENDANCE_DATA);
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int attendanceCount = rs.getInt("attendance_count");
                int absenceCount = rs.getInt("absence_count");
                int tardinessCount = rs.getInt("tardiness_count");

                StringBuilder attendanceData = new StringBuilder();
                attendanceData.append("출석 ").append(attendanceCount).append("\n");
                attendanceData.append("결석 ").append(absenceCount).append("\n");
                attendanceData.append("지각 ").append(tardinessCount);
                
                return attendanceData.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Error fetching data";
    }

    // Method to fetch this month's attendance data for the logged-in user
    private String fetchThisMonthAttendanceData(String userID) {
        try {
            LocalDate now = LocalDate.now();
            LocalDate firstDayOfMonth = now.withDayOfMonth(1);
            LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());

            PreparedStatement pstmt = connection.prepareStatement(Query.FETCH_THIS_MONTH_ATTENDANCE_DATA);
            pstmt.setString(1, userID);
            pstmt.setDate(2, java.sql.Date.valueOf(firstDayOfMonth));
            pstmt.setDate(3, java.sql.Date.valueOf(lastDayOfMonth));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int attendanceCount = rs.getInt("attendance_count");
                int absenceCount = rs.getInt("absence_count");
                int tardinessCount = rs.getInt("tardiness_count");
                
                // Construct the string with labels for each count value
                StringBuilder attendanceData = new StringBuilder();
                attendanceData.append(attendanceCount).append(" ");
                attendanceData.append(absenceCount).append(" ");
                attendanceData.append(tardinessCount).append("   "); // Adjust spacing here
                attendanceData.append("Attendance Absence Tardy");
                
                return attendanceData.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "Error fetching data";
    }
}
        