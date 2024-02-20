package config;

import java.util.List;

public class Query2 {
    


	public static final String FOR_USER_CLASS_LECTURE =
		    "SELECT attendancesystem.class.class_name " +
		    "FROM attendancesystem.class " +
		     "INNER JOIN attendancesystem.user ON attendancesystem.class.class_num = attendancesystem.user.class_num " +
		    "WHERE attendancesystem.user.user_id = ?";


public static final String FOR_LECTURE_CLASS_LECTURE =
"SELECT lecture_content " +
        "FROM lecture " +
        "INNER JOIN class ON lecture.class_num = class.class_num " +
        "WHERE class.class_name = ? AND lecture.week_number = ?";

}