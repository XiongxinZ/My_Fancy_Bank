
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    @SuppressWarnings("finally")
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

            String sql = "insert into customer(c_ID, c_Name, c_PSWD, a_Saving, a_Checking, a_Loan, a_Security) "
                    + "values(?,?,?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1, c_id);
            ps.setString(2, c_name);
            ps.setString(3, c_pswd);
            ps.setString(4, Boolean.toString(customer.hasAccount("Saving")));
            ps.setString(5, Boolean.toString(customer.hasAccount("Checking")));
            ps.setString(6, Boolean.toString(customer.hasAccount("Loan")));
            ps.setString(7, Boolean.toString(customer.hasAccount("Security")));
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
            return flag;
        }

    }

    @SuppressWarnings("finally")
    public int updateCustomer(Customer customer) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            String c_id = customer.getId();
            String c_pswd = customer.getPassword();

            StringBuffer sql = new StringBuffer("update customer set");

            if(!("".equals(c_pswd)) && c_pswd != null) {
                sql.append(" c_pswd = '" + c_pswd + "'");
            }
//
//			if(!("".equals(c_status)) && c_status != null) {
//				sql.append(" , c_status = '" + c_status + "'");
//			}

            if(!"".equals(c_id) && c_id != null) {
                sql.append(" where c_id = '" + c_id + "'");
            }

            ps = conn.prepareStatement(String.valueOf(sql));
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
            return flag;
        }
    }

    public int updateCustomerPSW(String id, String pwd) {

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
            return flag;
        }
    }


    @SuppressWarnings("finally")
    public int updateCustomerPSW(Customer customer) {
        return updateCustomerPSW(customer.getId(), customer.getPassword());
    }

    public int delCustomer(String id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();


            String sql = "delete from customer where c_ID = ?";

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

    @SuppressWarnings("finally")
    public int delCustomer(Customer customer) {

        return delCustomer(customer.getId());
    }

    @SuppressWarnings("finally")
    public Customer selectCustomer(Customer customer) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Customer user = null;

        try {
            conn = JDBCUtil.getConnection();

            String c_id = customer.getId();
            String c_pswd = customer.getPassword();

            String sql = "select * from customer where c_ID = ? and c_PSWD = ?";

            System.out.println("selectCustomer(Customer customer)" + sql);

            ps = conn.prepareStatement(sql);
            ps.setString(1, c_id);
            ps.setString(2, c_pswd);

            rs = ps.executeQuery();

            while(rs.next()) {
                user = new Customer();

                user.setId(rs.getString("c_ID"));
                user.setName(rs.getString("c_Name"));
                user.setPassword(rs.getString("c_PSWD"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
            return user;
        }
    }

    @SuppressWarnings("finally")
    public Customer selectCustomerWithCID(String id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Customer user = null;

        try {
            conn = JDBCUtil.getConnection();

            String sql = "select * from customer where c_ID = ?";

            System.out.println("selectCustomerWithCID(Customer customer)" + sql);

            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();

            while(rs.next()) {
                user = new Customer();

                user.setId(rs.getString("c_ID"));
                user.setName(rs.getString("c_Name"));
                user.setPassword(rs.getString("c_PSWD"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
            return user;
        }
    }

    @SuppressWarnings("finally")
    public List<Customer> selectCustomerList(Customer customer){
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
                user.setId(rs.getString("c_ID"));
                user.setName(rs.getString("c_Name"));
                user.setPassword(rs.getString("c_PSWD"));

                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
            return list;
        }
    }
}
