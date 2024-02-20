package ProgramMgr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import config.MysqlConnection;

public class AttendanceManager {

	// 출석 기록을 등록하는 메소드
	public static void markAttendance(String username) {
	    Connection connection = null;
	    try {
	        // MysqlConnection 클래스를 사용하여 연결 설정
	        connection = MysqlConnection.getConnection();

	        // 현재 날짜 가져오기
	        LocalDateTime currentDate = LocalDateTime.now();
	        LocalDate sqlDate = currentDate.toLocalDate();

	        // 오늘 날짜에 대한 출석 기록이 이미 있는지 확인
	        String checkSql = "SELECT COUNT(*) FROM attendance WHERE user_id = ? AND DATE(attendance_in) = ?";
	        PreparedStatement checkStatement = connection.prepareStatement(checkSql);
	        checkStatement.setString(1, username);
	        checkStatement.setDate(2, java.sql.Date.valueOf(sqlDate));
	        ResultSet resultSet = checkStatement.executeQuery();
	        resultSet.next();
	        int rowCount = resultSet.getInt(1);
	        if (rowCount > 0) {
	            System.out.println("이미 출석 기록이 존재합니다.");
	            return; // 출석 기록이 이미 있으면 종료
	        } else {
	        	  // 사용자의 class_name 가져오기
	            String getClassSql = "SELECT class_name FROM user WHERE user_id = ?";
	            PreparedStatement getClassStatement = connection.prepareStatement(getClassSql);
	            getClassStatement.setString(1, username);
	            ResultSet classResultSet = getClassStatement.executeQuery();
	            if (classResultSet.next()) {
	                String className = classResultSet.getString("class_name");

	                // 새로운 출석 기록 등록
	                String insertSql = "INSERT INTO attendance (user_id, attendance_in, attendance_date, class_name) VALUES (?, ?, ?, ?)";
	                PreparedStatement insertStatement = connection.prepareStatement(insertSql);
	                insertStatement.setString(1, username);
	                insertStatement.setTimestamp(2, Timestamp.valueOf(currentDate));
	                insertStatement.setDate(3, java.sql.Date.valueOf(sqlDate)); // attendance_date 값 설정
	                insertStatement.setString(4, className); // class_name 값 설정

	                // 실행
	                int rowsInserted = insertStatement.executeUpdate();
	                if (rowsInserted > 0) {
	                    System.out.println("출석 기록이 성공적으로 추가되었습니다.");
	                } else {
	                    System.out.println("출석 기록 추가에 실패하였습니다.");
	                }
	            } else {
	                System.out.println("사용자의 class_name을 찾을 수 없습니다.");
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("SQL exception occurred: " + e.getMessage());
	    } finally {
	        // 연결 종료
	        MysqlConnection.closeConnection(connection);
	    }
	}
    
	// 퇴근 기록을 등록하는 메소드
	public static void markDeparture(String username) {
	    Connection connection = null;
	    try {
	        // MysqlConnection 클래스를 사용하여 연결 설정
	        connection = MysqlConnection.getConnection();

	        // 현재 날짜 가져오기
	        LocalDateTime currentDate = LocalDateTime.now();
	        LocalDate sqlDate = currentDate.toLocalDate();

	        // 오늘 날짜에 대한 출석 기록이 있는지 확인
	        String checkSql = "SELECT COUNT(*) FROM attendance WHERE user_id = ? AND DATE(attendance_date) = ?";
	        PreparedStatement checkStatement = connection.prepareStatement(checkSql);
	        checkStatement.setString(1, username);
	        checkStatement.setDate(2, java.sql.Date.valueOf(sqlDate));
	        ResultSet resultSet = checkStatement.executeQuery();
	        resultSet.next();
	        int rowCount = resultSet.getInt(1);
	        if (rowCount > 0) {
	            // 퇴근 기록 등록
	            String updateSql = "UPDATE attendance SET attendance_out = ?, attendance_time = TIMEDIFF(attendance_out, attendance_in) WHERE user_id = ? AND DATE(attendance_date) = ?";
	            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
	            updateStatement.setTimestamp(1, Timestamp.valueOf(currentDate));
	            updateStatement.setString(2, username);
	            updateStatement.setDate(3, java.sql.Date.valueOf(sqlDate));
	            int rowsUpdated = updateStatement.executeUpdate();
	            if (rowsUpdated > 0) {
	                System.out.println("퇴근 기록이 성공적으로 추가되었습니다.");
	            } else {
	                System.out.println("퇴근 기록 추가에 실패하였습니다.");
	            }
	        } else {
	            System.out.println("출석 기록이 없습니다.");
	        }
	    } catch (SQLException e) {
	        System.err.println("SQL exception occurred: " + e.getMessage());
	    } finally {
	        // 연결 종료
	        MysqlConnection.closeConnection(connection);
	    }
	}
    
    // 사용자의 클래스 이름을 가져오는 메소드
    public static String getClassNameByUsername(String username) {
        Connection connection = null;
        String className = null;
        try {
            // MysqlConnection 클래스를 사용하여 연결 설정
            connection = MysqlConnection.getConnection();

            // 사용자의 class_name을 가져오는 SQL 쿼리
            String sql = "SELECT class_name FROM user WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            // 결과 처리
            if (resultSet.next()) {
                className = resultSet.getString("class_name");
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 연결 종료
            MysqlConnection.closeConnection(connection);
        }
        return className;
    }
    
 // 출석 상태를 결정하는 메소드
    private static String determineAttendanceStatus(Time attendanceIn, Time attendanceOut, Time classStartTime, Time classEndTime, Time classTime) {
        if (attendanceIn == null) {
            return "결석";
        } else if (attendanceIn.after(new Time(classStartTime.getTime() + 10 * 60 * 1000))) {
            return "지각";
        } else if (attendanceOut == null) {
            return "퇴실 미기록";
        } else if (attendanceOut.after(classEndTime)) {
            long attendanceTimeMillis = attendanceOut.getTime() - attendanceIn.getTime();
            if (attendanceTimeMillis >= classTime.getTime() / 2) {
                return "출석";
            } else {
                return "지각";
            }
        } else {
            return "결석";
        }
    }

    // 출석 상태를 업데이트하는 메소드
    public static void updateAttendanceStatus(String username, String className) {
        Connection connection = null;
        try {
            // MysqlConnection 클래스를 사용하여 연결 설정
            connection = MysqlConnection.getConnection();

            // 오늘 날짜를 가져옴
            LocalDate today = LocalDate.now();

            // 클래스 시작 시간과 클래스 시간 정보 가져오기
            Time classStartTime = null;
            Time classEndTime = null;
            Time classTime = null;
            String sql = "SELECT class_startTime, class_endTime, class_time FROM class WHERE class_name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, className);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                classStartTime = resultSet.getTime("class_startTime");
                classEndTime = resultSet.getTime("class_endTime");
                classTime = resultSet.getTime("class_time");
            } else {
                System.out.println("해당 클래스의 정보를 찾을 수 없습니다.");
                return;
            }

            // 사용자의 오늘 출석 정보 가져오기
            Time attendanceIn = null;
            Time attendanceOut = null;
            sql = "SELECT attendance_in, attendance_out FROM attendance WHERE user_id = ? AND attendance_date = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setDate(2, java.sql.Date.valueOf(today));
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                attendanceIn = resultSet.getTime("attendance_in");
                attendanceOut = resultSet.getTime("attendance_out");
            }
            
            // 유저의 Time 값들을 콘솔에 출력
            System.out.println("유저: " + username);
            System.out.println("오늘 출석 시간: " + attendanceIn);
            System.out.println("오늘 퇴실 시간: " + attendanceOut);
            System.out.println("수업 시작 시간: " + classStartTime);
            System.out.println("수업 시작 시간: " + classEndTime);
            
            System.out.println("수업 시간: " + classTime);
            // 출석 상태 결정
            String attendanceStatus = determineAttendanceStatus(attendanceIn, attendanceOut, classStartTime, classEndTime, classTime);

            // 출석 상태 업데이트
            sql = "UPDATE attendance SET attendance_status = ? WHERE user_id = ? AND attendance_date = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, attendanceStatus);
            statement.setString(2, username);
            statement.setDate(3, java.sql.Date.valueOf(today));
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println(username + "의 출석 상태가 " + attendanceStatus + "로 업데이트되었습니다.");
            } else {
                System.out.println("해당 사용자의 출석 상태 업데이트에 실패하였습니다.");
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred: " + e.getMessage());
        } finally {
            // 연결 종료
            MysqlConnection.closeConnection(connection);
        }
    }
}