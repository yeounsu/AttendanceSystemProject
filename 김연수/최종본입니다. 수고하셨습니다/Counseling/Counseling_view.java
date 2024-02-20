package Counseling;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import config.MysqlConnection;

public class Counseling_view extends JPanel {
    private String counselingClass;
    private String counselingTitle;
    private String counselingContent;

    public Counseling_view(String counselingTitle) {
        fetchDataFromDatabase(counselingTitle);
        initComponents();
    }

    private void fetchDataFromDatabase(String counselingTitle) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = MysqlConnection.getConnection();

            String query = "SELECT counseling_content FROM counseling WHERE counseling_title = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, counselingTitle);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                counselingContent = rs.getString("counseling_content");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400)); //  г    ũ       

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("신청 사항 내용");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 24)); //  ۲     ũ       
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea contentArea = new JTextArea(counselingContent);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("Inter", Font.BOLD, 18)); //  ۲     ũ       

        JScrollPane scrollPane = new JScrollPane(contentArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
    }
}