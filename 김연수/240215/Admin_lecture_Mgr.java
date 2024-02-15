package attendancesystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Admin_lecture_Mgr {
    private Connection connection;
    private String loggedInUserID;

    public Admin_lecture_Mgr(Connection connection, String loggedInUserID) {
        this.connection = connection;
        this.loggedInUserID = loggedInUserID;
    }

    public void displayLectureContentForStudent() {
        // Retrieve the class name for the logged-in user
        String className = retrieveClassNameForStudent(loggedInUserID);

        if (className == null) {
            System.out.println("어느 반에도 소속되어 있지 않습니다.");
            return;
        }

        // Determine the current week for the class
        int currentWeek = determineCurrentWeek(className);

        try {
            // Fetch lecture content for the current week of the class
            String lectureContent = fetchLectureContentForWeek(className, currentWeek);

            // Display the class name and lecture content
            System.out.println("Class Name: " + className);
            System.out.println("Week " + currentWeek + " Lecture Content: " + lectureContent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    String retrieveClassNameForStudent(String userId) {
        String className = null;
        try {
            String query = Query.FOR_USER_CLASS_LECTURE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                className = rs.getString("class_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return className;
    }

    int determineCurrentWeek(String className) {
        // Determine the current date
        Calendar cal = Calendar.getInstance();
        java.util.Date currentDate = cal.getTime();

        // Fetch the start date of the class
        Date startDate = null;
        try {
        	String query = "SELECT class_startDate FROM class " +
                    "INNER JOIN user ON class.class_num = user.class_num " +
                    "WHERE user.user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, className);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                startDate = rs.getDate("class_startDate");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (startDate == null) {
            System.out.println("Start date for class " + className + " not found.");
            return -1;
        }

        // Calculate the difference in weeks between current date and start date
        long differenceInMillis = currentDate.getTime() - startDate.getTime();
        long differenceInWeeks = differenceInMillis / (1000 * 60 * 60 * 24 * 7);
        return (int) differenceInWeeks + 1;
    }

    String fetchLectureContentForWeek(String className, int currentWeek) throws SQLException {
        String lectureContent = "";
        try {
            String query = Query.FOR_LECTURE_CLASS_LECTURE;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, className);
            preparedStatement.setInt(2, currentWeek);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                lectureContent = rs.getString("lecture_content");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return lectureContent;
    }
    JPanel createClassContentPanel(String classContent) {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setBackground(Color.WHITE);

        // Create a bordered label for class content
        JLabel classContentLabel = createBorderedLabel(classContent);
        classContentLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add the bordered label to the content panel
        contentPanel.add(classContentLabel, BorderLayout.CENTER);

        return contentPanel;
    }

    JLabel createBorderedLabel(String text) {
        JLabel borderedLabel = new JLabel(text) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(255, 255, 255, 100)); // Transparent white color
                g2d.fillRect(0, 0, getWidth(), getHeight()); // Fill the entire label with a transparent rectangle
                g2d.setColor(new Color(133, 91, 221));
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // Draw a border around the label
                g2d.dispose();
            }
        };
        borderedLabel.setOpaque(false); // Make the label transparent
        borderedLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align text
        return borderedLabel;
    }
    
}