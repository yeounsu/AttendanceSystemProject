package Chatting;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChattingServer {
    private List<PrintWriter> clientOutputStreams;

    public ChattingServer() {
        clientOutputStreams = new ArrayList<>();
    }

    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("서버가 시작되었습니다. 포트 번호: " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("클라이언트가 연결되었습니다. 주소: " + clientSocket);

                // 클라이언트와 통신하는 쓰레드 시작
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // 모든 클라이언트에게 메시지 전송
    private synchronized void broadcast(String message) {
        for (PrintWriter writer : clientOutputStreams) {
            writer.println(message);
            writer.flush();
        }
    }

    // 클라이언트 핸들러 클래스
    class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader reader;
        private PrintWriter writer;

        public ClientHandler(Socket socket) {
            try {
                clientSocket = socket;
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer); // 현재 클라이언트의 출력 스트림을 리스트에 추가

                // 클라이언트에게 환영 메시지 전송
                String welcomeMessage = "[" + socket.getInetAddress().getHostName() + "]: 님 환영합니다!";
                broadcast(welcomeMessage);

                // 새로운 클라이언트가 입장한 것을 모든 클라이언트에게 알림
                String entranceMessage = "[" + socket.getInetAddress().getHostName() + "]: 입장하셨습니다.";
                broadcast(entranceMessage);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("클라이언트로부터 수신: " + message);
                    broadcast(message); // 받은 메시지를 모든 클라이언트에게 전송
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                // 클라이언트 연결 종료 시 처리
                if (writer != null) {
                    clientOutputStreams.remove(writer); // 출력 스트림 제거
                }
                try {
                    clientSocket.close(); // 소켓 닫기
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ChattingServer server = new ChattingServer();
        server.start(5050); // 포트 번호는 필요에 따라 변경 가능
    }
}