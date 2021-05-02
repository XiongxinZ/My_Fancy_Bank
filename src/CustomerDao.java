
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
	

	public static int insertCustomer(Customer customer) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int flag = 0;
		
		try {
			conn = JDBCUtil.getConnection();
			
			String c_id = customer.getId();
			String c_name = customer.getName();
			String c_pswd = customer.getPassword();
			String c_email = customer.getEmail();


			String sql = "insert into customer(c_id, c_name, c_pswd,c_email, a_saving, a_checking, a_loan, a_security) "
					+ "values(?,?,?,?,?,?,?,?)";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, c_id);
			ps.setString(2, c_name);
			ps.setString(3, c_pswd);
			ps.setString(4, c_email);
			int i = 5;
			for (String s : SystemDatabase.accType) {
				ps.setString(i,
						customer.hasAccount(s)?customer.getAccount(s).getId():null);
				i++;
			}
			flag = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeResource(conn, ps, rs);
		}
		return flag;
	}


	public static int updateCustomerPSW(String id, String pwd) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int flag = 0;

		try {
			conn = JDBCUtil.getConnection();

			String sql = "update customer set c_pswd = ? where c_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, pwd);
			ps.setString(2, id);

			flag = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeResource(conn, ps, rs);

		}
		return flag;
	}


	public static int updateCustomerPSW(Customer customer) {
		return updateCustomerPSW(customer.getId(), customer.getPassword());
	}

	public static int delCustomer(String id) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int flag = 0;

		try {
			conn = JDBCUtil.getConnection();


			String sql = "delete from customer where c_id = ?";

			ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			System.out.println("delCustomer(Customer customer)" + ps.toString());
			flag = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeResource(conn, ps, rs);
			return flag;
		}
	}

	public static int delCustomer(Customer customer) {

		return delCustomer(customer.getId());
	}

	public static String getCustomerName(String id){

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String userName = "-";
		if (id == null){
			return userName;
		}

		try {
			conn = JDBCUtil.getConnection();

			String sql = "select * from customer where c_id = ?";

			System.out.println("selectCustomer(Customer customer)" + sql);

			ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			rs = ps.executeQuery();

			while(rs.next()){
				userName = rs.getString("c_name");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeResource(conn, ps, rs);
		}
		return userName;
	}

	public static Customer selectCustomer(String email, String pswd) {

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Customer user = null;

		try {
			conn = JDBCUtil.getConnection();

			String sql = "select * from customer where c_email = ? and c_PSWD = ?";

			System.out.println("selectCustomer(Customer customer)" + sql);

			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, pswd);

			rs = ps.executeQuery();

			while(rs.next()) {
				user = new Customer(pswd, email);

				user.setId(rs.getString("c_id"));
				user.setName(rs.getString("c_Name"));
				user.setPassword(rs.getString("c_PSWD"));
				user.setEmail(rs.getString("c_email"));
				user.setAccountList(AccountDao.selectAccountList(user));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeResource(conn, ps, rs);
		}
		return user;
	}

	public static Customer selectCustomer(Customer customer) {
		return selectCustomer(customer.getEmail(), customer.getPassword());
	}


	public static Customer selectCustomerWithCid(String id) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Customer user = null;
		
		try {
			conn = JDBCUtil.getConnection();
			
			String sql = "select * from customer where c_id = ?";
			
			System.out.println("selectCustomerWithCid(Customer customer)" + sql);
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				user = new Customer();
				
				user.setId(rs.getString("c_id"));
				user.setName(rs.getString("c_Name"));
				user.setPassword(rs.getString("c_PSWD"));
				user.setEmail(rs.getString("c_email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeResource(conn, ps, rs);
			return user;
		}
	}
	

	public static List<Customer> selectCustomerList(Customer customer){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<Customer> list = new ArrayList<>();
		
		try {
			conn = JDBCUtil.getConnection();
			
			String c_id = customer.getId();
			String c_name = customer.getName();
			String c_pswd = customer.getPassword();
			
			StringBuffer sql = new StringBuffer("select * from customer where 1 = 1");
			
			if(!"".equals(c_id) && c_id != null) {
				sql.append(" and c_id = '" + c_id + "'");
			}
			
			if(!("".equals(c_name)) && c_name != null) {
				sql.append(" and c_name = '" + c_name + "'");
			}
			
			if(!("".equals(c_pswd)) && c_pswd != null) {
				sql.append(" and c_pswd = '" + c_pswd + "'");
			}

			ps = conn.prepareStatement(String.valueOf(sql));
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Customer user = new Customer();
				user.setId(rs.getString("c_id"));
				user.setName(rs.getString("c_Name"));
				user.setPassword(rs.getString("c_PSWD"));
				user.setEmail(rs.getString("c_email"));
				
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeResource(conn, ps, rs);
		}
		return list;
	}
}
