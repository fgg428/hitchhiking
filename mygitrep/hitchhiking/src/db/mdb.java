package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class mdb {
	public static Connection getconntion()
	{
		String dbURL = "jdbc:mysql://localhost:3306/hitch?characterEncoding=utf8";
		String userName = "root";      //Ĭ���û���
		String userPwd = "123456";  //����
		Connection dbConn = null;
		
	    try 
		{
		   Class.forName("com.mysql.jdbc.Driver");  
		   dbConn = DriverManager.getConnection(dbURL,userName,userPwd);
		}catch (Exception e){
		      e.printStackTrace();
		}
		return dbConn;
	}
}
