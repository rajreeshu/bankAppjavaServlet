package databaseInfo;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseInfo {
	public static Connection con;
	
	public DatabaseInfo() {
		String url= "jdbc:mysql://localhost:3306/bankingApp";
		String user = "root";
		String pwd = "Dhoni$1234";
		try {
			  Class.forName("com.mysql.cj.jdbc.Driver");
	            con = DriverManager.getConnection(url, user, pwd);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
