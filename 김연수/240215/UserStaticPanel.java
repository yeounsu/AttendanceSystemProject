package attendancesystem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class UserStaticPanel extends JPanel {
    private Connection connection;
    private String loggedInUserID; 

    public UserStaticPanel(Connection connection, String loggedInUserID) {
        this.connection = connection;
        this.loggedInUserID = loggedInUserID;
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Create a panel to hold the subpanels
        JPanel containerPanel = new JPanel(new GridLayout(1, 2));
        containerPanel.setBackground(Color.WHITE); 

        // Panel for "���� ��� ��Ȳ (��ü)"
        JPanel overallAttendancePanel = new UserStaticMgr(connection, loggedInUserID).createOverallAttendancePanel();
        overallAttendancePanel.setPreferredSize(new Dimension(80, 160));
        containerPanel.add(overallAttendancePanel);

        // Panel for "���� ��� ��Ȳ (�̹� ��)"
        JPanel thisMonthAttendancePanel = new UserStaticMgr(connection, loggedInUserID).createThisMonthAttendancePanel();
        thisMonthAttendancePanel.setPreferredSize(new Dimension(80, 160));
        containerPanel.add(thisMonthAttendancePanel);

        add(containerPanel, BorderLayout.NORTH);

        // Fetch attendance data for the logged-in user
        DefaultTableModel model = fetchAttendanceData(loggedInUserID);

        // Create a table to display the attendance data
        JTable table = new JTable(model);
        table.setBackground(Color.WHITE); 
        table.getTableHeader().setBackground(Color.WHITE); 
        table.setGridColor(Color.WHITE); 
        table.setFillsViewportHeight(true); 

        // Customize the table panel
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the table to the main panel
        add(scrollPane, BorderLayout.CENTER);
    }
    private DefaultTableModel fetchAttendanceData(String userID) {
        DefaultTableModel model = new DefaultTableModel();

        try {
            String query = "SELECT * FROM attendance WHERE user_id = ?"; // attendance ���̺� user_id ��
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userID); //�α����� ���� id �ܾ�´ٴ� �ǹ� 

            
            ResultSet rs = pstmt.executeQuery();

           
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = rs.getObject(i + 1);
                }
                model.addRow(rowData);
            }

           
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return model;
    }
}