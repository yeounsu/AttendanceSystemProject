package page;
import config.*;
import lecture.User_lecture_Panel;

import javax.swing.*;
import java.sql.Connection;
import java.awt.*;
import Announcement.*;
import Chatting.ChattingClient;
import Clean.Clean_admin;
import Counseling.Counseling_user;
import Home.Home_user;
import Profile.Profile_user;
import ProgramMgr.*;


public class Main_user extends JFrame {

    private JPanel currentPanel; // 현재 보여지고 있는 패널을 추적하기 위한 변수
    private String userID;
    public String username;
    private Connection connection;
    private String className;

    // 생성자를 통해 Connection 객체와 userID를 전달 받도록 수정
    public Main_user(Connection connection, String userID) {
        this.connection = connection; // 생성자에서 Connection 객체를 전달받음
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

        JLabel toplabel = new JLabel("     동의대 학생 교육원 프로그램");
        toplabel.setForeground(Color.white);
        toplabel.setFont(new Font("Inter", Font.BOLD, 30));
        topPanel.add(toplabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 50));
        buttonPanel.setOpaque(false);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        JButton button1 = new JButton("로그아웃");
        button1.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                dispose(); // 현재 프레임(창) 닫기
                new Login().setVisible(true); // 로그인 화면으로 이동
            }
        });

        buttonPanel.add(button1);
        button1.setBackground(Color.WHITE);
        button1.setFont(new Font("Inter", Font.PLAIN, 18));
        button1.setBorderPainted(false);
        
        JLabel imgLabel = new JLabel();
        ImageIcon icon = new ImageIcon(Main_user.class.getResource("due.PNG"));
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

        String[] buttonNames = {"홈", "강의 정보", "출결 관리", "상담 신청", "채팅창", "프로필 수정", "청소 당번", "공지 사항", "퇴실 등록"};

        for (int i = 0; i < buttonNames.length; i++) {
            final int index = i; // final로 선언된 변수
            JButton button = CustomButton.createButton(buttonNames[i], Color.WHITE, Color.BLACK, null); // 기본값 설정
            int xCoordinate = 30;
            int yCoordinate = initialY + (height + 0) * i;
            button.setBounds(xCoordinate, yCoordinate, width, height);
            layeredPane.add(button, JLayeredPane.PALETTE_LAYER);

            button.addActionListener(e -> {
                // 모든 버튼의 배경색을 초기화
                for (Component comp : layeredPane.getComponents()) {
                    if (comp instanceof JButton) {
                        ((JButton) comp).setBackground(Color.WHITE);
                    }
                }
                // 현재 눌린 버튼의 배경색 변경
                button.setBackground(new Color(133, 91, 221));
                if (index == buttonNames.length - 1) {
                    // 출석 마감 버튼일 경우의 동작 설정
                    int result = JOptionPane.showConfirmDialog(null, "퇴실을 등록하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        // 퇴근 시간 추가
                        username = UserInfo.getUserID();
                        // 퇴실 등록
                        AttendanceManager.markDeparture(username);
                        // 퇴실 등록
                        className = AttendanceManager.getClassNameByUsername(username);
                        AttendanceManager.updateAttendanceStatus(username, className);

                        JOptionPane.showMessageDialog(null, "퇴실이 등록되었습니다."); // 퇴실 등록 메시지 표시
                    }
                
                } else if (index == buttonNames.length - 2) {
                    // 공지 사항 버튼일 경우의 동작 설정
                    showPanel(new Announcement_stu(), layeredPane);
                } else if (index == buttonNames.length - 3) {
                    // 청소 당번 버튼일 경우의 동작 설정
                    showPanel(new Clean_admin(), layeredPane);
                } else if (index == buttonNames.length - 4) {
                    // 프로필 수정 버튼일 경우의 동작 설정
                    showPanel(new Profile_user(), layeredPane);
                } else if (index == buttonNames.length - 5) {
                    // 채팅창 버튼일 경우의 동작 설정
                    showPanel(new ChattingClient(), layeredPane);
                } else if (index == buttonNames.length - 6) {
                    // 상담 신청 버튼일 경우의 동작 설정
                    showPanel(new Counseling_user(), layeredPane);
                } else if (index == buttonNames.length - 7) {
                    // 출결 관리 버튼일 경우의 동작 설정
                    button.addActionListener(ev -> {
                        int result = JOptionPane.showConfirmDialog(null, "권한이 없습니다.", "알림", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
                    });
                } else if (index == buttonNames.length - 8) {
                	showPanel(new User_lecture_Panel(connection, userID), layeredPane);
                	  button.addActionListener(e2 ->  showPanel(new User_lecture_Panel(connection, userID), layeredPane));
                } else if (index == buttonNames.length - 9) {
                    // 홈 버튼일 경우의 동작 설정
                	 showPanel(new Home_user(connection, userID), layeredPane);
                }

            });
        }
    }

    // 새로운 패널을 보여주는 메서드
    private void showPanel(JPanel panel, JLayeredPane layeredPane) {
        // 현재 보여지고 있는 패널을 숨김
        if (currentPanel != null) {
            currentPanel.setVisible(false);
        }
        // 새로운 패널을 추가하고 보여줌
        currentPanel = panel;
        panel.setBounds(300, 130, 1100, 770); // 패널의 위치와 크기 지정
        layeredPane.add(panel, JLayeredPane.MODAL_LAYER);
        panel.setBackground(Color.WHITE);
        layeredPane.setBackground(Color.WHITE);
        panel.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	 Connection connection = MysqlConnection.getConnection(); // 적절한 방식으로 Connection 객체를 가져옴
             new Main_user(connection, "사용자ID");
        });
    }
}