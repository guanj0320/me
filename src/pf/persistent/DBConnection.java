package pf.persistent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBConnection {
	private static final ThreadLocal threadLocal = new ThreadLocal();
	
	private DBConnection(){
		
	}
	
	public static Connection getConnection() {
		 Connection conn = (Connection)threadLocal.get();
		 if(conn == null) {
			 try {
				 conn = DriverManager.getConnection("proxool.basedb");
			 } catch (SQLException sqle) {
				Log log = LogFactory.getLog("FILE");
				log.error("Problem getting connection", sqle);
			 } 
			 threadLocal.set(conn);
		 }
		 return conn;
	 }
	
	public static Connection getConnection1() {
		 Connection conn = (Connection)threadLocal.get();
		 if(conn == null) {
			 try {
				 conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","test","test");
			 } catch (SQLException sqle) {
				Log log = LogFactory.getLog("FILE");
				log.error("Problem getting connection", sqle);
			 } 
			 threadLocal.set(conn);
		 }
		 return conn;
	 }
	 
	public static void closeConnection() {
		 Connection conn = (Connection)threadLocal.get();
		 threadLocal.set(null);
		 
		 if(conn != null) {
			 try {
				 conn.close();
			 } catch (SQLException sqle) {
				Log log = LogFactory.getLog("FILE");
				log.error("Problem closing connection", sqle);
			 }
		 }
	 }
}
