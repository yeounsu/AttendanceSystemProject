package config;
import java.util.List;

public class Query {
	
	private final List<String> CLASS_NAMES = Class_Name.CLASS_NAMES;
	   static String username = UserInfo.getUserID();
	
    // 공지사항 관련 쿼리////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String SELECT_CLASS = "SELECT * FROM class";
    public static final String SELECT_CLASS_NAMEUP = "SELECT * FROM class ORDER BY class_name";
    public static final String SELECT_TABLE_NAME = "SELECT * FROM table_name";
    
    // 출결 관련 쿼리////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final String Select_ADMIN_ID = "SELECT user_name FROM user WHERE user_id IN (SELECT user_id FROM user WHERE user_idvalue = 1)";
    public static final String FIND_USER_ID = "SELECT user_id FROM user WHERE user_name = ?";
    public static final String INSERT_ANNOUNCEMENT = "INSERT INTO announcement (announcement_title, announcement_content, announcement_writer, announcement_class, announcement_date, user_id) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String DELETE_ANNOUNCEMENT = "DELETE FROM announcement WHERE announcement_num = ?";
    
    public static final String UPDATE_ATTENDANCE = "UPDATE attendance SET attendance_status = ? WHERE user_id = ?";

    public static final String SELECT_ATTENDANCE = "SELECT u.user_name, u.user_major, u.user_id, a.attendance_in, a.attendance_out, a.attendance_status " +
            "FROM user u " +
            "JOIN attendance a ON u.user_id = a.user_id " +
            "WHERE u.class_name = ? AND a.attendance_date = ?";
    
    public static final String SELECT_STU_ATTENDANCE = "SELECT u.user_name, u.user_major, a.attendance_in, a.attendance_out, a.attendance_status " +
    	    "FROM attendance a " +
    	    "JOIN user u ON a.user_id = u.user_id " +
    	    "WHERE a.user_id = ?";
    
    
  //Profile_admin/////////////////////////////////////////////////////////////////////////////////////////////  

    
    public static final String SELECT_CAD1STU_TABLE = "SELECT user_id, user_major, user_name, user_gender "
          + "FROM user " + "WHERE class_name = 'cad1급반' AND user_idValue = 0";

    
    public static final String SELECT_CAD2STU_TABLE = "SELECT user_id, user_major, user_name, user_gender "
          + "FROM user " + "WHERE class_name = 'cad2급반' AND user_idValue = 0";

    
    public static final String SELECT_JAVASTU_TABLE = "SELECT user_id, user_major, user_name, user_gender "
          + "FROM user " + "WHERE class_name = 'java반' AND user_idValue = 0";

    
    public static final String SELECT_COMSTU_TABLE = "SELECT user_id, user_major, user_name, user_gender "
          + "FROM user " + "WHERE class_name = '컴활반' AND user_idValue = 0";

    
 // Profile_user//////////////////////////////////////////////////////////////////////////////////////////////
    public static final String SELECT_STU_TABLE = "SELECT user_id, user_major, user_name, user_gender FROM user WHERE class_name = ? AND user_id != ?";
    
    public static final String SELECT_STUPRO_TABLE = "SELECT user_name, user_major, user_birth, user_gender, user_address, user_id, profile_image "
          + "FROM user " + "WHERE user_id = '" + username + "'";

    public static final String SELECT_MYCLASS_TABLE = "SELECT class_name FROM user WHERE user_id = ?"; 
    public static final String SELECT_STUPASS_TABLE = "SELECT user_password FROM user WHERE user_id = ?";
    
    
    public static final String UPDATE_STUADPAS_TABLE = "UPDATE user SET user_address = ?, user_password = ?" + " WHERE user_id = ?";

    
    public static final String UPDATE_STUIMG_TABLE = "UPDATE user SET profile_image = ?, profile_name = ?, profile_uploadName = ?, profile_extension = ?, profile_size = ?" + " WHERE user_id = ?";
    
    
 // RegisterPage /////////////////////////////////////////////////////////////////////////////////////////////

    
    public static final String SELECT_REGID_TABLE = "SELECT * FROM user WHERE user_id = ?";
    
    
    public static final String INSERT_REINFO_TABLE = "INSERT INTO user (user_id, user_idValue, user_password, user_name, user_birth, user_address, user_gender, user_phoneNum, user_major, class_name, profile_image, profile_name, profile_uploadName, profile_extension, profile_Size) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      
//연수////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      
      public static final String SELECT_CLASS_NAMES = "SELECT class_name FROM class";
  	
  	//유저 일일 출석 통계 쿼리(join 포함)
  	public static final String SELECT_USER_ATTENDANCE = "SELECT * FROM attendance WHERE user_id = ?";   
  	public static final String GET_ATTENDANCE_STATISTICS_BY_JAVA =
              "SELECT " +
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
      

  	//유저 일일 출석 통계 쿼리(join 포함)
      public static final String GET_ATTENDANCE_STATISTICS_BY_CAD1 = 
              "SELECT " +
              "    SUM(CASE WHEN attendance.attendance_status = '출석' THEN 1 ELSE 0 END) AS present_count, " +
              "    SUM(CASE WHEN attendance.attendance_status = '결석' THEN 1 ELSE 0 END) AS absent_count, " +
              "    SUM(CASE WHEN attendance.attendance_status = '지각' THEN 1 ELSE 0 END) AS tardy_count " +
              "FROM " +
              "    user " +
              "    INNER JOIN class ON user.class_num = class.class_num " +
              "    LEFT JOIN attendance ON user.user_id = attendance.user_id " +
              "WHERE " +
              "    class.class_name = ?"+
              "    AND DATE(attendance_date) = CURDATE()";

  	//유저 일일 출석 통계 쿼리(join 포함)
      public static final String GET_ATTENDANCE_STATISTICS_BY_CAD2 = 
              "SELECT " +
              "    SUM(CASE WHEN attendance.attendance_status = '출석' THEN 1 ELSE 0 END) AS present_count, " +
              "    SUM(CASE WHEN attendance.attendance_status = '결석' THEN 1 ELSE 0 END) AS absent_count, " +
              "    SUM(CASE WHEN attendance.attendance_status = '지각' THEN 1 ELSE 0 END) AS tardy_count " +
              "FROM " +
              "    user " +
              "    INNER JOIN class ON user.class_num = class.class_num " +
              "    LEFT JOIN attendance ON user.user_id = attendance.user_id " +
              "WHERE " +
              "    class.class_name = ?"+
              "    AND DATE(attendance_date) = CURDATE()";

  	//유저 일일 출석 통계 쿼리(join 포함)
      public static final String GET_ATTENDANCE_STATISTICS_BY_COM = 
              "SELECT " +
              		  "    SUM(CASE WHEN attendance.attendance_status = '출석' THEN 1 ELSE 0 END) AS present_count, " +
                        "    SUM(CASE WHEN attendance.attendance_status = '결석' THEN 1 ELSE 0 END) AS absent_count, " +
                        "    SUM(CASE WHEN attendance.attendance_status = '지각' THEN 1 ELSE 0 END) AS tardy_count " +
              "FROM " +
              "    user " +
              "    INNER JOIN class ON user.class_num = class.class_num " +
              "    LEFT JOIN attendance ON user.user_id = attendance.user_id " +
              "WHERE " +
              "    class.class_name = ?"+
              "    AND DATE(attendance_date) = CURDATE()";

  	//유저 월 출석 통계 쿼리(join 포함)
      public static final String GET_ATTENDANCE_STATISTICS_BY_MONTH = 
              "SELECT " +
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

  	//유저 총 출석 통계 쿼리(join 포함)
      public static final String GET_ATTENDANCE_STATISTICS_BY_all =
      	    "SELECT " +
      	    		  "    SUM(CASE WHEN attendance.attendance_status = '출석' THEN 1 ELSE 0 END) AS present_count, " +
      	              "    SUM(CASE WHEN attendance.attendance_status = '결석' THEN 1 ELSE 0 END) AS absent_count, " +
      	              "    SUM(CASE WHEN attendance.attendance_status = '지각' THEN 1 ELSE 0 END) AS tardy_count " +
      	    "FROM " +
      	    "    user " +
      	    "    INNER JOIN class ON user.class_num = class.class_num " +
      	    "    LEFT JOIN attendance ON user.user_id = attendance.user_id " +
      	    "WHERE " +
      	    "    class.class_name = ?" +
      	    "    AND attendance.attendance_date IS NOT NULL";
      
   // Query to fetch overall attendance counts for a user
      public static final String FETCH_OVERALL_ATTENDANCE_DATA = "SELECT SUM(CASE WHEN attendance_status = '출석' THEN 1 ELSE 0 END) as attendance_count, " +
              "SUM(CASE WHEN attendance_status = '결석' THEN 1 ELSE 0 END) as absence_count, " +
              "SUM(CASE WHEN attendance_status = '지각' THEN 1 ELSE 0 END) as tardiness_count " +
              "FROM attendance WHERE user_id = ?";

      public static final String FETCH_THIS_MONTH_ATTENDANCE_DATA = "SELECT SUM(CASE WHEN attendance_status = '출석' THEN 1 ELSE 0 END) as attendance_count, " +
              "SUM(CASE WHEN attendance_status = '결석' THEN 1 ELSE 0 END) as absence_count, " +
              "SUM(CASE WHEN attendance_status = '지각' THEN 1 ELSE 0 END) as tardiness_count " +
              "FROM attendance WHERE user_id = ? AND attendance_date >= ? AND attendance_date <= ?";
      
//counseling
      public static final String SELECT_COUNSECAD1_TABLE =  "SELECT c.counseling_num, c.counseling_date, c.counseling_title, c.user_id, u.user_name " +
      		"FROM counseling c " +
              "JOIN user u ON c.user_id = u.user_id " +
              "WHERE counseling_class='cad1급반'"+
              "ORDER BY c.counseling_num DESC";
      public static final String SELECT_COUNSECAD2_TABLE =  "SELECT c.counseling_num, c.counseling_date, c.counseling_title, c.user_id, u.user_name " +
      		"FROM counseling c " +
      		"JOIN user u ON c.user_id = u.user_id " +
      		 "WHERE counseling_class='cad2급반'"+
      		 "ORDER BY c.counseling_num DESC";
      public static final String SELECT_COUNSEJAVA_TABLE =  "SELECT c.counseling_num, c.counseling_date, c.counseling_title, c.user_id, u.user_name " +
      		"FROM counseling c " +
      		"JOIN user u ON c.user_id = u.user_id " +
      		"WHERE counseling_class='java반'"+
      		 "ORDER BY c.counseling_num DESC";
      		
      public static final String SELECT_COUNSECOMPUTER_TABLE =  "SELECT c.counseling_num, c.counseling_date, c.counseling_title, c.user_id, u.user_name " +
      		"FROM counseling c " +
      		"JOIN user u ON c.user_id = u.user_id " +
      		"WHERE counseling_class='컴활반'"+
      		 "ORDER BY c.counseling_num DESC";
      		
      public static final String SELECT_CLEANDATE_TABLE = "SELECT class_startDate,class_endDate FROM attendancesystem.class WHERE class_name = ?";
      
      public static final String SELECT_CLEANWEEK_TABLE = "SELECT user.user_id, user.user_name FROM user INNER JOIN class ON user.class_num = class.class_num WHERE class.class_name = ?";		
     
      public static final String SELECT_USERCLEAN_TABLE = "SELECT user.class_name FROM user WHERE user.user_id";
      
      
//      public static final String FOR_USER_CLASS_LECTURE =
//    		    "SELECT class.class_name " + 
//    		    "FROM class " +
//    		    "INNER JOIN user ON class.class_num = user.class_num " +
//    		    "WHERE user.user_id = ?";
//
//    		public static final String FOR_LECTURE_CLASS_LECTURE =
//    		"SELECT lecture_content " +
//    		        "FROM lecture " +
//    		        "INNER JOIN class ON lecture.class_num = class.class_num " +
//    		        "WHERE class.class_name = ? AND lecture.week_number = ?";
//
//    		
//
//    		
//      
      
 }
      
      

