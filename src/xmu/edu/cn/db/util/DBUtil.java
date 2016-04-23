package xmu.edu.cn.db.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
	public static void closeConnection(Connection connection){
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection openConnection(){
		Properties prop = new Properties();
		String driver = null;
		String url = null;
		String username = null;
		String password = null;
		
		try {
			prop.load(DBUtil.class.getClassLoader().getResourceAsStream("DBConfig.properties"));
			driver = prop.getProperty("driver");
			Class.forName(driver);
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			return DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
}
