package attendancesystem;
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

public class StaticMgr extends JFrame {
 
    private Connection connection;
    private DefaultTableModel model;
    private JTextField attendanceRateTextField;
	private int presentCount;
	private int absentCount;
	private int tardyCount;
    public StaticMgr(Connection connection) {
//        super("Attendance Pie Chart");
        this.connection = connection;
        this.attendanceRateTextField = new JTextField();
        Font font = new Font("Inter", Font.PLAIN, 16); // Example: Arial, Plain, Size 16
        attendanceRateTextField.setFont(font);


    
        setLayout(new BorderLayout());

      
        add(attendanceRateTextField, BorderLayout.SOUTH);

        
    }

    public JPanel createJavaStaticChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
          
            String query = Query.GET_ATTENDANCE_STATISTICS_BY_JAVA;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "Java��");
            ResultSet resultSet = statement.executeQuery();

  
            int presentCount = 0, absentCount = 0, tardyCount = 0;
            if (resultSet.next()) {
                presentCount = resultSet.getInt("present_count");
                absentCount = resultSet.getInt("absent_count");
                tardyCount = resultSet.getInt("tardy_count");
            }

         
            dataset.setValue("�⼮", presentCount);
            dataset.setValue("�Ἦ", absentCount);
            dataset.setValue("����", tardyCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        JFreeChart chart = ChartFactory.createPieChart(
                "Java�� ���� �⼮ ������",
                dataset,
                false,
                false,
                false);
        Font titleFont = new Font("Inter", Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("�⼮", new Color(128, 0, 128)); 
        plot.setSectionPaint("�Ἦ", new Color(204, 153, 255));
        plot.setSectionPaint("����", Color.WHITE); 
        plot.setBackgroundPaint(Color.WHITE);
        Font labelFont = new Font("Inter", Font.PLAIN, 12); 
        plot.setLabelFont(labelFont);
        // �⼮���� ���̺� �߰�
        double total = presentCount + absentCount + tardyCount;
        double attendanceRate = (presentCount / total) * 100;
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}	", new Locale("0.##")));
        attendanceRateTextField.setText("�⼮��: " + String.format("%.2f%%", attendanceRate));
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
        
        //return new ChartPanel(chart);
    
    }

    public JPanel createCad1StaticChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
           
            String query = Query.GET_ATTENDANCE_STATISTICS_BY_CAD1; 
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "CAD1�޹�");
            ResultSet resultSet = statement.executeQuery();

           
            int presentCount = 0, absentCount = 0, tardyCount = 0;
            if (resultSet.next()) {
                presentCount = resultSet.getInt("present_count");
                absentCount = resultSet.getInt("absent_count");
                tardyCount = resultSet.getInt("tardy_count");
            }

         
            dataset.setValue("�⼮", presentCount);
            dataset.setValue("�Ἦ", absentCount);
            dataset.setValue("����", tardyCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }

      
        JFreeChart chart = ChartFactory.createPieChart(
                "CAD 1�� ���� �⼮ ������",
                dataset,
                false,
                false,
                false);
      
        Font titleFont = new Font("Inter", Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("�⼮", new Color(128, 0, 128)); 
        plot.setSectionPaint("�Ἦ", new Color(204, 153, 255));
        plot.setSectionPaint("����", Color.WHITE); 
        plot.setBackgroundPaint(Color.WHITE); 

        // �⼮���� ���̺� �߰�
        double total = presentCount + absentCount + tardyCount;
        double attendanceRate = (presentCount / total) * 100;
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}", new Locale("0.##")));
        attendanceRateTextField.setText("�⼮��: " + String.format("%.2f%%", attendanceRate));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }
    public JPanel createCad2StaticChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
           
            String query = Query.GET_ATTENDANCE_STATISTICS_BY_CAD2; 
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "CAD2�޹�");
            ResultSet resultSet = statement.executeQuery();

            // ��� ó��
            int presentCount = 0, absentCount = 0, tardyCount = 0;
            if (resultSet.next()) {
                presentCount = resultSet.getInt("present_count");
                absentCount = resultSet.getInt("absent_count");
                tardyCount = resultSet.getInt("tardy_count");
            }

          
            dataset.setValue("�⼮", presentCount);
            dataset.setValue("�Ἦ", absentCount);
            dataset.setValue("����", tardyCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }

       
        JFreeChart chart = ChartFactory.createPieChart(
                "CAD 2�� ���� �⼮ ������",
                dataset,
                false,
                false,
                false);
        Font titleFont = new Font("Inter", Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("�⼮", new Color(128, 0, 128));
        plot.setSectionPaint("�Ἦ", new Color(204, 153, 255)); 
        plot.setSectionPaint("����", Color.WHITE); 
        plot.setBackgroundPaint(Color.WHITE);
        Font labelFont = new Font("Inter", Font.PLAIN, 12); 
        plot.setLabelFont(labelFont);

        // �⼮���� ���̺� �߰�
        double total = presentCount + absentCount + tardyCount;
        double attendanceRate = (presentCount / total) * 100;
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}", new Locale("0.##")));
        attendanceRateTextField.setText("�⼮��: " + String.format("%.2f%%", attendanceRate));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }
    public JPanel createComStaticChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
           
            String query = Query.GET_ATTENDANCE_STATISTICS_BY_COM; 
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "��Ȱ��");
            ResultSet resultSet = statement.executeQuery();

           
            int presentCount = 0, absentCount = 0, tardyCount = 0;
            if (resultSet.next()) {
                presentCount = resultSet.getInt("present_count");
                absentCount = resultSet.getInt("absent_count");
                tardyCount = resultSet.getInt("tardy_count");
            }

            
            dataset.setValue("�⼮", presentCount);
            dataset.setValue("�Ἦ", absentCount);
            dataset.setValue("����", tardyCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }

 
        JFreeChart chart = ChartFactory.createPieChart(
                "��ǻ�� Ȱ��� ���� �⼮ ������",
                dataset,
                false,
                false,
                false);
        
        Font titleFont = new Font("Inter", Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("�⼮", new Color(128, 0, 128)); // Dark purple for attendance
        plot.setSectionPaint("�Ἦ", new Color(204, 153, 255)); // Light purple for absent
        plot.setSectionPaint("����", Color.WHITE); // White for tardy
        plot.setBackgroundPaint(Color.WHITE); // White background for the chart
        Font labelFont = new Font("Inter", Font.PLAIN, 12); 
        plot.setLabelFont(labelFont);
        // �⼮���� ���̺� �߰�
        double total = presentCount + absentCount + tardyCount;
        double attendanceRate = (presentCount / total) * 100;
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}", new Locale("0.##")));
        attendanceRateTextField.setText("�⼮��: " + String.format("%.2f%%", attendanceRate));
        
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }
    
    public JPanel createJavaMonthlyBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String query = Query.GET_ATTENDANCE_STATISTICS_BY_MONTH;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "Java��");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int month = resultSet.getInt("month");
                int presentCount = resultSet.getInt("present_count");
                int absentCount = resultSet.getInt("absent_count");
                int tardyCount = resultSet.getInt("tardy_count");

                dataset.addValue(presentCount, "�⼮", String.valueOf(month));
                dataset.addValue(absentCount, "�Ἦ", String.valueOf(month));
                dataset.addValue(tardyCount, "����", String.valueOf(month));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Java�� ���� ������", 
                "Month",
                "Count",
                dataset,
                PlotOrientation.VERTICAL, 
                false,
                false, 
                false 
        );
        Font titleFont = new Font("Inter", Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);
        // �� ��Ʈ�� Plot ��������
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);

        // BarRenderer ����
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // �⼮, �Ἦ, ������ ���� ���� ����
        renderer.setSeriesPaint(0, new Color(128, 0, 128)); 
        renderer.setSeriesPaint(1, new Color(204, 153, 255)); 
        renderer.setSeriesPaint(2, Color.blue); 
        
        

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        
        return chartPanel;
    
    }
    public JPanel createCad1MonthlyBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String query = Query.GET_ATTENDANCE_STATISTICS_BY_MONTH;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "cad1�޹�");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int month = resultSet.getInt("month");
                int presentCount = resultSet.getInt("present_count");
                int absentCount = resultSet.getInt("absent_count");
                int tardyCount = resultSet.getInt("tardy_count");

                dataset.addValue(presentCount, "�⼮", String.valueOf(month));
                dataset.addValue(absentCount, "�Ἦ", String.valueOf(month));
                dataset.addValue(tardyCount, "����", String.valueOf(month));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "CAD 1�޹� ���� ������", // Chart title
                "Month", 
                "Count", 
                dataset, 
                PlotOrientation.VERTICAL,
                false, 
                false, 
                false 
        );
        Font titleFont = new Font("Inter", Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);
        // �� ��Ʈ�� Plot ��������
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);

        // BarRenderer ����
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // �⼮, �Ἦ, ������ ���� ���� ����
        renderer.setSeriesPaint(0, new Color(128, 0, 128));
        renderer.setSeriesPaint(1, new Color(204, 153, 255));
        renderer.setSeriesPaint(2, Color.blue); 
        
        

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    
    }
    public JPanel createCad2MonthlyBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String query = Query.GET_ATTENDANCE_STATISTICS_BY_MONTH;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "cad2�޹�");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int month = resultSet.getInt("month");
                int presentCount = resultSet.getInt("present_count");
                int absentCount = resultSet.getInt("absent_count");
                int tardyCount = resultSet.getInt("tardy_count");

                dataset.addValue(presentCount, "�⼮", String.valueOf(month));
                dataset.addValue(absentCount, "�Ἦ", String.valueOf(month));
                dataset.addValue(tardyCount, "����", String.valueOf(month));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "CAD 2�޹� ���� ������", 
                "Month",
                "Count", 
                dataset, 
                PlotOrientation.VERTICAL, 
                false, 
                false,
                false 
        );
        Font titleFont = new Font("Inter", Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);
        // �� ��Ʈ�� Plot ��������
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);

       
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // �⼮, �Ἦ, ������ ���� ���� ����
        renderer.setSeriesPaint(0, new Color(128, 0, 128)); 
        renderer.setSeriesPaint(1, new Color(204, 153, 255)); 
        renderer.setSeriesPaint(2, Color.blue);
        
        

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    
    }
    public JPanel createComMonthlyBarChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String query = Query.GET_ATTENDANCE_STATISTICS_BY_MONTH;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, "��Ȱ��");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int month = resultSet.getInt("month");
                int presentCount = resultSet.getInt("present_count");
                int absentCount = resultSet.getInt("absent_count");
                int tardyCount = resultSet.getInt("tardy_count");

                dataset.addValue(presentCount, "�⼮", String.valueOf(month));
                dataset.addValue(absentCount, "�Ἦ", String.valueOf(month));
                dataset.addValue(tardyCount, "����", String.valueOf(month));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "��ǻ�� Ȱ��� ���� ������",
                "Month", 
                "Count", 
                dataset, 
                PlotOrientation.VERTICAL,
                false,
                false,
                false 
        );
        Font titleFont = new Font("Inter", Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);
      
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);

      
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        
        renderer.setSeriesPaint(0, new Color(128, 0, 128)); 
        renderer.setSeriesPaint(1, new Color(204, 153, 255)); 
        renderer.setSeriesPaint(2, Color.blue); 
        
        

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    
    }
    public JPanel createJavaoverallStaticChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            // ���� �ۼ� - Java�� �⼮ ��踦 �������� ������ Java�� Ŭ���� �̸��� �־��ݴϴ�.
            String query = Query.GET_ATTENDANCE_STATISTICS_BY_all;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "Java��");
            ResultSet resultSet = statement.executeQuery();

            // ��� ó��
            int presentCount = 0, absentCount = 0, tardyCount = 0;
            if (resultSet.next()) {
                presentCount = resultSet.getInt("present_count");
                absentCount = resultSet.getInt("absent_count");
                tardyCount = resultSet.getInt("tardy_count");
            }

            // �����ͼ¿� ������ �߰�
            dataset.setValue("�⼮", presentCount);
            dataset.setValue("�Ἦ", absentCount);
            dataset.setValue("����", tardyCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // ���� ��Ʈ ����
        JFreeChart chart = ChartFactory.createPieChart(
                "Java�� ��ü �⼮ ������",
                dataset,
                false,
                false,
                false);
        Font titleFont = new Font("Inter", Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("attendance", new Color(128, 0, 128)); // Dark purple for attendance
        plot.setSectionPaint("�Ἦ", new Color(204, 153, 255)); // Light purple for absent
        plot.setSectionPaint("����", Color.WHITE); // White for tardy
        plot.setBackgroundPaint(Color.WHITE); // White background for the chart
        Font labelFont = new Font("Inter", Font.PLAIN, 12); 
        plot.setLabelFont(labelFont);

        // �⼮���� ���̺� �߰�
        double total = presentCount + absentCount + tardyCount;
        double attendanceRate = (presentCount / total) * 100;
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}	", new Locale("0.##")));
        attendanceRateTextField.setText("�⼮��: " + String.format("%.2f%%", attendanceRate));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }
    public JPanel createCad1overallStaticChartPanel() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            
            String query = Query.GET_ATTENDANCE_STATISTICS_BY_all;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "cad1�޹�");
            ResultSet resultSet = statement.executeQuery();

            // ��� ó��
            int presentCount = 0, absentCount = 0, tardyCount = 0;
            if (resultSet.next()) {
                presentCount = resultSet.getInt("present_count");
                absentCount = resultSet.getInt("absent_count");
                tardyCount = resultSet.getInt("tardy_count");
            }

            // �����ͼ¿� ������ �߰�
            dataset.setValue("�⼮", presentCount);
            dataset.setValue("�Ἦ", absentCount);
            dataset.setValue("����", tardyCount);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // ���� ��Ʈ ����
        JFreeChart chart = ChartFactory.createPieChart(
                "CAD 1�� ��ü �⼮ ������",
                dataset,
                false,
                false,
                false);
        Font titleFont = new Font("Inter", Font.BOLD, 18); 
        chart.getTitle().setFont(titleFont);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("�⼮", new Color(128, 0, 128)); 
        plot.setSectionPaint("�Ἦ", new Color(204, 153, 255));
        plot.setSectionPaint("����", Color.WHITE); 
        plot.setBackgroundPaint(Color.WHITE); 
        Font labelFont = new Font("Inter", Font.PLAIN, 12); 
        plot.setLabelFont(labelFont);
        // �⼮���� ���̺� �߰�
        double total = presentCount + absentCount + tardyCount;
        double attendanceRate = (presentCount / total) * 100;
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}	", new Locale("0.##")));
        attendanceRateTextField.setText("�⼮��: " + String.format("%.2f%%", attendanceRate));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 600));
        chartPanel.setBackground(Color.WHITE);
        return chartPanel;
    }
        public JPanel createCad2overallStaticChartPanel() {
        	DefaultPieDataset dataset = new DefaultPieDataset();
        	try {
        		// ���� �ۼ� - Java�� �⼮ ��踦 �������� ������ Java�� Ŭ���� �̸��� �־��ݴϴ�.
        		String query = Query.GET_ATTENDANCE_STATISTICS_BY_all;
        		
        		PreparedStatement statement = connection.prepareStatement(query);
        		statement.setString(1, "cad2�޹�");
        		ResultSet resultSet = statement.executeQuery();
        		
        		// ��� ó��
        		int presentCount = 0, absentCount = 0, tardyCount = 0;
        		if (resultSet.next()) {
        			presentCount = resultSet.getInt("present_count");
        			absentCount = resultSet.getInt("absent_count");
        			tardyCount = resultSet.getInt("tardy_count");
        		}
        		
        		// �����ͼ¿� ������ �߰�
        		dataset.setValue("�⼮", presentCount);
        		dataset.setValue("�Ἦ", absentCount);
        		dataset.setValue("����", tardyCount);
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        	
        	// ���� ��Ʈ ����
        	JFreeChart chart = ChartFactory.createPieChart(
        			"CAD 2�� ��ü �⼮ ������",
        			dataset,
        			false,
        			false,
        			false);
        	Font titleFont = new Font("Inter", Font.BOLD, 18); 
            chart.getTitle().setFont(titleFont);
        	PiePlot plot = (PiePlot) chart.getPlot();
        	plot.setSectionPaint("�⼮", new Color(128, 0, 128)); 
        	plot.setSectionPaint("�Ἦ", new Color(204, 153, 255)); 
        	plot.setSectionPaint("����", Color.WHITE);
        	plot.setBackgroundPaint(Color.WHITE); // 
        	Font labelFont = new Font("Inter", Font.PLAIN, 12); 
            plot.setLabelFont(labelFont);
        	// �⼮���� ���̺� �߰�
        	double total = presentCount + absentCount + tardyCount;
        	double attendanceRate = (presentCount / total) * 100;
        	plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}	", new Locale("0.##")));
        	attendanceRateTextField.setText("�⼮��: " + String.format("%.2f%%", attendanceRate));
        	ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(800, 600));
            chartPanel.setBackground(Color.WHITE);
            return chartPanel;
        }
        	public JPanel createComoverallStaticChartPanel() {
        		DefaultPieDataset dataset = new DefaultPieDataset();
        		try {
        			// ���� �ۼ� - Java�� �⼮ ��踦 �������� ������ Java�� Ŭ���� �̸��� �־��ݴϴ�.
        			String query = Query.GET_ATTENDANCE_STATISTICS_BY_all;
        			
        			PreparedStatement statement = connection.prepareStatement(query);
        			statement.setString(1, "��Ȱ��");
        			ResultSet resultSet = statement.executeQuery();
        			
        			// ��� ó��
        			int presentCount = 0, absentCount = 0, tardyCount = 0;
        			if (resultSet.next()) {
        				presentCount = resultSet.getInt("present_count");
        				absentCount = resultSet.getInt("absent_count");
        				tardyCount = resultSet.getInt("tardy_count");
        			}
        			
        			// �����ͼ¿� ������ �߰�
        			dataset.setValue("�⼮", presentCount);
        			dataset.setValue("�Ἦ", absentCount);
        			dataset.setValue("����", tardyCount);
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
        		
        		// ���� ��Ʈ ����
        		JFreeChart chart = ChartFactory.createPieChart(
        				"��ǻ�� Ȱ��� ��ü �⼮ ������",
        				dataset,
        				false,
        				false,
        				false);
        		Font titleFont = new Font("Inter", Font.BOLD, 18); 
                chart.getTitle().setFont(titleFont);
        		PiePlot plot = (PiePlot) chart.getPlot();
        		plot.setSectionPaint("�⼮", new Color(128, 0, 128)); // Dark purple for attendance
        		plot.setSectionPaint("�Ἦ", new Color(204, 153, 255)); // Light purple for absent
        		plot.setSectionPaint("����", Color.WHITE); // White for tardy
        		plot.setBackgroundPaint(Color.WHITE); // White background for the chart
        		Font labelFont = new Font("Inter", Font.PLAIN, 12); 
                plot.setLabelFont(labelFont);
        		
        		// �⼮���� ���̺� �߰�
        		double total = presentCount + absentCount + tardyCount;
        		double attendanceRate = (presentCount / total) * 100;
        		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}	", new Locale("0.##")));
        		attendanceRateTextField.setText("�⼮��: " + String.format("%.2f%%", attendanceRate));
        		ChartPanel chartPanel = new ChartPanel(chart);
                chartPanel.setPreferredSize(new Dimension(800, 600));
                chartPanel.setBackground(Color.WHITE);
                return chartPanel;
    }
    public static void main(String[] args) {
        Connection connection = null;
        try {
        	 connection = MysqlConnection.getConnection();
             StaticMgr staticMgr = new StaticMgr(connection);
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
