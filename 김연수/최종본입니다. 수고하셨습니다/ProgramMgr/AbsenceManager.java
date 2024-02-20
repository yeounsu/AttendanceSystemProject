package ProgramMgr;
import config.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AbsenceManager {
    
    // 결석 처리 메서드
    public static void markAbsence(String adminName) {
        Connection connection = null;
        try {
            // MysqlConnection 클래스를 사용하여 연결 설정
            connection = MysqlConnection.getConnection();

            // 현재 날짜 가져오기
            LocalDate currentDate = LocalDate.now();

            // 관리자의 class_name 가져오기
            String getClassSql = "SELECT class_name FROM user WHERE user_id = ?";
            PreparedStatement getClassStatement = connection.prepareStatement(getClassSql);
            getClassStatement.setString(1, adminName);
            ResultSet classResultSet = getClassStatement.executeQuery();
            if (classResultSet.next()) {
                String className = classResultSet.getString("class_name");

                // 결석 처리를 위해 해당하는 클래스명과 오늘 날짜의 출석 기록을 가져옴
                String getAttendanceSql = "SELECT * FROM attendance WHERE class_name = ? AND attendance_date = ?";
                PreparedStatement getAttendanceStatement = connection.prepareStatement(getAttendanceSql);
                getAttendanceStatement.setString(1, className);
                getAttendanceStatement.setDate(2, java.sql.Date.valueOf(currentDate));
                ResultSet attendanceResultSet = getAttendanceStatement.executeQuery();

                // 결석 처리
                while (attendanceResultSet.next()) {
                    String userID = attendanceResultSet.getString("user_id");
                    boolean hasAttendanceIn = attendanceResultSet.getTimestamp("attendance_in") != null;
                    String status = hasAttendanceIn ? "출석" : "결석";

                    // 만약 출석 기록이 없다면 출석 상태를 결석으로 업데이트
                    if (!hasAttendanceIn) {
                        // 데이터베이스에 결석 상태를 업데이트하는 SQL 쿼리 실행
                        String updateStatusSql = "UPDATE attendance SET attendance_status = '결석' WHERE user_id = ? AND attendance_date = ?";
                        PreparedStatement updateStatusStatement = connection.prepareStatement(updateStatusSql);
                        updateStatusStatement.setString(1, userID);
                        updateStatusStatement.setDate(2, java.sql.Date.valueOf(currentDate));
                        updateStatusStatement.executeUpdate();
                    }

                    System.out.println(userID + "님의 출석 상태: " + status);
                }
            } else {
                System.out.println("사용자의 class_name을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 연결 종료
            MysqlConnection.closeConnection(connection);
        }
    }
}