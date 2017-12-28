package com.iu.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class DBConnector {

	public static Connection getConnect() throws Exception {
		 //1. login
	    String user = "user01";
	    String password = "user01";
	    String url = "jdbc:oracle:thin:@192.168.20.6:1521:xe";
	    String driver = "oracle.jdbc.driver.OracleDriver";
	    //2. driver생성
	    Class.forName(driver);
	    //3. connection
	    Connection con = DriverManager.getConnection(url, user, password);
	    
	    return con;
	}
	
	public static void disConnect(ResultSet rs, PreparedStatement st, Connection con) throws Exception {
		rs.close();
		st.close();
		con.close();
	}
	
	public static void disConnect(PreparedStatement st, Connection con) throws Exception {
		st.close();
		con.close();
	}
}
