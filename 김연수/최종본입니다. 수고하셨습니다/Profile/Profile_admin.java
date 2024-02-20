package Profile;

import config.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class Profile_admin extends JPanel{

   private final List<String> CLASS_NAMES = Class_Name.CLASS_NAMES;
   private DefaultTableModel model;
   private JScrollPane scrollPane;
   private Connection connection;
   String username = UserInfo.getUserID();


   public Profile_admin() {
      setPreferredSize(new Dimension(800, 600));
      setBackground(Color.WHITE);
      setLayout(new BorderLayout());

      JPanel mainPanel = new JPanel(new BorderLayout()); // 메인 패널 BorderLayout으로 생성
      mainPanel.setPreferredSize(new Dimension(500, 300)); // 메인 패널의 크기 설정
      mainPanel.setBackground(Color.WHITE);

      String[] columns = { "아이디", "전공", "이름", "성별" }; // 테이블 열 지정
      model = new DefaultTableModel(columns, 0); // DefaultTableModel 객체생성 model은
      JTable table = new JTable(model); // model으로 JTable 생성
      table.setRowHeight(60);
      table.setPreferredScrollableViewportSize(new Dimension(1100, 720));
      Font font = new Font("Inter", Font.BOLD, 18);
      table.setFont(font);
      table.setBackground(Color.WHITE);
      table.setDefaultEditor(Object.class, null);

      TableColumnModel columnModel = table.getColumnModel();
      columnModel.getColumn(0).setPreferredWidth(100); // 순번 열의 너비를 100으로 설정
      columnModel.getColumn(1).setPreferredWidth(500); // 게시글 열의 너비를 500으로 설정
      columnModel.getColumn(2).setPreferredWidth(200); // 작성자 열의 너비를 200으로 설정
      columnModel.getColumn(3).setPreferredWidth(200); // 작성일자 열의 너비를 200으로 설정
      table.setRowHeight(30); // 행의 높이를 30으로 설정
      DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
      centerRenderer.setHorizontalAlignment(JLabel.CENTER);

      for (int i = 0; i < table.getColumnCount(); i++) {
         table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
      }

      for (int i = 0; i < table.getColumnCount(); i++) {
         table.getColumnModel().getColumn(i).setHeaderRenderer(new MyHeaderRenderer(font));
      }
      
      JTableHeader header = table.getTableHeader();
      header.setBackground(new Color(231, 222, 248)); // 헤더의 배경색을 흰색으로 설정
      header.setForeground(Color.BLACK); // 헤더의 글자색을 검은색으로 설정
      header.setFont(new Font("Inter", Font.BOLD, 24)); // 헤더의 폰트를 Inter, 평범체, 크기 24로 설정

      scrollPane = new JScrollPane(table); // 스크롤 가능하게 scrollpane추가
      mainPanel.add(scrollPane, BorderLayout.CENTER); // 패널을 중앙에 추가

      CustomButton deleteButton = new CustomButton("삭제");
      deleteButton.setPreferredSize(new Dimension(90, 40)); // 크기 지정
      deleteButton.setBackground(new Color(231, 222, 248));
      deleteButton.setFont(font);
      deleteButton.addActionListener(e -> {
         int selectedRow = table.getSelectedRow(); // 행 값 가져오기
         if (selectedRow != -1) { // 만약 행이 선택되어있다면
            String user_id = (String) model.getValueAt(selectedRow, 0); // 선택된 행의 user_id 가져와서
            deleteFromDatabase(user_id); // 데이터베이스에서 삭제
            model.removeRow(selectedRow); // 테이블에서 삭제
         }
      });

      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
      buttonPanel.add(deleteButton);
      buttonPanel.setBackground(Color.WHITE);
      add(buttonPanel, BorderLayout.SOUTH);
      

      add(mainPanel, BorderLayout.CENTER); // 메인 패널추가
      // Data(); //데이터 가져오는 메서드

      
      try {
          connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root", "1234");

          // username에 해당하는 사용자의 class_name 가져오기
          String query = Query.SELECT_MYCLASS_TABLE;
          PreparedStatement statement = connection.prepareStatement(query);
          statement.setString(1, username);
          ResultSet rs = statement.executeQuery();

          if (rs.next()) {
              String className = rs.getString("class_name");

              // className과 동일한 class_name을 가진 다른 사용자들의 정보 가져오기
              String query2 = Query.SELECT_STU_TABLE;
              PreparedStatement statement2 = connection.prepareStatement(query2);
              statement2.setString(1, className);
              statement2.setString(2, username);
              ResultSet rs2 = statement2.executeQuery();

              model.setRowCount(0);
              while (rs2.next()) {
                  String user_id = rs2.getString("user_id");
                  String user_major = rs2.getString("user_major");
                  String user_name = rs2.getString("user_name");
                  String user_gender = rs2.getString("user_gender");
                  model.addRow(new Object[]{user_id, user_major, user_name, user_gender});
              }

              if (model.getRowCount() == 0) {
                  JOptionPane.showMessageDialog(null, "해당하는 사용자가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
              }

              rs2.close();
              statement2.close();
          }

          rs.close();
          statement.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }
      
      
      
      
      
      
      
      
      
      
      
   }

   private void deleteFromDatabase(String user_id) {
      try {
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/attendancesystem", "root",
               "1234"); // QueryExecutor 클래스의 getConnection() 메서드로 Connection을 가져옴
         if (conn != null) {
            String query = "DELETE FROM user WHERE user_id = '" + user_id + "'";
            System.out.println(query); // 콘솔에 쿼리 출력
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            // conn.commit(); // 변경 내용을 데이터베이스에 반영
            JOptionPane.showMessageDialog(null, "삭제완료", "알림", JOptionPane.INFORMATION_MESSAGE);
            statement.close();
         } else {
            System.out.println("Connection is null");
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }


   public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
         // Swing에서 GUI를 생성하고 변경하는 작업을 안전하게 처리하기 위해 사용
         JFrame frame = new JFrame("AdminProfile Panel");
         // Adminprofile Panel이라는이름의 프레임 생성
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         // 프레임 닫을때 종료
         frame.add(new Profile_admin());
         // AdminProfilePane라는 이름의 객체(Panel)를 생성
         frame.pack();
         // 프레임을 내부 크기에 맞게 자동조정
         frame.setLocationRelativeTo(null);
         // 프레임을 화면 중앙에 배치
         frame.setVisible(true);
         // 프레임을 화면에 표시
      });
   }
}