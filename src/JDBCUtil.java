import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 
 * @author NaiveKyo
 * JDBC 工具类
 */
public class JDBCUtil {
	
	static final String DRIVERCLASS;
	static final String URL;
	static final String USERNAME;
	static final String PASSWORD;

	private Connection conn = null;
	private static JDBCUtil instance = null;
	
	static {
		 Properties properties = new Properties();
		try {
			properties.load(new FileInputStream("jdbc.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}


		DRIVERCLASS = properties.getProperty("driverClass");
		URL = properties.getProperty("url");
		USERNAME = properties.getProperty("username");
		PASSWORD = properties.getProperty("password");
//		DRIVERCLASS = ConfigUtil.getConfig("driverClass");
//		URL = ConfigUtil.getConfig("url");
//		USERNAME = ConfigUtil.getConfig("username");
//		PASSWORD = ConfigUtil.getConfig("password");
	}

	public static void main(String[] args) {

	}
	// test
//	public static void main(String[] args) {
//		System.out.println(DRIVERCLASS + "<br>" + URL + "<br>" + USERNAME + "<br>" + PASSWORD);
//	}
	
	static {
		try {
			Class.forName(DRIVERCLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private JDBCUtil() throws SQLException {
		conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	/**
	 * 获取数据库连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		// return DriverManager.getConnection(URL, USERNAME, PASSWORD);
		return getInstance().conn;
	}

	public static JDBCUtil getInstance() throws SQLException {
		if (instance == null){
			instance = new JDBCUtil();
		}
		return instance;
	}

	public static Connection getConn() throws SQLException {
		return getInstance().conn;
	}
	
	/**
	 * 释放数据库资源
	 * @param conn
	 * @param stm
	 * @param rs
	 */
	public static void closeResource(Connection conn, Statement stm, ResultSet rs) {
		closeResultSet(rs);
		closeStatement(stm);
//		closeConnection(conn);
	}
	
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeStatement(Statement stm) {
		if(stm != null) {
			try {
				stm.close();
				stm = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void closeConnection(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
