package lecture;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class User_lecture_Panel extends JPanel {
    private Connection connection;
    private String loggedInUserID;
    private User_lecture_Mgr lectureMgr;

    public User_lecture_Panel(Connection connection, String loggedInUserID) {
        this.connection = connection;
        this.loggedInUserID = loggedInUserID;
        this.lectureMgr = new User_lecture_Mgr(connection, loggedInUserID);
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS)); 
        upperPanel.setBackground(Color.WHITE);

        // Fetch class content and current week from Admin_lecture_Mgr
        String className = lectureMgr.retrieveClassNameForStudent(loggedInUserID);
        String classContent = null;
        try {
            classContent = lectureMgr.fetchClassContent(className);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create labels to display class content
        JLabel classLabel = new JLabel("오늘 " + className + " 학생들 에게 알립니다!");
        classLabel.setFont(new Font("Inter", Font.CENTER_BASELINE, 24)); 
        classLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        classLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, classLabel.getPreferredSize().height));

        JLabel contentLabel = createBorderedLabel(classContent);
        contentLabel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100)); 
        contentLabel.setFont(new Font("Inter", Font.PLAIN, 18)); 
        contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, contentLabel.getPreferredSize().height));

        
        JPanel classLabelPanel = new JPanel();
        classLabelPanel.add(classLabel);
        classLabelPanel.setBackground(Color.white);
        upperPanel.add(classLabelPanel);

        // Add vertical strut to create spacing between labels
        upperPanel.add(Box.createVerticalStrut(0)); 

        // Add contentLabel to upperPanel
        JPanel contentLabelPanel = new JPanel();
        contentLabelPanel.add(contentLabel);
        contentLabelPanel.setBackground(Color.white);
        upperPanel.add(contentLabelPanel);

        Dimension preferredSize = new Dimension(400, 200); 
        upperPanel.setPreferredSize(preferredSize);

        
        
        
        
        
        add(upperPanel, BorderLayout.NORTH);
        JPanel downPanel = new JPanel(new BorderLayout());
        downPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        downPanel.setBackground(Color.WHITE);

        List<String[]> lectureContentList = null;
        try {
            lectureContentList = lectureMgr.fetchLectureContentForUser(loggedInUserID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        for (String[] lectureContent : lectureContentList) {
            JLabel title = new JLabel(lectureContent[0]);
            title.setFont(new Font("Inter", Font.BOLD, 16));
            Dimension titleSize = title.getPreferredSize();
            titleSize.width = Integer.MAX_VALUE; 
            title.setMaximumSize(titleSize); 
            contentPanel.add(title);

            JTextArea content = new JTextArea(lectureContent[1]);
            content.setEditable(false);
            content.setLineWrap(true);
            content.setWrapStyleWord(true);
            content.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            JScrollPane contentScrollPane = new JScrollPane(content);
            contentScrollPane.setPreferredSize(new Dimension(300, 100)); 
            contentPanel.add(contentScrollPane);

            contentPanel.add(Box.createVerticalStrut(10)); 
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        downPanel.add(scrollPane, BorderLayout.CENTER);

        add(downPanel, BorderLayout.CENTER);
    }

    private JLabel createBorderedLabel(String text) {
        JLabel borderedLabel = new JLabel(text) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(133, 91, 221, 50)); 
                g2d.fillRoundRect(60, 0, getWidth() - 130, getHeight() - 1, 20, 20); 
                g2d.dispose();
            }
        };
        borderedLabel.setFont(new Font("Inter", Font.PLAIN, 18));
        borderedLabel.setVerticalAlignment(SwingConstants.CENTER);
        borderedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        borderedLabel.setForeground(Color.black);
        return borderedLabel;
    }
}