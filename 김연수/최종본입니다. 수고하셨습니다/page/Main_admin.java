package page;
import config.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import java.awt.event.*;
import java.sql.Connection;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import Announcement.*;
import Attendance.Attendance_admin;
import Counseling.Counseling_admin;
import Home.Home_admin;
import Chatting.*;
import Profile.Profile_admin;
import ProgramMgr.*;
import Clean.*;
import lecture.*;


public class Main_admin extends JFrame {

    private JPanel currentPanel; // 현재 보여지고 있는 패널을 추적하기 위한 변수
    private Connection connection;
    public String adminname;
    private String userID;
 


    
    
    public Main_admin(Connection connection, String userID) {
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
        ImageIcon icon = new ImageIcon(Main_admin.class.getResource("due.PNG"));
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
        
        String[] buttonNames = {"홈", "강의 정보", "출결 관리", "상담 신청", "채팅창", "프로필 수정", "청소 당번", "공지 사항", "출석 마감"};

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
                
                // 해당 버튼에 따른 패널 표시 로직
                if (index == buttonNames.length - 1) {
                    // 출석 마감 버튼일 경우의 동작 설정
                	
                    int result = JOptionPane.showConfirmDialog(null, "출석을 마감하시겠습니까?", "출석 마감", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        String adminname = UserInfo.getUserID();
                        String className = AttendanceManager.getClassNameByUsername(adminname);
                        AbsenceManager.markAbsence(adminname); // 결석 처리 메서드 호출
                        JOptionPane.showMessageDialog(null, "출석이 마감되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else if (index == buttonNames.length - 2) {   
                    // 공지 사항 버튼일 경우의 동작 설정
                    showPanel(new Announcement_admin(), layeredPane);
                } else if (index == buttonNames.length - 3) {
                    // 청소 당번 버튼일 경우의 동작 설정
                    showPanel(new Clean_admin (), layeredPane);  
                } else if (index == buttonNames.length - 4) {
                    // 프로필 수정 버튼일 경우의 동작 설정
                    showPanel(new Profile_admin(), layeredPane);    
                } else if (index == buttonNames.length - 5) {
                    // 채팅창 버튼일 경우의 동작 설정
                    showPanel(new ChattingClient(), layeredPane);  
                } else if (index == buttonNames.length - 6) {
                    // 상담 신청 버튼일 경우의 동작 설정
                    showPanel(new Counseling_admin(), layeredPane);  
                } else if (index == buttonNames.length - 7) {
                    // 출결 관리 버튼일 경우의 동작 설정
                    showPanel(new Attendance_admin(), layeredPane);
                } else if (index == buttonNames.length - 8) {
                	showPanel(new Admin_lecture_Panel(connection, userID), layeredPane);
                	button.addActionListener(e2 ->  showPanel(new Admin_lecture_Panel(connection, userID), layeredPane));
                } else if (index == buttonNames.length - 9) {
                    // 홈 버튼일 경우의 동작 설정
                    showPanel(new Home_admin(), layeredPane);
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
            new Main_admin(connection, "사용자ID");
        });
    }
}