package lecture;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Admin_lecture_Panel extends JPanel {
    private Connection connection;
    private String loggedInUserID;
    private Admin_lecture_Mgr lectureMgr;

    private JLabel classLabel;
    private JLabel contentLabel;
    private JPanel contentPanel;

    public Admin_lecture_Panel(Connection connection, String loggedInUserID) {
        this.connection = connection;
        this.loggedInUserID = loggedInUserID;
        this.lectureMgr = new Admin_lecture_Mgr(connection, loggedInUserID);
        initializeUI();
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
        borderedLabel.setFont(new Font("Inter", Font.PLAIN, 18)); // Set font size and style
        borderedLabel.setVerticalAlignment(SwingConstants.CENTER);
        borderedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        borderedLabel.setForeground(Color.black); // Set font color
        //borderedLabel.setBackground(Color.blue);
        return borderedLabel;
    }
    private void updateLectureListPanel() {
        try {
            List<String[]> lectureContentList = lectureMgr.fetchLectureContentForUser(loggedInUserID);
            contentPanel.removeAll(); // Clear existing content
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
            
            contentPanel.revalidate(); 
            contentPanel.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.Y_AXIS));
        upperPanel.setBackground(Color.WHITE);

        String className = lectureMgr.retrieveClassNameForStudent(loggedInUserID);
        String classContent = null;
        try {
            classContent = lectureMgr.fetchClassContent(className);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        classLabel = new JLabel("오늘 " + className + " 학생들 에게 알립니다!");
        classLabel.setFont(new Font("Inter", Font.CENTER_BASELINE, 24));
        classLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentLabel = createBorderedLabel(classContent);
        contentLabel.setBorder(BorderFactory.createEmptyBorder(50, 180, 50, 200));
        contentLabel.setFont(new Font("Inter", Font.PLAIN, 18));
        contentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        upperPanel.add(classLabel);
        upperPanel.add(Box.createVerticalStrut(10));
        upperPanel.add(contentLabel);

        JButton upperButton = new JButton("학생들 에게 보내기");
        upperButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        upperButton.setPreferredSize(new Dimension(275, 30));
        upperButton.setBackground(Color.WHITE);
        upperButton.setFont(new Font("Inter", Font.PLAIN, 15));
        upperButton.setBorderPainted(true);

        upperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame editFrame = new JFrame("내용 변경하기");
                editFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                editFrame.setSize(400, 300);
                editFrame.setLocationRelativeTo(null); 
                editFrame.setLayout(new BorderLayout());

                JTextArea editTextArea = new JTextArea();
                editTextArea.setLineWrap(true);
                editTextArea.setWrapStyleWord(true);
                editTextArea.setText(contentLabel.getText()); 
                editFrame.add(new JScrollPane(editTextArea), BorderLayout.CENTER);

                JButton saveButton = new JButton("변경하기");
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String newContent = editTextArea.getText();
                        try {
                            lectureMgr.updateClassContent(className, newContent);
                            editFrame.dispose();
                            contentLabel.setText(newContent);
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(editFrame, "내용을 업데이트하는 도중 오류가 발생했습니다.");
                        }
                    }
                });
                saveButton.setBackground(Color.WHITE);
                saveButton.setFont(new Font("Inter", Font.PLAIN, 18));
                saveButton.setBorderPainted(true);
                editFrame.add(saveButton, BorderLayout.SOUTH);

                editFrame.setVisible(true);
            }
        });

        upperPanel.add(upperButton);

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

        contentPanel = new JPanel();
        //contentPanel.setBackground(new Color(231,222,248));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        for (String[] lectureContent : lectureContentList) {
            JLabel title = new JLabel(lectureContent[0]);
            title.setFont(new Font("Inter", Font.BOLD, 16));
            title.setOpaque(true); 
           // title.setBackground(new Color(231,222,248));
            title.setForeground(Color.BLACK); 
           
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
        
        
        
        
        JButton editContentButton = new JButton("강의 수정하기");
        editContentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                JFrame editContentFrame = new JFrame("Edit lecture content");
                editContentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                editContentFrame.setSize(1100, 900);
                editContentFrame.setLocationRelativeTo(null);
                editContentFrame.setLayout(new BorderLayout());

                // Create a panel to edit lecture content
                JPanel editContentPanel = new JPanel();
                editContentPanel.setBackground(new Color(231, 222, 248));
                editContentPanel.setLayout(new BoxLayout(editContentPanel, BoxLayout.Y_AXIS));

                try {
                    
                    int index = 1; 
                    for (Component component : contentPanel.getComponents()) {
                        if (component instanceof JScrollPane) {
                            JTextArea textArea = (JTextArea) ((JScrollPane) component).getViewport().getView();
                            JLabel weekLabel = new JLabel( index +"주차" + " lecture contents:"); // Update week label
                            JTextArea lectureContentArea = new JTextArea(textArea.getText());
                            lectureContentArea.setLineWrap(true);
                            lectureContentArea.setWrapStyleWord(true);
                            JScrollPane contentScrollPane = new JScrollPane(lectureContentArea);
                            contentScrollPane.setPreferredSize(new Dimension(200, 100));

                            editContentPanel.add(weekLabel);
                            editContentPanel.add(contentScrollPane);
                            index++;
                        }
                    }
                } catch (Exception ex) { 
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "An error occurred while retrieving lecture information.");
                    return;
                }

                
                JButton saveButton = new JButton("저장하기");
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            
                            int index = 1; 
                            for (Component component : editContentPanel.getComponents()) {
                                if (component instanceof JScrollPane) {
                                    JTextArea textArea = (JTextArea) ((JScrollPane) component).getViewport().getView();
                                    String lectureTitle = index + "주차"; // Update lecture title
                                    String lectureContent = textArea.getText();
                                    lectureMgr.updateLectureContent(lectureTitle, lectureContent, className);
                                    index++;
                                }
                            }

                            JOptionPane.showMessageDialog(null, "강의 내용이 업데이트되었습니다.");
                            updateLectureListPanel();
                            editContentFrame.dispose();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "강의 내용을 업데이트하는 도중 오류가 발생했습니다.");
                        }
                    }
                });

                
                saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                saveButton.setPreferredSize(new Dimension(900, 50));
                saveButton.setBackground(Color.WHITE);
                saveButton.setFont(new Font("Inter", Font.PLAIN, 18));
                saveButton.setBorderPainted(true);
                editContentPanel.add(saveButton);

               
                JScrollPane scrollPane = new JScrollPane(editContentPanel);
                editContentFrame.add(scrollPane, BorderLayout.CENTER);
                editContentFrame.setVisible(true);
                
            }
        });

        JButton registerContentButton = new JButton("강의 등록하기");
        registerContentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame registrationFrame = new JFrame("강의 등록");
                registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                registrationFrame.setSize(1100, 900);

                registrationFrame.setLocationRelativeTo(null); // Center the frame on the screen
                registrationFrame.setLayout(new BorderLayout());

                JPanel registrationPanel = new JPanel();
                registrationPanel.setBackground(new Color(231,222,248));
                registrationPanel.setLayout(new BoxLayout(registrationPanel, BoxLayout.Y_AXIS));

                try {
                    Date startDate = lectureMgr.getClassStartDate(className);
                    Date endDate = lectureMgr.getClassEndDate(className);
                    int numOfWeeks = calculateWeeks(startDate, endDate);

                    for (int i = 1; i <= numOfWeeks; i++) {
                        JLabel weekLabel = new JLabel("주차 " + i + " 강의 내용:");
                        JTextArea lectureContentArea = new JTextArea();
                        lectureContentArea.setLineWrap(true);
                        lectureContentArea.setWrapStyleWord(true);
                        JScrollPane contentScrollPane = new JScrollPane(lectureContentArea);
                        contentScrollPane.setPreferredSize(new Dimension(200, 100));

                        registrationPanel.add(weekLabel);
                        registrationPanel.add(contentScrollPane);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "주차 정보를 불러오는 도중 오류가 발생했습니다.");
                    registrationFrame.dispose();
                    return;
                }

                JButton saveButton = new JButton("저장하기");
                saveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Date startDate = lectureMgr.getClassStartDate(className);
                            Date endDate = lectureMgr.getClassEndDate(className);
                            int numOfWeeks = calculateWeeks(startDate, endDate);

                            for (int i = 1; i <= numOfWeeks; i++) {
                                Component[] components = registrationPanel.getComponents();
                                JTextArea contentArea = (JTextArea) ((JScrollPane) components[i * 2 - 1]).getViewport().getView();
                                String lectureTitle = i + "주차";
                                String lectureContent = contentArea.getText();
                                lectureMgr.insertLecture(lectureTitle, lectureContent, className);
                            }

                            JOptionPane.showMessageDialog(null, "강의가 등록되었습니다.");
                            updateLectureListPanel(); // Update lecture list panel after registration
                            registrationFrame.dispose();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "강의 등록 중 오류가 발생했습니다.");
                        }

                    }

                    private int calculateWeeks(Date startDate, Date endDate) {
                        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
                        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
                        return (int) (diffInDays / 7) + 1; // Add 1 to round up if necessary
                    }
                });

                saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                saveButton.setPreferredSize(new Dimension(900, 50));
                saveButton.setBackground(Color.WHITE);
                saveButton.setFont(new Font("Inter", Font.PLAIN, 18));
                saveButton.setBorderPainted(true);

                registrationPanel.add(saveButton);

                JScrollPane scrollPane = new JScrollPane(registrationPanel);
                registrationFrame.add(scrollPane, BorderLayout.CENTER);
                registrationFrame.setVisible(true);
            }

            private int calculateWeeks(Date startDate, Date endDate) {
                long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
                long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
                return (int) (diffInDays / 7) + 1; // Add 1 to round up if necessary

            }
        });
        registerContentButton.setPreferredSize(new Dimension(300, 30));
        registerContentButton.setBackground(Color.WHITE);
        registerContentButton.setFont(new Font("Inter", Font.PLAIN, 18));
        registerContentButton.setBorderPainted(true);
        
        editContentButton.setPreferredSize(new Dimension(300, 30));
        editContentButton.setBackground(Color.WHITE);
        editContentButton.setFont(new Font("Inter", Font.PLAIN, 18));
        editContentButton.setBorderPainted(true);

       
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerContentButton, BorderLayout.WEST); 
        buttonPanel.add(editContentButton, BorderLayout.EAST); 

        downPanel.add(buttonPanel, BorderLayout.SOUTH); 

        add(downPanel, BorderLayout.CENTER);

        
    
}
}