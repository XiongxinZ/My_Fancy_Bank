
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AccountDao {


    public static int insertAccount(Account account) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            String c_id = account.getCustomer().getId();
            String a_id = account.getId();
            String c_account = account.getAccountType();
            double c_Balance_USD = account.getAmount("USD");
            double c_Balance_CNY = account.getAmount("CNY");
            double c_Balance_JPY = account.getAmount("JPY");

            String sql = "insert into account(c_id, a_id, c_account, c_Balance_USD, c_Balance_CNY, c_Balance_JPY) "
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

    public static int updateAccountMoney(Account account, String curr) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

            StringBuffer sql = new StringBuffer("update account set");

            String a_id = account.getId();
            double val = account.getAmount(curr);
            sql.append(" c_Balance_").append(curr).append(" = ").append(val);

            if(!"".equals(a_id) && a_id != null) {
                sql.append(" where a_id = '").append(a_id).append("'");
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

    public static int updateAccountMoney(String id, String accountType, double val, String curr) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int flag = 0;

        try {
            conn = JDBCUtil.getConnection();

//            String c_id = id;
//            String type = accountType;
            String select = "select * from account where c_id = ? and c_account = ?";

            ps = conn.prepareStatement(select);
            ps.setString(1, id);
            ps.setString(2, accountType);

            rs = ps.executeQuery();
            double amount = 0;

            while(rs.next()) {
                amount = Double.parseDouble(rs.getString("c_Balance_"+curr));
            }


            String sql = "update account set c_Balance_" + curr + " = " + (amount + val) +
                    " where c_id = '" + id + "' and c_account = '" + accountType + "'";
            ps = conn.prepareStatement(String.valueOf(sql));
            flag = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return flag;
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


    public static Account selectAccount(Customer customer, String type) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Account account = null;
//        if (!customer.hasAccount(type)){
//            return null;
//        }

        try {
            conn = JDBCUtil.getConnection();
            String id = customer.getId();
            String sql = "select * from account where c_id = ? and c_account = ?";

            System.out.println("selectCustomerWithCid(Customer customer)" + sql);

            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, type);

            rs = ps.executeQuery();

            while(rs.next()) {
                switch (type) {
                    case "Saving" -> account = new SavingAccount(customer);
                    case "Checking" -> account = new CheckingAccount(customer);
                    case "Security" -> account = new SecurityAccount(customer);
                    case "Loan" -> account = new LoanAccount(customer);
                }

                account.setId(rs.getString("a_id"));

                account.setAmount(Double.parseDouble(rs.getString("c_Balance_USD")),"USD");
                account.setAmount(Double.parseDouble(rs.getString("c_Balance_JPY")),"CNY");
                account.setAmount(Double.parseDouble(rs.getString("c_Balance_JPY")),"JPY");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return account;
    }



    public static HashMap<String, Account> selectAccountList(Customer customer){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        HashMap<String,Account> list = new HashMap<>();

        try {
            conn = JDBCUtil.getConnection();

            for (String s : SystemDatabase.accType) {
                if (selectAccount(customer, s) != null){
                    list.put(s, selectAccount(customer, s));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(conn, ps, rs);
        }
        return list;
    }
}
