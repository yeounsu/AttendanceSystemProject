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

    public class User_lecture_Mgr {
        private Connection connection;
        private String loggedInUserID;

        public User_lecture_Mgr(Connection connection, String loggedInUserID) {
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
                String query = Query2.FOR_USER_CLASS_LECTURE;
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