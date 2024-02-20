package Clean;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ProgramMgr.AttendanceManager;
import config.*;
public class CleanMgr extends JFrame implements ActionListener {

   private Connection connection;
   private int green = 91;
   private int blue = 221;
   private JTextField tf1, tf2, tf3;
   private JButton btn1, btn2;
   private Date startDate;
   private Date endDate;
   private int MIN_VALUE;
   private int MAX_VALUE;
   private int weekStartCurrentWeek;
   private String startDateStr;
   private String endDateStr;
   private ArrayList<String> allNames;
   private List<List<String>> weekNamesList;
   private List<List<String>> teams;
   private int currentWeek;
   private JPanel cleanPanel, topPanel, centerPanel;
   private JLabel dateLabel, userLabel;
   private int value;
   private String dateRangeText;
   private StringBuilder sb;
   private int groups;
   private int totalNames;
   
   private static String username;
   private static String className;

   
   public CleanMgr(Connection connection) {
      this.connection = connection;
      this.allNames = new ArrayList<>(); // 필드 초기화
      this.weekNamesList = new ArrayList<>(); // 필드 초기화
      this.weekNamesList.add(new ArrayList<>()); // 예시: 주차별 이름 리스트를 초기화
      this.teams = new ArrayList<>();
      this.weekStartCurrentWeek = 0; // 초기화 필요
   }

   public JPanel createCleanPanel(String className) {
	    try {
	        String sql = "SELECT class_startDate, class_endDate FROM attendancesystem.class WHERE class_name = ?";
	        
	        PreparedStatement statement = connection.prepareStatement(sql);
	        statement.setString(1, className); // Set the className parameter
	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            startDateStr = resultSet.getString("class_startDate");
	            endDateStr = resultSet.getString("class_endDate");
         }
         // 시작 날짜와 종료 날짜를 Date 객체로 변환
         startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
         endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);

         // 주차 수 계산
         long diff = endDate.getTime() - startDate.getTime();
         long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
         int weeks = (int) (days / 7) + 1;
         System.out.println(weeks);

         // MIN_VALUE 와 MAX_VALUE 설정
         MIN_VALUE = 1;
         MAX_VALUE = weeks;

         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

         // 주차에 따른 시작 날짜 계산
         Calendar startCalendar = Calendar.getInstance();
         startCalendar.setTime(startDate);

         // weekStartDate의 별도의 currentWeek 변수 사용
         startCalendar.add(Calendar.WEEK_OF_YEAR, weekStartCurrentWeek);
         startCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 주의 시작은 일요일
         Date weekStartDate = startCalendar.getTime(); // 계산된 주의 시작 날짜를 가져옴

         // 주차에 따른 종료 날짜 계산
         Calendar endCalendar = Calendar.getInstance();
         endCalendar.setTime(weekStartDate);
         endCalendar.add(Calendar.DAY_OF_WEEK, 6); // 주의 마지막은 토요일
         Date weekEndDate = endCalendar.getTime(); // 계산된 주의 종료 날짜를 가져옴

         // 날짜 출력
         String formattedStartDate = sdf.format(weekStartDate);
         String formattedEndDate = sdf.format(weekEndDate);

         // 시작 날짜부터 종료 날짜까지의 날짜를 텍스트 형식으로 출력
         dateRangeText = "[   " + formattedStartDate + "(일)    ~    " + formattedEndDate + "(토)   ]";

      } catch (Exception e) {
         e.printStackTrace();
      }
      try {
          String adminname = UserInfo.getUserID();
          className = AttendanceManager.getClassNameByUsername(adminname);

          String sql = "SELECT user_id, user_name FROM user WHERE user_idValue = ? AND class_name = ?";
     PreparedStatement statement = connection.prepareStatement(sql);
     statement.setString(1, "0"); // Set the className parameter
     statement.setString(2, className); // Set the className parameter
     
     ResultSet resultSet1 = statement.executeQuery();

         while (resultSet1.next()) {
            String userId = resultSet1.getString("user_id");
            String userName = resultSet1.getString("user_name");
            allNames.add(userName + "(" + userId + ")");      
            System.out.println("totalNames:"+allNames);
         }
         
         // 청소당번 배열 돌리기
         totalNames = allNames.size();

         System.out.println("totalNames:"+allNames.size());
         int remainingPeople = totalNames;
         while (remainingPeople > 0) {
            // 남은 인원이 3명보다 작으면 그룹을 3명으로 구성
            int groupSize = Math.min(remainingPeople, 3);
            // 남은 인원이 4명 이상이면 3 또는 4명으로 랜덤하게 구성
            if (remainingPeople >= 4) {
               groupSize = (int) (Math.random() * 2) + 3;
            }
            List<String> team = new ArrayList<>();
            for (int i = 0; i < groupSize; i++) {
               team.add(allNames.remove(0));
            }
            System.out.println("team.size:"+team);
            teams.add(team);
            remainingPeople -= groupSize;
            System.out.println("teams.size:"+teams.size());
         }
         List<String> currentWeekNames = teams.get(currentWeek);
         System.out.println("teams.size:"+teams.size());
         sb = new StringBuilder();
         for (String name : currentWeekNames) {
            sb.append((name.trim())); // 오른쪽 정렬
            sb.append("    ");
         }

      } catch (Exception e) {
         e.printStackTrace();
      }

      // 통합 청소 패널 생성
      cleanPanel = new JPanel(new BorderLayout());

      // 주차,<>버튼 패널 생성
      topPanel = new JPanel(new GridBagLayout());
      topPanel.setBackground(new Color(231, 222, 248));
      topPanel.setPreferredSize(new Dimension(600, 100)); // topJPane

      topPanel.add(tf1 = new JTextField("1", 4));
      topPanel.add(tf2 = new JTextField("주차"));
      topPanel.add(tf3 = new JTextField("이번주 청소 당번은?", 30));
      topPanel.add(btn1 = new JButton("<"));
      topPanel.add(btn2 = new JButton(">"));

      // 모든 컴포넌트를 가운데 정렬로 설정
      tf1.setHorizontalAlignment(JTextField.CENTER);
      tf2.setHorizontalAlignment(JTextField.CENTER);
      tf3.setHorizontalAlignment(JTextField.CENTER);
      btn1.setHorizontalAlignment(JButton.CENTER);
      btn2.setHorizontalAlignment(JButton.CENTER);

      // 폰트 설정
      Font font = new Font("Inter", Font.BOLD, 20);
      tf1.setFont(font);
      tf2.setFont(font);
      tf3.setFont(font);
      btn1.setFont(font);
      btn2.setFont(font);
      
      tf1.setBackground(Color.WHITE);
      tf2.setBackground(Color.WHITE);
      tf3.setBackground(Color.WHITE);
      btn1.setBackground(Color.WHITE);
      btn2.setBackground(Color.WHITE);

      tf1.setEditable(false);
      tf3.setEditable(false);
      btn1.addActionListener(this);
      btn2.addActionListener(this);

      // 내용 패널 생성 (date+user)
      centerPanel = new JPanel(new BorderLayout());
      // 날짜 출력
      dateLabel = new JLabel(dateRangeText);
      dateLabel.setFont(new Font("Inter", font.BOLD, 24));
      dateLabel.setVerticalAlignment(JLabel.CENTER);
      dateLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬 설정
      dateLabel.setBackground(Color.WHITE);
      // user_id , user_name 출력
      userLabel = new JLabel();
      userLabel.setText(sb.toString());
      userLabel.setFont(new Font("Inter", font.BOLD, 18));
      userLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬 설정
      dateLabel.setBackground(Color.WHITE);

      // 내용(date+user) label 더하기
      centerPanel.add(dateLabel, BorderLayout.NORTH);
      centerPanel.add(userLabel, BorderLayout.CENTER);
      centerPanel.setBackground(Color.WHITE);

      // clean 패널에 top ,center 패널 추가
      cleanPanel.add(topPanel, BorderLayout.NORTH);
      cleanPanel.add(centerPanel, BorderLayout.CENTER);

      return cleanPanel;

   }

   @Override
   public void actionPerformed(ActionEvent e) {
      // 버튼 클릭 이벤트
      Object obj = e.getSource();
      if (obj == btn1 && tf1 != null && Integer.parseInt(tf1.getText()) > MIN_VALUE) {
         value = Integer.parseInt(tf1.getText());
         value--;
         tf1.setText(Integer.toString(value));
         weekStartCurrentWeek--;
         updateDateRangeText();
         System.out.println(weekStartCurrentWeek);
         currentWeek = (currentWeek - 1 + teams.size()) % teams.size();
         updateUserLabelText();

      }
      if (obj == btn2 && Integer.parseInt(tf1.getText()) < MAX_VALUE) {
         value = Integer.parseInt(tf1.getText());
         value++;
         tf1.setText(Integer.toString(value));
         weekStartCurrentWeek++;
         updateDateRangeText();
         System.out.println("week : " + weekStartCurrentWeek);
         System.out.println("teams.size() : " +  teams.size());
         
         currentWeek = (currentWeek + 1 + teams.size()) % teams.size();
         updateUserLabelText();
      } else if (Integer.parseInt(tf1.getText()) == MAX_VALUE) {
         JOptionPane.showMessageDialog(this, " 마지막 주차 입니다. ");
      }
   }

   private void updateUserLabelText() {
      List<String> currentWeekNames = teams.get(currentWeek);
      sb = new StringBuilder();
      
      for (String name : currentWeekNames) {
         sb.append(name);
         sb.append("   ");
      }
      userLabel.setText("\n\n\n\n\n\n"+sb.toString());
   }

   private void updateDateRangeText() {
      try {
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         Calendar startCalendar = Calendar.getInstance();
         startCalendar.setTime(startDate);
         startCalendar.add(Calendar.WEEK_OF_YEAR, weekStartCurrentWeek);
         startCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
         Date weekStartDate = startCalendar.getTime();

         Calendar endCalendar = Calendar.getInstance();
         endCalendar.setTime(weekStartDate);
         endCalendar.add(Calendar.DAY_OF_WEEK, 6);
         Date weekEndDate = endCalendar.getTime();

         String formattedStartDate = sdf.format(weekStartDate);
         String formattedEndDate = sdf.format(weekEndDate);

         String dateRangeText = "[   " + formattedStartDate + "(일)    ~    " + formattedEndDate + "(토)   ]";
         ((JLabel) centerPanel.getComponent(0)).setText(dateRangeText);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

 

}
