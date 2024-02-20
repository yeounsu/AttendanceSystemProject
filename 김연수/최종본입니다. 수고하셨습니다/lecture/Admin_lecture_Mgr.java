package lecture;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;



    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;

import config.Query2;

    public class Admin_lecture_Mgr {
        private Connection connection;
        private String loggedInUserID;

        public Admin_lecture_Mgr(Connection connection, String loggedInUserID) {
            this.connection = connection;
            this.loggedInUserID = loggedInUserID;
        }

        // Method to fetch class content for the specified class name
        public String fetchClassContent(String className) throws SQLException {
            String classContent = "";
            try {
                String query = "SELECT class_content FROM class WHERE class_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, className);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    classContent = rs.getString("class_content");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
            return classContent;
        }

        String retrieveClassNameForStudent(String userId) {
            String className = null;
            try {
                String query = 		    "SELECT attendancesystem.class.class_name " +
            		    "FROM attendancesystem.class " +
           		     "INNER JOIN attendancesystem.user ON attendancesystem.class.class_num = attendancesystem.user.class_num " +
           		    "WHERE attendancesystem.user.user_id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, userId);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    className = rs.getString("class_name");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return className;
        }
        public void updateClassContent(String className, String newContent) throws SQLException {
            String sql = "UPDATE class SET class_content = ? WHERE class_name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, newContent);
                preparedStatement.setString(2, className);
                preparedStatement.executeUpdate();
            }
            }
        
        public Date getClassStartDate(String className) throws SQLException {
            Date startDate = null;
            try {
                String query = "SELECT class_startDate FROM class WHERE class_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, className);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    startDate = rs.getDate("class_startDate");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
            return startDate;
        }
        public Date getClassEndDate(String className) throws SQLException {
            Date endDate = null;
            try {
                String query = "SELECT class_endDate FROM class WHERE class_name = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, className);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    endDate = rs.getDate("class_endDate");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
            return endDate;
        }
        public void insertLecture(String lectureTitle, String lectureContent, String className) throws SQLException {
            try {
                
                String classQuery = "SELECT class_num FROM class WHERE class_name = ?";
                PreparedStatement classStatement = connection.prepareStatement(classQuery);
                classStatement.setString(1, className);
                ResultSet classResult = classStatement.executeQuery();

                int classNum;
                if (classResult.next()) {
                    classNum = classResult.getInt("class_num");
                } else {
                    throw new SQLException("Class not found for className: " + className);
                }

              
                String insertQuery = "INSERT INTO lecture (lecture_title, lecture_content, class_num) VALUES (?, ?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, lectureTitle);
                insertStatement.setString(2, lectureContent);
                insertStatement.setInt(3, classNum);
                insertStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
    
        public void updateLectureContent(String lectureTitle, String newContent, String className) throws SQLException {
            try {
                
                String classQuery = "SELECT class_num FROM class WHERE class_name = ?";
                PreparedStatement classStatement = connection.prepareStatement(classQuery);
                classStatement.setString(1, className);
                ResultSet classResult = classStatement.executeQuery();

                int classNum;
                if (classResult.next()) {
                    classNum = classResult.getInt("class_num");
                } else {
                    throw new SQLException("Class not found for className: " + className);
                }

              
                String checkExistingQuery = "SELECT * FROM lecture WHERE lecture_title = ? AND class_num = ?";
                PreparedStatement checkExistingStatement = connection.prepareStatement(checkExistingQuery);
                checkExistingStatement.setString(1, lectureTitle);
                checkExistingStatement.setInt(2, classNum);
                ResultSet existingResult = checkExistingStatement.executeQuery();

                if (existingResult.next()) {
                   
                    String updateQuery = "UPDATE lecture SET lecture_content = ? WHERE lecture_title = ? AND class_num = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setString(1, newContent);
                    updateStatement.setString(2, lectureTitle); // Ensure lectureTitle matches database format
                    updateStatement.setInt(3, classNum);
                    updateStatement.executeUpdate();
                } else {
                    throw new SQLException("Lecture not found for lectureTitle: " + lectureTitle + " in class: " + classNum);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
        
        public List<String[]> fetchLectureContentForUser(String loggedInUserID) throws SQLException {
            List<String[]> lectureContentList = new ArrayList<>();
            try {
                String classQuery = "SELECT class_num FROM user WHERE user_id = ?";
                PreparedStatement classStatement = connection.prepareStatement(classQuery);
                classStatement.setString(1, loggedInUserID);
                ResultSet classResult = classStatement.executeQuery();

                if (classResult.next()) {
                    int classNum = classResult.getInt("class_num");
                    String lectureQuery = "SELECT lecture_title, lecture_content FROM lecture WHERE class_num = ?";
                    PreparedStatement lectureStatement = connection.prepareStatement(lectureQuery);
                    lectureStatement.setInt(1, classNum);
                    ResultSet lectureResult = lectureStatement.executeQuery();

                    while (lectureResult.next()) {
                        String lectureTitle = lectureResult.getString("lecture_title");
                        String lectureContent = lectureResult.getString("lecture_content");
                        String[] content = {lectureTitle, lectureContent};
                        lectureContentList.add(content);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
            return lectureContentList;
        }
    }