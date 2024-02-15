package attendancesystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Admin_lecture_Panel extends JPanel {
    private static Connection connection;
    private static String loggedInUserID;
    private Admin_lecture_Mgr lectureMgr;

    public Admin_lecture_Panel(Connection connection, String loggedInUserID) {
        this.connection = connection;
        this.loggedInUserID = loggedInUserID;
        this.lectureMgr = new Admin_lecture_Mgr(connection, loggedInUserID);
        initializeUI();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());

        // Create the upper panel for displaying lecture content
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS)); // Set BoxLayout with Y_AXIS alignment
        upperPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        upperPanel.setBackground(Color.WHITE);

        // Fetch lecture content and current week from Admin_lecture_Mgr
        int currentWeek = lectureMgr.determineCurrentWeek(loggedInUserID);
        String className = lectureMgr.retrieveClassNameForStudent(loggedInUserID);
        String lectureContent = null;
        try {
            lectureContent = lectureMgr.fetchLectureContentForWeek(className, currentWeek);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create labels to display lecture content
        JLabel weekLabel = new JLabel("Week " + currentWeek);
        JLabel contentLabel = new JLabel(lectureContent);

        // Set alignment for the labels to center
        weekLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add both labels to the upper panel
        upperPanel.add(weekLabel);
        upperPanel.add(contentLabel);
        Dimension preferredSize = new Dimension(400, 200); // Set your desired dimensions
        upperPanel.setPreferredSize(preferredSize);

        // Add the upper panel to the main panel
        add(upperPanel, BorderLayout.NORTH);

        // Create the panel for class content and add it below the upper panel
        JPanel classContentPanel = lectureMgr.createClassContentPanel(lectureContent);
        classContentPanel.setPreferredSize(preferredSize);
        
        add(classContentPanel, BorderLayout.CENTER);
    }
}