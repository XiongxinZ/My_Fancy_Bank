
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountDao {


    public static int insertSaving(SavingAccount savingAccount) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            String c_id = savingAccount.getCustomer().getId();
            String a_id = savingAccount.getId();
            String c_account = savingAccount.getAccountType();
            double c_Balance_USD = savingAccount.getAmount("USD");
            double c_Balance_CNY = savingAccount.getAmount("CNY");
            double c_Balance_JPY = savingAccount.getAmount("JPY");

            String sql = "insert into saving(c_id, a_id, c_account, c_Balance_USD, c_Balance_CNY, c_Balance_JPY) "
                    + "values(?,?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1, c_id);
            ps.setString(2, a_id);
            ps.setString(3, c_account);
            ps.setDouble(4, c_Balance_USD);
            ps.setDouble(5, c_Balance_CNY);
            ps.setDouble(6, c_Balance_JPY);
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return flag;
    }

    public static int insertChecking(CheckingAccount checkingAccount) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            String c_id = checkingAccount.getCustomer().getId();
            String a_id = checkingAccount.getId();
            String c_account = checkingAccount.getAccountType();
            double c_Balance_USD = checkingAccount.getAmount("USD");
            double c_Balance_CNY = checkingAccount.getAmount("CNY");
            double c_Balance_JPY = checkingAccount.getAmount("JPY");

            String sql = "insert into checking(c_id, a_id, c_account, c_Balance_USD, c_Balance_CNY, c_Balance_JPY) "
                    + "values(?,?,?,?,?,?)";

            ps = conn.prepareStatement(sql);
            ps.setString(1, c_id);
            ps.setString(2, a_id);
            ps.setString(3, c_account);
            ps.setDouble(4, c_Balance_USD);
            ps.setDouble(5, c_Balance_CNY);
            ps.setDouble(6, c_Balance_JPY);
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return flag;
    }


    public static int updateSavingMoney(SavingAccount savingAccount, String curr) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            StringBuffer sql = new StringBuffer("update saving set");

            String c_id = savingAccount.getCustomer().getId();
            double val = savingAccount.getAmount(curr);
            sql.append(" c_Balance_").append(curr).append(" =").append(val);

            if(!"".equals(c_id) && c_id != null) {
                sql.append(" where c_id = '").append(c_id).append("'");
            }

            ps = conn.prepareStatement(String.valueOf(sql));
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return flag;
    }

    public static int updateCheckingMoney(CheckingAccount checkingAccount, String curr) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            StringBuffer sql = new StringBuffer("update saving set");

            String c_id = checkingAccount.getCustomer().getId();
            double val = checkingAccount.getAmount(curr);
            sql.append(" c_Balance_").append(curr).append(" = ").append(val);

            if(!"".equals(c_id) && c_id != null) {
                sql.append(" where c_id = '").append(c_id).append("'");
            }

            ps = conn.prepareStatement(String.valueOf(sql));
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return flag;
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
        }
        return flag;
    }



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


    public int delCustomer(Customer customer) {

        return delCustomer(customer.getId());
    }


    public Customer selectCustomer(Customer customer) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Customer user = null;

        try {
            conn = JDBCUtil.getConnection();

            String c_id = customer.getId();
            String c_pswd = customer.getPassword();

            String sql = "select * from customer where c_id = ? and c_PSWD = ?";

            System.out.println("selectCustomer(Customer customer)" + sql);

            ps = conn.prepareStatement(sql);
            ps.setString(1, c_id);
            ps.setString(2, c_pswd);

            rs = ps.executeQuery();

            while(rs.next()) {
                user = new Customer();

                user.setId(rs.getString("c_id"));
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
//
    public static Account selectLoanWithCid(Customer customer) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SavingAccount savingAccount = null;

        try {
            conn = JDBCUtil.getConnection();
            String id = customer.getId();
            String sql = "select * from loan where c_id = ?";

            System.out.println("selectCustomerWithCid(Customer customer)" + sql);

            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();

            while(rs.next()) {
                savingAccount = new SavingAccount(customer);
//
//                savingAccount.setAmount(Double.parseDouble(rs.getString("c_Balance_USD")),"USD");
//                savingAccount.setAmount(Double.parseDouble(rs.getString("c_Balance_JPY")),"CNY");
//                savingAccount.setAmount(Double.parseDouble(rs.getString("c_Balance_JPY")),"JPY");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return savingAccount;
    }

//
    public static Account selectSecurityWithCid(Customer customer) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SavingAccount savingAccount = null;

        try {
            conn = JDBCUtil.getConnection();
            String id = customer.getId();
            String sql = "select * from security where c_id = ?";

            System.out.println("selectCustomerWithCid(Customer customer)" + sql);

            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();

            while(rs.next()) {
                savingAccount = new SavingAccount(customer);

                savingAccount.setAmount(Double.parseDouble(rs.getString("c_Balance_USD")),"USD");
                savingAccount.setAmount(Double.parseDouble(rs.getString("c_Balance_JPY")),"CNY");
                savingAccount.setAmount(Double.parseDouble(rs.getString("c_Balance_JPY")),"JPY");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return savingAccount;
    }

//
    public static Account selectCheckingWithCid(Customer customer) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CheckingAccount checkingAccount = null;

        try {
            conn = JDBCUtil.getConnection();
            String id = customer.getId();
            String sql = "select * from checking where c_id = ?";

            System.out.println("selectCustomerWithCid(Customer customer)" + sql);

            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();

            while(rs.next()) {
                checkingAccount = new CheckingAccount(customer);

                checkingAccount.setAmount(Double.parseDouble(rs.getString("c_Balance_USD")),"USD");
                checkingAccount.setAmount(Double.parseDouble(rs.getString("c_Balance_JPY")),"CNY");
                checkingAccount.setAmount(Double.parseDouble(rs.getString("c_Balance_JPY")),"JPY");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return checkingAccount;
    }


    public static Account selectSavingWithCid(Customer customer) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        SavingAccount savingAccount = null;

        try {
            conn = JDBCUtil.getConnection();
            String id = customer.getId();
            String sql = "select * from saving where c_id = ?";

            System.out.println("selectCustomerWithCid(Customer customer)" + sql);

            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            rs = ps.executeQuery();

            while(rs.next()) {
                savingAccount = new SavingAccount(customer);

                savingAccount.setAmount(Double.parseDouble(rs.getString("c_Balance_USD")),"USD");
                savingAccount.setAmount(Double.parseDouble(rs.getString("c_Balance_JPY")),"CNY");
                savingAccount.setAmount(Double.parseDouble(rs.getString("c_Balance_JPY")),"JPY");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return savingAccount;
    }



    public static HashMap<String, Account> selectAccountList(Customer customer){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        HashMap<String,Account> list = new HashMap<>();

        try {
            conn = JDBCUtil.getConnection();

            if (selectCheckingWithCid(customer) != null){
                list.put("Checking", selectCheckingWithCid(customer));
            }

            if (selectSavingWithCid(customer) != null){
                list.put("Saving", selectSavingWithCid(customer));
            }

            if (selectSecurityWithCid(customer) != null){
                list.put("Security", selectSecurityWithCid(customer));
            }

            if (selectLoanWithCid(customer) != null){
                list.put("Loan", selectLoanWithCid(customer));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return list;
    }
}
