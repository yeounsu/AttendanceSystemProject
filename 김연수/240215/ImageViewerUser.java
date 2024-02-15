package attendancesystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class ImageViewerUser extends JFrame {

    private JPanel currentPanel; // ���� �������� �ִ� �г��� �����ϱ� ���� ����
    private Connection connection; // Connection ��ü ����
    private String userID;

    public ImageViewerUser(Connection connection, String userID) {
        this.connection = connection; // �����ڿ��� Connection ��ü�� ���޹���
        this.userID = userID;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Attendance_System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 1024);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);
        JLayeredPane layeredPane = new JLayeredPane();
        getContentPane().add(layeredPane);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(133, 91, 221));
        topPanel.setPreferredSize(new Dimension(770, 200));
        topPanel.setLayout(new BorderLayout());
        layeredPane.add(topPanel, JLayeredPane.DEFAULT_LAYER);
        topPanel.setBounds(300, 0, 1100, 130);

        JLabel toplabel = new JLabel("     ���Ǵ� �л� ������ ���α׷�");
        toplabel.setForeground(Color.white);
        toplabel.setFont(new Font("Inter", Font.BOLD, 30));
        topPanel.add(toplabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 50));
        buttonPanel.setOpaque(false);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        JButton button1 = new JButton("�α׾ƿ�");
        JButton button2 = new JButton("����ȭ");
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        button1.setBackground(Color.WHITE);
        button1.setFont(new Font("Inter", Font.PLAIN, 18));
        button1.setBorderPainted(false);
        button2.setBackground(Color.WHITE);
        button2.setFont(new Font("Inter", Font.PLAIN, 18));
        button2.setBorderPainted(false);

        JLabel imgLabel = new JLabel();
        ImageIcon icon = new ImageIcon(ImageViewerUser.class.getResource("due.PNG"));
        Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        ImageIcon updateIcon = new ImageIcon(img);
        imgLabel.setIcon(updateIcon);
        imgLabel.setBounds(85, 10, 150, 150);
        layeredPane.add(imgLabel, JLayeredPane.PALETTE_LAYER);

        addButtons(layeredPane, imgLabel.getY() + imgLabel.getHeight() + 20);

        setVisible(true);
    }

    private void addButtons(JLayeredPane layeredPane, int initialY) {
        int width = 280;
        int height = 80;

        String[] buttonNames = {"Ȩ", "���� ����", "��� ����", "��� ��û", "ä��â", "������ ����", "û�� ���", "���� ����", "���"};

        for (int i = 0; i < buttonNames.length; i++) {
            JButton button = new JButton(buttonNames[i]);
            int xCoordinate = 30;
            int yCoordinate = initialY + (height + 0) * i;
            button.setBounds(xCoordinate, yCoordinate, width, height);
            layeredPane.add(button, JLayeredPane.PALETTE_LAYER);

            if (i == buttonNames.length - 1) {
                button.setBackground(new Color(133, 91, 221));
                button.setFont(new Font("Inter", Font.PLAIN, 18));
                button.setForeground(Color.WHITE);
                button.setBorderPainted(false);
            } else if (i == buttonNames.length - 9) {
                button.setBackground(new Color(255, 255, 255));
                button.setFont(new Font("Inter", Font.PLAIN, 18));
                button.setForeground(Color.black);
                button.setBorderPainted(false);

                button.addActionListener(e -> {
                    showPanel(new UserStaticPanel(connection, userID), layeredPane);
                });
            } else if (i == buttonNames.length - 8) {
            	button.setBackground(new Color(255, 255, 255));
            	button.setFont(new Font("Inter", Font.PLAIN, 18));
            	button.setForeground(Color.black);
            	button.setBorderPainted(false);
            	
            	  button.addActionListener(e ->  showPanel(new Admin_lecture_Panel(connection, userID), layeredPane));
            	

            } else if (i == buttonNames.length - 6) {
                button.setBackground(new Color(255, 255, 255));
                button.setFont(new Font("Inter", Font.PLAIN, 18));
                button.setForeground(Color.black);
                button.setBorderPainted(false);

                //button.addActionListener(e -> showPanel(new CounselingPanel(), layeredPane)); // CounselingPanel �����ִ� ����

            } else if (i == buttonNames.length - 4) {
                button.setBackground(new Color(255, 255, 255));
                button.setFont(new Font("Inter", Font.PLAIN, 18));
                button.setForeground(Color.black);
                button.setBorderPainted(false);

                //button.addActionListener(e -> showPanel(new ProfilePanel(), layeredPane)); // CounselingPanel �����ִ� ����
            } else if (i == buttonNames.length - 3) {
                button.setBackground(new Color(255, 255, 255));
                button.setFont(new Font("Inter", Font.PLAIN, 18));
                button.setForeground(Color.black);
                button.setBorderPainted(false);

                //button.addActionListener(e -> showPanel(new CleanPanel(), layeredPane)); // CleanPanel �����ִ� ����
            } else {
                button.setBackground(Color.WHITE);
                button.setFont(new Font("Inter", Font.PLAIN, 18));
                button.setBorderPainted(false);
            }
        }
    }


    // ���ο� �г��� �����ִ� �޼���
    private void showPanel(JPanel panel, JLayeredPane layeredPane) {
        // ���� �������� �ִ� �г��� ����
        if (currentPanel != null) {
            currentPanel.setVisible(false);
        }
        // ���ο� �г��� �߰��ϰ� ������
        currentPanel = panel;
        panel.setBounds(300, 130, 1100, 770); // �г��� ��ġ�� ũ�� ����
        layeredPane.add(panel, JLayeredPane.MODAL_LAYER);
        panel.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Connection connection = MysqlConnection.getConnection(); // ������ ������� Connection ��ü�� ������
            new ImageViewerUser(connection, "�����ID"); // Connection ��ü�� �����Ͽ� ImageViewerUser ��ü ����
        });
    }
}
