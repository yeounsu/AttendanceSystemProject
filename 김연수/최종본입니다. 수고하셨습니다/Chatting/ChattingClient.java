package Chatting;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChattingClient extends JPanel implements ActionListener {
    JButton bt4;
    JTextField tf3, nicknameField; // nicknameField 추가
    JTextArea area, area2;
    Socket sock;
    BufferedReader in;
    PrintWriter out;
    boolean flag = false;
    String host = "127.0.0.1"; // 서버의 IP 주소
    int port = 5050; // 포트 번호
    String nickname; // 닉네임 저장 변수 추가

    public ChattingClient() {
        tf3 = new JTextField(30);

        // 대화 내용 출력을 위한 TextArea 설정
        area = new JTextArea(); // 입력칸
        area.setBorder(new TitledBorder("채팅창"));
        area.setLineWrap(true); // TextArea 가로길이를 벗어나는 text발생시 자동 줄바꿈
        area2 = new JTextArea();
        area2.setLineWrap(true);

        tf3.setBorder(new TitledBorder("Message 입력"));

        bt4 = new JButton("보내기");

        JPanel p = new JPanel(null);
        p.setBackground(new Color(231, 222, 248));
        p.add(tf3);
        p.add(area2);
        p.add(area);
        p.add(bt4);

        area.setBounds(30, 30, 1040, 640);
//        list.setBounds(940, 30, 130, 640);

        tf3.setBounds(30, 685, 880, 65);
        bt4.setBounds(940, 720, 130, 30); // 730 -> 760

        tf3.addActionListener(this);
        bt4.addActionListener(this);

        p.setPreferredSize(new Dimension(1100, 800)); // 패널 크기 조정

        // 패널을 프레임에 추가하고 프레임 설정
        add(p);

        // 서버에 연결
        connectToServer();
    }

    // 서버로부터 환영 메시지를 받아서 TextArea에 표시하는 메서드
    private void displayWelcomeMessage(String message) {
        area.append(message + "\n");
    }

    // 서버로부터 환영 메시지를 받기 위한 내부 클래스
    class WelcomeReader implements Runnable {
        public void run() {
            try {
                // 클라이언트가 서버에 연결되면 닉네임과 함께 환영 메시지를 받음
                String welcomeMessage = in.readLine();
                displayWelcomeMessage(welcomeMessage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // 서버에 연결하는 메서드
    private void connectToServer() {
        try {
            sock = new Socket(host, port);
            out = new PrintWriter(sock.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            // 서버로부터 오는 메시지를 처리할 쓰레드 시작
            new Thread(new IncomingReader()).start();

            // 닉네임 입력 다이얼로그 표시
            getNickname();

            // 서버로부터 환영 메시지를 받기 위한 쓰레드 시작
            new Thread(new WelcomeReader()).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // 닉네임 입력 다이얼로그 표시 메서드
    private void getNickname() {
        nickname = JOptionPane.showInputDialog(this, "닉네임을 입력하세요:", "닉네임 설정", JOptionPane.PLAIN_MESSAGE);
        if (nickname == null || nickname.trim().isEmpty()) {
            // 닉네임이 입력되지 않았을 경우 기본값으로 설정
            nickname = "Guest";
        }
        out.println("[" + nickname + "]: 입장하셨습니다.");
    }

    // 서버로부터 오는 메시지를 처리하는 내부 클래스
    class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    // 서버로부터 받은 메시지를 TextArea에 표시
                    area.append(message + "\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // 사용자가 입력한 메시지를 서버로 전송하는 메서드
    private void sendMessage() {
        try {
            String message = tf3.getText();
            out.println("[" + nickname + "]: " + message); // 서버로 메시지 전송 (닉네임과 함께 전송)
            tf3.setText(""); // 입력창 비우기
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt4 || e.getSource() == tf3) {
            // '보내기' 버튼이나 엔터키가 눌리면 메시지 전송
            sendMessage();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chatting Program");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new ChattingClient());
            frame.pack();
            frame.setLocationRelativeTo(null); // 화면을 중앙에 배치
            frame.setVisible(true);
        });
    }
}