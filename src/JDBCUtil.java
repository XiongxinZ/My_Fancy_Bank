import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 *
 * JDBC Util
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
	}
	
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
	 * get database connection
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return getInstance().conn;
	}

	public static JDBCUtil getInstance() throws SQLException {
		if (instance == null){
			instance = new JDBCUtil();
		}
		return instance;
	}
	
	/**
	 * release database resources, do not close connection.
	 * @param stm
	 * @param rs
	 */
	public static void closeResource(Statement stm, ResultSet rs) {
		closeResultSet(rs);
		closeStatement(stm);
	}

	public static void closeResource(Connection coon, Statement stm, ResultSet rs) {
		closeResultSet(rs);
		closeStatement(stm);
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
