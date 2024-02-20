package Home;

import config.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class Home_admin_Mgr extends JFrame {
 
    private Connection connection;
    private DefaultTableModel model;
    private JTextField attendanceRateTextField;
   private int presentCount;
   private int absentCount;
   private int tardyCount;
    public Home_admin_Mgr(Connection connection) {
//        super("Attendance Pie Chart");
        this.connection = connection;
        this.attendanceRateTextField = new JTextField();
        Font font = new Font("Inter", Font.BOLD, 18); 
        attendanceRateTextField.setFont(font);


    
        setLayout(new BorderLayout());

      
        add(attendanceRateTextField, BorderLayout.SOUTH);

        
    }

    public JPanel createStaticChartPanel(String className) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            String query = "SELECT " +
                    "    SUM(CASE WHEN attendance.attendance_status = '출석' THEN 1 ELSE 0 END) AS present_count, " +
                    "    SUM(CASE WHEN attendance.attendance_status = '결석' THEN 1 ELSE 0 END) AS absent_count, " +
                    "    SUM(CASE WHEN attendance.attendance_status = '지각' THEN 1 ELSE 0 END) AS tardy_count " +
                    "FROM " +
                    "    user " +
                    "    INNER JOIN class ON user.class_num = class.class_num " +
                    "    LEFT JOIN attendance ON user.user_id = attendance.user_id " +
                    "WHERE " +
                    "    class.class_name = ?" +
                    "    AND DATE(attendance_date) = CURDATE()";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, className); // 매개변수로 받아온 className 설정
            ResultSet resultSet = statement.executeQuery();

            int presentCount = 0, absentCount = 0, tardyCount = 0;
            if (resultSet.next()) {
                presentCount = resultSet.getInt("present_count");
                absentCount = resultSet.getInt("absent_count");
                tardyCount = resultSet.getInt("tardy_count");
            }

            dataset.setValue("출석", presentCount);
            dataset.setValue("결석", absentCount);
            dataset.setValue("지각", tardyCount);

            JFreeChart chart = ChartFactory.createPieChart(
                    className + " 오늘 출석 데이터",
                    dataset,
                    false,
                    false,
                    false);
            Font titleFont = new Font("Inter", Font.BOLD, 18); 
            chart.getTitle().setFont(titleFont);

            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setSectionPaint("출석", new Color(153, 204, 255)); 
            plot.setSectionPaint("결석", new Color(255, 0, 0));
            plot.setSectionPaint("지각", new Color(255,255,0)); 
        

            plot.setBackgroundPaint(Color.WHITE);
            
            Font labelFont = new Font("Inter", Font.BOLD, 18); 
            plot.setLabelFont(labelFont);
            // 출석률을 레이블에 추가
            double total = presentCount + absentCount + tardyCount;
            double attendanceRate = (presentCount / (double) total) * 100;
            plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}   ", new Locale("0.##")));
            attendanceRateTextField.setText("출석률: " + String.format("%.2f%%", attendanceRate));
            
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(800, 600));
            chartPanel.setBackground(Color.WHITE);
            return chartPanel;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 예외가 발생하면 null 반환 혹은 예외 처리를 진행하셔야 합니다.
    }

  
    public JPanel createMonthlyBarChartPanel(String className) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String query =               "SELECT " +
                "    MONTH(attendance_date) AS month, " +
                "    SUM(CASE WHEN attendance.attendance_status = '출석' THEN 1 ELSE 0 END) AS present_count, " +
                "    SUM(CASE WHEN attendance.attendance_status = '결석' THEN 1 ELSE 0 END) AS absent_count, " +
                "    SUM(CASE WHEN attendance.attendance_status = '지각' THEN 1 ELSE 0 END) AS tardy_count " +
                "FROM " +
                "    user " +
                "    INNER JOIN class ON user.class_num = class.class_num " +
                "    LEFT JOIN attendance ON user.user_id = attendance.user_id " +   
                "WHERE " +
                "    class.class_name = ?" +
                "    AND YEAR(attendance_date) = YEAR(CURDATE()) " +
                "GROUP BY " +
                "    MONTH(attendance_date)";


        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, className);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int month = resultSet.getInt("month");
                int presentCount = resultSet.getInt("present_count");
                int absentCount = resultSet.getInt("absent_count");
                int tardyCount = resultSet.getInt("tardy_count");

                dataset.addValue(presentCount, "출석", String.valueOf(month));
                dataset.addValue(absentCount, "결석", String.valueOf(month));
                dataset.addValue(tardyCount, "지각", String.valueOf(month));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                className + " 월별 데이터", 
                "Month",
                "Count",
                dataset,
                PlotOrientation.VERTICAL, 
                true,
                true, 
                true 
        );
        Font titleFont = new Font("Inter", Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);
        // 바 차트의 Plot 가져오기
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);

        // BarRenderer 설정
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // 출석, 결석, 지각에 대한 색상 설정
        renderer.setSeriesPaint(0, new Color(153, 204, 255)); 
        renderer.setSeriesPaint(1, new Color(255, 0, 0)); 
        renderer.setSeriesPaint(2, new Color(255,255,0)); 
        

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        
        return chartPanel;
    }
    
    
    public JPanel createoverallStaticChartPanel(String className) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            // 쿼리 작성
            String query = "SELECT " +
                    "    SUM(CASE WHEN attendance.attendance_status = '출석' THEN 1 ELSE 0 END) AS present_count, " +
                    "    SUM(CASE WHEN attendance.attendance_status = '결석' THEN 1 ELSE 0 END) AS absent_count, " +
                    "    SUM(CASE WHEN attendance.attendance_status = '지각' THEN 1 ELSE 0 END) AS tardy_count " +
                    "FROM " +
                    "    user " +
                    "    INNER JOIN class ON user.class_num = class.class_num " +
                    "    LEFT JOIN attendance ON user.user_id = attendance.user_id " +
                    "WHERE " +
                    "    class.class_name = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, className);
            ResultSet resultSet = statement.executeQuery();

            // 결과 처리
            int presentCount = 0, absentCount = 0, tardyCount = 0;
            if (resultSet.next()) {
                presentCount = resultSet.getInt("present_count");
                absentCount = resultSet.getInt("absent_count");
                tardyCount = resultSet.getInt("tardy_count");
            }

            // 데이터셋에 데이터 추가
            dataset.setValue("출석", presentCount);
            dataset.setValue("결석", absentCount);
            dataset.setValue("지각", tardyCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 파이 차트 생성
        JFreeChart chart = ChartFactory.createPieChart(
                className + " 전체 출석 데이터",
                dataset,
                false,
                false,
                false);
        Font titleFont = new Font("Inter", Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("출석", new Color(153, 204, 255)); // Dark purple for attendance
        plot.setSectionPaint("결석", new Color(255, 0, 0)); // Light purple for absent
        plot.setSectionPaint("지각", new Color(255,255,0)); // White for tardy
        plot.setBackgroundPaint(Color.WHITE); // White background for the chart
        Font labelFont = new Font("Inter", Font.BOLD, 12); 
        plot.setLabelFont(labelFont);

        // 출석률을 레이블에 추가
        double total = presentCount + absentCount + tardyCount;
        double attendanceRate = (presentCount / total) * 100;
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}   ", new Locale("0.##")));
        attendanceRateTextField.setText("출석률: " + String.format("%.2f%%", attendanceRate));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }

  
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = MysqlConnection.getConnection();
             Home_admin_Mgr staticMgr = new Home_admin_Mgr(connection);
             staticMgr.attendanceRateTextField = new JTextField();
             staticMgr.add(staticMgr.attendanceRateTextField, BorderLayout.SOUTH);
             
             staticMgr.setSize(800, 600);
             staticMgr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             staticMgr.setVisible(true);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}