package Counseling;
import config.*;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CounselingMgr {
    private Connection connection;
    private DefaultTableModel model;
    private DefaultTableModel tableModel;
    private JTable table;

    public CounselingMgr(DefaultTableModel tableModel) {
        this.connection = MysqlConnection.getConnection();
        this.tableModel = tableModel;
    }

    public void cad1Data(String query) {
        try {
        	tableModel.setRowCount(0);
            query = Query.SELECT_COUNSECAD1_TABLE;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {
            	String counselingNum = resultSet.getString("counseling_num");
				String userId = resultSet.getString("user_id");
				String userName = resultSet.getString("user_name");
				Timestamp counselingDate = resultSet.getTimestamp("counseling_date");
				String formattedDate = new SimpleDateFormat("yyyy-	MM-dd").format(counselingDate); 
				String counselingTitle = resultSet.getString("counseling_title");
				tableModel.addRow(new Object[]{counselingNum, userId, userName,  formattedDate, counselingTitle});
			}
            
            if (tableModel.getRowCount() == 0) {
            	JOptionPane.showMessageDialog(null, "삭제 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void cad2Data(String query) {
        try {
        	tableModel.setRowCount(0);
            query = Query.SELECT_COUNSECAD2_TABLE;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
            	String counselingNum = resultSet.getString("counseling_num");
            	String userId = resultSet.getString("user_id");
            	String userName = resultSet.getString("user_name");
            	Timestamp counselingDate = resultSet.getTimestamp("counseling_date");
            	String formattedDate = new SimpleDateFormat("yyyy-	MM-dd").format(counselingDate); 
            	String counselingTitle = resultSet.getString("counseling_title");
            	tableModel.addRow(new Object[]{counselingNum, userId, userName,  formattedDate, counselingTitle});
            }
            
            
            if (tableModel.getRowCount() == 0) {
            	JOptionPane.showMessageDialog(null, "삭제 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void javaData(String query) {
    	try {
    		tableModel.setRowCount(0);
    		query = Query.SELECT_COUNSEJAVA_TABLE;
    		Statement statement = connection.createStatement();
    		ResultSet resultSet = statement.executeQuery(query);
    		
    		
    		while (resultSet.next()) {
    			String counselingNum = resultSet.getString("counseling_num");
    			String userId = resultSet.getString("user_id");
    			String userName = resultSet.getString("user_name");
    			Timestamp counselingDate = resultSet.getTimestamp("counseling_date");
    			String formattedDate = new SimpleDateFormat("yyyy-	MM-dd").format(counselingDate); 
    			String counselingTitle = resultSet.getString("counseling_title");
    			tableModel.addRow(new Object[]{counselingNum, userId, userName,  formattedDate, counselingTitle});
    		}
    		
    		
    		if (tableModel.getRowCount() == 0) {
    			JOptionPane.showMessageDialog(null, "삭제 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
    		}
    		resultSet.close();
    		statement.close();
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    public void computerData(String query) {
        try {
        	tableModel.setRowCount(0);
            query = Query.SELECT_COUNSECOMPUTER_TABLE;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
            	String counselingNum = resultSet.getString("counseling_num");
            	String userId = resultSet.getString("user_id");
            	String userName = resultSet.getString("user_name");
            	Timestamp counselingDate = resultSet.getTimestamp("counseling_date");
            	String formattedDate = new SimpleDateFormat("yyyy-	MM-dd").format(counselingDate); 
            	String counselingTitle = resultSet.getString("counseling_title");
            	tableModel.addRow(new Object[]{counselingNum, userId, userName,  formattedDate, counselingTitle});
            }
           
            if (tableModel.getRowCount() == 0) {
            	JOptionPane.showMessageDialog(null, "삭제 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

